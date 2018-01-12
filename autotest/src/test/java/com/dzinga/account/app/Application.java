package com.dzinga.account.app;

import com.dzinga.account.pages.shopfront.HeaderPage;

import java.lang.reflect.Field;

public class Application {

    public static void main(String[] args) {
        try {
            Class cls = null;
            try {


                cls = Class.forName("com.dzinga.account.pages.shopfront.HeaderPage");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            System.out.println("Fields =");

            // returns the array of Field objects representing the public fields

            Field f[] = cls.getFields();
            for (int i = 0; i < f.length; i++) {
                System.out.println(f[i]);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
