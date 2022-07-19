package org.example;

import com.pff.PSTAttachment;
import org.apache.james.mime4j.dom.Body;
import org.apache.tika.exception.TikaException;
import org.apache.tika.extractor.EmbeddedDocumentExtractor;
import org.apache.tika.extractor.EmbeddedDocumentUtil;
import org.apache.tika.metadata.Message;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.OutlookExtractor;
import org.apache.tika.parser.microsoft.pst.OutlookPSTParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExtractPstFile {
    public static boolean extractPstFile(String filepath) throws IOException, TikaException, SAXException {

//        BufferedWriter fw = new BufferedWriter(new FileWriter(filepath+".csv"));


//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ToTextContentHandler handler = new ToTextContentHandler(byteArrayOutputStream, "UTF-8");

        // detecting the file type
        BodyContentHandler handler = new BodyContentHandler(-1); // -1: 쓰기 제한 비활성화. writeLimit: 문자열에 포함할 최대 문자 수
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(new File(filepath));
        ParseContext pcontext = new ParseContext();


        // parser
        Parser parser = new OutlookPSTParser();
        parser.parse(inputStream, handler, metadata, pcontext);


//        XHTMLContentHandler xhtmlContentHandler = new XHTMLContentHandler(handler, metadata);
//        System.out.println("xhtmlContentHandler: " + xhtmlContentHandler.toString());


        System.out.println("Contents of the document:" + handler.toString());
        System.out.println("Metadata of the document:");

//        System.out.println("MESSAGE_FROM: " + metadata.get(Metadata.MESSAGE_FROM));
//        System.out.println("MESSAGE_FROM_NAME: " + metadata.get(Metadata.MESSAGE_FROM_NAME));
//        System.out.println("MESSAGE_TO: " + metadata.get(Metadata.MESSAGE_TO));
//        System.out.println("MESSAGE_TO_NAME: " + metadata.get(Metadata.MESSAGE_TO_NAME));
//        System.out.println("MESSAGE_TO_DISPLAY_NAME: " + metadata.get(Metadata.MESSAGE_TO_DISPLAY_NAME));
//        System.out.println("MESSAGE_CC_DISPLAY_NAME: " + metadata.get(Metadata.MESSAGE_CC_DISPLAY_NAME));
//        System.out.println("MESSAGE_BCC_DISPLAY_NAME: " + metadata.get(Metadata.MESSAGE_BCC_DISPLAY_NAME));
//        System.out.println("MESSAGE_RAW_HEADER_PREFIX: " + metadata.get(Metadata.MESSAGE_RAW_HEADER_PREFIX));

//        System.out.println("CONTENT_ENCODING: " + metadata.get(Metadata.CONTENT_ENCODING));
//        System.out.println("CONTENT_MD5: " + metadata.get(Metadata.CONTENT_MD5));
//        System.out.println("MULTIPART_SUBTYPE: " + metadata.get(Metadata.MULTIPART_SUBTYPE));


//        String[] metadataNames = metadata.names();
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


//        fw.flush();
//        fw.close();

        return true;
    }
}
