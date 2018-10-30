package com.gaussic.util;

import com.gaussic.model.dcs_history.H000Pojo_Base;
import com.gaussic.model.history.CoalPipingHistory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HandlDcsHistoryListUtil {

    public static List<Float> getMilHistoryList(List<? extends CoalPipingHistory> list, List<H000Pojo_Base> hList) {
        List<Float> resultList = new ArrayList<>();

        System.out.println("hList:" +hList);
        if(null != hList && hList.size() > 0){
            if(null != list) {
                Comparator<H000Pojo_Base> comparator = Comparator.comparing(H000Pojo_Base::getvTime).reversed();
                List<H000Pojo_Base> sortedList = hList.stream().sorted(comparator).collect(Collectors.toList());
                list.forEach((coalPipingHistory )->{
                    Timestamp timestamp = coalPipingHistory.gethTime();
                    Float smallValue = sortedList.get(0).getV(),
                            bigValue = null,
                            latestNewerValue = null,
                                    resultValue = null;
                    System.out.println("数据量：" + sortedList.size() + "," +sortedList);
                    if(sortedList.size() == 1){
                        coalPipingHistory.setCoalMillValue(smallValue);
                    }else {
                        System.out.println("begin:" + sortedList.get(0).getvTime() + ",    " + sortedList.get
                                (sortedList.size()-1).getvTime());
                        if(sortedList.size() == 2){
                            if(timestamp.getTime() <= sortedList.get(0).getvTime().getTime()){
                                resultValue = sortedList.get(0).getV();
                            }else if(timestamp.getTime() >= sortedList.get(1).getvTime().getTime()){
                                resultValue = sortedList.get(1).getV();
                            }else{
                                long intervalCount = sortedList.get(1).getvTime().getTime() - sortedList.get(0)
                                        .getvTime().getTime() ;
                                long currentLocation = timestamp.getTime() - sortedList.get(0).getvTime().getTime();
                                float intervalValue = sortedList.get(1).getV() - sortedList.get(0).getV();
                                resultValue = sortedList.get(0).getV() + (intervalValue/intervalCount *
                                        currentLocation);
                            }
                        }else{
                            int size = sortedList.size();
                            if(timestamp.getTime() < sortedList.get(0).getvTime().getTime()){
                                resultValue = sortedList.get(0).getV();
                            }else if(timestamp.getTime() >= sortedList.get(size -1).getvTime().getTime()){
                                resultValue = sortedList.get(size -1).getV();
                            }else{
                                for (int i = 0; i < sortedList.size(); i++) {
                                    Timestamp timestampForH = sortedList.get(i).getvTime();
                                    if (timestamp.getTime() <= timestampForH.getTime()) {
                                        bigValue = sortedList.get(i).getV();
                                        smallValue = sortedList.get(i -1).getV();
                                        long intervalCount = sortedList.get(i).getvTime().getTime() - sortedList.get(i-1)
                                                .getvTime().getTime();
                                        Float intervalValue = bigValue - smallValue;
                                        long currentLocation = timestamp.getTime() - sortedList.get(i-1).getvTime()
                                                .getTime();
                                        resultValue = smallValue + intervalValue * (currentLocation/intervalCount);
                                    }
                                }
                            }
                        }
                        coalPipingHistory.setCoalMillValue(resultValue);
                    }
                });
            }
        }

        return resultList;
    }
}
