import java.util.Arrays;

public class singleNeuron {
    static int[][] dataset = {
            // x1, x2, x3, desiredResult
            {1, 2, 1, -1},
            {2, 1, 1, 1},
    };
    static float[] weights = {1, 3, -3};
    static int threshold = 0, iterations = 3;
    static float alpha = (float) 0.3;

    public static void main(String[] args) {
        float result, neuronOutput;

        for (int i = 0; i < iterations; i++)
            for (int j = 0; j < dataset.length; j++) {
                result = summation(dataset[j], weights);
                neuronOutput = evaluation(result);
                adjustWeights(neuronOutput, j);
                System.out.println("Iteration " + i + "." + j + ": " + Arrays.toString(weights));
            }
    }

    private static void adjustWeights(float neuronOutput, int item) {
        float desiredOutput = dataset[item][dataset[item].length - 1];
        if (neuronOutput != desiredOutput) for (int i = 0; i < weights.length; i++)
            weights[i] = weights[i] + alpha * dataset[item][i] * (desiredOutput - neuronOutput);
    }

    private static int evaluation(float result) {
        return result >= threshold ? 1 : -1;
    }

    private static float summation(int[] dataset, float[] weights) {
        var num_of_variables = dataset.length - 1;
        float total = 0;
        for (int i = 0; i < num_of_variables; i++) total += (float) dataset[i] * weights[i];
        return total;
    }
}