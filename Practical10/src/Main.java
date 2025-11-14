import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.Scanner;

// DiningPhilosophers
public class Main {

    static Semaphore[] forks;   // One fork (semaphore) between each philosopher
    static int n;               // Number of philosophers/forks
    static Random rnd = new Random();

    static class Philosopher extends Thread {
        int id;
        int left, right;

        Philosopher(int id) {
            this.id = id;
            this.left = id;
            this.right = (id + 1) % n;
        }

        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is THINKING");
            Thread.sleep(200 + rnd.nextInt(300));
        }

        private void eat() throws InterruptedException {
            System.out.println("\tPhilosopher " + id + " is EATING");
            Thread.sleep(300 + rnd.nextInt(200));
        }

        public void run() {
            try {
                think();

                // Odd philosophers pick LEFT then RIGHT
                // Even philosophers pick RIGHT then LEFT
                if (id % 2 == 1) {
                    forks[left].acquire();
                    System.out.println("Philosopher " + id + " picked up LEFT fork " + left);
                    forks[right].acquire();
                    System.out.println("Philosopher " + id + " picked up RIGHT fork " + right);
                } else {
                    forks[right].acquire();
                    System.out.println("Philosopher " + id + " picked up RIGHT fork " + right);
                    forks[left].acquire();
                    System.out.println("Philosopher " + id + " picked up LEFT fork " + left);
                }

                eat();

                // Put down both forks
                forks[left].release();
                forks[right].release();
                System.out.println("Philosopher " + id + " has FINISHED EATING");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of philosophers: ");
        n = sc.nextInt();

        forks = new Semaphore[n];
        for (int i = 0; i < n; i++) {
            forks[i] = new Semaphore(1);
        }

        Philosopher[] philosophers = new Philosopher[n];
        for (int i = 0; i < n; i++) {
            philosophers[i] = new Philosopher(i);
            philosophers[i].start();
        }

        // Wait for all philosophers to finish one meal
        for (int i = 0; i < n; i++) {
            try {
                philosophers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nAll philosophers have eaten one meal. Program finished.");
        sc.close();
    }
}
