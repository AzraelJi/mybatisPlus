package com.yang.template.util;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ExportWordUtils {

    public static void testTemplateWrite() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("code", "GHHZ-2017-B-008");
        params.put("user", "张三");
        params.put("name", "ETC车道控制器");
        params.put("production_date", "2019-08-10");
        params.put("date", "2019-08-10");
        params.put("count", "10套");
        params.put("addr", "永汉");
        params.put("type", "研华IPC6606");
        params.put("cause", "   新购设备入库");
        params.put("spare_parts_manager_opinion", "   同意入库");
        params.put("s_p_m_name", "季阳");
        params.put("s_p_m_date", "2019-10-1");
        params.put("supervisor_opinion", "   同意入库");
        params.put("s_name", "季阳");
        params.put("s_date", "2019-10-1");
        String filePath = "C:\\Users\\jjyy\\Desktop\\temp.docx";
        @Cleanup InputStream is = new FileInputStream(filePath);
        XWPFDocument doc = new XWPFDocument(is);
        replaceInTable(doc, params);
        @Cleanup OutputStream os = new FileOutputStream("C:\\Users\\jjyy\\Desktop\\ok.docx");
        doc.write(os);
    }

    public static void exportTemplateExcel(ClassLoader classLoader, String path, String titleString,
                                           HttpServletResponse response, Map<String, String> params) {
        try {

            path = "classpath:/templates/" + path;
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
            Resource resource = resolver.getResource(path);
            @Cleanup InputStream is = resource.getInputStream();
            String titleName = new String(titleString.getBytes("GB2312"), "ISO8859-1");
            response.setContentType("application/x-word");
            response.setHeader("Content-Disposition", "attachment;filename=" + titleName + ".docx");
            XWPFDocument doc = new XWPFDocument(is);
            replaceInTable(doc, params);
            @Cleanup OutputStream os = response.getOutputStream();
            doc.write(os);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    private static void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            replaceInPara(para, params);
        }
    }

    private static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = matcher(runText);
                if (matcher.find()) {
                    while ((matcher = matcher(runText)).find()) {
                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                    }
                    para.removeRun(i);
                    para.insertNewRun(i).setText(runText);
                }
            }
        }
    }


    private static void replaceInTable(XWPFDocument doc, Map<String, String> params) {
        doc.getTablesIterator().forEachRemaining(xwpfTable -> {
            xwpfTable.getRows().forEach(xwpfTableRow -> {
                xwpfTableRow.getTableCells().forEach(xwpfTableCell -> {
                    String text = xwpfTableCell.getText();
                    if (matcher(text).find()) {
                        xwpfTableCell.removeParagraph(0);
                        int b = text.indexOf("{"), e = text.indexOf("}");
                        xwpfTableCell.setText(params.get(text.substring(b + 1, e)));
                    }
                });
            });
        });
    }

    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(str);
    }

}
