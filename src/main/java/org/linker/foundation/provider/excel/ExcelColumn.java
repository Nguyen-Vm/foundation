package org.linker.foundation.provider.excel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    /**
     * 标题名称
     */
    String title();

    /**
     * 标题所在列的索引
     */
    int index() default 0;

    /**
     * 字符串转日期pattern
     */
    String pattern() default "yyyy-MM-dd HH:mm:ss";

}
