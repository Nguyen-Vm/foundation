package org.linker.foundation.provider.excel;

import org.junit.Test;

import java.io.FileInputStream;
import java.util.List;

public class ImportExcelTest {

    @Test
    public void importExcel() throws Exception {
        String fileName = "staff_import.xlsx";
        List<StaffImport> staffs = ImportExcelUtil.readExcel(new FileInputStream(fileName), fileName, StaffImport.class, 0, 0);
        System.out.println(staffs);
    }
}
