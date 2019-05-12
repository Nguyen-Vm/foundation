package org.linker.foundation.provider.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeaderUtil {

    /**
     *
     * @param titleRow
     * @param clz
     * @return
     */
    public static Map<Integer, ExcelHeader> getHeaderMap(Row titleRow, Class clz) {
        // 得到Excel表头信息
        List<ExcelHeader> headers = getHeaderList(clz);
        Map<Integer, ExcelHeader> maps = new HashMap<>();
        for (Cell cell : titleRow) {
            int index = cell.getColumnIndex();
            String title = cell.getStringCellValue();
            ExcelHeader eh = headers.get(index);
            if (eh == null) continue;
            if (eh.getTitle().equals(title.trim())) {
                maps.put(cell.getColumnIndex(), eh);
            }
        }
        return maps;
    }

    /**
     * 获取Excel表头信息
     */
    private static List<ExcelHeader> getHeaderList(Class clz) {
        List<ExcelHeader> headers = new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        for (Class clazz = clz; clazz != Object.class; clazz = clazz.getSuperclass()) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        for (Field field : fields) {
            // 是否使用ExcelField注解
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn column = field.getAnnotation(ExcelColumn.class);
                headers.add(new ExcelHeader(column.index(), column.title(), field, column.pattern()));
            }
        }
        Collections.sort(headers);
        return headers;
    }

}
