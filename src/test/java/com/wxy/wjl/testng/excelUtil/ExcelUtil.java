package com.wxy.wjl.testng.excelUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    private static final Log log= LogFactory.getLog(ExcelUtil.class);
    private static String currentExcelFile;
    private static WorkBook workBook;

    public static void read(String excelFile) throws IOException{
        log.info("Excel File Path"+ excelFile);
        if(!excelFile.equals(currentExcelFile)){
            currentExcelFile=excelFile;
            workBook=new WorkBook(currentExcelFile);
        }
    }

    public static Map<String,String>[][] getData(String sheet){
        Object sheetList=new ArrayList<>();

        try{
            sheetList =getSheet(sheet);
        }catch (Exception var6){
            var6.printStackTrace();
        }
        Map<String,String>[][] objects=new Map[((List)sheetList).size()][1];
        int index=0;

        for(Iterator var4=((List)sheetList).iterator();var4.hasNext();++index){
            Map<String,String> dataRow=(Map)var4.next();
            objects[index][0]=dataRow;
        }
        return objects;
    }

    public static List<Map<String,String>> getSheet(String name) throws Exception{
        checkWorkBook();
        WorkSheet workSheet=workBook.getSheet(name);
        return workSheet.toMapList();
    }

    public static void checkWorkBook() throws Exception{
        if(workBook == null){
            throw new Exception("Excel WorkBook not initialized,please provider Excel data file");
        }
    }


}
