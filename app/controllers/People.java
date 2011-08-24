package controllers;

import java.util.List;
import javax.persistence.*;

import models.Content;
import models.Person;
import models.Photo;
import play.mvc.Controller;

public class People extends Controller {
    
    public static void register(String name, Integer age, Boolean sex,
            Integer area) throws PersistenceException {
                
        Person p = new Person(name, age, sex, area);
        p.save();
        renderArgs.put("person", p);
        render();
    }
    
    // ToDo
    public static void serach(String name, Integer age, Boolean sex, Integer area) {
        Person person = Person.findById(new Long(1));
        List<Content> list = person.contents;        
    }    

    public static void login(String name) {
        
    }
    
    public static void show() {
        Long id = params.get("id", Long.class);
        List<Person> people = null;
        people = Person.find("byId", id).fetch();
        renderArgs.put("people", people);
        //response.encoding = "ISO-8859-1";
        render();
    }
    
}
