import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class geneticAlgorithm {

    //set number of digits per sequence
    private static final int digits = 20;

    public static void main(String[] args) {

        // create pool of 'digits' sequences
        var population = new Sequence[digits - 1];

        // create two parents with random data
        population[0] = new Sequence(digits);
        population[1] = new Sequence(digits);
        population[0].populate();
        population[1].populate();

        // fill population with children using crossover
        for (int i = 0; i < population.length; i++) {
            if (population[i] == null) {

                // random parents
                Random rand = new Random();
                int randomFather = rand.nextInt(i - 1);
                int randomMother = rand.nextInt(i - 1);

                population[i] = crossOver(population[randomFather], population[randomMother]);
            }
        }

        // print whole population
        for (Sequence sequence : population) {
            System.out.println(Arrays.toString(sequence.getSeq()) + " => " + sequence.evaluate());
        }

        // setup population array
        int[][] populationArray = new int[digits][2];
        for (int i = 0; i < population.length; i++) {
            populationArray[i][0] = i; // index
            populationArray[i][1] = population[i].evaluate(); // sum
        }

        // sort population array and get index of best sequence
        Arrays.sort(populationArray, Comparator.comparingInt(a -> a[1]));
        int bestIndex = populationArray[digits - 1][0];

        // print best sequence
        System.out.println();
        System.out.println(Arrays.toString(population[bestIndex].getSeq())+ " => " + population[bestIndex].evaluate());

        // add mutation to best sequence
        int[] bestSeq = population[bestIndex].getSeq();
        int mutation = (int) (Math.random() * ((9) + 1));
        Sequence mutatedSeq = new Sequence(digits);
        bestSeq[mutation] = mutation;
        mutatedSeq.setSeq(bestSeq);

        // print mutated sequence
        System.out.println(Arrays.toString(mutatedSeq.getSeq()) + " => " + mutatedSeq.evaluate());
    }

    public static Sequence crossOver(Sequence father, Sequence mother) {
        int[] p_one = father.getSeq();
        int[] p_two = mother.getSeq();
        int[] c_seq = new int[digits];

        int crossPoint = (int) (Math.random() * ((digits) + 1));

        for (int i = 0; i < digits; ++i) {
            if (i < crossPoint)
                c_seq[i] = p_one[i];
            else
                c_seq[i] = p_two[i];
        }

        Sequence child = new Sequence(digits);
        child.setSeq(c_seq);
        return child;
    }
}