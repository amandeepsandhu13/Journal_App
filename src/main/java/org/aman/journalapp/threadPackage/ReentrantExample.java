package org.aman.journalapp.threadPackage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample {

    Lock lock = new ReentrantLock();

    public void outerMethod(){
        lock.lock();
        try{
            System.out.println("inside outerMethod");
            InnerMethod();
        }finally {
            lock.unlock();
        }

    }

    public void InnerMethod(){
        lock.lock();
        try {
            System.out.println("Inside InnerMethod");
        }finally {
            lock.unlock();
        }
    }

//    public static void main(String[] args) {
//        ReentrantExample exp = new ReentrantExample();
//        exp.outerMethod();
//    }
}
