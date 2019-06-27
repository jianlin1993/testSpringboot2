package com.murong.wjl.testng.excelUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkSheet {
    private XSSFSheet xssfSheet;
    private List<WorkRow> workRows;
    private Map<String ,Integer> colIndexMap;
    private Map<String,Integer> rowIndexMap;
    private int rowCount;
    private int colCount;

    public WorkSheet(XSSFSheet xssfSheet){
        this.xssfSheet=xssfSheet;
        this.init();
    }

    private void init(){
        this.rowCount=0;
        this.colCount=0;
        XSSFRow topRow;
        XSSFCell xssfCell;
        while(true){
            topRow=this.xssfSheet.getRow(this.rowCount);
            if(topRow == null){
                break;
            }
            xssfCell=topRow.getCell(0);
            if(this.isCellEmpty(xssfCell)){
                break;
            }
            ++this.rowCount;
        }
        if(this.rowCount >0){
            topRow=this.xssfSheet.getRow(0);
            while (true){
                xssfCell=topRow.getCell(this.colCount);
                if(this.isCellEmpty(xssfCell)){
                    break;
                }
                ++this.colCount;
            }
        }
        if(this.colCount > 0){
            this.initWorkRows();
            this.initColIndexMap();
            this.initRowIndexMap();
        }
    }

    private void initWorkRows(){
        this.workRows=new ArrayList(this.rowCount);
        for(int index=0;index<this.rowCount;++index){
            XSSFRow xssfRow=this.xssfSheet.getRow(index);
            WorkRow workRow=new WorkRow(xssfRow,this.colCount);
            this.workRows.add(workRow);
        }
    }


    private void initColIndexMap(){
        this.colIndexMap=new HashMap<>();
        WorkRow topRow=(WorkRow)this.workRows.get(0);
        for(int colIndex=0;colIndex<this.colCount;++colIndex){
            WorkCell workCell=topRow.getWorkCell(colIndex);
            this.colIndexMap.put(workCell.getValue(),colIndex);
        }
    }


    private void initRowIndexMap(){
        this.rowIndexMap=new HashMap<>();
        for(int rowIndex=1;rowIndex<this.rowCount;++rowIndex){
            WorkRow dataRow=(WorkRow)this.workRows.get(rowIndex);
            WorkCell workCell=dataRow.getWorkCell(0);
            this.rowIndexMap.put(workCell.getValue(),rowIndex);
        }
    }


    private boolean isCellEmpty(XSSFCell xssfCell) {
        boolean result=false;
        if(xssfCell ==null){
            result=true;
        }
        if(!result){
            WorkCell workCell=new WorkCell(xssfCell);
            if(StringUtils.isEmpty(workCell.getValue())){
                result=true;
            }
        }
        return result;
    }


    public List<Map<String ,String>> toMapList(){
        List<Map<String ,String>> dataMaps=new ArrayList();
        WorkRow topRow=(WorkRow)this.workRows.get(0);

        for(int rowIndex=1;rowIndex<this.rowCount;++rowIndex){
            WorkRow dataRow=(WorkRow)this.workRows.get(rowIndex);
            Map<String,String> dataRowMap=new HashMap<>();

            for(int colIndex =0;colIndex < this.colCount;++colIndex){
                WorkCell header=topRow.getWorkCell(colIndex);
                WorkCell data=dataRow.getWorkCell(colIndex);
                dataRowMap.put(header.getValue(),data.getValue());
            }
            dataMaps.add(dataRowMap);
        }
        return dataMaps;
    }


}
