package controllers;

import java.util.List;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.itextpdf.text.Document;
import com.itextpdf.text.Jpeg;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

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

    public static void show(Integer id) {
        List<Content> contents = null;
        String controller = request.get().controller;
        FileInputStream is = null;
		Logger.debug("Contents#show -> id: " + id);
		Logger.debug("controller: " + controller);
		
		if (controller.equals("Events")) {
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
    }
    

    
    public static void thumbnail(Integer id) {
        List<Content> contents = null;
        FileInputStream is = null;
        Logger.debug("Contents#thumbnail -> id: " + id);
        contents = Photo.find("byId", new Long(id)).fetch();
        is = convert(contents.get(0), 116, 116);
        Logger.debug("return contents: " + contents);
        response.setContentTypeIfNotSet("image/bmp");
        renderBinary(is);
    }
    
    public static void detail(Integer id) {
        List<Content> contents = null;
        FileInputStream is = null;
        Logger.debug("Contents#detail -> id: " + id);
        contents = Photo.find("byId", new Long(id)).fetch();
        is = convert(contents.get(0), 230, 230);
        Logger.debug("return contents: " + contents);
        response.setContentTypeIfNotSet("image/bmp");
        renderBinary(is);
    }

    public static void print(Integer id) {
        List<Content> contents = null;
        String controller = request.get().controller;
        if (controller.equals("Photos")) {
            contents = Photo.find("byId", new Long(id)).fetch();
        } else if (controller.equals("Events")) {
            contents = Event.find("byId", new Long(id)).fetch();
        } else if (controller.equals("Coupons")) {
            contents = Coupon.find("byId", new Long(id)).fetch();
        } else if (controller.equals("Flyers")) {
            contents = Flyer.find("byId", new Long(id)).fetch();
        }
        Logger.debug("return contents: " + contents);

        Content content = contents.get(0);
        String fileName = content.image.getFile().getAbsolutePath();

        byte[] imageBytes;
        byte[] pdfBytes = null;
        try {
            imageBytes = getBytesFromImage(fileName);
            pdfBytes = convertByteArrayToPdf(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setContentTypeIfNotSet("application/pdf");
        renderBinary(new ByteArrayInputStream(pdfBytes));
    }
    
        
    /**
     * 
     * @param pdfBytes
     * @param outFile
     * @throws IOException
     */
    public static void output(byte[] pdfBytes, String outFile) throws IOException {
        File f = new File(outFile);
        OutputStream out = new FileOutputStream(f);
        ByteArrayInputStream input = new ByteArrayInputStream(pdfBytes);
        byte[] bytes = new byte[1024];
        int b = 0;
        while ((b = input.read(bytes)) != -1) {
            out.write(bytes);
        }
        out.close();
    }
    
    /**
     * 
     * @param url
     * @return
     * @throws IOException
     */
    public static byte[] getBytesFromImage(String url) throws IOException {
        File f = new File(url);
        InputStream is = new BufferedInputStream(new FileInputStream(f));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int b = 0;
        while ((b = is.read(bytes)) != -1) {
            out.write(bytes);
        }
        out.close();
        return out.toByteArray();
    }

    /**
     * 
     * @param imageData
     * @return
     */
    public static byte[] convertByteArrayToPdf(byte[] imageData) {
        Document doc = new Document();
        doc.setPageSize(PageSize.A4.rotate());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            doc.setPageSize(Jpeg.getInstance(imageData));
            PdfWriter.getInstance(doc,  outputStream);
            doc.open();
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(imageData);
            doc.add(image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            doc.close();
        }
        return outputStream.toByteArray();
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
