/**
 * 
 */
package com.org.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * @author ajan.rengarajan
 *
 */
@Path("/fetchDetails")
public class ParseDataService {
	
	private static String FILE = "c:/tempFolder/FirstPdf.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font orangeFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
            Font.NORMAL, BaseColor.ORANGE);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);

	@POST
	@Path("getPosterDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPrevYrsGoal(@FormParam("firmNm") String firmNm, @FormParam("executiveSummary") String executiveSummary){
		String json = "{\"Rating\": [{\"year\": \"2008\",\"Comm\": \"Okay\",\"Rating\": \"3\"},{\"year\": \"2009\",\"Comm\": \"Good\","+
            "\"Rating\": \"4\"},{\"year\": \"2010\",\"Comm\": \"Excellent\",\"Rating\": \"5\"},{\"year\": \"2011\",\"Comm\": \"Okay\","+
            "\"Rating\": \"3\"},{\"year\": \"2012\",\"Comm\": \"Good\",\"Rating\": \"4\"},{\"year\": \"2013\",\"Comm\": \"Okay\",\"Rating\": \"3\""+
       "},{\"year\": \"2014\",\"Comm\": \"Excellent\",\"Rating\": \"5\"},{\"year\": \"2015\",\"Comm\": \"Good\",\"Rating\": \"4\"}]}";
		
		System.out.println("FirmNm"+ firmNm);
		try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document, firmNm, executiveSummary);
            //addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return FILE;
	}
	
    private static void addMetaData(Document document) {
        document.addTitle("Member Review");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("arengara");
        document.addCreator("Ajan");
    }

    private static void addTitlePage(Document document, String firmNm, String executiveSummary)
            throws DocumentException {
    	
//    	String HTML = "<h1>"+firmNm+"</h1><p>Report generated by: " + System.getProperty("user.name") + ", " + new Date()+"</p>";
      
    	System.out.println("FirmNm"+ firmNm);
    	Paragraph preface = new Paragraph();
       // addEmptyLine(preface, 1);
       
        preface.add(new Paragraph(firmNm, catFont));
        preface.setAlignment(Paragraph.ALIGN_CENTER);        

        addEmptyLine(preface, 1);
        
        preface.add(new Paragraph(
                "Report generated by: " + System.getProperty("user.name") + ", " + new Date(),
                smallBold));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph(
                "Executive Summary ",
                orangeFont));

        addEmptyLine(preface, 1);
        
//        preface.add(new Paragraph(
//                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</b>",
//                smallFont));
//        addEmptyLine(preface, 1);
        
        preface.add(new Paragraph(executiveSummary, smallFont));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("First Chapter", catFont);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list
        createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);

        // Next section
        anchor = new Anchor("Second Chapter", catFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(catPart);

    }
    
    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
}