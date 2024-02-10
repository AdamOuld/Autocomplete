
import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdOut;

public class BinarySearchDeluxe {
    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> c) {
        // checks corner case if a or c or key is null
        if (a == null || c == null || key == null) {
            throw new NullPointerException("a, key, or c is null");
        }
        // initializes index to -1 and creates lo and hi variables for binary search
        int index = -1;
        int lo = 0;
        int hi = a.length - 1;
        // implements a modified binary search where if key and a[mid] are the same
        // index is set to mid and hi is set to the element to the right of a[mid]
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (c.compare(key, a[mid]) < 0) {
                hi = mid - 1;
            }
            if (c.compare(key, a[mid]) > 0) {
                lo = mid + 1;
            }
            if (c.compare(key, a[mid]) == 0) {
                index = mid;
                hi = mid - 1;
            }
        }
        return index;
    }

    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> c) {
        // checks corner case is a or c or key is null
        if (a == null || c == null || key == null) {
            throw new NullPointerException("a, key, or c is null");
        }
        // implements the same search as the previous method but in the case that
        // key is equal to a[mid], lo is set the the element to the left of a[mid]
        int index = -1;
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (c.compare(key, a[mid]) < 0) {
                hi = mid - 1;
            }
            if (c.compare(key, a[mid]) > 0) {
                lo = mid + 1;
            }
            if (c.compare(key, a[mid]) == 0) {
                index = mid;
                lo = mid + 1;
            }
        }
        return index;
    }

    // Unit tests the library. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println("firstIndexOf(" + prefix + ") = " + i);
        StdOut.println("lastIndexOf(" + prefix + ")  = " + j);
        StdOut.println("frequency(" + prefix + ")    = " + count);
    }
}
