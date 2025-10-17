import java.util.*;

class Process {
    int pid;           // Process ID
    int arrivalTime;   // Arrival Time
    int burstTime;     // Burst Time
    int remainingTime; // Remaining Burst Time
    int completionTime;
    int turnAroundTime;
    int waitingTime;

    Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

// Round Robin Scheduling
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival Time and Burst Time for P" + (i + 1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            processes[i] = new Process(i + 1, at, bt);
        }

        System.out.print("Enter Time Quantum: ");
        int quantum = sc.nextInt();

        Queue<Process> readyQueue = new LinkedList<>();
        int currentTime = 0, completed = 0;
        boolean[] inQueue = new boolean[n];

        // Sort processes by arrival time for initial reference
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        // Start scheduling
        readyQueue.add(processes[0]);
        inQueue[0] = true;
        currentTime = processes[0].arrivalTime;

        while (!readyQueue.isEmpty()) {
            Process current = readyQueue.poll();

            int execTime = Math.min(current.remainingTime, quantum);
            current.remainingTime -= execTime;
            currentTime += execTime;

            // Add newly arrived processes to queue
            for (int i = 0; i < n; i++) {
                Process p = processes[i];
                if (!inQueue[i] && p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    readyQueue.add(p);
                    inQueue[i] = true;
                }
            }

            // If current process still has burst left, requeue it
            if (current.remainingTime > 0) {
                readyQueue.add(current);
            } else {
                current.completionTime = currentTime;
                completed++;
            }

            // If queue empty but some process yet to arrive, fast-forward time
            if (readyQueue.isEmpty() && completed < n) {
                for (int i = 0; i < n; i++) {
                    if (!inQueue[i]) {
                        readyQueue.add(processes[i]);
                        inQueue[i] = true;
                        currentTime = Math.max(currentTime, processes[i].arrivalTime);
                        break;
                    }
                }
            }
        }

        // Calculate TAT and WT
        double totalTAT = 0, totalWT = 0;
        for (Process p : processes) {
            p.turnAroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnAroundTime - p.burstTime;
            totalTAT += p.turnAroundTime;
            totalWT += p.waitingTime;
        }

        // Output
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" +
                    p.completionTime + "\t" + p.turnAroundTime + "\t" + p.waitingTime);
        }

        System.out.println("\nAverage TAT = " + (totalTAT / n));
        System.out.println("Average WT  = " + (totalWT / n));
    }
}
