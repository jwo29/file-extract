package org.example;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.tika.exception.TikaException;
import org.apache.tika.extractor.EmbeddedDocumentExtractor;
import org.apache.tika.extractor.EmbeddedDocumentUtil;
import org.apache.tika.metadata.Message;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.parser.ocr.TesseractOCRParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.*;

public class ExtractMsgFile {
    public static void extractMsgFile(String filepath) throws IOException, TikaException, SAXException {

        BufferedWriter fw = new BufferedWriter(new FileWriter(filepath+".csv"));

        /* tesseract 테스트 */
        // set tesseract
//        TesseractOCRParser tesseractOCRParser = new TesseractOCRParser();
//        tesseractOCRParser.setTesseractPath("C:/Program Files/Tesseract-OCR/tesseract.exe");

//        System.out.println("Tesseract Path: " + tesseractOCRParser.getTesseractPath());
//        System.out.println("Tesseract Path is empty: " + tesseractOCRParser.getTesseractPath().isEmpty());


        FileOutputStream outputStream = new FileOutputStream(new File("D:/data/test.txt"));


        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(new File(filepath));
        ParseContext pcontext = new ParseContext();

        Parser parser = new OfficeParser();
        parser.parse(inputStream, handler, metadata, pcontext);


        /* EmbeddedDocuemntExtractor 테스트 */
//        POIFSFileSystem fs = new POIFSFileSystem(inputStream);
//        EmbeddedDocumentExtractor extractor = EmbeddedDocumentUtil.getEmbeddedDocumentExtractor(pcontext);
//        OfficeParser.extractMacros(fs, handler, extractor);
//        boolean shParEm = extractor.shouldParseEmbedded(metadata);
//        System.out.println("Should parse embedded: " + shParEm);
//
//        // b: true
//        extractor.parseEmbedded(inputStream, handler, metadata, true);
//
//        // b: false
//        extractor.parseEmbedded(inputStream, handler, metadata, false);


        System.out.println("Contents of the document:");
        System.out.println(handler.toString());
        System.out.println("Metadata of the document:");
        String[] metadataNames = metadata.names();
//
//
//        fw.write("Contents of the document,"+handler.toString());
//        fw.newLine();
//
//        for(String name : metadataNames) {
//            System.out.println(name + ": " + metadata.get(name));
//            fw.write(name+","+metadata.get(name));
//            fw.newLine();
//        }

        /* 원하는 메타데이터만 선택적으로 출력 가능 */
        System.out.println("MESSAGE_FROM: " + metadata.get(Metadata.MESSAGE_FROM));
        System.out.println("MESSAGE_FROM_NAME: " + metadata.get(Metadata.MESSAGE_FROM_NAME));
        System.out.println("MESSAGE_TO: " + metadata.get(Metadata.MESSAGE_TO));
        System.out.println("MESSAGE_TO_NAME: " + metadata.get(Metadata.MESSAGE_TO_NAME));
        System.out.println("MESSAGE_RAW_HEADER_RECEIVED: " + metadata.get(Metadata.MESSAGE_RAW_HEADER_PREFIX+"Received"));
        System.out.println("MESSAGE_CONTENT_ENCODING: " + metadata.get(Metadata.CONTENT_ENCODING));


        fw.flush();
        fw.close();
    }
}
