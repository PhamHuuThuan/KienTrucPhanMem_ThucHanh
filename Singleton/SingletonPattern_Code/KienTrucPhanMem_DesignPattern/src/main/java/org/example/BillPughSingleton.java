package org.example;

public class BillPughSingleton {
    private BillPughSingleton(){}

    private static class SingletonHelper{
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance(){
        return SingletonHelper.INSTANCE;
    }
    public static void main(String[] args) {
        // Số lượng luồng
        int numThreads = 5;

        // Mảng để lưu trữ các luồng
        Thread[] threads = new Thread[numThreads];

        // Thời gian bắt đầu
        long startTime = System.currentTimeMillis();

        // Tạo và khởi chạy các luồng
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new SingletonTask(), "Thread-" + (i + 1));
            threads[i].start();
        }

        // Chờ tất cả các luồng kết thúc
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted.");
            }
        }

        // Thời gian kết thúc
        long endTime = System.currentTimeMillis();

        // Tính thời gian thực thi
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }

    // Lớp SingletonTask thực thi công việc trong mỗi luồng
    static class SingletonTask implements Runnable {
        @Override
        public void run() {
            // Lấy thể hiện của BillPughSingleton
            BillPughSingleton instance = BillPughSingleton.getInstance();

            // In thông tin về thể hiện
            System.out.println(Thread.currentThread().getName() + " got instance with hashcode: " + instance.hashCode());
        }
    }
}
