package org.linker.foundation.provider.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExportExcelTest {

    @Test
    public void exportExcel() throws Exception {
        List<StaffExport> list = new ArrayList<>();
        list.add(new StaffExport("阮威敏", "技术", new Date(), 101, 52312.30, true));
        list.add(new StaffExport("阮威敏", "人事", new Date(), 201, 73621.20, false));
        list.add(new StaffExport("阮威敏", "财务", new Date(), 301, 58321.10, false));

        Workbook book = ExportExcelUtil.export(list, new SXSSFWorkbook(), "员工信息");
        try (OutputStream os = new FileOutputStream("staff.xlsx")) {
            book.write(os);
        }
    }
}
