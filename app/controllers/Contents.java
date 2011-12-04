package controllers;

import java.util.List;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;

import models.*;

import play.Logger;
import play.db.jpa.*;
import play.libs.MimeTypes;
import play.mvc.Controller;

public class Contents extends Controller {

    public static void index() {
        render();
    }

    public static void create(String title, File photo)
	throws FileNotFoundException {

    }

    public static void show(Integer id, String type) {
        List<Content> contents = null;
        String controller = request.get().controller;
        FileInputStream is = null;
		Logger.debug("Contents#show -> id: " + id);
		Logger.debug("controller: " + controller);

		if (controller.equals("Photos")) {
            contents = Photo.find("byId", new Long(id)).fetch();
            if (type.equals("list")) {
                is = convert(contents.get(0), 116, 116);
            } else if (type.equals("detail")) {
                is = convert(contents.get(0), 230, 230);
            }

        } else if (controller.equals("Events")) {
            contents = Event.find("byId", new Long(id)).fetch();
            is = convert(contents.get(0), 400, 280);

        } else if (controller.equals("Coupons")) {
            contents = Coupon.find("byId", new Long(id)).fetch();
            is = convert(contents.get(0), 400, 280);
            
        } else if (controller.equals("Flyers")) {
            contents = Flyer.find("byId", new Long(id)).fetch();
            is = convert(contents.get(0), 280, 400);
        }
		
        Logger.debug("return contents: " + contents);
        response.setContentTypeIfNotSet("image/bmp");
        renderBinary(is);

        //response.setContentTypeIfNotSet(contents.get(0).image.type());
        //renderBinary(contents.get(0).image.get());
    }
    
    public static FileInputStream convert(Content content, int width, int height) {
        String fileName = content.image.getFile().getAbsolutePath();
        File inFile = new File(fileName);
        File bitmap = new File("/tmp/tentative.bmp");
        FileInputStream is = null;
        try {
            BufferedImage bi = ImageIO.read(inFile);
            ImageIO.write(bi,  "bmp", bitmap);
            
            BufferedImage newbi = ImageIO.read(bitmap);
            BufferedImage newImage = null;
            newImage = new BufferedImage(width, height, newbi.getType());
            newImage.getGraphics().drawImage(newbi.getScaledInstance(width, height,
                    Image.SCALE_AREA_AVERAGING), 0, 0, width, height, null);
            ImageIO.write(newImage,  "bmp", new File("/tmp/converted.bmp"));
            is = new FileInputStream(new File("/tmp/converted.bmp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }
    
    public static void list(String userId) {

    }    
    
}
