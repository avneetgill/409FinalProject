# java threads
## fun facts
- all java program must have atleast one thread



## implementing Class Thread
- one way to implement **class thread** is to extend the "thread class" and then override the run() function
- consider the following snipit of code 
~~~java
public class SimpleThread extends Thread {
    public void run() {
        System.out.println("A simple thread that does nothing!");
    }
    public static void main(String args[]) {
        Thread t = new SimpleThread();
        t.start();   //function start() calls function run()
    }
}
~~~
1. **extends** thread class
2. overrides method run()
- **issue** here because java does not support multiple inheritance, therefore if class **SimpleThread** has to extend another class it will not be able to 

## Overcoming the multiple inheritance issue 
~~~java
public class SimpleThread implements Runnable {
    public void run() {
        System.out.println("A simple thread that does
        nothing!");
    }
    public static void main(String args[]) {
        Runnable r = new SimpleThread();
        Thread t = new Thread(r);
        t.start(); //function start() calls function run()
    }
}
~~~
1. **implement** a **Runnable** interface 
2. defines function run()

## Difference between wait and sleep method
- wait() is called on the object **current** thread
- sleep() always called on the thread that is **expected**

## Fun Facts
- Synchronize resources otherwise you may mess stuff up, example bank withdraws

## class thread methods 
- **start()**: a program launches a thread by calling this function
- **run()**: is called by the start() function 
- **sleep(milliseconds)**: this is a **static method** that indicates how long a thread should "sleep"
    - **note**: the wait time is not exact and may vary
- **interupt()**: DOES NOTE INTERUPT A RUNNING THREAD!! However it changes the interupt status of the thread. It indicate to a thread that is should stop what its doing and do something else. Implemented using a internal flag invoking Thread.interrupt
- **static boolean interrupted()**: Returns true if the method is interrupted, checks the current thread, clears the satus flag of the thread
- **boolean isInterrupted()**: returns true if thread is interrupted, DOES NOT CLEAR STATUS
- **join()**: gets the current thread to wait for another thread to complete its job
    - **join(milliseconds)**: ask another thread to stop and wait for another thread to terminate or wait for the time specified 
- Many other methods just google that shit

## thread issues 
- limit on how many threads can be created before performance is degraded
- data corruption: threads that are unaware of sharing the same data
- deadlocks: program no longer reacts 
- saftely failure: program creates incorrect data 

## over coming thread issues 
- two keywords are provided for this purpose
    1. Sychornized: avoid lock to an object when it used by another thread, synch threads so that resouces can only be accessed by one thread at a time. This is done by a creating a block marked with the keyword **synchronized**. All synchornized block in java synchonized by the same object can only have one thread accessing at a time
    2. volatile: a key word that indicates that its value will be modified by different threads. Access to this varialbe acts as though it is enclosed in a sychornized block
    


 