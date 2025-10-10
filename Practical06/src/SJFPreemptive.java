import java.util.*;

class ProcessPreemptive {
    int pid;           // Process ID
    int arrivalTime;   // Arrival Time
    int burstTime;     // Burst Time
    int remainingTime; // Remaining Burst Time
    int completionTime;
    int turnAroundTime;
    int waitingTime;
    boolean isCompleted;

    ProcessPreemptive(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.isCompleted = false;
    }
}

// SJF Preemptive (SRTF)
public class SJFPreemptive {

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
        ProcessPreemptive current = null;

        while (completed < n) {
            ProcessPreemptive shortest = null;
            int minRemaining = Integer.MAX_VALUE;

            // Find process with smallest remaining time at this moment
            for (ProcessPreemptive p : processPreemptives) {
                if (!p.isCompleted && p.arrivalTime <= currentTime && p.remainingTime < minRemaining) {
                    minRemaining = p.remainingTime;
                    shortest = p;
                }
            }

            if (shortest == null) {
                currentTime++;
                continue;
            }

            // Execute the chosen process for 1 unit time (preemption point)
            shortest.remainingTime--;
            currentTime++;

            if (shortest.remainingTime == 0) {
                shortest.isCompleted = true;
                shortest.completionTime = currentTime;
                shortest.turnAroundTime = shortest.completionTime - shortest.arrivalTime;
                shortest.waitingTime = shortest.turnAroundTime - shortest.burstTime;
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
