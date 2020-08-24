package com.wxy.wjl.testspringboot2.utils.sftp;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 文件扩展工具类
 *
 * @Author: Beijing R&D Centerwangshenghe .
 * @version:5.0.0 created at 下午11:39  2020/5/27
 * copyright @2018 Beijing Murong Information Technology Co., Ltd.
 */
public class MrFileUtilsSftpExt {


    public static void upload(String workDir, String fileUploadPath, String fileName) throws Exception {

        String sftpHost = "10.0.0.5";
        int sftpPort = 22;
        String sftpUuserName = "sftp_user";
        String sftpPassword = "sftp_user#123";
        String remoteRootDire = "/home/sftp_user/chkdata/";

        MrSFTPConfig mrSFTPConfig = new MrSFTPConfig();
        mrSFTPConfig.setHost(sftpHost);
        mrSFTPConfig.setPort(sftpPort);
        mrSFTPConfig.setUser(sftpUuserName);
        mrSFTPConfig.setPassword(sftpPassword);
        mrSFTPConfig.setRemotePath(remoteRootDire.concat(fileUploadPath));
        mrSFTPConfig.setLocalPath(workDir.concat("/").concat(fileUploadPath));
        mrSFTPConfig.setFileName(fileName);

        boolean success = MrSFTPUtil.upload(mrSFTPConfig);
        if (!success) {
            throw new Exception("upload file from nas server failed");
        }
        //push .ok 文件
        File file = new File(mrSFTPConfig.getLocalPath().concat(fileName).concat(Constants.OK));
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new Exception(e);
        }
        mrSFTPConfig.setFileName(fileName.concat(Constants.OK));
        success = MrSFTPUtil.upload(mrSFTPConfig);
        if (!success) {
            throw new Exception("upload file to nas server failed");
        }

        //上传后清理本地文件
        success = deleteFile(mrSFTPConfig.getLocalPath().concat(fileName).concat(Constants.OK));
        if (!success) {
            throw new Exception("Delete .ok file  failed");
        }
        success =deleteFile(mrSFTPConfig.getLocalPath().concat(fileName));
        if (!success) {
            throw new Exception("Delete file  failed");
        }
    }


    public static void download(String workDir, String fileUploadPath, String fileName) throws Exception {

//        String sftpHost = MrSpringContextHolder.getProperty(Constants.COMMON_NAS_SFTP_HOST);
//        int sftpPort = Integer.parseInt(MrSpringContextHolder.getProperty(Constants.COMMON_NAS_SFTP_PORT));
//        String sftpUuserName = MrSpringContextHolder.getProperty(Constants.COMMON_NAS_SFTP_USERNAME);
//        String sftpPassword = MrSpringContextHolder.getProperty(Constants.COMMON_NAS_SFTP_PASSWORD);
//        String remoteRootDire = MrSpringContextHolder.getProperty(Constants.COMMON_NAS_SFTP_REMOTE_ROOT_DIR);

        String sftpHost = "10.0.0.5";
        int sftpPort = 22;
        String sftpUuserName = "sftp_user";
        String sftpPassword = "sftp_user#123";
        String remoteRootDire = "/home/sftp_user/chkdata/";

        MrSFTPConfig mrSFTPConfig = new MrSFTPConfig();
        mrSFTPConfig.setHost(sftpHost);
        mrSFTPConfig.setPort(sftpPort);
        mrSFTPConfig.setUser(sftpUuserName);
        mrSFTPConfig.setPassword(sftpPassword);
        mrSFTPConfig.setRemotePath(remoteRootDire.concat(fileUploadPath));
        mrSFTPConfig.setLocalPath(workDir.concat("/").concat(fileUploadPath));

        //download .ok 文件
        mrSFTPConfig.setFileName(fileName.concat(Constants.OK));
        boolean success = MrSFTPUtil.download(mrSFTPConfig);
        if (!success) {
            throw new Exception("upload file to nas server failed");
        }

        mrSFTPConfig.setFileName(fileName);
        success = MrSFTPUtil.download(mrSFTPConfig);
        if (!success) {
            throw new Exception("download file from nas server failed");
        }
    }


    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }



}
