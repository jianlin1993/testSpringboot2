package com.wxy.wjl.testng.excelUtil;


import org.apache.commons.lang.NullArgumentException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WorkBook {
    private String filePath;
    private XSSFWorkbook xssfWorkBook;
    private Map<String,WorkSheet> sheetMap;

    public WorkBook(String excelFile){
        this.filePath=excelFile;
        File file=new File(excelFile);
        try{
            FileInputStream fileInputStream=new FileInputStream(file);
            this.xssfWorkBook =new XSSFWorkbook(fileInputStream);
        }catch(FileNotFoundException var5){
            var5.printStackTrace();
        }catch(IOException var6){
            var6.printStackTrace();
        }
        this.sheetMap=new HashMap();
    }


    public WorkSheet getSheet(String name) throws Exception{
        if(this.sheetMap.containsKey(name)){
            return (WorkSheet)this.sheetMap.get(name);
        }else{
            XSSFSheet xssfSheet=this.xssfWorkBook.getSheet(name);
            if(xssfSheet == null){
                throw new NullArgumentException("There is no sheet named'"+name+"exists in the excel,please create it before your test!");
            }else{
                WorkSheet workSheet=new WorkSheet(xssfSheet);
                this.sheetMap.put(name,workSheet);
                return workSheet;
            }
        }
    }

//    public void updateSheet(String sheetName,Map<String ,String> data)  throws Exception{
//        WorkSheet workSheet=(WorkSheet)this.sheetMap.get(sheetName);
//        workSheet.updateRow(data);
//
//    }


}
