package com.test.game.core.utils;

import com.google.common.base.Preconditions;
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

    public static boolean isExcel(File file) {
        if (file.isHidden()) {
            //                log.info("{} is a hidden file", file.getAbsolutePath());
            return false;
        }
        if (file.getName().startsWith("~")) {
            //            log.info("{} is a temp file", file.getAbsolutePath());
            return false;
        }
        return file.getName().endsWith(excel_suffix);
    }

    public static void parse(
            String excelDir,
            Map<String, ConfigUtils.CustomClass> name2customClass,
            List<Class> classes,
            List<ConfigUtils.Data> datas) {
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

    static class Field {
        public final String fieldName;
        public final String fieldDesc;
        public final String asType;
        public final String javaType;
        public final BelongType belong;
        public int col = -1;

        public Field(
                String fieldName,
                String asType,
                String javaType,
                String fieldDesc,
                BelongType belong) {
            this.fieldName = fieldName;
            this.asType = asType;
            this.javaType = javaType;
            this.fieldDesc = fieldDesc;
            this.belong = belong;
        }
    }

    enum BelongType {
        CLIENT(true, false),
        SERVER(false, true),
        BOTH(true, true),
        NULL(false, false);

        private final boolean client;
        private final boolean server;

        private BelongType(boolean client, boolean server) {
            this.client = client;
            this.server = server;
        }

        public boolean isClient() {
            return this.client;
        }

        public boolean isServer() {
            return this.server;
        }

        public BelongType add(BelongType belong) {
            if (belong == null) {
                return this;
            } else {
                boolean client = this.client || belong.client;
                boolean server = this.server || belong.server;
                if (client) {
                    return server ? BOTH : CLIENT;
                } else {
                    return server ? SERVER : NULL;
                }
            }
        }

        public static BelongType valueOf(boolean client, boolean server) {
            BelongType[] var2 = values();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                BelongType type = var2[var4];
                if (type.client == client && type.server == server) {
                    return type;
                }
            }

            return null;
        }
    }

    public static void parseSheet(
            String fileName,
            Sheet sheet,
            Map<String, ConfigUtils.CustomClass> name2customClass,
            List<Class> classes,
            List<ConfigUtils.Data> datas) {
        boolean client = false;
        boolean server = false;
        // 表命
        String sheetName = sheet.getSheetName();
        // 表描述
        String desc = getStringValue(sheet, 0, 0);
        //
        List<Field> fields = new LinkedList<>();
        int blankFieldCount = 0;
        for (int i = 0; i < 999 && blankFieldCount < 100; blankFieldCount++, i++) {
            int col = i;
            String value = getStringValue(sheet, 1, col);
            if (StringUtils.notNullOrEmpty(value)) {
                blankFieldCount = 0;
                String fieldName = fname(value);
                String asType = getStringValue(sheet, 2, col);
                String javaType = getStringValue(sheet, 3, col);
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
                                        !StringUtils.isNullOrEmpty(asType),
                                        !StringUtils.isNullOrEmpty(javaType)));
                field.col = col;
                fields.add(field);
                client = field.belong.isClient() || client;
                server = field.belong.isServer() || server;
            }
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
