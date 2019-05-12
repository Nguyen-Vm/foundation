package org.linker.foundation.provider.excel;

import java.lang.reflect.Field;

public class ExcelHeader implements Comparable<ExcelHeader> {

    // 标题所在列的索引
    private int index;

    // 标题名称
    private String title;

    // 属性
    private Field filed;

    // 日期格式
    private String pattern;

    public ExcelHeader() {}

    public ExcelHeader(int index, String title, Field filed, String pattern) {
        this.index = index;
        this.title = title;
        this.filed = filed;
        this.pattern = pattern;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Field getFiled() {
        return filed;
    }

    public void setFiled(Field filed) {
        this.filed = filed;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public int compareTo(ExcelHeader o) {
        return index - o.index;
    }



}
