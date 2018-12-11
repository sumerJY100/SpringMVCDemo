package com.gaussic.util;

import com.gaussic.model.dcs_history.H000Pojo_Base;
import com.gaussic.model.history.CoalPipingHistory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HandlDcsHistoryListUtil {


    public static List<Float> getMilHistoryListNoChange(List<? extends CoalPipingHistory> list, List<H000Pojo_Base>
            hList) {
        List<Float> resultList = new ArrayList<>();

//        System.out.println("hList:" +hList);
        if(null != hList && hList.size() > 0){
            if(null != list) {
                //排序
                Comparator<H000Pojo_Base> comparator = Comparator.comparing(H000Pojo_Base::getvTime);
                List<H000Pojo_Base> sortedList = hList.stream().sorted(comparator).collect(Collectors.toList());
//                System.out.println("开始时间：" +sortedList.get(0).getvTime());
//                System.out.println(hList.size() + "," + hList.get(0).getV());
//                System.out.println("结束时间：" + sortedList.get(sortedList.size() -1).getvTime());
                //循环遍历，并将数据煤量数据根据粉管数据进行赋值
                /**
                 * 基本思路
                 * 1、当磨煤机磨煤量数据为1时，粉管对应的数据全部为 hList.get(0).getV
                 * 2、当hList.size == 2时
                 *      cpTime <= hList.get(0).getTime 则时间≤cpTime的值，为hList.get(0).getV;
                 *      cpTime >= hList.get(1).getTime ,则时间 ≥  cpTime的值，为hList.get(1).getV;
                 *      cpTime处于 htList.get(0).getTime与 hList.get(1).getTime，进行如下处理：
                 *            找到时间最接近hList.get(0).getTime的List 的数据位置 A，A的时间小于hList.get(0).getTime;
                 *            找到时间最接近hList.get(1).getTime的List 的数据位置 B, B的时间大于hList.get(0).getTime;
                 *
                 *            A与B之间的数据，C的值为 hList.get(0).getV
                 * 3、当hLisit.size > 2时
                 *      cpTime <= hList.get(0).getTime 则时间≤cpTime的值，为hList.get(0).getV;
                 *      cpTime >= hList.get(length -1 ).getTime ,则时间 ≥  cpTime的值，为hList.get(1).getV;
                 *      cpTime处于 htList.get(0).getTime与 hList.get(length -1).getTime，进行如下处理：
                 *              根据list.get(i)定义为C
                 *              循环遍历HList，查找C的时间处于的最小区间，
                 *              【hList已经进行升序排列】
                 *              HList.get(n).getTime >= C.getTime，则(n-1)为当前点处于的最小时间点A，(n)为最大时间点为B。
                 *
                 *              C的值为hList.get(n-1).getV()
                 *
                 */
                list.forEach((coalPipingHistory )->{
                    Long timeForCoalPiping = coalPipingHistory.gethTime().getTime();
                    Float smallValue = sortedList.get(0).getV(),
                            resultValue = null;
                    //  System.out.println("数据量：" + sortedList.size() + "," +sortedList);
                    if(sortedList.size() == 1){
                        coalPipingHistory.setCoalMillValue(smallValue);
                    }else {
//                        System.out.println("begin:" + sortedList.get(0).getvTime() + ",    " + sortedList.get(sortedList.size()-1).getvTime());
                        H000Pojo_Base H0 = sortedList.get(0);
                        H000Pojo_Base H1 = sortedList.get(1);
                        Long H0Time = H0.getvTime().getTime();
                        Long H1Time = H1.getvTime().getTime();
                        int sortedListize = sortedList.size();
                        if(sortedListize == 2){

                            if(timeForCoalPiping <= H1Time){
                                resultValue = H0.getV();
                            }else if(timeForCoalPiping > H1Time){
                                resultValue = H1.getV();
                            }
                        }else if(sortedListize > 2){
                            if(timeForCoalPiping < H0Time){
                                resultValue = H0.getV();
                            }else if(timeForCoalPiping >= sortedList.get(sortedListize -1).getvTime().getTime()){
                                resultValue = sortedList.get(sortedListize -1).getV();
                            }else{
                                for (int i = 2; i < sortedListize; i++) {
                                    H000Pojo_Base HCurrent = sortedList.get(i);
                                    H000Pojo_Base HBefore = sortedList.get(i -1);
                                    Long timeForCurrent = HCurrent.getvTime().getTime();
                                    if (timeForCoalPiping <= timeForCurrent) {
                                        smallValue = HBefore.getV();
                                        resultValue = smallValue ;
                                        break;
                                    }
                                }
                            }
                        }
                        coalPipingHistory.setCoalMillValue(resultValue);
//                        System.out.println("resultValue:" + resultValue);
                    }
                });
            }
        }

        return resultList;
    }


    /**
     * 将磨煤机的数据，设置到CoalPipingHistory中，实现每一个coalPipingHistory都对应一个磨煤机的磨煤量
     * @param list
     * @param hList
     * @return
     */
    public static List<Float> getMilHistoryList(List<? extends CoalPipingHistory> list, List<H000Pojo_Base> hList) {
        List<Float> resultList = new ArrayList<>();

//        System.out.println("hList:" +hList);
        if(null != hList && hList.size() > 0){
            if(null != list) {
                //排序
                Comparator<H000Pojo_Base> comparator = Comparator.comparing(H000Pojo_Base::getvTime).reversed();
                List<H000Pojo_Base> sortedList = hList.stream().sorted(comparator).collect(Collectors.toList());
                //循环遍历，并将数据煤量数据根据粉管数据进行赋值
                /**
                 * 基本思路
                 * 1、当磨煤机磨煤量数据为1时，粉管对应的数据全部为 hList.get(0).getV
                 * 2、当hList.size == 2时
                 *      cpTime <= hList.get(0).getTime 则时间≤cpTime的值，为hList.get(0).getV;
                 *      cpTime >= hList.get(1).getTime ,则时间 ≥  cpTime的值，为hList.get(1).getV;
                 *      cpTime处于 htList.get(0).getTime与 hList.get(1).getTime，进行如下处理：
                 *            找到时间最接近hList.get(0).getTime的List 的数据位置 A，A的时间小于hList.get(0).getTime;
                 *            找到时间最接近hList.get(1).getTime的List 的数据位置 B, B的时间大于hList.get(0).getTime;
                 *
                 *            A与B之间的时间间隔为 ABCount
                 *            A与B之间的数值差距为 ABValue = hList(1).getV - hList(0).getV
                 *
                 *            当前list.get(i)定位为C，C的位置为：CCount = C.getTime - A.getTime
                 *            C的值为：hList.get(0).getV + (CCount/ABCount * ABValue)
                 * 3、当hLisit.size > 2时
                 *      cpTime <= hList.get(0).getTime 则时间≤cpTime的值，为hList.get(0).getV;
                 *      cpTime >= hList.get(length -1 ).getTime ,则时间 ≥  cpTime的值，为hList.get(1).getV;
                 *      cpTime处于 htList.get(0).getTime与 hList.get(length -1).getTime，进行如下处理：
                 *              根据list.get(i)定义为C
                 *              循环遍历HList，查找C的时间处于的最小区间，
                 *              【hList已经进行升序排列】
                 *              HList.get(n).getTime >= C.getTime，则(n-1)为当前点处于的最小时间点A，(n)为最大时间点为B。
                 *
                 *              A与B之间的时间间隔为 ABCount
                 *              A与B之间的数值差距为 ABValue = hList(1).getV - hList(0).getV
                 *
                 *              当前list.get(i)定位为C，C的位置为：CCount = C.getTime - A.getTime
                 *              C的值为：hList.get(0).getV + (CCount/ABCount * ABValue)
                 *
                 */
                list.forEach((coalPipingHistory )->{
                    Long timeForCoalPiping = coalPipingHistory.gethTime().getTime();
                    Float smallValue = sortedList.get(0).getV(),
                            bigValue = null,
                            latestNewerValue = null,
                                    resultValue = null;
                  //  System.out.println("数据量：" + sortedList.size() + "," +sortedList);
                    if(sortedList.size() == 1){
                        coalPipingHistory.setCoalMillValue(smallValue);
                    }else {
//                        System.out.println("begin:" + sortedList.get(0).getvTime() + ",    " + sortedList.get(sortedList.size()-1).getvTime());
                        H000Pojo_Base H0 = sortedList.get(0);
                        H000Pojo_Base H1 = sortedList.get(1);
                        Long H0Time = H0.getvTime().getTime();
                        Long H1Time = H1.getvTime().getTime();
                        int sortedListize = sortedList.size();
                        if(sortedListize == 2){

                            if(timeForCoalPiping <= H0Time){
                                resultValue = H0.getV();
                            }else if(timeForCoalPiping >= H1Time){
                                resultValue = H1.getV();
                            }else{
                                long intervalCount = H1Time - H0Time ;
                                long currentLocation = timeForCoalPiping - H0Time;
                                float intervalValue = H1.getV() - H0.getV();
                                resultValue = H0.getV() + (intervalValue/intervalCount * currentLocation);
                            }
                        }else if(sortedListize > 2){
                            if(timeForCoalPiping < H0Time){
                                resultValue = H0.getV();
                            }else if(timeForCoalPiping >= sortedList.get(sortedListize -1).getvTime().getTime()){
                                resultValue = sortedList.get(sortedListize -1).getV();
                            }else{
                                for (int i = 2; i < sortedListize; i++) {
                                    H000Pojo_Base HCurrent = sortedList.get(i);
                                    H000Pojo_Base HBefore = sortedList.get(i -1);
                                    Long timeForCurrent = HCurrent.getvTime().getTime();
                                    Long timeForBefore = HBefore.getvTime().getTime();
                                    if (timeForCoalPiping <= timeForCurrent) {
                                        bigValue = HCurrent.getV();
                                        smallValue = HBefore.getV();
                                        Float intervalValue = bigValue - smallValue;
                                        long intervalCount = timeForCurrent - timeForBefore;
                                        long currentLocation = timeForCoalPiping - timeForBefore;
                                        resultValue = smallValue + intervalValue * (currentLocation/intervalCount);
                                        break;
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
