import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input
        System.out.print("Enter number of processes: ");
        int P = sc.nextInt();

        System.out.print("Enter number of resources: ");
        int R = sc.nextInt();

        int[][] max = new int[P][R];
        int[][] alloc = new int[P][R];
        int[][] need = new int[P][R];
        int[] avail = new int[R];

        System.out.println("\nEnter MAX matrix:");
        for (int i = 0; i < P; i++)
            for (int j = 0; j < R; j++)
                max[i][j] = sc.nextInt();

        System.out.println("\nEnter ALLOCATION matrix:");
        for (int i = 0; i < P; i++)
            for (int j = 0; j < R; j++)
                alloc[i][j] = sc.nextInt();

        System.out.println("\nEnter AVAILABLE vector:");
        for (int j = 0; j < R; j++)
            avail[j] = sc.nextInt();

        // Need = Max - Alloc
        for (int i = 0; i < P; i++)
            for (int j = 0; j < R; j++)
                need[i][j] = max[i][j] - alloc[i][j];

        // Safety check
        boolean[] finish = new boolean[P];
        int[] work = avail.clone();
        int count = 0;

        System.out.println("\nChecking Safe State...");
        while (count < P) {
            boolean found = false;

            for (int i = 0; i < P; i++) {
                if (!finish[i]) {
                    boolean ok = true;

                    for (int j = 0; j < R; j++) {
                        if (need[i][j] > work[j]) {
                            ok = false;
                            break;
                        }
                    }

                    if (ok) {
                        for (int j = 0; j < R; j++)
                            work[j] += alloc[i][j];

                        finish[i] = true;
                        found = true;
                        count++;

                        System.out.println("Process P" + i + " can complete.");
                    }
                }
            }

            if (!found) {
                System.out.println("System is NOT in safe state!");
                sc.close();
                return;
            }
        }

        System.out.println("System is in SAFE state.");

        // REQUEST SECTION
        System.out.print("\nEnter process number making a request: ");
        int p = sc.nextInt();

        int[] req = new int[R];
        System.out.println("Enter request vector:");
        for (int j = 0; j < R; j++)
            req[j] = sc.nextInt();

        // Check request validity
        for (int j = 0; j < R; j++) {
            if (req[j] > need[p][j]) {
                System.out.println("Request exceeds process NEED. Denied.");
                sc.close();
                return;
            }
            if (req[j] > avail[j]) {
                System.out.println("Not enough available resources. Process must WAIT.");
                sc.close();
                return;
            }
        }

        // Pretend allocation
        for (int j = 0; j < R; j++) {
            avail[j] -= req[j];
            alloc[p][j] += req[j];
            need[p][j] -= req[j];
        }

        // Check again if safe
        for (int i = 0; i < P; i++) finish[i] = false;
        work = avail.clone();
        count = 0;

        while (count < P) {
            boolean found = false;

            for (int i = 0; i < P; i++) {
                if (!finish[i]) {
                    boolean ok = true;

                    for (int j = 0; j < R; j++) {
                        if (need[i][j] > work[j]) {
                            ok = false;
                            break;
                        }
                    }

                    if (ok) {
                        for (int j = 0; j < R; j++)
                            work[j] += alloc[i][j];
                        finish[i] = true;
                        found = true;
                        count++;
                    }
                }
            }

            if (!found) {
                System.out.println("After request → UNSAFE. Request DENIED.");
                sc.close();
                return;
            }
        }

        System.out.println("Request can be safely granted.");
        sc.close();
    }
}
