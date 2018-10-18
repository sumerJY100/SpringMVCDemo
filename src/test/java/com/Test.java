package com;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

public class Test {
    public static void main(String[] args) {
        Integer i = 1;
        i.byteValue();
//        System.out.println(i.byteValue());
//        System.out.println(1&0xFF);
//        System.out.println(128&0xFF);
//        System.out.println(256&0xFF);
//        System.out.println((256>>8)&0XFF);
        o(255);
        o(256);
        o(512);
        o(Integer.MAX_VALUE);
        o(Integer.MIN_VALUE);
        o((Integer.MAX_VALUE-1) * 2 );

        System.out.println(2&4);
        System.out.println(2|7);
    }

    private static void o(int a){

        System.out.print(a + ":");
        System.out.print(a&0xff);
        System.out.print(",");
        System.out.print((a>>8)&0xFF );
        System.out.print(",");
        System.out.print((a>>16)&0xFF);
        System.out.print(",");
        System.out.println((a>>24)&0xFF);

    }
}
