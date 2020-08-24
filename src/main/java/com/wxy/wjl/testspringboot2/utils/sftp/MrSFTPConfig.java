package com.wxy.wjl.testspringboot2.utils.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;


public class MrSFTPConfig {

    private ChannelSftp sftp;

    private Session session;


    /**
     * 服务地址
     */
    private String host;

    /**
     * 服务端口
     */
    private int port = 22;

    /**
     * 超时时间
     */
    private int timeOut = 30;

    /**
     * 登录用户
     */
    private String user;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 目标目录
     */
    private String remotePath;

    /**
     * 本地目录
     */
    private String localPath;

    /**
     * 文件名称
     */
    private String fileName;

    public MrSFTPConfig() {
    }

    public MrSFTPConfig(String host, String user, String password, String remotePath, String localPath, String fileName) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.remotePath = remotePath;
        this.localPath = localPath;
        this.fileName = fileName;
    }

    public MrSFTPConfig( String host, int port, String user, String password, String remotePath, String localPath, String fileName) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.remotePath = remotePath;
        this.localPath = localPath;
        this.fileName = fileName;
    }

    public MrSFTPConfig(String host, int port, int timeOut, String user, String password, String remotePath, String localPath, String fileName) {
        this.host = host;
        this.port = port;
        this.timeOut = timeOut;
        this.user = user;
        this.password = password;
        this.remotePath = remotePath;
        this.localPath = localPath;
        this.fileName = fileName;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ChannelSftp getSftp() {
        return sftp;
    }

    public void setSftp(ChannelSftp sftp) {
        this.sftp = sftp;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
