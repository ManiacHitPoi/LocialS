package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Person extends Model {
    
    @Required
    @Column(unique=true)
    public String name;

    @Required
    public Integer ageId;
    
    @Required
    @Column(nullable=true)
    public Boolean sex;

    @Required
    public Integer areaId;
    
    @OneToMany(mappedBy="owner", cascade=CascadeType.ALL)
    public List<Content> contents;

    public Person(String name, Integer age, Boolean sex, Integer area) {
        this.name = name;
        this.ageId = age;
        this.sex = sex;
        this.areaId = area;
    }

}
