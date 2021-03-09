package neuralNetwork;

import java.io.IOException;
import java.text.DecimalFormat;

public class mainMethod {
    static String trainSet = "src/neuralNetwork/datasets/trainingSet.csv";
    static String testSet = "src/neuralNetwork/datasets/testSet.csv";

    public static void main(String[] args) throws IOException {
        Dataset trainingSet = Dataset.createDataSet(trainSet);
        Network net = new Network(64, 250, 10);

        net.train(trainingSet, 500, 0.068);
        System.out.println("[STATUS] training complete");

        System.out.println("[STATUS] loading testing dataset");
        Dataset testingSet = Dataset.createDataSet(testSet);

        System.out.println("[STATUS] beginning testing");
        testTrainingSet(net, testingSet);
    }

    // testing method - comparing outputs
    private static void testTrainingSet(Network net, Dataset testSet) {
        int correct = 0;

        for (int i = 0; i < testSet.size(); i++) {
            // run testSet through trained network
            double computedHighest = highestIndex(net.feedForward(testSet.getIn(i)));

            // get actual outputs to perform comparison
            double actualHighest = highestIndex(testSet.getOut(i));

            if (computedHighest == actualHighest)
                correct++;
        }
        System.out.println("[STATUS] testing finished");

        double accuracy = ((double) correct / (double) testSet.size() * 100);
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.printf("[INFO] accuracy: %d / %d -> %s %%%n", correct, testSet.size(), df.format(accuracy));
    }

    // returns the index of highest value in array
    public static int highestIndex(double[] values) {
        int index = 0;
        for (int i = 1; i < values.length; i++)
            if (values[i] > values[index])
                index = i;
        return index;
    }
}
