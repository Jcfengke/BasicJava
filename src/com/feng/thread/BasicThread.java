package com.feng.thread;

/**
 * 基础的线程知识
 */
public class BasicThread {

    /**
     * 线程机制的实现：
     *              1.java.lang.Thread类
     *              2.java.lang.Runnable接口
     */



    //创建Thread和Runnable对象
    public void  createThread(){

        Runnable r=new Runnable() {
            @Override
            public void run() {
                System.out.println("通过实现Runnable接口的线程");
            }
        };
        //运行
        r.run();

    }

    static  class  MyThread extends  Thread{

        @Override
        public void run() {
            System.out.println("通过继承Thread类的线程");
        }
    }

    static  MyThread myThread=new MyThread();


    /**
     *==================================================================================================================
     */

    //获取和设置线程的名称
    public void  getOrSetThreadName(){
        //创建一个零时线程
        Runnable r=new Runnable() {
            @Override
            public void run() {

            }
        };

        Thread thread1=new Thread(r,"thread 线程一");
        System.out.println(thread1.getName());

        Thread thread2=new Thread(r);
        thread2.setName("thread 线程二");
        System.out.println(thread2.getName());



    }

    /**
     *==================================================================================================================
     */

    //获取线程存活状态
    public  void  getThreadIsAlive(){
        //isAlive()方法判断一条堪称的存活状态
        Runnable r=new Runnable() {
            @Override
            public void run() {
                System.out.println("零时线程");
            }
        };
        Thread t=new Thread(r);
        System.out.println("线程的存活状态："+t.isAlive());

    }


    /**
     *==================================================================================================================
     */

    /**
     * 获取线程的执行状态
     *
     * 方法：
     *      Thread.getState();
     *
     * 常见的执行状态
     *               NEW                    线程还没执行
     *               RUNNABLE               该线程正在JVM下执行
     *               BLOCKED                该线程被阻塞并等待下一个监听锁
     *               WAITING                该线程无限等待另一条线程执行某种操作
     *               TIMED_WAITING          该线程在特定时间内等待另一条线程执行某种操作
     *               TERMINATED             该状态下线程已推出
     */
    public  void  getThreadExecut(){
        Runnable r=new Runnable() {
            @Override
            public void run() {
                System.out.println("零时线程");
            }
        };
        Thread t=new Thread(r);
        System.out.println("线程的状态："+t.getState());
        t.start();
        System.out.println("线程的状态："+t.getState());

    }

    /**
     *==================================================================================================================
     *
     *获取和设置线程的优先级
     *
     * 可以调用java.lang.Runtime.getRuntime().availableProcessors() 获取当前JVM可用的处理器或处理器核心的数量
     *
     * 优先级：1-10
     *
     *
     *
     */
    public  void getOrSetThreadPriority(){
        Runnable r=new Runnable() {
            @Override
            public void run() {
                System.out.println("零时线程！");
            }
        };
        Thread t=new Thread(r);

        System.out.println("当前JVM可用的处理器或处理器核心的数量："+java.lang.Runtime.getRuntime().availableProcessors());

        System.out.println("获取线程的优先级："+t.getPriority());
        t.setPriority(Thread.MIN_PRIORITY);
        System.out.println("获取线程的优先级："+t.getPriority());
        t.run();
        System.out.println("获取线程的优先级："+t.getPriority());

    }


    /**
     *==================================================================================================================
     *
     * 获取和设置线程的守护线程状态
     *
     * java把线程氛围守护和非守护线程，一条守护线程扮演这非守护线程辅助者的角色，并且会在应用程序最后一条非守护线程消失之后自动死亡。
     *
     * Thread.isDaemon();方法判断线程是否是守护线程。
     *
     *
     */
    public  void  isDaemon(){
        Runnable r=new Runnable() {
            @Override
            public void run() {
                System.out.println("零时线程！");
            }
        };
        Thread t=new Thread(r);

        System.out.println("是否是守护线程："+t.isDaemon());
        System.out.println("Id:"+t.getId());

        //创建守护线程
        Thread t2=new Thread(r);
        t2.setDaemon(true);
        System.out.println("是否是守护线程："+t2.isDaemon());
        System.out.println("Id:"+t2.getId());

    }


    //守护进程和非守护进程的区别
    //守护进程随主进程结束而结束
    public  static  void main(String[] args){

        boolean isDaemon=args.length==0;

        Runnable r=new Runnable() {
            @Override
            public void run() {
                Thread thd=Thread.currentThread();
                while (true)
                    System.out.printf("%s is %salive and in %s state %n",thd.getName(),thd.isAlive()?"":"not",thd.getState());
            }
        };

        Thread t1=new Thread(r,"th1");
        if (isDaemon) t1.setDaemon(true);
        System.out.printf("%s is %salive and in %s state %n",t1.getName(),t1.isAlive()?"":"not",t1.getState());

        Thread t2=new Thread(r,"th2");
        if (isDaemon) t2.setDaemon(true);
        System.out.printf("%s is %salive and in %s state %n",t2.getName(),t2.isAlive()?"":"not",t2.getState());

        t1.start();
        t2.start();
    }



}
