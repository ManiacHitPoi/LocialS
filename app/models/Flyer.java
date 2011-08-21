package models;

import java.util.Date;

import play.data.validation.*;
import play.db.jpa.*;

import javax.persistence.*;

@Entity
public class Flyer extends Content {
    public Flyer(Person owner, String title, Blob image,
            Integer targetAreaId, Boolean targetSex, Integer targetAgeId) {
        super(owner, title, image);
        this.targetAreaId = targetAreaId;
        this.targetSex = targetSex;
        this.targetAgeId = targetAgeId;
    }
}
