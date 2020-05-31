package com.yjz.file.util;

import com.yjz.file.constant.FileConstant;
import com.yjz.file.enums.ErrorCodeEnum;
import com.yjz.file.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Description
 * @Author youjianzhao
 * @Date 2020/5/31 14:39
 * @Version
 */
@Slf4j
public class FileUtil {

    private FileUtil() {
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件路径
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteFileOrDir(File dir) {
        if (dir == null) {
            return true;
        }
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteFileOrDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        log.info("FileUtil#deleteFileOrDir: delete file: {}", dir.getAbsolutePath());
        return dir.delete();
    }

    /**
     * 批量上传文件
     *
     * @param files    文件数组
     * @param filePath 存储路径
     * @return
     */
    public static List<String> upload(MultipartFile[] files, final String filePath) {
        List<String> pictureList = new ArrayList<>();
        //判断file数组不能为空并且长度大于0
        if (files != null && files.length > 0) {
            //循环获取file数组中得文件
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                //保存文件
                String tmp = upload(file, filePath);
                pictureList.add(tmp);
            }
        }
        return pictureList;
    }

    public static List<String> upload(List<MultipartFile> files, final String filePath) {
        List<String> pictureList = new ArrayList<>();
        for (MultipartFile file : files) {
            //保存文件
            String tmp = upload(file, filePath);
            pictureList.add(tmp);
        }
        return pictureList;
    }

    /**
     * 上传文件至服务器,需要先创建相应的文件夹
     *
     * @param file     文件
     * @param filePath 文件存储子路径
     * @return 文件存储路径
     */
    public static String upload(MultipartFile file, final String filePath) {
        File targetFile;
        // 文件存储路径
        StringBuilder result = new StringBuilder(FileConstant.FILE_PATH);
        // 获取文件名 带后缀
        String fileName = file.getOriginalFilename();
        if (!StringUtils.isEmpty(fileName)) {
            //文件后缀
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            //新的文件名
            fileName = System.currentTimeMillis()+ "_" + new Random().nextInt(1000) + fileF;
            // 更新文件存储路径
            result.append(filePath);

            //先判断文件是否存在
            String fileAdd = new SimpleDateFormat("yyyyMMdd").format(new Date());
            File file1 = new File("/data/resource" + result.toString() + "/" + fileAdd);
            //如果文件夹不存在则创建
            if (!file1.exists() && !file1.isDirectory()) {
                file1.mkdirs();
            }
            targetFile = new File(file1, fileName);
            try {
                file.transferTo(targetFile);

                // 更新文件存储路径
                result.append("/").append(fileAdd).append("/").append(fileName);
            } catch (IOException e) {
                log.error("FileUtil upload,file.transferTo fail,msg={}", e);
                throw new BusinessException(ErrorCodeEnum.FILE_HANDLE_ERROR);
            }
            return FileConstant.URL_PRE + result.toString();
        } else {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_NULL);
        }
    }

    /**
     * 保存文件
     *
     * @param file
     * @param fileName
     */
    public static void save(MultipartFile file, final String fileName) {
        File tmpFile = new File(fileName);
        //如果文件夹不存在则创建
        if (!tmpFile.exists() && !tmpFile.isDirectory()) {
            tmpFile.mkdir();
        }
        File targetFile = new File(tmpFile, "");
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            log.error("FileUtil save,file.transferTo fail,msg={}", e);
            throw new BusinessException(ErrorCodeEnum.FILE_HANDLE_ERROR);
        }
    }
}
