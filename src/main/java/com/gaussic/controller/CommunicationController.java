package com.gaussic.controller;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;
import com.gaussic.model.dcs.DeviceDcsPojo;
import com.gaussic.model.dcs_history.H074Pojo;
import com.gaussic.model.dcs_history.H075Pojo;
import com.gaussic.model.dcs_history.H076Pojo;
import com.gaussic.model.dcs_history.H077Pojo;
import com.gaussic.repository.CoalMillRepository;
import com.gaussic.repository.DeviceDcsRepository;
import com.gaussic.repository.dcs_history.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName CommunicationController
 * @Description 通讯控制类
 * @Author jiangyong xia
 * @Date 18/8/30 18:03
 * @Version 1.0
 */
@Controller
public class CommunicationController {
    @Autowired
    private CoalMillRepository coalMillRepository;
    @Autowired
    private DeviceDcsRepository deviceDcsRepository;
    @Autowired
    private H074Rep h074Rep;
    @Autowired
    private H075Rep h075Rep;
    @Autowired
    private H076Rep h076Rep;
    @Autowired
    private H077Rep h077Rep;
    @Autowired
    private H078Rep h078Rep;











    @RequestMapping(value="getAllDeviceState",method= RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllDeviceState(){

        JSONObject jsonObject = new JSONObject();

        JSONObject jsonObjectMillA = generatorJsonObjectMill(coalMillRepository.findOne(1L));
        JSONObject jsonObjectMillB = generatorJsonObjectMill(coalMillRepository.findOne(2L));
        JSONObject jsonObjectMillC = generatorJsonObjectMill(coalMillRepository.findOne(3L));
        JSONObject jsonObjectMillD = generatorJsonObjectMill(coalMillRepository.findOne(4L));

        DeviceDcsPojo deviceDcsPojo = deviceDcsRepository.findOne(1L);
        JSONObject jsonObjectDCS = new JSONObject();
        jsonObjectDCS.put("id",deviceDcsPojo.getDeviceId());
        jsonObjectDCS.put("name",deviceDcsPojo.getDeviceName());
        jsonObjectDCS.put("state",deviceDcsPojo.getDeviceLinkState());

        jsonObject.put("dcs",jsonObjectDCS);
        jsonObject.put("millA",jsonObjectMillA);
        jsonObject.put("millB",jsonObjectMillB);
        jsonObject.put("millC",jsonObjectMillC);
        jsonObject.put("millD",jsonObjectMillD);


//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.getSheet("d:/10.xlsx");
        return jsonObject.toString();
    }


    @RequestMapping(value="insertData",method= RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void insertData(){
        updateCoalMillAndBE("10","074");
        updateCoalMillAndBE("20","075");
        updateCoalMillAndBE("30","076");
        updateCoalMillAndBE("40","077");
    }


    private void updateCoalMillAndBE(String fileName,String hName){
        String path = "e:/"+fileName+".xlsx";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            //创建一个工作簿
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            //创建一个电子表格
            XSSFSheet sheet;
            sheet = workbook.getSheet(fileName);
            int rowNum = 5;
            while(true){
                Row row = sheet.getRow(rowNum++);
                if(null != row){
                    Cell cell = row.getCell(0);
                    if(null != cell){
                        Date date = cell.getDateCellValue();
                        Cell cell1 = row.getCell(1);
                        Double value = cell1.getNumericCellValue();
//                        System.out.println("rowNum:" + rowNum + ",date:" + date + ",value:" + value);
                        if(hName.equals("074")) {
                            H074Pojo h074Pojo = new H074Pojo();
                            h074Pojo.setvTime(new Timestamp(date.getTime()));
                            h074Pojo.setV(value.floatValue()*100);
                            h074Rep.saveAndFlush(h074Pojo);
                        }else if(hName.equals("075")){
                            H075Pojo h075Pojo = new H075Pojo();
                            h075Pojo.setvTime(new Timestamp(date.getTime()));
                            h075Pojo.setV(value.floatValue()*100);
                            h075Rep.saveAndFlush(h075Pojo);
                        }else if(hName.equals("076")){
                            H076Pojo h076Pojo = new H076Pojo();
                            h076Pojo.setvTime(new Timestamp(date.getTime()));
                            h076Pojo.setV(value.floatValue()*100);
                            h076Rep.saveAndFlush(h076Pojo);
                        }else if(hName.equals("077")){
                            H077Pojo h077Pojo = new H077Pojo();
                            h077Pojo.setvTime(new Timestamp(date.getTime()));
                            h077Pojo.setV(value.floatValue()*100);
                            h077Rep.saveAndFlush(h077Pojo);
                        }
                        Thread.sleep(50);
                        System.out.println("保存");
                    }else{
                        break;
                    }
                }else{
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }





    private JSONObject generatorJsonObjectMill(CoalMillEntity coalMillEntity){

        JSONObject jsonObjectMill = new JSONObject();
        jsonObjectMill.put("id",coalMillEntity.getId());
        jsonObjectMill.put("name",coalMillEntity.getcName());
        List<CoalPipingEntity> coalPipingEntityList = coalMillEntity.getCoalPipingEntityList();
        List<CoalPipingEntity> coalPipingEntityListSorted = coalPipingEntityList.stream().sorted(Comparator.comparing
                (CoalPipingEntity::getId)).collect(Collectors.toList());


        jsonObjectMill.put("pipeA",generatorJsonObjectPipe(coalPipingEntityListSorted.get(0)));
        jsonObjectMill.put("pipeB",generatorJsonObjectPipe(coalPipingEntityListSorted.get(1)));
        jsonObjectMill.put("pipeC",generatorJsonObjectPipe(coalPipingEntityListSorted.get(2)));
        jsonObjectMill.put("pipeD",generatorJsonObjectPipe(coalPipingEntityListSorted.get(3)));

        return jsonObjectMill;
    }
    private JSONObject generatorJsonObjectPipe(CoalPipingEntity coalPipingEntity) {
        JSONObject jsonObjectPipe = new JSONObject();
        jsonObjectPipe.put("id",coalPipingEntity.getId());
        jsonObjectPipe.put("name",coalPipingEntity.getpName());
        jsonObjectPipe.put("state",coalPipingEntity.getpCommunicationState());
        return jsonObjectPipe;
    }
}
