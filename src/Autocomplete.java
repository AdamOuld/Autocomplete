
import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

public class Autocomplete {
    Term[] terms; // creates instance variable terms

    // Constructs an autocomplete data structure from an array of terms.
    public Autocomplete(Term[] terms) {
        // checks corner case if terms is null
        if (terms == null) {
            throw new NullPointerException("terms is null");
        }
        // initializes terms and sorts it
        this.terms = terms;
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with prefix, in descending order of their weights.
    public Term[] allMatches(String prefix) {
        // checks corner case if prefix is null
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        // creates new variable to store prefix as a Term
        Term prefixKey = new Term(prefix, 0);
        // creates new variable to store the prefix comparator
        Comparator<Term> c = Term.byPrefixOrder(prefix.length());
        // finds the first index in terms that contains a word
        int i = BinarySearchDeluxe.firstIndexOf(terms, prefixKey, c);
        int n = 0;
        // iterates through every term in terms and checks if it has the prefix
        for (int x = 0; x < terms.length; x++) {
            if (c.compare(terms[x], prefixKey) == 0) {
                n++;
            }
        }
        // creates array matches and adds n elements from terms to it
        Term[] matches = new Term[n];
        for (int x = i; x < n + i; x++) {
            matches[x] = terms[x];
        }
        // creates a variable to store the reverse weight order comparator
        Comparator<Term> c2 = Term.byReverseWeightOrder();
        Arrays.sort(matches, c2);
        return matches;
    }

    // Returns the number of terms that start with prefix.
    public int numberOfMatches(String prefix) {
        // checks corner case if prefix is null
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        // creates new variable to store prefix as a Term
        Term prefixKey = new Term(prefix, 0);
        // creates new variable to store the prefix comparator
        Comparator<Term> c = Term.byPrefixOrder(prefix.length());
        // stores the first and last indexes of the terms with the given prefix
        int i = BinarySearchDeluxe.firstIndexOf(terms, prefixKey, c);
        int j = BinarySearchDeluxe.lastIndexOf(terms, prefixKey, c);
        // returns the number of matches
        return j - i + 1;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
