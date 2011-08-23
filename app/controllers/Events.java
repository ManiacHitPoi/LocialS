package controllers;

import java.util.Date;
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

    public static void create(Long id, String title, Date eventDate,
            File image, Integer area) throws FileNotFoundException {
        Person person = Person.findById(id);
        Blob photo = new Blob();

        photo.set(new FileInputStream(image.getAbsolutePath()),
                MimeTypes.getContentType(image.getName()));
        Event content = new Event(person, title, eventDate, photo, area);
        content.save();
        render(content);
    }
    
    public static void list() {
        //List<Event> eventList = Event.find("id > ?", new Long(45)).fetch();
        List<Event> eventList = Event.find("posteddate >= ?", "2011-08-20 00:00:00").fetch();
        //List<Event> eventList = Event.findAll();
        renderArgs.put("eventList", eventList);
        render();
    }
}
