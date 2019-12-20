package com.cloud.springcloud.controller;


import com.cloud.springcloud.file.FileUtility;
import com.cloud.springcloud.file.ReadExcelUtil;
import com.cloud.springcloud.file.TxtFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/file")
public class FileUploadController {


    @RequestMapping(value = "/upload" ,method = {RequestMethod.POST} )
    public  String   fileUploag(@RequestParam (value = "file" ) MultipartFile file) throws Exception {


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

        String url = "G:/"+simpleDateFormat.format(new Date())+"/"+ file.getOriginalFilename() ;


        List<String>  list= ReadExcelUtil.readExcelInfo(url);


        String  txtUrl =   "F:/"+simpleDateFormat.format(new Date())+"/"+ file.getOriginalFilename().replaceAll("xlsx","txt") ;
        FileUtility.judeDirExists("F:/"+simpleDateFormat.format(new Date()));
        //写文件
        TxtFile.writeFileContext(list,txtUrl);

        return list.toString().replaceAll(";," ,";") ;

    }


    @RequestMapping(value = "/upload1" ,method = {RequestMethod.POST} )
    public  String   fileUploag1(@RequestParam (value = "file" ) MultipartFile file) throws Exception {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd") ;

        byte[] rebyte = new byte[1024] ;
        InputStream fileInputStream = file.getInputStream() ;
        FileUtility.judeDirExists("F:/"+simpleDateFormat.format(new Date())+"/TJ");
        OutputStream outputStream = new FileOutputStream("F:/"+simpleDateFormat.format(new Date())+"/TJ/"+ file.getOriginalFilename()) ;
        while (fileInputStream.read(rebyte) != -1){
            outputStream.write(rebyte);
        }
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();

        String url = "F:/"+simpleDateFormat.format(new Date())+"/TJ/"+ file.getOriginalFilename() ;

        List<String>  list= ReadExcelUtil.readExcelInfo1(url);

        String  txtUrl =   "F:/"+simpleDateFormat.format(new Date())+"/TJ/"+ file.getOriginalFilename().replaceAll("xlsx","txt") ;
        FileUtility.judeDirExists("F:/"+simpleDateFormat.format(new Date())+"/TJ");
        //写文件
        TxtFile.writeFileContext(list,txtUrl);

        return list.toString().replaceAll(";," ,";") ;

    }


}
