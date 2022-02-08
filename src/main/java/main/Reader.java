package main;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {

    public String getEmailReceiver(File filePDF) {
        return findEmailReceiver(readPDF(filePDF), filePDF);
    }

    private String readPDF(File filePDF){
        PDDocument pdDocument = null;
        try {
            pdDocument = PDDocument.load(filePDF);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(1);
            return pdfStripper.getText(pdDocument);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String findEmailReceiver(String contentPDF, File filePDF){
        Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(contentPDF);
        if (m.find()) return m.group();
        throw new RuntimeException("La " + filePDF.getName() + " no tiene el email del destinatario. No se pudo enviar esta factura ni las siguientes.");
    }
}
