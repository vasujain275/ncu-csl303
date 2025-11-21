import java.util.*;

// Page replacement policy using a) LRU b) FIFO c) Optimal
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // INPUT
        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();

        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();

        int[] pages = new int[n];
        System.out.println("Enter page reference string:");
        for (int i = 0; i < n; i++)
            pages[i] = sc.nextInt();

        System.out.println("\n--- PAGE REPLACEMENT RESULTS ---");

        fifo(pages, frames);
        lru(pages, frames);
        optimal(pages, frames);

        sc.close();
    }


    // ---------------- FIFO ----------------
    static void fifo(int[] pages, int frames) {
        Queue<Integer> q = new LinkedList<>();
        HashSet<Integer> set = new HashSet<>();

        int faults = 0;

        for (int p : pages) {
            if (!set.contains(p)) {
                if (set.size() == frames) {
                    int removed = q.poll();  // Remove oldest
                    set.remove(removed);
                }
                q.add(p);
                set.add(p);
                faults++;
            }
        }

        System.out.println("\nFIFO Page Faults = " + faults);
    }


    // ---------------- LRU ----------------
    static void lru(int[] pages, int frames) {
        ArrayList<Integer> list = new ArrayList<>();
        int faults = 0;

        for (int p : pages) {
            if (!list.contains(p)) {
                if (list.size() == frames)
                    list.remove(0);   // Least recently used at front
                list.add(p);
                faults++;
            } else {
                list.remove((Integer)p);
                list.add(p);          // Move to most recently used position
            }
        }

        System.out.println("LRU Page Faults = " + faults);
    }


    // ---------------- OPTIMAL ----------------
    static void optimal(int[] pages, int frames) {
        ArrayList<Integer> list = new ArrayList<>();
        int faults = 0;
        int n = pages.length;

        for (int i = 0; i < n; i++) {
            int p = pages[i];

            if (list.contains(p)) continue;

            if (list.size() < frames) {
                list.add(p);
                faults++;
            } else {
                int indexToReplace = -1;
                int farthest = i;

                for (int f = 0; f < list.size(); f++) {
                    int val = list.get(f);
                    int j;
                    for (j = i + 1; j < n; j++) {
                        if (pages[j] == val)
                            break;
                    }

                    if (j == n) {          // Not used again → best to replace
                        indexToReplace = f;
                        break;
                    } else if (j > farthest) {
                        farthest = j;
                        indexToReplace = f;
                    }
                }

                list.set(indexToReplace, p);
                faults++;
            }
        }

        System.out.println("Optimal Page Faults = " + faults);
    }
}
