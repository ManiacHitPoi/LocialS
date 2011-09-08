package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import play.Logger;
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

		Logger.debug("Comments#create");
		Logger.debug("id: " + id + ", text: " + text);
		Logger.debug("opinion: " + opinion + ", userId: " + userId);

        Person person = Person.findById(new Long(userId));

        Photo photo = Photo.findById(new Long(id));
        Comment comment = new Comment(person, photo, text, opinion).save();
		Logger.debug("return comment : " + comment);
        render(comment);
    }

    public static void list(Long id) {
		Logger.debug("Comments#list -> id: " + id);
        List<Photo> photoList = Photo.find("byOwner_Id", id).fetch();
        List<Comment> commentList = new ArrayList<Comment>();

        Iterator it = photoList.iterator();
        while (it.hasNext()) {
            Photo photo = (Photo) it.next();
            List<Comment> comments = Comment.find("Photo_Id", photo.id).fetch();
            commentList.addAll(comments);
        }
        Collections.sort(commentList, new CommentComparator());
		Logger.debug("return commentList: " + commentList);
        renderArgs.put("comments", commentList);
        render();
    }

    public static void getByPhoto(Long id) {
		Logger.debug("Comments#getByPhoto-> id: " + id);
        List<Comment> commentList = Comment.find("photo_id is ? order by postedDate desc", id).fetch();
		Logger.debug("return commentList: " + commentList);
        renderArgs.put("comments", commentList);
        render();
    }

}
