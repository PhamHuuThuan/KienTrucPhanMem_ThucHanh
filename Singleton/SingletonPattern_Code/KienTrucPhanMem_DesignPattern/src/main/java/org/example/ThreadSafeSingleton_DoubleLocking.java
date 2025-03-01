package org.example;

public class ThreadSafeSingleton_DoubleLocking {
    // Sử dụng volatile để đảm bảo tính nhất quán trong môi trường đa luồng
    private static volatile ThreadSafeSingleton_DoubleLocking instance;

    // Private constructor để ngăn chặn việc tạo thể hiện từ bên ngoài
    private ThreadSafeSingleton_DoubleLocking() {}

    // Phương thức getInstance sử dụng Double-Checked Locking
    public static ThreadSafeSingleton_DoubleLocking getInstanceUsingDoubleLocking() {
        if (instance == null) { // Kiểm tra lần đầu (không đồng bộ)
            synchronized (ThreadSafeSingleton_DoubleLocking.class) { // Đồng bộ hóa
                if (instance == null) { // Kiểm tra lần thứ hai (đồng bộ)
                    instance = new ThreadSafeSingleton_DoubleLocking();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        // Số lượng luồng
        int numThreads = 3;

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
            // Lấy thể hiện của ThreadSafeSingleton_DoubleLocking
            ThreadSafeSingleton_DoubleLocking instance = ThreadSafeSingleton_DoubleLocking.getInstanceUsingDoubleLocking();

            // In thông tin về thể hiện
            System.out.println(Thread.currentThread().getName() + " got instance with hashcode: " + instance.hashCode());
        }
    }
}