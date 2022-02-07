package com.qinzi123.controller.web;

import com.qinzi123.service.FileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @title: FileController
 * @package: com.qinzi123.controller.web
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "上传文件")
public class FileController {

    @Autowired
    FileService fileService;

    /**
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @Author: jie.yuan
     * @Description: 上传文件
     * @Date: 2021/11/1 0001 10:53
     * @Param [file, columnName]
     **/
    @RequestMapping(value = "/manage/uploadFile/{columnName}", method = RequestMethod.POST)
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("columnName") String columnName) {
        return fileService.uploadFile(file, columnName);
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @Author: jie.yuan
     * @Description: 上传图片
     * @Date: 2021/11/1 0001 10:53
     * @Param [file, columnName]
     **/
    @RequestMapping(value = "/uploadPicture/{columnName}", method = RequestMethod.POST)
    public Map<String, String> uploadPicture(@RequestParam("file") MultipartFile file, @PathVariable("columnName") String columnName) {
        return fileService.uploadFile(file, columnName);
    }
}
