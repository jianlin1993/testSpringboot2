package com.wxy.wjl.testspringboot2.enums;

/**
 * msl系统名与数据库用户名映射
 */
public enum MslSystemDsEnum {
    ACM("Account","CSDACM"),
    ACT("Accounting","CSDACT"),
    BAT("Batch","BSDBAT"),
    BAP("Basic-service","CSDBAP"),
    BUI("Boss","BSDBUI"),
    URM("Customer","CSDURM"),
    CCS("Customer-campaign","CSDCCS"),
    DFS("Data-feed","DSDDFS"),
    EMS("Ecp-message","TSDEMS"),
    EJS("Ecp-job","TSDEJS"),
    EGW("Gateway-external","CGDEGW"),
    IGW("Gateway-internal","CGDIGW"),
    IAS("Industry-application","ASDIAS"),
    LNS("Loan-service","CSDLNS"),
    LON("Loan-account","CSDLON"),
    NTC("Notification","TSDNTC"),
    PRD("Product","CSDPRD"),
    SAS("Savings-service","CSDSAS"),
    WLS("Wallet-service","CSDWLS"),
    OPN("Gateway-openapi","CGDOGW"),
            ;
    private String systemName;
    private String userName;


    MslSystemDsEnum(String systemName, String userName) {
        this.systemName = systemName;
        this.userName = userName;
    }

    public static String getUserNameBySystemName(String systemName) {
        if (null == systemName) {
            return null;
        }
        for (MslSystemDsEnum mslSystemDsEnum : values()) {
            if (mslSystemDsEnum.getSystemName().equals(systemName)) {
                return mslSystemDsEnum.getUserName();
            }
        }
        return null;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSystemName() {
        return systemName;
    }
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
