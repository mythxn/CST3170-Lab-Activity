package geneticAlgorithm;

import java.util.Random;

public class Sequence {

    private final int digits;
    private int[] seq;

    public Sequence(int digits) {
        this.seq = new int[digits];
        this.digits = digits;
    }

    public void populate() {
        Random rand = new Random();
        for (int i = 0; i < digits; i++)
            seq[i] = rand.nextInt(9 + 1);
    }

    public int evaluate() {
        int sum = 0;
        for (int j : seq) sum += j;
        return sum;
    }

    public int[] getSeq() {
        return seq;
    }

    public void setSeq(int[] seq) {
        this.seq = seq;
    }
}