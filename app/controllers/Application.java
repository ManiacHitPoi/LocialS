package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import play.*;
import play.db.jpa.Blob;
import play.libs.MimeTypes;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
}