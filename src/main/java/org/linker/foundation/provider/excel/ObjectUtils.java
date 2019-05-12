package org.linker.foundation.provider.excel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RWM on 2019/3/19
 **/
public class ObjectUtils {

    public static Object getObjectValue(Object object, Field field) throws Exception {
        return getObjectValue(object, field.getName());
    }

    public static Object getObjectValue(Object object, String fieldName) throws Exception {
        Method method = object.getClass().getMethod("get" + upperCaseFirstChar(fieldName));
        return method.invoke(object);
    }

    public static void setObjectValue(Object object, ExcelHeader excelHeader, String value) throws Exception {
        try {
            Field field = excelHeader.getFiled();
            Method method;
            switch (field.getGenericType().toString()) {
                case "class java.lang.String":
                    method = object.getClass().getMethod("set" + upperCaseFirstChar(field.getName()), String.class);
                    method.invoke(object, value);
                    break;
                case "class java.lang.Integer":
                    method = object.getClass().getMethod("set" + upperCaseFirstChar(field.getName()), Integer.class);
                    method.invoke(object, new BigDecimal(value).intValue());
                    break;
                case "class java.lang.Float":
                    method = object.getClass().getMethod("set" + upperCaseFirstChar(field.getName()), Float.class);
                    method.invoke(object, new BigDecimal(value).floatValue());
                    break;
                case "class java.lang.Double":
                    method = object.getClass().getMethod("set" + upperCaseFirstChar(field.getName()), Double.class);
                    method.invoke(object, new BigDecimal(value).doubleValue());
                    break;
                case "class java.lang.Boolean":
                    method = object.getClass().getMethod("set" + upperCaseFirstChar(field.getName()), Boolean.class);
                    method.invoke(object, Boolean.valueOf(value));
                    break;
                case "class java.util.Date":
                    method = object.getClass().getMethod("set" + upperCaseFirstChar(field.getName()), Date.class);
                    method.invoke(object, new SimpleDateFormat(excelHeader.getPattern()).parse(value));
                    break;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // 字符串首字母大写，效率最高
    private static String upperCaseFirstChar(String string) {
        byte[] items = string.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
