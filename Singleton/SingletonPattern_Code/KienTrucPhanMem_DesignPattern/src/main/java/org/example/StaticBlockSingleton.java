package org.example;

public class StaticBlockSingleton {
    private static StaticBlockSingleton instance;

    private StaticBlockSingleton(){}

    //static block initialization for exception handling
    static{
        try{
            instance = new StaticBlockSingleton();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }

    public static StaticBlockSingleton getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        // Tạo và khởi chạy nhiều luồng
        for (int i = 1; i <= 5; i++) {
            Thread thread = new Thread(new SingletonTask(), "Thread-" + i);
            thread.start();
        }
    }

    // Lớp SingletonTask thực thi công việc trong mỗi luồng
    static class SingletonTask implements Runnable {
        @Override
        public void run() {
            try {
                StaticBlockSingleton instance = StaticBlockSingleton.getInstance();
                System.out.println(Thread.currentThread().getName() + " successfully got the singleton instance: " + instance.hashCode());
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " encountered an exception: " + e.getMessage());
            }
        }
    }
}
