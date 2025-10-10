import java.util.*;

class Process {
    int pid;           // Process ID
    int arrivalTime;   // Arrival Time
    int burstTime;     // Burst Time
    int remainingTime; // Remaining Burst Time
    int priority;      // Priority (lower = higher)
    int completionTime;
    int turnAroundTime;
    int waitingTime;
    boolean isCompleted;

    Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
        this.isCompleted = false;
    }
}

// Priority Scheduling (Preemptive)
public class PriorityPreemptive {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival Time, Burst Time, and Priority for P" + (i + 1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            int pr = sc.nextInt();
            processes[i] = new Process(i + 1, at, bt, pr);
        }

        int completed = 0, currentTime = 0;

        while (completed < n) {
            Process highestPriority = null;
            int minPriority = Integer.MAX_VALUE;

            // Find process with the highest priority among arrived ones
            for (Process p : processes) {
                if (!p.isCompleted && p.arrivalTime <= currentTime) {
                    if (p.priority < minPriority ||
                            (p.priority == minPriority && p.arrivalTime < (highestPriority != null ? highestPriority.arrivalTime : Integer.MAX_VALUE))) {
                        minPriority = p.priority;
                        highestPriority = p;
                    }
                }
            }

            if (highestPriority == null) {
                currentTime++;
                continue;
            }

            // Execute 1 time unit
            highestPriority.remainingTime--;
            currentTime++;

            if (highestPriority.remainingTime == 0) {
                highestPriority.isCompleted = true;
                highestPriority.completionTime = currentTime;
                highestPriority.turnAroundTime = highestPriority.completionTime - highestPriority.arrivalTime;
                highestPriority.waitingTime = highestPriority.turnAroundTime - highestPriority.burstTime;
                completed++;
            }
        }

        // Output
        System.out.println("\nPID\tAT\tBT\tPR\tCT\tTAT\tWT");
        double totalTAT = 0, totalWT = 0;
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" +
                    p.priority + "\t" + p.completionTime + "\t" + p.turnAroundTime + "\t" + p.waitingTime);
            totalTAT += p.turnAroundTime;
            totalWT += p.waitingTime;
        }

        System.out.println("\nAverage TAT = " + (totalTAT / n));
        System.out.println("Average WT  = " + (totalWT / n));
    }
}
