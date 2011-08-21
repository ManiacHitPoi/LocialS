package models;

import java.util.Date;

import play.data.validation.*;
import play.db.jpa.*;
import javax.persistence.*;

import controllers.Contents;

@Entity
public class Coupon extends Content {
    
    public Coupon(Person person, String title, Blob image,
            Integer targetAreaId, Boolean targetSex, Integer targetAgeId) {
        super(person, title, image);
        this.targetAreaId = targetAreaId;
        this.targetSex = targetSex;
        this.targetAgeId = targetAgeId;
    }

}
