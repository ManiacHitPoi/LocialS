package models;

import java.util.Date;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Comment extends Model {
    
    @Required
    public Long userId;
    
    @Required
    //@Lob
    public String text;

    public Boolean opinion;
    
    @Required
    public Long photoId;
    
    @Required
    public Date postedDate;

    @ManyToOne
    public Photo photo;
    
    public Comment(Person person, Photo photo, String text, Boolean opinion) {
        this.userId = person.id;
        this.photo = photo;
        this.text = text;
        //this.opinion = Boolean.parseBoolean(opinion);
        this.opinion = opinion;
        this.postedDate = new Date();
    }
}
