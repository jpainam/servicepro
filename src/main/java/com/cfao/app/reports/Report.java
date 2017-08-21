package com.cfao.app.reports;

import com.cfao.app.util.AlertUtil;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by JP on 7/13/2017.
 */
public class Report {

    protected Image logo;
    protected String destination;
    protected Document document;
    protected PdfWriter writer;
    protected PdfDocument pdf;

    public Report() {

        try {
            URL filename = getClass().getResource(ResourceBundle.getBundle("Application").getString("app.logo.transparent"));
            logo = new Image(ImageDataFactory.create(filename));
            logo.setWidth(100);
            logo.setHeight(60);
            logo.setFixedPosition(36, 790);

            Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("document.dir")).toAbsolutePath();
            destination = path.toString();
            writer = new PdfWriter(destination + File.separator + "test.pdf");
            writer.setSmartMode(true);
            pdf = new PdfDocument(writer);
            document = new Document(pdf);

            document.add(logo);
            /** Proprietes du documents **/
            /*document.addAuthor("Bernard Pierre");
            document.addCreator("Bernard Pierre");
            document.addSubject("Fiche Apprenant");
            document.addTitle("Fiche Apprenant");
            document.addKeywords("CFAO, Formation, Apprenant");**/

            PdfCanvas canvas = new PdfCanvas(pdf, 1);
            canvas.moveTo(36, 800);
            canvas.lineTo(570, 800);
            canvas.closePathStroke();
            FooterHandler footerHandler = new FooterHandler();
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, footerHandler);

        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }
    public void showReport() {
        try {
            document.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showErrorMessage(ex);
        }
    }

    public void close() {
        document.close();
    }

    public class FooterHandler implements IEventHandler{

        @Override
        public void handleEvent(Event event) {
            try {
                PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
                PdfPage page = docEvent.getPage();
                int pageNum = docEvent.getDocument().getPageNumber(page);
                PdfCanvas canvas = new PdfCanvas(page);
                canvas.beginText();
                try {
                    canvas.setFontAndSize(PdfFontFactory.createFont(FontConstants.HELVETICA), 12);
                } catch (IOException ex) {
                    AlertUtil.showErrorMessage(ex);
                }
                canvas.moveTo(34, 34);
                canvas.lineTo(570, 34);
                canvas.closePathStroke();

                canvas.moveText(34, 20);
                canvas.showText("Bernard Pierre");

                DateFormat format = new SimpleDateFormat("dd/MM/YYYY");
                canvas.moveText(250, 0);
                canvas.showText(format.format(new Date()));

                canvas.moveText(220, 0);
                canvas.showText(String.format("Page %d / %d", pageNum, pdf.getNumberOfPages()));
                canvas.endText();
                canvas.stroke();
                canvas.release();

            } catch (Exception ex) {
                AlertUtil.showErrorMessage(ex);
            }
        }
    }

}
