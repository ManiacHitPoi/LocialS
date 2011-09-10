package controllers;

import java.util.List;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import play.Logger;
import play.db.jpa.*;

import models.Coupon;
import models.Event;
import models.Person;
import play.libs.MimeTypes;
import play.mvc.Controller;

public class Coupons extends Contents {

    public static void create(Long id, String title, Integer age, Boolean sex,
            Integer area, String eventDate, File image) throws FileNotFoundException {
		Logger.debug("Coupons#create");
		Logger.debug("id: " + id + ", title: " + title);
		Logger.debug("age: " + age + ", image: " + image);
		Logger.debug("area: " + area + ", sex: " + sex);
		Logger.debug("eventDate: " + eventDate);
    	Coupon content = null;

        Person person = Person.findById(id);
        Blob photo = new Blob();

        try {
			photo.set(new FileInputStream(image.getAbsolutePath()),
			        MimeTypes.getContentType(image.getName()));
	        content = new Coupon(person, title, photo, area, sex, age);
	        content.save();
		    Logger.debug("return content: " + content);
			//throw new Exception();
		} catch (FileNotFoundException e1) {
			content = null;
		} catch (Exception e2) {
			content = null;
		} finally {
			render(content);
		}
    }

    public static void search(String title, Integer age, Boolean sex, Integer area) {
		Logger.debug("Coupons#search");
		Logger.debug("title: " + title + ", age: " + age);
		Logger.debug("area: " + area + ", sex: " + sex);
        //List<Event> couponList = Coupon.find("Title like ? and targetAgeId = ? and targetSex = ? and targetAreaId = ?",
        //        title + "%", age, sex, area).fetch();
        String query = "title like ?";

        if (age != null && age != -1) {
            query += " and targetAgeId = " + age;
        }
        if (sex != null) {
            query += " and targetSex = " + sex;
        }
        if (area != null && area != -1) {
            query += " and targetAreaId = " + area;
        }
        List<Event> couponList = Coupon.find(query, title + "%").fetch();
		Logger.debug("return couponList: " + couponList);

       renderArgs.put("couponList", couponList);
        render();
    }
}
