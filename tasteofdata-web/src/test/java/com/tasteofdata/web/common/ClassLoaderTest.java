package com.tasteofdata.web.common;

/**
 * Created by wwj on 2014/6/26.
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        while (cl != null) {
            System.out.println(cl);
            System.out.println("---pcl=:" + cl.getParent());
            cl = cl.getParent();
        }
    }
}
