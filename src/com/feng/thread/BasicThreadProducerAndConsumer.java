package com.feng.thread;

/**
 * 生产者和消费者的实例
 */
public class BasicThreadProducerAndConsumer {

    public static void main(String[] args) {
        Shared s=new Shared();
        new Producer(s).start();
        new Consumer(s).start();
    }
}
class  Shared{

    private  char c;
    private  volatile  boolean writeable=true;

    synchronized  void setSharedChar(char c){
        while (!writeable){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.c=c;
        writeable=false;
        notify();
    }
    synchronized  char getSharedChar(){
        while (writeable){
            try {
                wait();
            }catch (Exception e){

            }

        }
        writeable=true;
        notify();
        return  c;
    }


}
class  Producer extends Thread{
    private  final  Shared s;
    Producer(Shared s){
        this.s=s;
    }

    @Override
    public void run() {
        for (char ch='A';ch<='Z';ch++){
            //优化输出顺序
            synchronized (s){
                s.setSharedChar(ch);
                System.out.println(ch + " produced by producer!");
            }

        }
    }
}
class  Consumer extends  Thread{
    private  final  Shared s;
    Consumer(Shared s){
        this.s=s;
    }

    @Override
    public void run() {
        char ch;
        do {
            synchronized (s){
                ch=s.getSharedChar();
                System.out.println(ch+" consumed by consumer!");
            }

        }
        while (ch != 'Z');
    }
}
