package org.example;

import com.pff.PSTException;
import com.pff.PSTFile;
import org.apache.tika.exception.TikaException;
import org.example.aspose.test.ExtractAttachments;
import org.example.aspose.test.ExtractContacts;
import org.example.aspose.test.LoadPst;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import java.io.IOException;


@Controller
public class HomeController {

    @GetMapping(path = "/test")
    public @ResponseBody String getPram(
            @RequestParam(name = "param1", defaultValue = "true") String param1,
            @RequestParam(name = "param2", defaultValue = "false") String param2
    ) {
        return "param1: " + param1 + ", param2: " + param2;
    }

    // JSON 가독성있게 표현 필요
    @GetMapping(path = "/parse/audio")
    public @ResponseBody String getAudioMeta(
            @RequestParam(name = "filepath") String filepath) throws TikaException, IOException, SAXException {

        JSONObject object = (JSONObject) ExtractAudioFile.extractAudioFile(filepath);

        return object.toString();

    }

    @GetMapping(path = "/parse/video")
    public  @ResponseBody String getVideoMeta(
            @RequestParam(name = "filepath") String filepath) throws TikaException, IOException, SAXException {

        JSONObject object = (JSONObject) ExtractVideoFile.extractVideoFile(filepath);

        return object.toString();
    }

    @GetMapping(path = "/parse/pst")
    public  @ResponseBody String getPst(
            @RequestParam(name = "filepath") String filepath) throws TikaException, IOException, SAXException, PSTException {

        boolean result = ExtractPstFile.extractPstFile(filepath);

        return "parse pst using Tika OutlookPSTParser ---- end";
    }

    @GetMapping(path = "/parse/msg")
    public  @ResponseBody String getMsg(
            @RequestParam(name = "filepath") String filepath) throws TikaException, IOException, SAXException, PSTException {

        ExtractMsgFile.extractMsgFile(filepath);

        return "parse msg using Tika OfficeParser ---- end";
    }

    @GetMapping(path = "/libpst/extract/pst")
    public @ResponseBody String testLibpst(
            @RequestParam(name = "filepath") String filepath) {

        Test test = new Test(filepath);

        return "extract pst using java-libpst ---- end";
    }

    @GetMapping(path = "/aspose/extract/attachment")
    public @ResponseBody String extractAttachs(
            @RequestParam(name = "filepath") String filepath) {

        ExtractAttachments test = new ExtractAttachments(filepath);

        return "extract attachments using aspose ---- end";
    }

    @GetMapping(path = "/aspose/extract/contact")
    public @ResponseBody String extractContacts(
            @RequestParam(name = "filepath") String filepath) {

        ExtractContacts test = new ExtractContacts(filepath);

        return "extract contacts using aspose ---- end";
    }
}
