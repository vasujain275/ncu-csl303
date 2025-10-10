import java.util.*;

class Process {
    int pid;           // Process ID
    int arrivalTime;   // Arrival Time
    int burstTime;     // Burst Time
    int completionTime;
    int turnAroundTime;
    int waitingTime;
    boolean isCompleted;

    Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.isCompleted = false;
    }
}

// SJFNonPreemptive
public class SJFNonPreemptive {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        ProcessPreemptive[] processPreemptives = new ProcessPreemptive[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival Time and Burst Time for P" + (i + 1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            processPreemptives[i] = new ProcessPreemptive(i + 1, at, bt);
        }

        int completed = 0, currentTime = 0;
        while (completed < n) {
            ProcessPreemptive shortest = null;
            int minBT = Integer.MAX_VALUE;

            for (ProcessPreemptive p : processPreemptives) {
                if (!p.isCompleted && p.arrivalTime <= currentTime && p.burstTime < minBT) {
                    minBT = p.burstTime;
                    shortest = p;
                }
            }

            if (shortest == null) {
                currentTime++; // No process has arrived, increment time
            } else {
                shortest.completionTime = currentTime + shortest.burstTime;
                shortest.turnAroundTime = shortest.completionTime - shortest.arrivalTime;
                shortest.waitingTime = shortest.turnAroundTime - shortest.burstTime;
                shortest.isCompleted = true;

                currentTime = shortest.completionTime;
                completed++;
            }
        }

        // Output
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        double totalTAT = 0, totalWT = 0;
        for (ProcessPreemptive p : processPreemptives) {
            System.out.println("P" + p.pid + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" +
                    p.completionTime + "\t" + p.turnAroundTime + "\t" + p.waitingTime);
            totalTAT += p.turnAroundTime;
            totalWT += p.waitingTime;
        }

        System.out.println("\nAverage TAT = " + (totalTAT / n));
        System.out.println("Average WT  = " + (totalWT / n));
    }
}
