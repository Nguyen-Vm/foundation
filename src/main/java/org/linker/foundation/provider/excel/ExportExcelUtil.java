package org.linker.foundation.provider.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportExcelUtil {

    /**
     * 将列表数据导出Excel
     * @param list      列表数据
     * @param wb        excel表格
     * @param sheetName sheet名称
     * @param <T>
     * @return
     */
    public static <T> Workbook export(List<T> list, Workbook wb, String sheetName) {
        if (list == null || list.size() == 0) {
            return null;
        }
        Class<?> clazz = list.get(0).getClass();

        Sheet sheet = wb.createSheet(sheetName);
        // 表头样式
        CellStyle headerStyle = wb.createCellStyle();
        // 单元格样式
        CellStyle cellStyle = wb.createCellStyle();

        Map<Integer, Field> indexFieldMap = new HashMap<>();
        Row headRow = sheet.createRow(0);

        // 写标题行
        for (Field field : clazz.getDeclaredFields()) {
            ExcelColumn column = field.getAnnotation(ExcelColumn.class);
            writeHeader(headRow, headerStyle, column.index(), column.title());
            indexFieldMap.put(column.index(), field);
        }
        // 将列表数据写入excel
        for (int i = 0; i < list.size(); i++) {
            writeRow(sheet, cellStyle, i + 1, list.get(i), indexFieldMap);
        }
        return wb;
    }

    /**
     * 写标题行
     * @param headRow
     * @param cellStyle
     * @param index
     * @param title
     */
    private static void writeHeader(Row headRow, CellStyle cellStyle, int index, String title) {
        Cell cell = headRow.createCell(index);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(title);
    }

    /**
     * 写数据行
     * @param sheet
     * @param cellStyle
     * @param i
     * @param t
     * @param indexFieldMap
     * @param <T>
     */
    private static <T> void writeRow(Sheet sheet, CellStyle cellStyle, int i, T t, Map<Integer, Field> indexFieldMap) {
        Row row = sheet.createRow(i);
        for (Map.Entry<Integer, Field> entry : indexFieldMap.entrySet()) {
            Cell cell = createCell(row, cellStyle, entry.getKey());
            Field field = entry.getValue();
            Object value = getObjectValue(t, field);
            setCellValue(field.getGenericType().toString(), cell, value);
        }
    }

    /**
     * 创建单元格
     * @param row
     * @param cellStyle
     * @param index
     * @return
     */
    private static Cell createCell(Row row, CellStyle cellStyle, Integer index) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 得到Object field的值
     * @param object
     * @param field
     * @return
     */
    private static Object getObjectValue(Object object, Field field) {
        try {
            return ObjectUtils.getObjectValue(object, field);
        } catch (Exception e) {
            throw new RuntimeException("get object field value error", e);
        }
    }

    /**
     * 设置单元格数据
     * @param fieldGenericType
     * @param cell
     * @param value
     */
    private static void setCellValue(String fieldGenericType, Cell cell, Object value) {
        switch (fieldGenericType) {
            case "class java.lang.String":
                cell.setCellValue(String.valueOf(value));
                break;
            case "class java.lang.Integer":
                cell.setCellValue((Integer) value);
                break;
            case "class java.lang.Float":
                cell.setCellValue((Float) value);
                break;
            case "class java.lang.Double":
                cell.setCellValue((Double) value);
                break;
            case "class java.lang.Boolean":
                cell.setCellValue((Boolean) value);
                break;
            case "class java.util.Date":
                cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) value));
                break;
            default:
                break;
        }
    }
}
