package com.feng.thread;

/**
 * 线程的等待和通知
 *
 * java.lang.Object类提供了一套等待/通知的API，他由3个wait（），一个notify（）和一个notifyAll（）组成
 */
public class BasicThreadWaitAndNotify {
    /**
     * =================================================================================================================
     *
     * wait()的作用是让当前线程进入等待状态，同时，wait()也会让当前线程释放它所持有的锁。
     * “直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法”，当前线程被唤醒(进入“就绪状态”)
     *
     * notify()和notifyAll()的作用，则是唤醒当前对象上的等待线程；notify()是唤醒单个线程，而notifyAll()是唤醒所有的线程。
     *
     */
    public void waitTest(){
        ThreadA t1 = new ThreadA("t1");
        synchronized(t1) {
            try {
                // 启动“线程t1”
                System.out.println(Thread.currentThread().getName()+" start");
                t1.start();
                // 主线程等待t1通过notify()唤醒。
                System.out.println(Thread.currentThread().getName()+" wait()");
                t1.wait();  //  不是使t1线程等待，而是当前执行wait的线程等待
                System.out.println(Thread.currentThread().getName()+" continue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public  void  waitTest2(){
        ThreadB t1 = new ThreadB("t1");
        synchronized(t1) {
            try {
                // 启动“线程t1”
                System.out.println(Thread.currentThread().getName() + " start t1");
                t1.start();
                // 主线程等待t1通过notify()唤醒 或 notifyAll()唤醒，或超过3000ms延时；然后才被唤醒。
                System.out.println(Thread.currentThread().getName() + " call wait ");
                t1.wait(6000);
                System.out.println(Thread.currentThread().getName() + " continue");
                t1.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        new BasicThreadWaitAndNotify().waitTest();
        new BasicThreadWaitAndNotify().waitTest2();
    }
}


class ThreadA extends Thread{
    public ThreadA(String name) {
        super(name);
    }
    public void run() {
        synchronized (this) {
            try {
                Thread.sleep(1000);	//	使当前线阻塞 1 s，确保主程序的 t1.wait(); 执行之后再执行 notify()
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" call notify()");
            // 唤醒当前的wait线程
            this.notify();
        }
    }
}

class ThreadB extends Thread{
    public ThreadB(String name) {
        super(name);
    }
    public void run() {
        System.out.println(Thread.currentThread().getName() + " run");
        // 死循环，不断运行。
        while(true){;}	//	这个线程与主线程无关，无 synchronized
    }
}