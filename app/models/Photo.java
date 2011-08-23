package models;

import java.util.List;
import java.util.ArrayList;

import play.data.validation.*;
import play.db.jpa.*;
import javax.persistence.*;

import models.Content;

@Entity
public class Photo extends Content {
    
    //@ManyToOne
    //public Person owner;

    @OneToMany(mappedBy="photo", cascade=CascadeType.ALL)
    public List<Comment> comments;    
    
    public Photo(Person owner, String title, Blob image) {
        super(owner, title, image);
        this.comments = new ArrayList<Comment>();
    }
   
}
