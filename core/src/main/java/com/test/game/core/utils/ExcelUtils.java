package com.test.game.core.utils;

import com.google.common.base.Preconditions;
import com.test.game.core.gen.*;
import com.test.game.core.gen.Class;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/** @Auther: zhouwenbin @Date: 2019/8/9 00:00 */
public abstract class ExcelUtils {

    // excel文件后缀
    private static final String excel_suffix = ".xlsx";

    private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    private ExcelUtils() {}

    /**
     * 是不是excel文件
     *
     * @param file
     * @return
     */
    public static boolean isExcel(File file) {
        if (file.isHidden()) {
            return false;
        }
        if (file.getName().startsWith("~")) {
            return false;
        }
        return file.getName().endsWith(excel_suffix);
    }

    public static void parse(
            String excelDir,
            Map<String, ConfigUtils.CustomClass> name2customClass,
            List<Class> classes,
            List<DataWithClass> datas) {
        File dir = new File(excelDir);
        Preconditions.checkArgument(dir.exists(), "directory not exists : %s", excelDir);
        Preconditions.checkArgument(dir.isDirectory());
        List<File> excelFiles = new ArrayList<>();
        File[] tempFiles = dir.listFiles();
        if (tempFiles == null) {
            return;
        }
        // 筛选出excel文件
        for (File file : tempFiles) {
            if (file.isFile()) {
                if (isExcel(file)) {
                    excelFiles.add(file);
                }
            }
        }
        excelFiles.sort(Comparator.comparing(File::getName));
        for (File file : excelFiles) {
            log.info("parse file : " + file.getName());
            Workbook workbook = null;
            try {
                workbook = WorkbookFactory.create(file, null, true);
                Iterator<Sheet> it = workbook.sheetIterator();
                while (it.hasNext()) {
                    Sheet sheet = it.next();
                    if (StringUtils.isNullOrEmpty(sheet.getSheetName())
                            && sheet.getSheetName().startsWith("q_")
                            && StringUtils.allEnglish(sheet.getSheetName())) {
                        parseSheet(file.getName(), sheet, name2customClass, classes, datas);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void parseSheet(
            String fileName,
            Sheet sheet,
            Map<String, ConfigUtils.CustomClass> name2customClass,
            List<Class> classes,
            List<DataWithClass> datas) {
        boolean client = false;
        boolean server = false;
        // 表名
        String sheetName = sheet.getSheetName();
        // 表描述
        String desc = getStringValue(sheet, 0, 0);
        // 属性列表
        List<Field> fields = new LinkedList<>();
        int blankFieldCount = 0;
        for (int i = 0; i < 999 && blankFieldCount < 100; blankFieldCount++, i++) {
            int col = i;
            String value = getStringValue(sheet, 1, col);
            if (StringUtils.notNullOrEmpty(value)) {
                blankFieldCount = 0;
                // 属性名
                String fieldName = fname(value);
                // as类型
                String asType = getStringValue(sheet, 2, col);
                // java类型
                String javaType = getStringValue(sheet, 3, col);
                // 属性描述
                String fieldDesc = getStringValue(sheet, 4, col);
                if (StringUtils.isNullOrEmpty(fieldDesc)) {
                    fieldDesc = "";
                }
                fieldDesc = fieldDesc.replaceAll("\r", "\t");
                Field field =
                        new Field(
                                fieldName,
                                asType,
                                javaType,
                                fieldDesc,
                                BelongType.valueOf(
                                        !StringUtils.isBlank(asType),
                                        !StringUtils.isBlank(javaType)));
                field.col = col;
                fields.add(field);
                client = field.belong.isClient() || client;
                server = field.belong.isServer() || server;
            }
        }
        Class clazz = new Class(sheetName, desc, fields, fileName);
        classes.add(clazz);
        if (datas != null) {
            List<List<FieldWithValue>> list = new LinkedList<>();
            for (int row = 5; row <= sheet.getLastRowNum(); row++) {
                if (StringUtils.isNullOrEmpty(getStringValue(sheet, row, 0))) {
                    List<FieldWithValue> array = new ArrayList<>();
                    for (Field field : fields) {
                        String value = getStringValue(sheet, row, field.col);
                        array.add(new FieldWithValue(field, value));
                    }
                    list.add(array);
                }
            }
            datas.add(new DataWithClass(list, clazz));
        }
    }

    private static String fname(String value) {
        if (value.contains(">")) {
            return value.substring(0, value.indexOf(">"));
        } else {
            return value.contains("$") ? value.substring(0, value.indexOf("$")) : value;
        }
    }

    /**
     * 指定行列获取字符串
     *
     * @param sheet
     * @param row
     * @param col
     * @return
     */
    public static String getStringValue(Sheet sheet, int row, int col) {
        Row row0 = sheet.getRow(row);
        if (row0 == null) {
            return null;
        }
        Cell col0 = row0.getCell(col);
        if (col0 == null) {
            return null;
        }
        String value = cellStringValue(col0, col0.getCellTypeEnum());
        if (StringUtils.isNullOrEmpty(value)) {
            return null;
        }
        if (value.contains("<font")) {
            value = HtmlUtils.font2span(value);
        }
        return value;
    }

    private static String cellStringValue(Cell cell, CellType type) {
        switch (type) {
            case _NONE:
                return "";
            case BLANK:
                return "";
            case ERROR:
                return "";
            case NUMERIC:
                return "" + (long) cell.getNumericCellValue();
            case STRING:
                return cell.getStringCellValue();
            case FORMULA:
                return cellStringValue(cell, cell.getCachedFormulaResultTypeEnum());
            case BOOLEAN:
                return cell.getBooleanCellValue() ? "1" : "0";
            default:
                return null;
        }
    }
}
