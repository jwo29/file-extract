package org.example;

import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.ocr.TesseractOCRParser;

public class TesseractTest {
    public static void tesseractTest() {
        TesseractOCRParser parser = new TesseractOCRParser();

        System.out.println("Tesseract Path: " + parser.getTesseractPath());
        System.out.println("Tesseract Path is empty: " + parser.getTesseractPath().isEmpty());

        parser.setTesseractPath("C:/Program Files/Tesseract-OCR/tesseract.exe");

        System.out.println("Tesseract Path: " + parser.getTesseractPath());
        System.out.println("Tesseract Path is empty: " + parser.getTesseractPath().isEmpty());
    }
}
