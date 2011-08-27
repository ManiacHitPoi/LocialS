package controllers;

import java.util.Comparator;
import java.util.Date;

import models.Comment;

class CommentComparator implements Comparator  {
    public int compare(Object obj1, Object obj2) {
        Date date1 = ((Comment)obj1).postedDate;
        Date date2 = ((Comment)obj2).postedDate;
        int compRes = date1.compareTo(date2);
        if (compRes > 0) {
            return -1;
        } else if (compRes < 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
