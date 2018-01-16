package com.feng.thread;


/**
 * 同步问题
 *
 * 必备知识：
 *
 *      互斥性
 *      可见行
 *
 * 概念：
 *      竞态条件：
 *              check-then-act
 *              read-modify-write
 *      数据竞争：
 *              两条或两条以上的线程（在单个应用中）并发的访问同一块内存区域的访问，同时其中至少一条是为了写，而且这些线程没有协调对那块内存区域的访问。
 *      缓存变量：
 *              JVM以及操作系统会协调在寄存器中或者处理器缓存中缓存变量，而不依赖主存，每条线程都会有其自己的变量拷贝。
 *              当主线程写入这个变量的事后，其实是写入自己的拷贝，其他线程不太可能在看到自己的变量拷贝发生更改。
 *
 *
 */
public class BasicSynchronization {

    private static int  result;

    /**
     *
     * =================================================================================================================
     * 缓存变量
     *
     */
    public void cacheVariables(){

        Runnable r=new Runnable() {
            @Override
            public void run() {
                result= ++result;
                System.out.println("缓存变量："+result);
            }
        };

        Thread t1=new Thread(r);
        Thread t2=new Thread(r);

        t1.start();
        t2.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    /**
     *
     * =================================================================================================================
     * 同步方法：
     *      同步在实例方法上，锁会和调用该方法的实例对象关联。
     *      同步在一个类方法上，锁会和调用该类方法的类所对应的java.lang.Class对象相关联。
     *
     */
    static class  ID{
        private int counter;
        synchronized int getID(){
            return counter++;
        }
    }
    static  class ID1{
        private  static int counter;
        public  static  synchronized  int getCounter(){
            return counter++;
        }
    }

    public  static void synchronizedMethod(){
        //如果其他线程在该方法执行过程中调用id.getID()方法，这些线程不得不等待正在执行的线程释放锁；
        ID id=new ID();
        System.out.println(id.getID());

        //锁和ID类关联的Class对象ID.class相关联，如果其他线程在该方法执行过程中调用ID1.getCounter()方法，这些线程不得不等待正在执行的线程释放掉锁。
        System.out.println(ID1.getCounter());
    }


    /**
     *
     * =================================================================================================================
     * 同步块
     *
     *
     */
    public void synchronizedBlock(int value){


    Runnable r=new Runnable() {
        @Override
        public void run() {
            synchronized (this){
                System.out.println("this is test");
            }

        }
    };
    synchronized (this){
        System.out.println("this is test too!");
    }


    }

    /**
     *
     * =================================================================================================================
     * 死锁
     *
     *
     */
    static class DeadlockDemo{
        private  final  Object lock1=new Object();
        private final Object lock2=new Object();

        public  void  instanceMethod1(){
            synchronized (lock1){
                synchronized (lock2){
                    System.out.println("first thread in instanceMethod1");
                }
            }
        }

        public void instanceMethod2(){
            synchronized (lock2){
                synchronized (lock1){
                    System.out.println("second thread in instanceMethod2");
                }
            }
        }

        public void  run(){
            final DeadlockDemo dld=new DeadlockDemo();
            Runnable r1=new Runnable() {
                @Override
                public void run() {
                    while (true){
                        dld.instanceMethod1();;
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            };

            final Thread threadA=new Thread(r1);

            Runnable r2=new Runnable() {
                @Override
                public void run() {
                    while (true){
                        dld.instanceMethod2();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Thread threadB=new Thread(r2);

            threadA.start();
            threadB.start();
        }
    }

    /**
     *
     * =================================================================================================================
     * volatile和final
     *
     *
     */

    public static void main(String[] args) {
        BasicSynchronization basicSynchronization=new BasicSynchronization();
        //basicSynchronization.cacheVariables();
        new DeadlockDemo().run();
    }
}
