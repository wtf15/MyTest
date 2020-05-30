package com.example.wtf;

import java.util.Random;

/**
 * @author qingmei
 * @date 2020-05-28
 * @desc TODO
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(new Random().nextInt(2));
        }

    }
}
