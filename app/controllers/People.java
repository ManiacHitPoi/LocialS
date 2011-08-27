package controllers;

import java.util.List;
import javax.persistence.*;

import models.Content;
import models.Person;
import models.Photo;

import play.mvc.Controller;

public class People extends Controller {
    
    public static void index() {
        render();
    }
    
    public static void register(String name, Integer age, Boolean sex,
            Integer area) throws PersistenceException {
                
        Person p = new Person(name, age, sex, area);
        p.save();
        renderArgs.put("person", p);
        render();
    }
    
    public static void search(String name, Integer age, Boolean sex, Integer area) {
 
        // TODO
        //if (validation.hasErrors()) {
        //}
        
        String query = "name like ? ";
        
        if (age != null && age != -1) {
            query += " and ageId = " + age;
        }

        if (sex != null) {
            query += " and sex = " + sex;            
        }

        if (area != null && area != -1) {
            query += " and areaId is " + area;
        }
        
        List<Person> people = Person.find(query, name + "%").fetch();
        renderArgs.put("people", people);
        render();

    }

    public static void login(String name) {
        List<Person> people = Person.find("byName", name).fetch();
        renderArgs.put("people", people);
        render();                    
    }
    
    public static void show() {
        Long id = params.get("id", Long.class);
        List<Person> people = null;
        people = Person.find("byId", id).fetch();
        renderArgs.put("people", people);
        render();
    }
    
}
