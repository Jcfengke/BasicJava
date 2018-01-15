package com.feng.thread;


import org.junit.Test;

public class BasicThreadTest {

    BasicThread basicThread=new BasicThread();

    //创建对象
    @Test
    public void  test1(){

        basicThread.createThread();
        BasicThread.myThread.run();
    }

    @Test
    public  void  test2(){
        basicThread.getOrSetThreadName();
        System.out.println("==========================================");
        basicThread.getThreadIsAlive();
        System.out.println("==========================================");
        basicThread.getThreadExecut();
        System.out.println("==========================================");
        basicThread.getOrSetThreadPriority();
        System.out.println("==========================================");
        basicThread.isDaemon();
    }

}
