package com.example.controller;

import com.example.misc.HashMapResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api")
public class ApiController {

    @PostMapping("/upload")
    @ResponseBody
    public HashMapResponse saveFile(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String namepath = "files"+File.separator+filename;
        File localFile = new File(namepath);
        System.out.println("Запрос на /api/upload ("+localFile.getAbsolutePath()+")");
        try {
            InputStream inputStream = file.getInputStream();
            FileOutputStream outStream = new FileOutputStream(localFile);
            IOUtils.copy(inputStream,outStream);
            outStream.close(); inputStream.close();
        }
        catch (IOException e){
            System.out.printf("Name: %s Size: %d Content type: %s%n", file.getOriginalFilename(), file.getSize(), file.getContentType());
            throw e;
        }
        return new HashMapResponse().putVal("message","Successfully uploaded"); //Javascript fSlash escape
    }
    @PostMapping("/save")
    @ResponseBody
    public HashMapResponse saveString(@RequestHeader(value = "MyHeader",required = false) String header,@RequestBody(required = false) String body) throws IOException {
        HashMapResponse response = new HashMapResponse();
        JSONObject jsonBody;
        try { jsonBody = new JSONObject(body); }
        catch (JSONException | NullPointerException e){ response.putVal("JsonException",e.getClass().getName()+":"+e.getMessage()); }
        response.putVal("yourHeader",header == null ? "Not specified" : "Value: "+header);
        response.putVal("yourBody", body == null || body.length() == 0 ? "Body is empty" : "Payload: "+body);
        System.out.println("Запрос на /api/save "+response);
        File logFile = new File("log.txt");
        if(!logFile.exists()) logFile.createNewFile();
        Scanner logScan = new Scanner(logFile).useDelimiter("\\Z");
        String logContent = logScan.hasNext() ? logScan.next() : "";
        logScan.close();

        FileWriter fWriter = new FileWriter(logFile);
        fWriter.write(String.format("%s\n%s: Header: %s Body: %s",logContent ,new Date(), header, body));
        fWriter.close();
        return response;
    }
    @GetMapping("/timeJSON")
    @ResponseBody
    public HashMapResponse getTime(){
        HashMapResponse response = new HashMapResponse();
        System.out.println("Запрос на /api/timeJSON");
        response.putVal("currentTime",new Date().toString());
        return response;
    }
    @GetMapping("/time")
    public String getTimeText(){ System.out.println("Запрос на /api/time"); return new Date().toString(); }
}
