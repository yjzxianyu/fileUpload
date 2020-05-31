package com.yjz.file.controller;

import com.yjz.file.enums.ErrorCodeEnum;
import com.yjz.file.exception.BusinessException;
import com.yjz.file.util.FileUtil;
import com.yjz.file.util.ResultUtil;
import com.yjz.file.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description
 * @Author youjianzhao
 * @Date 2020/5/31 14:57
 * @Version
 */
@RestController
@RequestMapping("/resource")
@Slf4j
@CrossOrigin(allowedHeaders = "*",allowCredentials = "true",maxAge = 3600,methods = {RequestMethod.DELETE,RequestMethod.POST,
        RequestMethod.PUT,RequestMethod.OPTIONS,RequestMethod.GET})
public class FileController {


    /**
     * 实验室上传文件
     *
     * @param files 文件数组
     */
    @PostMapping("/upload")
    public ResultVo upload(@RequestParam(value = "files") MultipartFile[] files,
                           @RequestParam(value = "project", required = false, defaultValue = "demo") String project
    ) {
        if (files == null || files.length <= 0) {
            log.error("FileController.upload(file,modular): param is null.");
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_NULL);
        }
        List<String> list = FileUtil.upload(files, project);
        return ResultUtil.success(list);
    }
}
