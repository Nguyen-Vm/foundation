package org.linker.foundation.provider.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ImportExcelUtil {

    /**
     * 读取网络文件Excel数据
     */
    public static <T> List<T> readExcel(String fileName, Class<T> clazz, int sheetNum, int titleRowNum) throws Exception {
        if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
            throw new RuntimeException("非Excel文件，导入失败！");
        }
        InputStream is = getInputStream(fileName);
        if (is == null) {
            throw new RuntimeException("获取Excel文件失败！");
        }
        return readExcel(is, fileName, clazz, sheetNum, titleRowNum);
    }

    public static InputStream getInputStream(String fileName) {
        try {
            URL url = new URL(fileName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            return conn.getInputStream();// 通过输入流获取文件数据
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取输入流Excel数据
     */
    public static <T> List<T> readExcel(InputStream is, String fileName, Class<T> clazz, int sheetNum, int titleRowNum) throws Exception {
        Workbook workbook = fileName.endsWith(".xls") ? new HSSFWorkbook(is) : new XSSFWorkbook(is);
        return readExcel(workbook, clazz, sheetNum, titleRowNum);
    }

    /**
     * 读取Excel表格数据
     */
    public static <T> List<T> readExcel(Workbook workbook, Class<T> clazz, int sheetNum, int titleRowNum) throws Exception {
        //获取相应的sheet
        Sheet sheet = workbook.getNumberOfSheets() >= 1 ? workbook.getSheetAt(sheetNum) : null;
        //获取sheet的表头
        Row titleRow = sheet != null ? sheet.getRow(titleRowNum) : null;
        if (titleRow == null) {
            throw new Exception("Excel为空");
        }
        //返回的list<T>结果
        List<T> list = getExcelList(sheet, titleRow, titleRowNum, clazz);
        workbook.close();
        return list;
    }

    private static <T> List<T> getExcelList(Sheet sheet, Row titleRow, int titleRowNum, Class<T> clazz) throws Exception {
        // 返回的list<T>结果
        List<T> list = new ArrayList<>();
        // 获取Excel表头信息
        Map<Integer, ExcelHeader> maps = HeaderUtil.getHeaderMap(titleRow, clazz);
        // 遍历当前sheet中的所有行
        Iterator<Row> iterator = sheet.rowIterator();
        // 忽略标题行及其之前行的数据
        for (int i = 0; i <= titleRowNum; i++) {
            iterator.next();
        }
        while (iterator.hasNext()) {
            Row row = iterator.next();
            //新建一个对象
            T object = clazz.newInstance();
            //遍历h该行的所有单元格
            for (Cell cell : row) {
                // 单元格所在列
                int ci = cell.getColumnIndex();
                // 所在列对应的表头
                ExcelHeader header = maps.get(ci);
                if (null == header) continue;
                // 得到单元格的值
                String value = getCellValue(cell);
                // 设置值
                ObjectUtils.setObjectValue(object, header, value);
            }
            list.add(object);
        }
        return list;
    }

    private static String getCellValue(Cell cell) {
        String value;
        switch (cell.getCellTypeEnum()) {
            // 字符串型
            case STRING:
                value = StringUtils.trim(cell.getStringCellValue());
                break;
            // 日期或数字
            case NUMERIC:
                value = StringUtils.trim(String.valueOf(cell.getNumericCellValue()));
                break;
            // 布尔型
            case BOOLEAN:
                value = StringUtils.trim(String.valueOf(cell.getBooleanCellValue()));
                break;
            // 公式
            case FORMULA:
                value = StringUtils.trim(cell.getCellFormula());
                break;
            // 空字符串
            case BLANK:
            case _NONE:
            case ERROR:
            default:
                value = "";
                break;
        }
        return value;
    }



}
