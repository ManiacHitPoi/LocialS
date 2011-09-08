package controllers;

import java.util.Date;
import java.util.List;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;

import play.Logger;
import play.db.jpa.*;

import models.Photo;
import models.Person;
import play.libs.MimeTypes;
import play.mvc.Controller;

public class Photos extends Contents {

    public static void create(Long id, String title, Date eventDate,
            File image) throws FileNotFoundException {
		Logger.debug("Photos#create");
		Logger.debug("id: " + id + ", title: " + title);
		Logger.debug("eventDate: " + eventDate + ", image: " + image);
        Person person = Person.findById(id);
        Blob photo = new Blob();

        photo.set(new FileInputStream(image.getAbsolutePath()),
                MimeTypes.getContentType(image.getName()));
        Photo content = new Photo(person, title, photo);
        content.save();
		Logger.debug("return content: " + content);
        render(content);
    }

    public static void list(Long id) {
		Logger.debug("Photos#list -> id: " + id);
        List<Photo> photoList = null;
        Person person = Person.findById(id);
        photoList = Photo.find("byOwner_Id",id).fetch();
		Logger.debug("return photosList: " + photoList);
        renderArgs.put("photoList", photoList);
        render();
    }   

    /*
    public Photo addComment(Person person, Photo photo, String comment, String opinion) {
            Comment newComment = new Comment(person.id, photo, comment, opinion).save();
            this.comments.add(newComment);
            this.save();
            return this;
        }
    */     
}
