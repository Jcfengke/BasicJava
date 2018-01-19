package com.feng.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器框架
 *
 * Timer 适用于大规模并发调度定时任务（数以千计的任务不在话下）
 *
 *
 * 1.Timer在执行所有定时任务时只会创建一个线程。如果某个任务的执行时间长度大于其周期时间长度，那么就会导致这一次的任务还在执行，
 * 而下一个周期的任务已经需要开始执行了，当然在一个线程内这两个任务只能顺序执行，有两种情况：对于之前需要执行但还没有执行的任务，
 * 一是当前任务执行完马上执行那些任务（按顺序来），二是干脆把那些任务丢掉，不去执行它们。至于具体采取哪种做法，
 * 需要看是调用schedule还是scheduleAtFixedRate。
 *
 * 2.如果TimerTask抛出了一个未检出的异常，那么Timer线程就会被终止掉，之前已经被调度但尚未执行的TimerTask就不会再执行了，新的任务也不能被调度了。
 *
 */
public class BasicJavaTimer {

    public static void main(String[] args) {
        //new TimerDemo1().execute();
        new TimerDemo2().execute();
    }

}
//单次执行定时器任务
class  TimerDemo1{

    public  void  execute(){
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                System.out.println("alarm going off");
                System.exit(0);
            }
        };

        Timer timer=new Timer();
        timer.schedule(task,2000);
    }
}
//以一秒的间隔不断显示当前系统时间
class  TimerDemo2{
    public void  execute(){
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis());
            }
        };
        Timer timer=new Timer();
        timer.schedule(task,0,1000);
    }
}
