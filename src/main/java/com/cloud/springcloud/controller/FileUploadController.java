package com.cloud.springcloud.controller;


import com.cloud.springcloud.file.FileUtility;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/file")
public class FileUploadController {


    @RequestMapping(value = "/upload" ,method = {RequestMethod.POST} )
    public  String   fileUploag(@RequestParam (value = "file" ) MultipartFile file) throws IOException {


//        byte[] by = file.getBytes() ;
//        File file1 = new File(file.getOriginalFilename());
//        System.out.println( "文件路径："+file1.getPath());
//        FileCopyUtils.copy(by,file1);
//        return file1.getAbsolutePath() ;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd") ;

        byte[] rebyte = new byte[1024] ;

        InputStream fileInputStream = file.getInputStream() ;
        FileUtility.judeDirExists("G:/"+simpleDateFormat.format(new Date()));
        OutputStream outputStream = new FileOutputStream("G:/"+simpleDateFormat.format(new Date())+"/"+ file.getOriginalFilename()) ;
        while (fileInputStream.read(rebyte) != -1){
            outputStream.write(rebyte);
        }
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();


        return "G:/"+simpleDateFormat.format(new Date())+"/"+ file.getOriginalFilename() ;
    }




}
