package com.qinzi123.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @title: FileService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface FileService {

    Map<String, String> uploadFile(MultipartFile file, String columnName);
}
