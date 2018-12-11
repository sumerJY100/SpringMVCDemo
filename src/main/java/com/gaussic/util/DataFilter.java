package com.gaussic.util;

import com.gaussic.model.history.CoalPipingHistory;
import com.gaussic.service.CoalPipingHistoryService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataFilter {

    private static final String excelSwitchFileName = "d:/A.xlsx";


    public static void test(String millLocation){
        if (millLocation.equals("A")) {
            LocalDateTime localDateTime01 = LocalDateTime.of(2018, 12, 1, 10, 00);
            LocalDateTime localDateTime02 = LocalDateTime.of(2018, 12, 1, 13, 00);
            double[] v01_02 = new double[]{0.75f, 0.92f, 0.85f, 0.71f};
            TT t0102 = new TT(localDateTime01, localDateTime02, v01_02);

            LocalDateTime localDateTime03 = LocalDateTime.of(2018, 12, 1, 13, 00);
            LocalDateTime localDateTime04 = LocalDateTime.of(2018, 12, 1, 14, 15);
            double[] v03_04 = new double[]{0.60f, 0.92f, 0.78f, 0.71f};
            TT t0304 = new TT(localDateTime03, localDateTime04, v03_04);


            LocalDateTime localDateTime05 = LocalDateTime.of(2018, 12, 1, 14, 15);
            LocalDateTime localDateTime06 = LocalDateTime.of(2018, 12, 1, 15, 30);
            double[] v05_06 = new double[]{0.55f, 0.92f, 0.78f, 0.71f};
            TT t0506 = new TT(localDateTime05, localDateTime06, v05_06);


            LocalDateTime localDateTime07 = LocalDateTime.of(2018, 12, 1, 15, 30);
            LocalDateTime localDateTime08 = LocalDateTime.of(2018, 12, 2, 12, 00);
            double[] v07_08 = new double[]{0.58f, 0.92f, 0.78f, 0.71f};
            TT t0708 = new TT(localDateTime07, localDateTime08, v07_08);


            LocalDateTime localDateTime10 = LocalDateTime.of(2018, 12, 2, 12, 00);
            LocalDateTime localDateTime11 = LocalDateTime.of(2018, 12, 2, 13, 05);
            double[] v10_11 = new double[]{0.50f, 0.92f, 0.78f, 0.71f};
            TT t1011 = new TT(localDateTime10, localDateTime11, v10_11);


            LocalDateTime localDateTime12 = LocalDateTime.of(2018, 12, 2, 13, 05);
            LocalDateTime localDateTime13 = LocalDateTime.of(2018, 12, 2, 14, 36);
            double[] v12_13 = new double[]{0.50f, 0.92f, 0.78f, 0.615f};
            TT t1213 = new TT(localDateTime12, localDateTime13, v12_13);

            LocalDateTime localDateTime14 = LocalDateTime.of(2018, 12, 2, 14, 36);
            LocalDateTime localDateTime15 = LocalDateTime.of(2018, 12, 2, 18, 35);
            double[] v14_15 = new double[]{0.50f, 0.92f, 0.78f, 0.650f};
            TT t1415 = new TT(localDateTime14, localDateTime15, v14_15);

            List<TT> ttList = new ArrayList<>();
            ttList.add(t0102);
            ttList.add(t0304);
            ttList.add(t0506);
            ttList.add(t0708);
            ttList.add(t1011);
            ttList.add(t1213);
            ttList.add(t1415);

        }
    }


    /**
     *  将磨煤机的4根粉管的数据，所有取值乘以系数
     *  系数来自d:/ABCD.xlsx文件
     * @param millLocation  磨煤机名称A B C D
     * @param resultList    List<? extends  CoalPipingHistory>
     */
    public static void filter(String  millLocation,List<? extends  CoalPipingHistory> resultList){
        try {
            String path = "d:/ABCD.xlsx";
            File file = new File(path);
//            file.createNewFile();
            if(file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(path);
                //创建一个工作簿
                XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                //创建一个电子表格
                XSSFSheet sheet;
                sheet = workbook.getSheet(millLocation);
                if (null != sheet) {
                    //行对象

                    List<TT> ttList = new ArrayList<>();
                    for (int i = 2; i < 100; i++) {
                        XSSFRow row = sheet.getRow(i);
                        if (null != row) {
                            Cell firstCell = row.getCell(0);
                            if (null != firstCell) {
                                TT tt = new TT(row);
                                ttList.add(tt);
//                                System.out.println("tt:" + i);
                            }
                        }
                    }
                    for (CoalPipingHistory coalPipingHistory : resultList) {
                        Timestamp timestamp = coalPipingHistory.gethTime();
                        LocalDateTime localDateTime = timestamp.toLocalDateTime();
                        for (int i = 0; i < ttList.size(); i++) {
                            TT tt = ttList.get(i);
                            if (localDateTime.isAfter(tt.getBegin()) && localDateTime.isBefore(tt.getEnd())) {
                                coalPipingHistory.sethPipeADencity((float) (coalPipingHistory.gethPipeADencity() * tt.getValues()[0]));
                                coalPipingHistory.sethPipeBDencity((float) (coalPipingHistory.gethPipeBDencity() * tt.getValues()[1]));
                                coalPipingHistory.sethPipeCDencity((float) (coalPipingHistory.gethPipeCDencity() * tt.getValues()[2]));
                                coalPipingHistory.sethPipeDDencity((float) (coalPipingHistory.gethPipeDDencity() * tt.getValues()[3]));
                            }
                        }

                    }

                }

                workbook.close();

                fileInputStream.close();
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    /**
     * 使用幂指数处理数据
     * @param resultList    List<CoalPipingHistory>
     * @param pow           默认使用-2.5
     */
    public static void updateDateWithPow(List<? extends  CoalPipingHistory> resultList, int pow) {
//        double m = 3;
        double m = Math.abs(pow);
        resultList.forEach(coalPipingHistory -> {
            System.out.println("coalPipingHistory.gethPipeADencity():" +coalPipingHistory.gethPipeADencity() +"," + (float) Math.pow(coalPipingHistory.gethPipeADencity(), 1.0/m));
            coalPipingHistory.sethPipeADencity((float) Math.pow(coalPipingHistory.gethPipeADencity(), 1.0/m));
            coalPipingHistory.sethPipeBDencity((float) Math.pow(coalPipingHistory.gethPipeBDencity(), 1.0/m));
            coalPipingHistory.sethPipeCDencity((float) Math.pow(coalPipingHistory.gethPipeCDencity(), 1.0/m));
            coalPipingHistory.sethPipeDDencity((float) Math.pow(coalPipingHistory.gethPipeDDencity(), 1.0/m));
        });
    }

    /**
     * 读取excel文件中的原始数据开关功能，对数据进行处理
     * @param resultList List<CoalPipingHistory>
     */
    public static void updateOrigianlDataWithExcel(List<CoalPipingHistory> resultList) {
        File file = new File(excelSwitchFileName);
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
            XSSFSheet sheet = xssfWorkbook.getSheet("01");
            Row row = sheet.getRow(1);
            if(null != row) {
                Cell cellForSwith = row.getCell(5);
                if(null != cellForSwith) {
                    int k = (int) cellForSwith.getNumericCellValue();
                    if (k == 1) {
                        //使用幂指数
                        Row rowForM = sheet.getRow(6);
                        if(null != rowForM) {
                            Cell cellForM = rowForM.getCell(5);
                            if(null != cellForM){
                                int m = (int) cellForM.getNumericCellValue();
                                DataFilter.updateDateWithPow(resultList, m);
                            }
                        }
                    } else if (k == 0) {
                        //显示原始数据
                    }
                }
            }
            xssfWorkbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 读取excel文件中的历史数据开关功能，对数据进行处理
     * 0 原始数据
     * 1 偏执数据，读取ABCD.xlsx文件
     * 2 幂指数
     * @param millLocation
     * @param list
     */
    public static void updateHandleDataWithExcel(String millLocation, List<? extends CoalPipingHistory> list) {
        File file = new File(excelSwitchFileName);
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
            XSSFSheet sheet = xssfWorkbook.getSheet("01");
            Row row = sheet.getRow(1);
            if(null != row) {
                Cell cellForSwith = row.getCell(2);
                if(null != cellForSwith) {
                    int k = (int) cellForSwith.getNumericCellValue();
                    if (k == 2) {
                        //使用幂指数
                        Row rowForM = sheet.getRow(7);
                        if(null != rowForM) {
                            Cell cellForM = rowForM.getCell(2);
                            if(null != cellForM){
                                int m = (int) cellForM.getNumericCellValue();
                                DataFilter.updateDateWithPow(list, m);
                            }
                        }
                    } else if (k == 1) {
                        // 使用偏执数据
                        filter(millLocation,list);
                    }else if(k == 0){
                        //使用原始数据
                    }
                }
            }
            xssfWorkbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private static class TT {
        private LocalDateTime begin;
        private LocalDateTime end;
        private double[] values;

        public TT(LocalDateTime begin, LocalDateTime end, double[] values) {
            this.begin = begin;
            this.end = end;
            this.values = values;
        }

        public TT(Date beginDate, Date beginTime, Date endDate, Date endTime, double pipe1, double pipe2, double pipe3, double pipe4) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
            String beginDateStr = dateFormat.format(beginDate) + " " + timeFormat.format(beginTime);
            String endDateStr = dateFormat.format(endDate) + " " + timeFormat.format(endTime);
            try {
                Date begin = format.parse(beginDateStr);
                Date end = format.parse(endDateStr);
                LocalDateTime localDateTimeBegin = DateUtil.getLocalDateTimeFromDate(begin);
                LocalDateTime localDateTimeEnd = DateUtil.getLocalDateTimeFromDate(end);
                double[] doubles = new double[]{pipe1, pipe2, pipe3, pipe4};
                this.begin = localDateTimeBegin;
                this.end = localDateTimeEnd;
                this.values = doubles;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public TT(XSSFRow row) {
            Cell firstCell = row.getCell(0);
            Date beginDate = firstCell.getDateCellValue();
            Date beginTime = row.getCell(1).getDateCellValue();
            Date endDate = row.getCell(2).getDateCellValue();
            Date endTime = row.getCell(3).getDateCellValue();
            double pipe1 = row.getCell(4).getNumericCellValue();
            double pipe2 = row.getCell(5).getNumericCellValue();
            double pipe3 = row.getCell(6).getNumericCellValue();
            double pipe4 = row.getCell(7).getNumericCellValue();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
            String beginDateStr = dateFormat.format(beginDate) + " " + timeFormat.format(beginTime);
            String endDateStr = dateFormat.format(endDate) + " " + timeFormat.format(endTime);
            try {
                Date begin = format.parse(beginDateStr);
                Date end = format.parse(endDateStr);
                LocalDateTime localDateTimeBegin = DateUtil.getLocalDateTimeFromDate(begin);
                LocalDateTime localDateTimeEnd = DateUtil.getLocalDateTimeFromDate(end);
                double[] doubles = new double[]{pipe1, pipe2, pipe3, pipe4};
                this.begin = localDateTimeBegin;
                this.end = localDateTimeEnd;
                this.values = doubles;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public LocalDateTime getBegin() {
            return begin;
        }

        public void setBegin(LocalDateTime begin) {
            this.begin = begin;
        }

        public LocalDateTime getEnd() {
            return end;
        }

        public void setEnd(LocalDateTime end) {
            this.end = end;
        }

        public double[] getValues() {
            return values;
        }

        public void setValues(double[] values) {
            this.values = values;
        }
    }
}
