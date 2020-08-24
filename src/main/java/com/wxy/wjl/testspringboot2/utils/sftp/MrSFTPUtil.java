package com.wxy.wjl.testspringboot2.utils.sftp;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Properties;


@Slf4j
public class MrSFTPUtil {


    
    private static boolean connect(MrSFTPConfig config) throws Exception {
        

        JSch jsch = new JSch();

        try {
            Session session = jsch.getSession(config.getUser(), config.getHost(), config.getPort());
            if (log.isInfoEnabled()) {
                log.info("host:[" + config.getHost() + "] port:[" + config.getPort() + "] session created.");
            }

            if (StringUtils.isNotBlank(config.getPassword())) {
                session.setPassword(config.getPassword());
            }

            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            properties.put("GSSAPIAuthentication", "no");

            session.setConfig(properties);
            session.setTimeout(config.getTimeOut() * 1000);
            session.connect();

            config.setSession(session);

            if (log.isInfoEnabled()) {
                log.info("session connected.");
            }

            return session.isConnected();
        } catch (JSchException e) {
            if (log.isErrorEnabled()) {
                log.error("session connect error.");
                log.error(e.getMessage());
            }
            throw new Exception(e);
        }

    }

    private static boolean openChannel(MrSFTPConfig config) throws Exception {
        try {
            Session session = config.getSession();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            config.setSftp((ChannelSftp) channel);
            return channel.isConnected();
        } catch (JSchException e) {
            if (log.isErrorEnabled()) {
                log.error("sftp channel connect error.");
                log.error(e.getMessage());
            }
            throw new Exception(e);
        }
    }

    /**
     * 关闭连接
     */
    private static void disconnect(MrSFTPConfig config) throws Exception {
        ChannelSftp sftp = config.getSftp();
        Session session = config.getSession();

        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
                if (log.isInfoEnabled()) {
                    log.info("sftp channel is closed already");
                }
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
                if (log.isInfoEnabled()) {
                    log.info("session is closed already");
                }
            }
        }
    }

    /**
     * 下载单个文件
     * @param config：SFTP参数
     *         remotePath： 远程保存路径
     *         remoteFileName：下载文件名
     *         localPath：本地保存目录(以路径符号结束)
     *         localFileName：保存文件名
     */
    public static boolean download(MrSFTPConfig config) throws Exception {


        connect(config);
        openChannel(config);

        ChannelSftp sftp = config.getSftp();

        if (log.isInfoEnabled()) {
            log.info("open sftp channel successfully");
        }

        try {
            sftp.cd(config.getRemotePath());
            if (log.isInfoEnabled()) {
                log.info("sftp cd " + config.getRemotePath());
            }

            createDir(config.getLocalPath());
            sftp.lcd(config.getLocalPath());
            if (log.isInfoEnabled()) {
                log.info("sftp lcd " + config.getLocalPath());
            }

            sftp.get(config.getFileName(), config.getFileName());
            if (log.isInfoEnabled()) {
                log.info("download: " + config.getFileName() + " successfully.");
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("download: " + config.getFileName() + " error.");
                log.error(e.getMessage());
            }
            throw new Exception(e);
        } finally {
            disconnect(config);
        }

        return true;
    }

    /**
     * 上传单个文件
     * @param config：SFTP参数
     *         remotePath： 远程保存路径
     *         remoteFileName：下载文件名
     *         localPath：本地保存目录(以路径符号结束)
     *         localFileName：保存文件名
     */
    public static boolean upload(MrSFTPConfig config) throws Exception {


        connect(config);
        openChannel(config);

        ChannelSftp sftp = config.getSftp();

        if (log.isInfoEnabled()) {
            log.info("open sftp channel successfully");
        }

        try {
            createDir(config.getRemotePath(), sftp );

            sftp.cd(config.getRemotePath());
            if (log.isInfoEnabled()) {
                log.info("sftp cd " + config.getRemotePath());
            }

            sftp.lcd(config.getLocalPath());
            if (log.isInfoEnabled()) {
                log.info("sftp lcd " + config.getLocalPath());
            }

            sftp.put(config.getFileName(), config.getFileName());

            if (log.isInfoEnabled()) {
                log.info("upload: " + config.getFileName() + " successfully.");
            }
        } catch (SftpException e) {
            if (log.isErrorEnabled()) {
                log.error("upload: " + config.getFileName() + " error.");
                log.error(e.getMessage());
            }

            throw new Exception(e);
        } finally {
            disconnect(config);
        }

        return true;
    }

    /**
     * 创建一个文件目录
     */
    private static void createDir(String createpath, ChannelSftp sftp) throws SftpException {
        log.info("create remote path ");
        if (isDirExist(createpath, sftp)) {
            log.info("remote path exists = {}",createpath);
            sftp.cd(createpath);
            return;
        }
        String[] paths = createpath.split("/");
        StringBuilder filePath = new StringBuilder("/");
        for (String path : paths) {
            if (path.equals("")) {
                continue;
            }
            filePath.append(path).append("/");
            if (isDirExist(filePath.toString(), sftp)) {
                sftp.cd(filePath.toString());
            } else {
                // 建立目录
                try{
                    sftp.mkdir(filePath.toString());
                }catch (SftpException e){
                    log.info("The directory = {} , has been created by another thread ",filePath.toString());
                    // 目录已被其他线程创建
                    sftp.cd(filePath.toString());
                }
                // 进入并设置为当前目录
                sftp.cd(filePath.toString());
            }
        }
        sftp.cd(createpath);
    }

    /**
     * 创建本地目录
     * @param filePath
     * @return
     */
    public static boolean createDir(String filePath){
        try {

            File dir = new File(filePath);
            log.info("dir.exists() : [{}]", dir.exists());
            if(!dir.exists()){
                log.info("mkdirs : [{}]", filePath);
                dir.mkdirs();
            }
            return true;
        }catch (Exception e){
            log.info(e.getMessage());
            return false;
        }
    }

    /**
     * 判断目录是否存在
     */
    private static boolean isDirExist(String directory, ChannelSftp sftp) throws SftpException {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }


}
