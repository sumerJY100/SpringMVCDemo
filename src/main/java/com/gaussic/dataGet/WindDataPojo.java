package com.gaussic.dataGet;


import org.json.JSONArray;
import org.json.JSONObject;

public class WindDataPojo {

    private static final String pipeNumKey = "bn";
    private String pipeNumValue;

    private static final String speedCountKey = "n";
    private String speedCountValue;
    private static final String speedPointKey = "u";
    private String speedPointValue;
    private static final String speedValueKey = "v";
    private String speedValue;

    private static final String DENSITY_X_KEY = "n";
    private String densityXKeyValue;
    private static final String DENSITY_X_VALUE_KEY="v";
    private String densityXValue;

    private static final String DENSITY_Y_KEY = "n";
    private String densityYKeyValue;
    private static final String DENSITY_Y_VALUE_KEY="v";
    private String densityYValue;

    private static final String ERROR_KEY = "n";
    private String errorKeyValue ;
    private static final String ERROR_VALUE_KEY = "v";
    private String errorValue;


    public WindDataPojo(JSONArray jsonArray){
        if(null != jsonArray && jsonArray.length() == 5){
            JSONObject pipeNumPojo = new JSONObject(jsonArray.get(0).toString());
            this.pipeNumValue = pipeNumPojo.getString(pipeNumKey);

            JSONObject pipeSpeedPojo = new JSONObject(jsonArray.get(1).toString());
            this.speedValue = String.valueOf(pipeSpeedPojo.getInt(speedValueKey));

            JSONObject pipeDensityXPojo = new JSONObject(jsonArray.get(2).toString());
            this.densityXValue = String.valueOf(pipeDensityXPojo.getInt(DENSITY_X_VALUE_KEY));

            JSONObject pipeDensityYPojo = new JSONObject(jsonArray.get(3).toString());
            this.densityYValue = String.valueOf(pipeDensityYPojo.getInt(DENSITY_Y_VALUE_KEY));

            JSONObject errorPojo = new JSONObject(jsonArray.get(4).toString());
            this.errorValue = String.valueOf(errorPojo.getInt(ERROR_VALUE_KEY));
        }
    }

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("管号：" + this.pipeNumValue + ",");
        buffer.append("速度:" + this.speedValue + ",");
        buffer.append("浓度X:" + this.densityXValue + ",");
        buffer.append("浓度Y:" + this.densityYValue + ",");
        buffer.append("错误代码:" + this.errorValue + ".");
        return buffer.toString();
    }

    public String getPipeNumValue() {
        return pipeNumValue;
    }

    public String getSpeedValue() {
        return speedValue;
    }

    public String getDensityXValue() {
        return densityXValue;
    }

    public String getDensityYValue() {
        return densityYValue;
    }

    public String getErrorValue() {
        return errorValue;
    }
}

