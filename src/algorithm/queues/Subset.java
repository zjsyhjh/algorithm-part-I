package algorithm.queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by iHge2k on 15/10/21.
 */
public class Subset {

    public static void main(String[] args) {
        RandomizedQueue<String> que = new RandomizedQueue<>();
        int N = Integer.valueOf(args[0]);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            que.enqueue(item);
        }

        while (N-- > 0) {
            StdOut.println(que.dequeue());
        }
    }
}
