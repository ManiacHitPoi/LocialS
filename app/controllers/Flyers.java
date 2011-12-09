package controllers;

import java.util.Random;
import java.util.List;
import java.util.Iterator;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;

import play.Logger;
import play.db.jpa.*;

import models.Flyer;
import models.Person;
import play.libs.MimeTypes;
import play.mvc.Controller;

public class Flyers extends Contents {

    public static void create(Long id, String title, Integer age, Boolean sex,
            Integer area, String eventDate, File image) throws FileNotFoundException {
		Logger.debug("Flyers#create");
		Logger.debug("id: " + id + ", title: " + title);
		Logger.debug("age: " + age + ", image: " + image);
		Logger.debug("area: " + area + ", sex: " + sex);
		Logger.debug("eventDate", eventDate);

        Person person = Person.findById(id);
        Blob photo = new Blob();

        photo.set(new FileInputStream(image.getAbsolutePath()),
                MimeTypes.getContentType(image.getName()));
        Flyer content = new Flyer(person, title, photo, area, sex, age);
        content.save();
		Logger.debug("return content: " + content);
        render(content);
    }

    public static void random() {
      List<Flyer> contents = Flyer.findAll();
      int randInt = new Random().nextInt(contents.size());
      Flyer content = contents.get(randInt);
      FileInputStream is = convert(content, 280, 400);
      Logger.debug("Flyers#random return: " + content);
      response.setContentTypeIfNotSet("image/bmp");
      renderBinary(is);
    }

}
