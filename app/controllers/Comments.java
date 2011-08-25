package controllers;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import play.db.jpa.*;

import models.Person;
import models.Photo;
import models.Comment;

import play.libs.MimeTypes;
import play.mvc.Controller;

public class Comments extends Controller {
    
    public static void index() {
        render();
    }
    
    public static void create(Integer id, String text,
            Boolean opinion, Long userId) throws FileNotFoundException {
                
        Person person = Person.findById(new Long(userId));

        Photo photo = Photo.findById(new Long(id));
        Comment comment = new Comment(person, photo, text, opinion).save();
        render(comment);
    }
        
    public static void list(Integer id) {
        List<Comment> c = Comment.findAll();
        renderArgs.put("comments", c);
        response.encoding = "UTF-8";
        render();
    }
    
    public static void getByPhoto(Integer id) {
        
    }

}
