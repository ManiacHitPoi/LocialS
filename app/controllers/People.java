package controllers;

import java.util.List;
import play.Logger;

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

		Logger.debug("People#register");
		Logger.debug("name: " + name + ", age: " + age);
		Logger.debug("area: " + area + ", sex: " + sex);
                
        Person p = new Person(name, age, sex, area);
        p.save();
		Logger.debug("created person id: " + p.id);
        renderArgs.put("person", p);
        render();
    }
    
    public static void search(String name, Integer age, Boolean sex, Integer area) {

		Logger.debug("People#search");
		Logger.debug("name: " + name + ", age: " + age);
		Logger.debug("area: " + area + ", sex: " + sex);
 
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
		Logger.debug("return people: " + people);
        renderArgs.put("people", people);
        render();

    }

    public static void login(String name) {
        Logger.debug("People#login -> name: " + name);
        List<Person> people = Person.find("byName", name).fetch();
        renderArgs.put("people", people);
        //person = Person.findById(people.get(0).id);
        //renderArgs.put("person", person);
        Logger.debug("loggined people: " + people);
        render();                    
    }
    
    public static void show() {
        Long id = params.get("id", Long.class);
        Logger.debug("People#show -> id: " + id);
        List<Person> people = null;
        people = Person.find("byId", id).fetch();
		Logger.debug("return people: " + people);
        renderArgs.put("people", people);
        render();
    }
    
}
