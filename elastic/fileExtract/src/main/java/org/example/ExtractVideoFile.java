package org.example;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp4.MP4Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.*;

public class ExtractVideoFile {

    public static Object extractVideoFile(String filepath) throws IOException, TikaException, SAXException {

        JSONObject jsonObject = new JSONObject();

        BufferedWriter fw = new BufferedWriter(new FileWriter(filepath+".csv"));

        // detecting the file type
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(new File(filepath));
        ParseContext pcontext = new ParseContext();

        // parser
        AutoDetectParser parser = new AutoDetectParser();
//        Parser parser = new MP4Parser();
        parser.parse(inputStream, handler, metadata, pcontext);

        System.out.println("Contents of the document:" + handler.toString());
        System.out.println("Metadata of the document:");
        String[] metadataNames = metadata.names();


        for(String name : metadataNames) {

            System.out.println(name + ": " + metadata.get(name));
            jsonObject.put(name, metadata.get(name));

            // 해상도, 영상길이 추출
            if (name.equals("tiff:ImageLength")
                    || name.equals("tiff:ImageWidth")
                    || name.equals("xmpDM:duration")) {
                fw.write(name+","+metadata.get(name));
                fw.newLine();
            }
        }


        String thumbnailPath = filepath + "-thumbnail.png";


        // 썸네일 추출 - command line 방식(차후 api를 사용한 방식으로 변환 필요)
        /***
        Runtime run = Runtime.getRuntime();

        String FFMPEG_HOME = System.getenv("FFMPEG_HOME");

        
        // 동영상 1초에서 썸네일 추출
        String command = "D:/ffmpeg-4.4.1-essentials_build/ffmpeg-4.4.1-essentials_build/bin/ffmpeg.exe -i \"" + filepath + "\" -ss 00:00:01 -vcodec png -vframes 1 \"" + thumbnailPath + "\"";
//        String command = FFMPEG_HOME + "/bin/ffmpeg.exe -i \"" + filepath + "\" -ss 00:00:01 -vcodec png -vframes 1 " + thumbnailPath;

        System.out.println(command);

        run.exec(command);
        fw.write("ThumbnailPath,"+thumbnailPath); ***/


        // 썸네일 추출 - api 사용 방식
        String ffmpegPath = "D:/ffmpeg-4.4.1-essentials_build/ffmpeg-4.4.1-essentials_build/bin/ffmpeg.exe";
        String ffprobePath = "D:/ffmpeg-4.4.1-essentials_build/ffmpeg-4.4.1-essentials_build/bin/ffprobe.exe";

        FFmpeg fFmpeg = new FFmpeg(ffmpegPath);
        FFprobe fFprobe = new FFprobe(ffprobePath);

        FFmpegBuilder builder = new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(filepath)
                .addExtraArgs("-ss", "00:00:05")
                .addOutput(thumbnailPath)
                .setFrames(1)
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(fFmpeg, fFprobe);
        executor.createJob(builder).run();

        jsonObject.put("thumbnail-file-path", thumbnailPath);
        fw.write("thumbnail-file-path,"+thumbnailPath);
        fw.newLine();
        
        // 자막 추출
        /* */


        fw.flush();
        fw.close();

        return jsonObject;
    }
}
