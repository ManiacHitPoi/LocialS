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

    public static void create(Long id, String title, File image,
    Integer age, Integer area, Boolean sex) throws FileNotFoundException {
        Person person = Person.findById(id);
        Blob photo = new Blob();

        photo.set(new FileInputStream(image.getAbsolutePath()),
                MimeTypes.getContentType(image.getName()));
        Coupon content = new Coupon(person, title, photo, area, sex, age);
        content.save();
        render(content);
    }

    public static void search(String title, Integer age, Boolean sex, Integer area) {
        //List<Event> couponList = Coupon.find("Title like ? and targetAgeId = ? and targetSex = ? and targetAreaId = ?",
        //        title + "%", age, sex, area).fetch();

        String query = "";
        query += "title like ?";

        if (age != -1) {
            query += " and targetAgeId = " + age;
        }
        if (sex != null) {
            query += " and targetSex = " + sex;
        }
        if (area != -1) {
            query += " and targetAreaId = " + area;
        }
        List<Event> couponList = Coupon.find("Title like ?", title + "%").fetch();

       renderArgs.put("couponList", couponList);
        render();
    }
}
