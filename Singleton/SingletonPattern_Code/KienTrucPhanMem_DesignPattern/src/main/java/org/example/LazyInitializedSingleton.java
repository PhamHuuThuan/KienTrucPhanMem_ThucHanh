package org.example;

public class LazyInitializedSingleton {
    private static LazyInitializedSingleton instance;

    private LazyInitializedSingleton(){}

    public static LazyInitializedSingleton getInstance(){
        if(instance == null){
            instance = new LazyInitializedSingleton();
        }
        return instance;
    }
    public static void main(String[] args) {
        // Tạo và khởi chạy hai luồng
        Thread thread1 = new Thread(new SingletonTask(), "Thread-1");
        Thread thread2 = new Thread(new SingletonTask(), "Thread-2");

        thread1.start();
        thread2.start();

        // Chờ các luồng kết thúc
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("Main thread finished.");
    }

    // Lớp SingletonTask thực thi công việc trong mỗi luồng
    static class SingletonTask implements Runnable {
        @Override
        public void run() {
            // Lấy thể hiện của LazyInitializedSingleton
            LazyInitializedSingleton instance = LazyInitializedSingleton.getInstance();

            // In thông tin về thể hiện
            System.out.println(Thread.currentThread().getName() + " got instance with hashcode: " + instance.hashCode());
        }
    }
}