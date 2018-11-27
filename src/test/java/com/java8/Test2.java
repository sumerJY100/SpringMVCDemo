package com.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName Test2
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/11/27 12:40
 * @Version 1.0
 */
public class Test2 {

    public static void main(String[] args) {
        Float[] densityArr = new Float[]{1f,2f};
        List<Object > objectList = Arrays.asList(densityArr);
        for(Object o:objectList){
            System.out.println("o----------" + o);
        }
        objectList.forEach(o->{
            System.out.println("o:" + o + "," + o.getClass().getSimpleName());
        });
        Stream.of(densityArr).forEach(x-> System.out.println(x + "," + x.getClass().getSimpleName()));
        List<Float> list = Arrays.asList(densityArr).stream().map(Float.class::cast).collect(Collectors.toList());
        List<Double> list1 = Arrays.asList(densityArr).stream().map(Float.class::cast).map(Float::doubleValue).collect(Collectors.toList());
        List<Double> doubleList =  Arrays.asList(densityArr).stream().map(Float.class::cast).map(Float::doubleValue).filter(d->d>500000).filter(d->d<20000000).collect(Collectors.toList());
    }
}
