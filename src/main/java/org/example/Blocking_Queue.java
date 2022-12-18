package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//package _22_23_WiSe._20221121_notify_hashfinder;

public class Blocking_Queue {
    public static void main(String[] args) {

        DataShareWait ds = new DataShareWait();

        Runnable sender = () -> {
           // try {
                for (int i = 0; i < 100; i++) {
                    ds.setDataString("Message " + i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        };

        Runnable receiver = () ->{
            for (int i = 0; i < 100; i++) {
                System.out.println("Got Message: " + ds.getDataString());
            }
        };

        new Thread(sender).start();
        new Thread(receiver).start();
    }
}

class DataShareWait {
    BlockingQueue<String> queue = new LinkedBlockingQueue<>(100);

    synchronized public String getDataString() {
        String returnVal="";
        try {
            returnVal=queue.take();
            System.out.println("Message received!");
            // take method retrieves and removes element at the queue head.
            // If the queue is empty then it waits until an element becomes available
        } catch (InterruptedException e) {
            System.out.println("Message not received!");
            e.printStackTrace();
        }
        return returnVal;
    }

    synchronized public void setDataString(String message) {
        try {
            queue.put(message);
            // put method adds an element to the queue.
            // If the queue is full then it waits until space becomes available
            System.out.println("Message sent!");

        } catch (InterruptedException e1) {
            System.out.println("Message not sent!");
            e1.printStackTrace();
        }
    }
}
