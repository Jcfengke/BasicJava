package com.feng.thread;


/**
 * 同步问题
 *
 * 必备知识：
 *
 *      互斥性
 *      可见行
 *
 * Java 语言包含两种内在的同步机制：同步块（或方法）和 volatile 变量。这两种机制的提出都是为了实现代码线程的安全性。
 * 其中 Volatile 变量的同步性较差（但有时它更简单并且开销更低），而且其使用也更容易出错。
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
     *Volatile 变量具有 synchronized 的可见性特性，但是不具备原子特性。这就是说线程能够自动发现 volatile 变量的最新值。
     *下载Volatile 变量可用于提供线程安全，但是只能应用于非常有限的一组用例：多个变量之间或者某个变量的当前值与修改后值之间没有约束。
     *因此，单独使用 volatile 还不足以实现计数器、互斥锁或任何具有与多个变量相关的不变式（Invariants）的类（例如 “start <=end”）。
     *
     * 要使 volatile 变量提供理想的线程安全，必须同时满足下面两个条件：
     *                  对变量的写操作不依赖于当前值。
     *                          第一个条件的限制使 volatile 变量不能用作线程安全计数器。虽然增量操作（x++）看上去类似一个单独操作，实际上它是一个由读取－修改－写入操作序列组成的组合操作，必须以原子方式执行，而 volatile 不能提供必须的原子特性。实现正确的操作需要使 x 的值在操作期间保持不变，而 volatile 变量无法实现这点。（然而，如果将值调整为只从单个线程写入，那么可以忽略第一个条件。）
     *                  该变量没有包含在具有其他变量的不变式中。
     *
     */




    public static void main(String[] args) {
        BasicSynchronization basicSynchronization=new BasicSynchronization();
        //basicSynchronization.cacheVariables();
        //new DeadlockDemo().run();
        new StopThread().execut();
    }
}
class StopThread{
    //
    private volatile   boolean flag=false;
    //多试几次  可以看到循环的打印数据
    //private  boolean flag=false;
    private  int counter=0;

    public void  execut(){

        Runnable r=new Runnable() {
            @Override
            public void run() {
                while (!flag){
                    for (int i=0;i<5;i++)
                        System.out.println("this is test:"+counter++);
                }
            }
        };

        Thread thread=new Thread(r);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();

        flag=true;
    }


}
