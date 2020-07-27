package com.yang.template.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Slf4j
public class ExportExcelUtils {

    public static void exportExcel(@SuppressWarnings("rawtypes") Map beans, String srcPath, OutputStream os) {
        XLSTransformer transformer = new XLSTransformer();
        try {
            // 获得模板的输入流
            FileInputStream in = new FileInputStream(srcPath);
            // 将beans通过模板输入流写到workbook中
            Workbook workbook = transformer.transformXLS(in, beans);
            // 将workbook中的内容用输出流写出去
            workbook.write(os);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("IO Exception", e);
                }
            }
        }
    }

    public static void exportExcel(@SuppressWarnings("rawtypes") Map beans, InputStream in, OutputStream os) {
        XLSTransformer transformer = new XLSTransformer();
        try {
            // 将beans通过模板输入流写到workbook中
            Workbook workbook = transformer.transformXLS(in, beans);
            // 将workbook中的内容用输出流写出去
            workbook.write(os);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("IO Exception", e);
                }
            }
        }
    }

    public static void exportTemplateExcel(ClassLoader classLoader, String path, String titleString,
                                           HttpServletResponse response, Map<String, Object> bean) {
        try {
            path = "classpath:/templates/" + path;
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
            Resource resource = resolver.getResource(path);
            String titleName = new String(titleString.getBytes("GB2312"), "ISO8859-1");
            response.setContentType("application/x-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + titleName + ".xls");
            OutputStream os = response.getOutputStream();
            ExportExcelUtils.exportExcel(bean, resource.getInputStream(), os);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    public static void exportExcel(Map<String, Object> data, String templateExcelAbsolutePath, String downloadFileName,
                                   HttpServletResponse response,
                                   List<CellRangeAddress> mergeRegions) {
        try (FileInputStream in = new FileInputStream(templateExcelAbsolutePath); OutputStream os = response.getOutputStream()) {
            String titleName = new String(downloadFileName.getBytes("GB2312"), "ISO8859-1");
            response.setContentType("application/x-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + titleName + ".xls");

            transformXLS(data, os, in, mergeRegions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void exportExcel(ClassLoader classLoader, Map<String, Object> data, String path,
                                   String downloadFileName, HttpServletResponse response, List<CellRangeAddress> mergeRegions) {
        path = "classpath:/templates/" + path;
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
        Resource resource = resolver.getResource(path);
        try (OutputStream os = response.getOutputStream()) {
            String titleName = new String(downloadFileName.getBytes("GB2312"), "ISO8859-1");
            response.setContentType("application/x-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + titleName + ".xls");
            transformXLS(data, os, resource.getInputStream(), mergeRegions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void transformXLS(Map<String, Object> data, OutputStream os, InputStream in,
                                     List<CellRangeAddress> mergeRegions)
            throws IOException, ParsePropertyException, InvalidFormatException {
        XLSTransformer transformer = new XLSTransformer();
        Workbook workbook = transformer.transformXLS(in, data);
        if (isNotEmpty(mergeRegions)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (CellRangeAddress r : mergeRegions) {
                if (r.getNumberOfCells() >= 2) {
                    sheet.addMergedRegion(r);
                }
            }
        }
        workbook.write(os);
    }

    protected static String formatLeaveInfo(String conf, Float hour, Float value) {
        if (value == 0) {
            return "";
        }
        String val = "";
        NumberFormat format2 = NumberFormat.getNumberInstance();
        format2.setRoundingMode(RoundingMode.HALF_UP);
        format2.setMaximumFractionDigits(2);
        if ("DAY".equals(conf)) {
            val = format2.format(value / hour) + "天";
        } else if ("HOUR".equals(conf)) {
            val = format2.format(value) + "小时";
        } else {
            int days = new Float(value / hour).intValue();
            Float time = value - days * hour;
            if (days > 0) {
                val += days + "天";
            }
            if (time >= 0.01) {
                val += (format2.format(time) + "小时");
            }
        }
        return val;
    }

    public static void setRegionStyle(HSSFSheet sheet, CellRangeAddress region,
                                      HSSFCellStyle cs) {
        for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null)
                row = sheet.createRow(i);
            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                HSSFCell cell = row.getCell(j);
                if (cell == null) {
                    cell = row.createCell(j);
                    cell.setCellValue("");
                }
                cell.setCellStyle(cs);
            }
        }
    }

}
