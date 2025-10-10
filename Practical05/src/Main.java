import java.util.*;

class Process {
    int pid;          // process id
    int arrival;      // arrival time
    int burst;        // burst time
    int completion;   // completion time
    int turnaround;   // turnaround time
    int waiting;      // waiting time

    Process(int pid, int arrival, int burst) {
        this.pid = pid;
        this.arrival = arrival;
        this.burst = burst;
    }
}

public class Main {

    // Case 1: FCFS WITH arrival time
    public static void fcfsWithArrival(List<Process> processes) {
        // Sort by arrival time
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrival));

        int time = 0;
        for (Process p : processes) {
            if (time < p.arrival) {
                time = p.arrival;  // CPU waits for process
            }
            time += p.burst;
            p.completion = time;
            p.turnaround = p.completion - p.arrival;
            p.waiting = p.turnaround - p.burst;
        }

        System.out.println("\n--- FCFS (With Arrival Time) ---");
        printTable(processes);
    }

    // Case 2: FCFS WITHOUT arrival time
    public static void fcfsWithoutArrival(List<Process> processes) {
        int time = 0;
        for (Process p : processes) {
            time += p.burst;
            p.completion = time;
            p.turnaround = p.completion;  // since arrival = 0
            p.waiting = p.turnaround - p.burst;
        }

        System.out.println("\n--- FCFS (Without Arrival Time) ---");
        printTable(processes);
    }

    // Print process table
    public static void printTable(List<Process> processes) {
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.println(p.pid + "\t" + p.arrival + "\t" + p.burst + "\t" +
                    p.completion + "\t" + p.turnaround + "\t" + p.waiting);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        List<Process> list1 = new ArrayList<>();
        List<Process> list2 = new ArrayList<>();

        // Take process input
        for (int i = 1; i <= n; i++) {
            System.out.print("Enter arrival time and burst time for P" + i + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            list1.add(new Process(i, at, bt));
            list2.add(new Process(i, 0, bt)); // arrival = 0 for no-arrival case
        }

        // Run both algorithms
        fcfsWithArrival(list1);
        fcfsWithoutArrival(list2);

        sc.close();
    }
}
