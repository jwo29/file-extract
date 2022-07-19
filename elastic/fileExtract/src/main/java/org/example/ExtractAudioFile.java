package org.example;

import com.google.gson.JsonObject;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
//import com.groupdocs.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.*;
import org.apache.tika.sax.BodyContentHandler;
import org.gagravarr.tika.OggParser;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.*;

public class ExtractAudioFile {
    public static Object extractAudioFile(String filepath) throws IOException, TikaException, SAXException {

        // JSON Object로 표현
        JSONObject jsonObject = new JSONObject();


        BufferedWriter fw = new BufferedWriter(new FileWriter(filepath+".csv"));

        // detecting the file type
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(new File(filepath));
        ParseContext pcontext = new ParseContext();

        // Mp3 parser
        Parser parser = new AutoDetectParser();
//        Parser parser = new Mp3Parser();
//        OggParser parser = new OggParser();

        parser.parse(inputStream, handler, metadata, pcontext);


        System.out.println("Contents of the document:" + handler.toString());
        System.out.println("Metadata of the document:");
        String[] metadataNames = metadata.names();


        for(String name : metadataNames) {

            System.out.println(name + ": " + metadata.get(name));
            jsonObject.put(name, metadata.get(name));

            // 오디오 길이 추출
            if (name.equals("xmpDM:duration")) {
                fw.write(name+","+ metadata.get(name));
                fw.newLine();
            }
        }

        // 태그 추출

        MP3Frame frame;
        while ((frame = ID3v2Frame.createFrameIfPresent(inputStream)) != null) {
            if (frame instanceof ID3v2Frame) {
                ID3v22Handler id3v22Handler = new ID3v22Handler((ID3v2Frame) frame);
                ID3v23Handler id3v23Handler = new ID3v23Handler((ID3v2Frame) frame);
                ID3v24Handler id3v24Handler = new ID3v24Handler((ID3v2Frame) frame);


                System.out.print(id3v22Handler.getAlbum());
                System.out.print(id3v23Handler.getAlbum());
                System.out.print(id3v24Handler.getAlbum());
            }


        }

//        /****** groupdocs 라이브러리 테스트 *******/
//        Metadata metadata1 = new Metadata(filepath)
//        MP3RootPackage root = metadata1.getRootPackageGeneric();
//
//        // MP3 파일 ID3v1 태그 읽기
//        if (root.getID3V1() != null) {
//            System.out.println(root.getID3V1().getAlbum());
//            System.out.println(root.getID3V1().getArtist());
//            System.out.println(root.getID3V1().getTitle());
//            System.out.println(root.getID3V1().getVersion());
//            System.out.println(root.getID3V1().getComment());
//        }
//
//        // MP3 파일 ID3v2 태그 읽기
//        if (root.getID3V2() != null) {
//            System.out.println(root.getID3V2().getAlbum());
//            System.out.println(root.getID3V2().getArtist());
//            System.out.println(root.getID3V2().getBand());
//            System.out.println(root.getID3V2().getTitle());
//            System.out.println(root.getID3V2().getComposers());
//            System.out.println(root.getID3V2().getCopyright());
//            System.out.println(root.getID3V2().getPublisher());
//            System.out.println(root.getID3V2().getOriginalAlbum());
//            System.out.println(root.getID3V2().getMusicalKey());
//
//            if (root.getID3V2().getAttachedPictures() != null) {
//                for (ID3V2AttachedPictureFrame attachedPicture : root.getID3V2().getAttachedPictures()) {
//                    System.out.println(attachedPicture.getAttachedPictureType());
//                    System.out.println(attachedPicture.getMimeType());
//                    System.out.println(attachedPicture.getDescription());
//                }
//            }
//        }
//
//        // MP3 가사 태그 읽기
//        if (root.getLyrics() != null) {
//            System.out.println(root.getLyrics3V2().getLyrics());
//            System.out.println(root.getLyrics3V2().getAlbum());
//            System.out.println(root.getLyrics3V2().getArtist());
//            System.out.println(root.getLyrics3V2().getTrack());
//            // ...
//
//            // 마찬가지로 태그 필드를 순회할 수 있습니다.
//            for (LyricsField field : root.getLyrics3V2().toList()) {
//                System.out.println(String.format("%s = %s", field.getID(), field.getData()));
//            }
//        }


        // 가사 추출
        // 가사가 있는 경우 filePath-lyrics.txt 파일로 저장 후 경로 csv 필드에 추가
        // 가사가 없는 경우 null값 저장
        LyricsHandler lyrics = new LyricsHandler(inputStream, handler);

        fw.write("lyrics-file-path,");

//        System.out.println();
//        System.out.println("hasLyrics: " + lyrics.hasLyrics()); // false
//        System.out.println("hasID3v1: " + lyrics.hasID3v1()); // false
//        System.out.println();

        if(lyrics.hasLyrics()) {

            String lyricsFilePath = filepath+"-lyrics.txt";
            BufferedWriter fw2 = new BufferedWriter(new FileWriter(lyricsFilePath));

            while (lyrics.hasLyrics()) {
                System.out.println(lyrics.toString());
                fw2.write(lyrics.toString());
                fw2.newLine();
            }

            fw2.flush();
            fw2.close();

            fw.write(lyricsFilePath);
            fw.newLine();

        } else {
            fw.write("null");
            fw.newLine();
        }

        inputStream.close();

        fw.flush();
        fw.close();

        return jsonObject;
    }
}
