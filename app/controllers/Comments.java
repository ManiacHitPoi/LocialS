package controllers;

import java.util.ArrayList;
import java.util.Iterator;
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

    public static void list(Long id) {
        List<Photo> photoList = Photo.find("byOwner_Id", id).fetch();
        List<Comment> commentList = new ArrayList<Comment>();

        Iterator it = photoList.iterator();
        while (it.hasNext()) {
            Photo photo = (Photo) it.next();
            List<Comment> comments = Comment.find("Photo_Id", photo.id).fetch();
            commentList.addAll(comments);
        }
        renderArgs.put("comments", commentList);
        render();
    }

    public static void getByPhoto(Long id) {
        List<Comment> commentList = Comment.find("photo_id is ? order by postedDate desc", id).fetch();
        renderArgs.put("comments", commentList);
        render();
    }
}
