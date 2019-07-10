package com.wxy.wjl.testng;

import com.alibaba.fastjson.JSON;
import com.wxy.wjl.testng.dataprovider.TestNGExtensionUtils;
import com.wxy.wjl.testng.excelUtil.ExcelUtil;
import org.testng.annotations.DataProvider;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class TestNGBaseTest  {



    public TestNGBaseTest(){
    }



    @DataProvider
    public Object[][] excelDataProvider(Method method) throws Exception{
        String belongClassNameSpace=this.getClass().getCanonicalName();
        String className=belongClassNameSpace.substring(belongClassNameSpace.lastIndexOf(".")+1,belongClassNameSpace.length());
        String excelPathString =this.buildTestExcelPath(className);
        Map<String ,String>[][] testData=getTestDataFormSheet(excelPathString,method.getName());
        this.checkTestData(testData);
        return testData;
    }

    private void checkTestData(Map<String,String>[][] testData) throws Exception{
        Map<String,String > map=testData[0][0];
        if(StringUtils.isEmpty((map.get("TEST_CASE_ID")))){
            throw new Exception("there is no 'TEST_CASE_ID' configured in your excel,please check it!");
        }
    }

    private String buildTestExcelPath(String className){
        return "src/test/resources/data-excel/"+className +".xlsx";
    }

    public static Map<String,String>[][] getTestDataFormSheet(String excel,String sheetName) throws Exception{
        try{
            ExcelUtil.read(excel);
            return ExcelUtil.getData(sheetName);
        }catch(Throwable var3){
            throw var3;
        }

    }

    //构建对象
    public static <T> T buildJavaBeanModel(String jsonName,Class<T> modelClass,Map<String,String> testData,Object object) throws Exception{
        if(modelClass != null && object != null && testData != null){
            String classNameString =modelClass.getName().substring(modelClass.getName().lastIndexOf(".")+1,modelClass.getName().length());
            String pathNameString =object.getClass().getName().substring(object.getClass().getName().lastIndexOf(".")+1,object.getClass().getName().length());
            String pathString="src/test/resources/data-json/"+pathNameString+"/"+jsonName+".json";
            String jsonData= TestNGExtensionUtils.ReadFile(pathString);
            String replace=ParseJsonString(jsonData,testData);
            return JSON.parseObject(replace,modelClass);
        }else{
            throw new Exception("'"+modelClass.getName()+"  or '"+object.getClass().getName()+"' or 'testData' should not be null" );
        }
    }


    //构建对象list
    public static <T> List<T> buildJavaBeanListModel(String jsonName, Class<T> modelClass, Map<String,String> testData, Object object) throws Exception{
        if(modelClass != null && object != null && testData != null){
            String classNameString =modelClass.getName().substring(modelClass.getName().lastIndexOf(".")+1,modelClass.getName().length());
            String pathNameString =object.getClass().getName().substring(object.getClass().getName().lastIndexOf(".")+1,object.getClass().getName().length());
            String pathString="src/test/resources/data-json/"+pathNameString+"/"+jsonName+".json";
            String jsonData= TestNGExtensionUtils.ReadFile(pathString);
            String replace=ParseJsonString(jsonData,testData);
            return JSON.parseArray(replace,modelClass);
        }else{
            throw new Exception("'"+modelClass.getName()+"  or '"+object.getClass().getName()+"' or 'testData' should not be null" );
        }
    }



    private static String ParseJsonString(String jsonData,Map<String,String> testData){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<jsonData.length();++i){
            if('$' == jsonData.charAt(i) && i+1<jsonData.length() && '{' == jsonData.charAt(i+1)){
                StringBuffer str=new StringBuffer();
                str.append(jsonData.charAt(i));
                str.append(jsonData.charAt(i+1));
                for(int j=i+2;j<jsonData.length();++j){
                    if('}' == jsonData.charAt(j)){
                        str.append(jsonData.charAt(j));
                        break;
                    }
                    str.append(jsonData.charAt(j));
                }
                sb.append((String)testData.get(str.substring(2,str.length()-1)));
                i += str.length();
            }
            if(i<jsonData.length()){
                sb.append(jsonData.charAt(i));
            }
        }
        return sb.toString();
    }


}
