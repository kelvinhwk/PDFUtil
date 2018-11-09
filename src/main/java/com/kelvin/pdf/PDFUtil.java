package com.kelvin.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

public class PDFUtil {

	/**
	 * 创建文字PDF
	 * 
	 * @param filePath
	 * @param paragraphs
	 */
	public static void createPDF(String filePath, String... paragraphs) {
		// Initialize PDF writer
		PdfWriter writer;
		try {
			File f = new File(filePath);
			f.getParentFile().mkdirs();
			writer = new PdfWriter(filePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);

		// Initialize document
		Document document = new Document(pdf);

		// Add paragraph to the document
		try {
			for (String p : paragraphs) {
				document.add(new Paragraph(p));
			}
		} finally {
			// Close document
			document.close();
		}

	}

	/**
	 * 创建图片PDF
	 * 
	 * @param filePath
	 * @param paragraphs
	 */
	public static void createPDFImage(String filePath, String imagePath) {
		// Initialize PDF writer
		PdfWriter writer;

		try {
			File f = new File(filePath);
			f.getParentFile().mkdirs();
			writer = new PdfWriter(filePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);

		// Initialize document
		Document document = new Document(pdf);

		// Add image to the document
		Image image;
		try {
			image = new Image(ImageDataFactory.create(imagePath));
			// image.setAutoScaleWidth(true);
			// image.setAutoScaleHeight(true);
			document.add(image);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} finally {
			document.close();
		}
	}

	/**
	 * 多个图片拼成一个pdf
	 * 
	 * @param filePath
	 * @param imagePaths
	 */
	public static void createPDFImages(String filePath, String... imagePaths) {
		// Initialize PDF writer
		PdfWriter writer;
		try {
			File f = new File(filePath);
			f.getParentFile().mkdirs();
			writer = new PdfWriter(filePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);

		// Initialize document
		Document document = new Document(pdf);

		try {
			for (String imgPath : imagePaths) {
				try {
					Image image = new Image(ImageDataFactory.create(imgPath));
					document.add(image);
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}
				catch (IOException e) {
					if(e.getCause() instanceof FileNotFoundException) {
						throw new RuntimeException("文件已过期");
					}
					throw e;
				}
			}
		} finally {
			try {
				document.close();
			} catch (Exception e) {

			}
		}
	}
}
