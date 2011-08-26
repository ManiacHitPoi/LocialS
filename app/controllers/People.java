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
    
    // ToDo
    public static void search(String name, Integer age, Boolean sex, Integer area) {
 
        // TODO
        //if (validation.hasErrors()) {
        //}
        
        String query = "select p from Person p where ";
        
        query += "p.name like ?";
                
        if (age != null) {
            query += " and p.ageId is " + age;
        }

        if (sex != null) {
            query += " and p.sex = " + sex;            
        }

        if (area != null) {
            query += " and p.areaId is " + area;
        }
        
        List<Person> people = Person.find(query, name + "%").fetch();
        //List<Person> people = Person.find("select p from Person p where p.name like ?", name+"%").fetch();
        //List<Person> people = Person.find("select p from Person p where p.name like ? and p.sex = false", name+"%").fetch();
        //List<Person> people = Person.find("select p from Person p where p.name like ? and p.sex = ? and p.ageId = ? and areaId = ?", name+"%", sex, age, area).fetch();
        renderArgs.put("people", people);
        render();

        //Query query = JPA.em().createQuery("select c from Comment c");
        //List<Comment> comments = query.getResultList();
        //renderArgs.put("comments", comments);
        //render();
        
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
