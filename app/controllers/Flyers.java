package controllers;

import java.util.List;
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

    public static void create(Long id, String title, File image,
	Integer age, Integer area, Boolean sex) throws FileNotFoundException {
        Person person = Person.findById(id);
        Blob photo = new Blob();

        photo.set(new FileInputStream(image.getAbsolutePath()),
                MimeTypes.getContentType(image.getName()));
        Flyer content = new Flyer(person, title, photo, area, sex, age);
        content.save();
        render(content);
    }

    public static void random() {
        
    }

}
