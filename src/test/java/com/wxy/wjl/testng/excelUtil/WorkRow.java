package com.wxy.wjl.testng.excelUtil;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.util.ArrayList;
import java.util.List;

public class WorkRow {
    private List<WorkCell> workCells;

    public WorkRow(XSSFRow xssfRow,int colCount){
        this.workCells=new ArrayList<>(colCount);

        for(int index=0;index<colCount;++index){
            XSSFCell xssfCell=xssfRow.getCell(index);
            if(xssfCell == null){
                xssfCell=xssfRow.createCell(index);
                xssfCell.setCellValue("");
            }
            WorkCell workCell=new WorkCell(xssfCell);
            this.workCells.add(workCell);
        }
    }

    public WorkCell getWorkCell(int index){
        return (WorkCell)this.workCells.get(index);
    }

    public void update(Integer colIndex,String value){
        WorkCell workCell=(WorkCell)this.workCells.get(colIndex);
        workCell.setValue(value);
    }



}
