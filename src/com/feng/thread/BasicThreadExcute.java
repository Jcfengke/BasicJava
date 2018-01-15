package com.feng.thread;

/**
 * 基本的线程操作
 */
public class BasicThreadExcute {

    /**
     * =================================================================================================================
     * 中断线程
     * interrupt（）
     *
     *
     */
    public void interruptThread(){

        Runnable r=new Runnable() {
           @Override
           public void run() {
                String name=Thread.currentThread().getName();
                int count=0;
                while (!Thread.interrupted())
                    System.out.println(name+":"+count++);
           }
       };

       Thread threadA=new Thread(r);
       Thread threadB=new Thread(r);

       threadA.start();
       threadB.start();

       //忙循环，给予后台线程一些时间，以便可以打印出一些结果
       while (true){

           double n=Math.random();
           if (n>=0.49999999&&n<=0.50000001)
               break;
       }

       threadA.interrupt();
       threadB.interrupt();

    }


    /**
     * =================================================================================================================
     * 等待线程
     * join（）
     * join是Thread类的一个方法，启动线程后直接调用，即join()的作用是：“等待该线程终止”，这里需要理解的就是该线程是指的主线程等待子线程
     * 的终止。也就是在子线程调用了join()方法后面的代码，只有等到子线程结束了才能执行。
     *
     * 可以让主线程在子线程结束后再结束运行
     *
     */
    public void joinThread(){

        class Thread1 extends Thread{
            private String name;
            public Thread1(String name) {
                super(name);
                this.name=name;
            }
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
                for (int i = 0; i < 5; i++) {
                    System.out.println("子线程"+name + "运行 : " + i);
                    //休眠
                    try {
                        sleep((int) Math.random() * 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
            }
        }

        System.out.println(Thread.currentThread().getName()+"主线程运行开始!");

        Thread1 mTh1=new Thread1("A");
        Thread1 mTh2=new Thread1("B");
        mTh1.start();
        mTh2.start();

        //通过控制台观察效果
        try {
            mTh1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            mTh2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+ "主线程运行结束!");

    }


    /**
     * =================================================================================================================
     * 线程睡眠
     *
     *
     */
    public void  sleepThread(){
        Runnable r=new Runnable() {
            @Override
            public void run() {
                int count=0;
                while (!Thread.interrupted())
                System.out.println(Thread.currentThread().getName() + ":"+ count++);
            }
        };

        Thread thread1=new Thread(r);
        Thread thread2=new Thread(r);

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){

        }

        thread1.interrupt();
        thread2.interrupt();
    }



    /**
     * =================================================================================================================
     * 暂停线程
     *
     * yield():暂停当前正在执行的线程对象，并执行其他线程。
     *
     * Thread.yield()方法作用是：暂停当前正在执行的线程对象，并执行其他线程。
     *
     *  yield()应该做的是让当前运行线程回到可运行状态，以允许具有相同优先级的其他线程获得运行机会。
     *  因此，使用yield()的目的是让相同优先级的线程之间能适当的轮转执行。但是，实际中无法保证yield()达到让步目的，
     *  因为让步的线程还有可能被线程调度程序再次选中。
     *
     */
    public void yieldThread(){
        class ThreadYield extends Thread{
            public ThreadYield(String name) {
                super(name);
            }

            @Override
            public void run() {
                for (int i = 1; i <= 50; i++) {
                    System.out.println("" + this.getName() + "-----" + i);
                    // 当i为30时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
                    if (i ==30) {
                        this.yield();
                    }
                }

            }
        }

        ThreadYield yt1 = new ThreadYield("张三");
        ThreadYield yt2 = new ThreadYield("李四");
        yt1.start();
        yt2.start();

    }




    public static void main(String[] args) {
        BasicThreadExcute basicThreadExcute=new BasicThreadExcute();
        //basicThreadExcute.interruptThread();
        //basicThreadExcute.joinThread();
//        basicThreadExcute.sleepThread();
        basicThreadExcute.yieldThread();
    }
}
