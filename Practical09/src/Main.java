import java.util.concurrent.Semaphore;
import java.util.Scanner;

class ReaderWriter {
    static Semaphore mutex = new Semaphore(1);   // To protect readCount variable
    static Semaphore writeLock = new Semaphore(1); // To give writers exclusive access
    static int readCount = 0;

    // Reader thread
    static class Reader extends Thread {
        int readerId;

        Reader(int id) {
            this.readerId = id;
        }

        public void run() {
            try {
                // Entry section
                mutex.acquire();
                readCount++;
                if (readCount == 1) writeLock.acquire(); // first reader locks writing
                mutex.release();

                // Critical section (reading)
                System.out.println("Reader " + readerId + " is READING");
                Thread.sleep(500);

                // Exit section
                mutex.acquire();
                readCount--;
                if (readCount == 0) writeLock.release(); // last reader unlocks writing
                mutex.release();

                System.out.println("Reader " + readerId + " has FINISHED READING");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Writer thread
    static class Writer extends Thread {
        int writerId;

        Writer(int id) {
            this.writerId = id;
        }

        public void run() {
            try {
                // Entry section
                writeLock.acquire();

                // Critical section (writing)
                System.out.println("\tWriter " + writerId + " is WRITING");
                Thread.sleep(500);
                System.out.println("\tWriter " + writerId + " has FINISHED WRITING");

                // Exit section
                writeLock.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Main program
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of readers: ");
        int nr = sc.nextInt();
        System.out.print("Enter number of writers: ");
        int nw = sc.nextInt();

        Reader[] readers = new Reader[nr];
        Writer[] writers = new Writer[nw];

        // Start readers and writers alternately
        for (int i = 0; i < Math.max(nr, nw); i++) {
            if (i < nr) {
                readers[i] = new Reader(i + 1);
                readers[i].start();
            }
            if (i < nw) {
                writers[i] = new Writer(i + 1);
                writers[i].start();
            }
        }
    }
}
