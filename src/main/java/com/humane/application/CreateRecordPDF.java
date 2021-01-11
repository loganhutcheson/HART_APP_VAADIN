package com.humane.application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.awt.Color;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

/**
 * Create a blank PDF and write the contents to a file.
 */
public final class CreateRecordPDF
{
    private CreateRecordPDF()
    {        
    }
    
    public static ByteArrayOutputStream createPDF(Animal animal) throws IOException
    {

        try (PDDocument doc = new PDDocument())
        {
            String message = animal.getName();
            PDPage page = new PDPage();
            float fontSize = 36.0f;
            PDFont font = PDType1Font.HELVETICA_BOLD;
            PDRectangle pageSize = page.getMediaBox();
            float stringWidth = font.getStringWidth( message )*fontSize/1000f;
            // calculate to center of the page
            int rotation = page.getRotation();
            boolean rotate = rotation == 90 || rotation == 270;
            float pageWidth = rotate ? pageSize.getHeight() : pageSize.getWidth();
            float pageHeight = rotate ? pageSize.getWidth() : pageSize.getHeight();
            float centerX = rotate ? pageHeight/2f : (pageWidth - stringWidth)/2f;
            float centerY = rotate ? (pageWidth - stringWidth)/2f : pageHeight/2f;
            // append the content to the existing stream
            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true, true))
            {
                contentStream.beginText();
                // set font and font size
                contentStream.setFont( font, fontSize );
                // set text color to red
                contentStream.setNonStrokingColor(Color.red);
                if (rotate)
                    {
                    // rotate the text according to the page rotation
                    contentStream.setTextMatrix(Matrix.getRotateInstance(Math.PI / 2, centerX, centerY));
                    }
                else
                {
                    contentStream.setTextMatrix(Matrix.getTranslateInstance(centerX, centerY));
                }
                contentStream.showText(message);
                contentStream.endText();
            }
            doc.addPage(page);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            doc.save(baos);
            return baos;
        }
    }
}
