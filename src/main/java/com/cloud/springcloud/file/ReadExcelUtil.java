package com.cloud.springcloud.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lubing
 * @describe 一句话描述
 * @Date 2019/12/11 14:35
 * @since
 */

public class ReadExcelUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String EXCEL_XLS = ".xls";
    private static final String EXCEL_XLSX = ".xlsx";

    /**
     *读取excel数据
     * @throws Exception
     *
     */
    public static List<String> readExcelInfo(String url) throws Exception{
        /*
         * workbook:工作簿,就是整个Excel文档
         * sheet:工作表
         * row:行
         * cell:单元格
         */

//        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(url)));
//        支持excel2003、2007
        File excelFile = new File(url);//创建excel文件对象
        InputStream is = new FileInputStream(excelFile);//创建输入流对象
        checkExcelVaild(excelFile);
        Workbook workbook = getWorkBook(is, excelFile);
//        Workbook workbook = WorkbookFactory.create(is);//同时支持2003、2007、2010
//        获取Sheet数量
        int sheetNum = workbook.getNumberOfSheets();
//      创建二维数组保存所有读取到的行列数据，外层存行数据，内层存单元格数据
        List<String> dataList = new ArrayList<String>();
//        FormulaEvaluator formulaEvaluator = null;
//        遍历工作簿中的sheet,第一层循环所有sheet表
        for(int index = 0;index<sheetNum;index++){
            Sheet sheet = workbook.getSheetAt(index);
            if(sheet==null){
                continue;
            }
            System.out.println("表单行数："+sheet.getLastRowNum());  //2019-12-10 16:27:25
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            如果当前行没有数据跳出循环，第二层循环单sheet表中所有行
            for(int rowIndex=1;rowIndex<=sheet.getLastRowNum();rowIndex++){
                Row row = sheet.getRow(rowIndex);
//                根据文件头可以控制从哪一行读取，在下面if中进行控制
                if(row==null){
                    continue;
                }
//                遍历每一行的每一列，第三层循环行中所有单元格
                List<String> cellList = new ArrayList<String>();
                StringBuilder sb =  new StringBuilder("UPDATE ecommerce_order set  VBELN='0000") ;
                for(int cellIndex=0;cellIndex<row.getLastCellNum();cellIndex++){
                    Cell cell = row.getCell(cellIndex);
                    if(cell != null ){
                        if(cellIndex == 0 ){
                            sb.append(getCellValue(cell)).append("', POSNR='0000");
                        }
                        if(cellIndex == 1 ){
                            sb.append(getCellValue(cell)).append("',sync_sap_status = '2' ,sync_sap_time='").append(sdf.format(new Date())).append("' where  material_code ='");
                        }
                        if(cellIndex == 2 ){
                            sb.append(getCellValue(cell)).append("' and order_code='");
                        }
                        if(cellIndex == 3 ){
                            sb.append(getCellValue(cell)).append("';") ;
                        }
                        continue;
                    }
                    cellList.add(sb.toString());

                }
                dataList.add(sb.toString());

            }

        }
        is.close();
        return dataList;
    }
    /**
     *获取单元格的数据,暂时不支持公式
     *
     *
     */
    public static String getCellValue(Cell cell){
        CellType cellType = cell.getCellTypeEnum();
        String cellValue = "";
        if(cell==null || cell.toString().trim().equals("")){
            return null;
        }

        if(cellType==CellType.STRING){
            cellValue = cell.getStringCellValue().trim();
            return cellValue = StringUtils.isEmpty(cellValue)?"":cellValue;
        }
        if(cellType==CellType.NUMERIC){
            if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                //cellValue = DateFormatUtil.formatDurationYMD(cell.getDateCellValue().getTime());
            } else {  //否
                cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
            }
            return cellValue;
        }
        if(cellType==CellType.BOOLEAN){
            cellValue = String.valueOf(cell.getBooleanCellValue());
            return cellValue;
        }
        return null;

    }
    /**
     *判断excel的版本，并根据文件流数据获取workbook
     * @throws IOException
     *
     */
    public static Workbook getWorkBook(InputStream is,File file) throws Exception{

        Workbook workbook = null;
        if(file.getName().endsWith(EXCEL_XLS)){
            workbook = new HSSFWorkbook(is);
        }else if(file.getName().endsWith(EXCEL_XLSX)){
            workbook = new XSSFWorkbook(is);
        }

        return workbook;
    }
    /**
     *校验文件是否为excel
     * @throws Exception
     *
     *
     */
    public static void checkExcelVaild(File file) throws Exception {
        String message = "该文件是EXCEL文件！";
        if(!file.exists()){
            message = "文件不存在！";
            throw new Exception(message);
        }
        if(!file.isFile()||((!file.getName().endsWith(EXCEL_XLS)&&!file.getName().endsWith(EXCEL_XLSX)))){
            System.out.println(file.isFile()+"==="+file.getName().endsWith(EXCEL_XLS)+"==="+file.getName().endsWith(EXCEL_XLSX));
            System.out.println(file.getName());
            message = "文件不是Excel";
            throw new Exception(message);
        }
    }
/*    public static void main(String[] args) throws Exception {
        readExcelInfo("g://批量新增设备表.xlsx");
    }*/


    /**
     * 天津银行功能
     * @param url
     * @return
     * @throws Exception
     */
    public static List<String> readExcelInfo1(String url) throws Exception{


//        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(url)));
//        支持excel2003、2007
        File excelFile = new File(url);//创建excel文件对象
        InputStream is = new FileInputStream(excelFile);//创建输入流对象
        checkExcelVaild(excelFile);
        Workbook workbook = getWorkBook(is, excelFile);
//        Workbook workbook = WorkbookFactory.create(is);//同时支持2003、2007、2010
//        获取Sheet数量
        int sheetNum = workbook.getNumberOfSheets();
//      创建二维数组保存所有读取到的行列数据，外层存行数据，内层存单元格数据
        List<String> dataList = new ArrayList<String>();
//        FormulaEvaluator formulaEvaluator = null;
//        遍历工作簿中的sheet,第一层循环所有sheet表
        for(int index = 0;index<sheetNum;index++){
            Sheet sheet = workbook.getSheetAt(index);
            if(sheet==null){
                continue;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            如果当前行没有数据跳出循环，第二层循环单sheet表中所有行
            for(int rowIndex=1;rowIndex<=sheet.getLastRowNum();rowIndex++){
                Row row = sheet.getRow(rowIndex);
//                根据文件头可以控制从哪一行读取，在下面if中进行控制
                if(row==null){
                    continue;
                }
//                遍历每一行的每一列，第三层循环行中所有单元格
                List<String> cellList = new ArrayList<String>();
                StringBuilder sb =  new StringBuilder("UPDATE ecommerce_order set  VBELN='0000") ;
                for(int cellIndex=0;cellIndex<row.getLastCellNum();cellIndex++){
                    Cell cell = row.getCell(cellIndex);
                    if(cell != null ){
                        if(cellIndex == 0 ){
                            sb.append(getCellValue(cell)).append("', POSNR='0000");
                        }
                        if(cellIndex == 1 ){
                            sb.append(getCellValue(cell)).append("',sync_sap_status = '2' ,sync_sap_time='").append(sdf.format(new Date())).append("' where  material_code ='");
                        }
                        if(cellIndex == 2 ){
                            sb.append(getCellValue(cell)).append("' and order_code='");
                        }
                        if(cellIndex == 3 ){
                            sb.append(getCellValue(cell)).append("';") ;
                        }
                        continue;
                    }
                    cellList.add(sb.toString());

                }
                dataList.add(sb.toString());

            }

        }
        is.close();
        return dataList;
    }
}
