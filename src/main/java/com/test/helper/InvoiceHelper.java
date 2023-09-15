package com.test.helper;

import java.awt.image.RenderedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.test.entity.Invoice;

import lombok.var;
import net.sourceforge.tess4j.Tesseract;

@Component
public class InvoiceHelper {

	@Autowired
	 private  ResourceLoader resourceLoader;
	 
	public String processPdf(MultipartFile pdfFile) throws Exception {
       
		// Create a temporary directory to store PDF page images
		StringBuilder response = new StringBuilder();
        Path tempDir = Files.createTempDirectory("pdfPages");

        try (PDDocument pdfDocument = PDDocument.load(pdfFile.getInputStream())) {
            PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);

            Resource resource = resourceLoader.getResource("classpath:tessdata-main");
            String dataPath = Paths.get(resource.getURI()).toFile().getAbsolutePath();
            
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(dataPath);

            for (int page = 0; page < pdfDocument.getNumberOfPages(); ++page) {
                RenderedImage image = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                File imageFile = new File(tempDir.toString(), "page_" + page + ".png");
                ImageIO.write(image, "PNG", imageFile);

                // Perform OCR on the temporary image file
                String data = tesseract.doOCR(imageFile);

                // Clean up the temporary image file
                Files.delete(imageFile.toPath());
                response.append(data);
            }
        } finally {
            Files.delete(tempDir);
        }
        return response.toString();
    }

    public String processImage(MultipartFile imageFile) throws Exception {
        // Create a temporary image file and a Path to it
        Path tempImagePath = Files.createTempFile("tempImage", ".png");

        try (var inputStream = imageFile.getInputStream()) {
            Files.copy(inputStream, tempImagePath, StandardCopyOption.REPLACE_EXISTING);
        }

        Resource resource = resourceLoader.getResource("classpath:tessdata-main");
        String dataPath = Paths.get(resource.getURI()).toFile().getAbsolutePath();
        
        // Initialize Tesseract and set the datapath
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(dataPath);

        // Perform OCR on the temporary image file
        String data = tesseract.doOCR(tempImagePath.toFile());

        // Delete the temporary image file when done
        Files.delete(tempImagePath);

        return data;
    }

	public Invoice processOCRData(String ocrData) {
		
		Invoice invoice = new Invoice();
		invoice.setInvoiceDate(LocalDate.now());
		
	    Pattern invoiceNumberPattern = Pattern.compile("Invoice Number : (IN\\d+)");
        Pattern supplierNamePattern = Pattern.compile("Supplier Name : ([^:]+)");
        Pattern invoiceAmountPattern = Pattern.compile("Invoice Amount : (\\d+)");

        Matcher invoiceNumberMatcher = invoiceNumberPattern.matcher(ocrData);
        Matcher supplierNameMatcher = supplierNamePattern.matcher(ocrData);
        Matcher invoiceAmountMatcher = invoiceAmountPattern.matcher(ocrData);

        // Find and print the invoice number
        if (invoiceNumberMatcher.find()) {
            String invoiceNumber = invoiceNumberMatcher.group(1);
            invoice.setInvoiceNumber(invoiceNumber);
        } else {
        	invoice.setInvoiceNumber("N/A");
        }

        // Find and print the supplier name
        if (supplierNameMatcher.find()) {
            String supplierName = supplierNameMatcher.group(1);
            int indexOfAddress = supplierName.indexOf(" Address");
            if (indexOfAddress != -1) {
                supplierName = supplierName.substring(0, indexOfAddress);
            }
            invoice.setSupplierName(supplierName);
        } else {
        	 invoice.setSupplierName("N/A");
        }

        // Find and print the invoice amount
        if (invoiceAmountMatcher.find()) {
            String invoiceAmount = invoiceAmountMatcher.group(1);
            invoice.setAmount(Double.valueOf(invoiceAmount));
        } else {
        	invoice.setAmount(Double.valueOf(0));
        }

        return invoice;
	}
}
