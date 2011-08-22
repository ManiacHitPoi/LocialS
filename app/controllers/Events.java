package controllers;

import java.util.List;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;

import play.Logger;
import play.db.jpa.*;

import models.Event;
import models.Person;
import models.Photo;
import play.libs.MimeTypes;
import play.mvc.Controller;

public class Events extends Contents {

    public static void create(Long id, String title, File image, Integer area) throws FileNotFoundException {
        Person person = Person.findById(id);
        Blob photo = new Blob();

        photo.set(new FileInputStream(image.getAbsolutePath()),
                MimeTypes.getContentType(image.getName()));
        Event content = new Event(person, title, photo, area);
        content.save();
        render(content);
    }
    
    public static void list() {
        List<Event> eventList = Event.findAll();
        //Person person = Person.findById(id);
        //photoList = Photo.find("byOwner_Id",id).fetch();
        renderArgs.put("eventList", eventList);
        render();
    }
}
