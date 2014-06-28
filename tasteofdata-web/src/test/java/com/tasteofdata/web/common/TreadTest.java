package com.tasteofdata.web.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wwj on 2014/6/26.
 */
public class TreadTest {

    public static void main(String[] args){
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
           pool.execute(new TreadTest.MyThread(i));
        }

        pool.shutdown();
    }

    static class MyThread implements Runnable{
        private int i;
        public MyThread(int i){
            this.i = i;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread i="+i+" run");
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
