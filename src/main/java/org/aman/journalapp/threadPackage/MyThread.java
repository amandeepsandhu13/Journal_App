package org.aman.journalapp.threadPackage;

public class MyThread extends Thread {

    private  Counter counter;

    public MyThread(Counter counter) {
        this.counter = counter;
    }

    public MyThread(String name){
        super(name);
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() +" Priority " + Thread.currentThread().getPriority());
        }
        System.out.println("Running MyThread");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

//class MyThreadMain {
//    public static void main(String[] args) throws InterruptedException {
//
//        Counter counter = new Counter();
//        MyThread myThreadCount = new MyThread(counter);
//        myThreadCount.start();
//
//        Thread t1 = new MyThread("LowPriority");
//        Thread t2 = new MyThread("NormalPriority");
//        Thread t3 = new MyThread("MaxPriority");
//        t1.setPriority(Thread.MIN_PRIORITY);
//        t2.setPriority(Thread.NORM_PRIORITY);
//        t3.setPriority(Thread.MAX_PRIORITY);
//            t1.start();
//            t2.start();
//            t3.start();
//        System.out.println(Thread.currentThread().getName() + " thread state: " + Thread.currentThread().getState());
//        t1.join()
//;    }
//}
