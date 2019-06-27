package com.murong.wjl.testng.excelUtil;

import org.apache.poi.xssf.usermodel.XSSFCell;

public class WorkCell {
    private XSSFCell xssfCell;
    private String value;

    public WorkCell(XSSFCell xssfCell){
        this.xssfCell=xssfCell;
        switch (xssfCell.getCellType()){
            case 0:
                this.value=String.valueOf(xssfCell.getNumericCellValue());
                break;
            case 1:
                this.value=xssfCell.getStringCellValue();
                break;
            case 2:
                default:
                    break;
            case 3:
                this.value="";
                break;
            case 4:
                this.value=String.valueOf(xssfCell.getBooleanCellValue());
        }

    }

    public String getValue(){
        return this.value;
    }
    public void setValue(String value){
        this.value=value;
        this.xssfCell.setCellValue(value);
    }

}
