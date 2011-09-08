package controllers;

import java.util.Date;
import java.util.List;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import play.Logger;
import play.db.jpa.*;

import models.Event;
import models.Person;
import models.Photo;
import play.libs.MimeTypes;
import play.mvc.Controller;

public class Events extends Contents {

    public static void create(Long id, String title, String eventDate,
            File image, Integer area) throws FileNotFoundException {

		Logger.debug("Events#create -> id: " + id);
		Logger.debug("area: " + area + ", title: " + title);
		Logger.debug("eventDate: " + eventDate+ ", image: " + image);

        Person person = Person.findById(id);
        Blob photo = new Blob();

        photo.set(new FileInputStream(image.getAbsolutePath()),
                MimeTypes.getContentType(image.getName()));
        Event content = new Event(person, title, string2date(eventDate), photo, area);
        content.save();
		Logger.debug("return content: " + content);
        render(content);
    }

    public static void list(Long id) {
		Logger.debug("Events#list-> id: " + id);
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //List<Event> eventList = null;
        //Long userId = params.get("id", Long.class);

        String query = "eventdate >= '" + formatter.format(today) + "'";
        if (id != -1) {
            Person person = Person.findById(id);
            query += "and targetareaid = " + person.areaId;
        }
        query += "order by eventDate desc";
        List<Event> eventList = Event.find(query).fetch();

        //eventList = Event.find("eventdate >= '?' order by eventDate desc", formatter.format(today)).fetch();
        //List<Event> eventList = Event.find("id > ?", new Long(45)).fetch();
        //List<Event> eventList = Event.find("posteddate >= ?", "2011-08-20 00:00:00").fetch();
        //List<Event> eventList = Event.findAll();
		Logger.debug("eventList: " + eventList);
        renderArgs.put("eventList", eventList);
        render();
    }

    private static Date string2date(String strdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            return formatter.parse(strdate);
        } catch (ParseException e) {
            return null;
        }
    }
}
