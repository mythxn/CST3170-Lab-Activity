package neuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;

public class Network {
    public final int[] layer_size; // the network [3, 2, 1]; 3 input, 1 output
    public final int input_size; // input layer
    public final int output_size; // output layer
    public final int network_size; // number of layers

    /*
     we won't be representing neurons using classes
     instead we will store neuron values in arrays
    */
    private final double[][] output; // [layer][neuron]
    private final double[][][] weights; // [layer][neuron][prevNeuron]
    private final double[][] bias; // [layer][neuron]
    private final double[][] error_cost; // [layer][neuron]
    private final double[][] output_derivative; // [layer][neuron]

    public Network(int... layer_size) {
        // initialise hidden layers
        this.layer_size = layer_size;
        this.input_size = layer_size[0];
        this.network_size = layer_size.length;
        this.output_size = layer_size[network_size - 1];

        // initialise neuron values
        this.output = new double[network_size][];
        this.weights = new double[network_size][][];
        this.bias = new double[network_size][];
        this.error_cost = new double[network_size][];
        this.output_derivative = new double[network_size][];

        for (int i = 0; i < network_size; i++) {
            /*
             setup the 2nd dimension for output, error costs,
             output derivative and biases [neuron]
            */
            this.output[i] = new double[layer_size[i]];
            this.error_cost[i] = new double[layer_size[i]];
            this.output_derivative[i] = new double[layer_size[i]];

            this.bias[i] = populateRandValues(layer_size[i], -0.5, 0.7); // assign random biases

            /*
             input layer has no weights, therefore
             exclude it -> assign random weights
            */
            if (i > 0) weights[i] = populateRandValues(layer_size[i], layer_size[i - 1], -1, 1);
        }

        ArrayList<Integer> hiddenLayer = new ArrayList<>();
        for (int neurons = 1; neurons < layer_size.length - 1; neurons++)
            hiddenLayer.add(layer_size[neurons]);

        System.out.println("[INFO] hidden layers: " + hiddenLayer);
        System.out.println("[STATUS] neural network initialised");
    }

    // basic neural network - testing purposes
    public static void main(String[] args) {
        Network net = new Network(4, 3, 2);
        Dataset set = new Dataset(4, 2);

        set.addData(new double[]{0.1, 0.2, 0.3, 0.4}, new double[]{0.9, 0.1});
        set.addData(new double[]{0.9, 0.8, 0.7, 0.6}, new double[]{0.1, 0.9});
        set.addData(new double[]{0.3, 0.8, 0.1, 0.4}, new double[]{0.3, 0.7});
        set.addData(new double[]{0.9, 0.8, 0.1, 0.2}, new double[]{0.7, 0.3});

        net.train(set, 1000, 0.3);

        for (int i = 0; i < set.size(); i++) {
            System.out.println(Arrays.toString(net.feedForward(set.getIn(i))));
        }

        // Random Accuracy - Tester
        int randIndex = (int) (Math.random() * set.size() + 0);
        double randComputedVal = net.feedForward(set.getIn(randIndex))[0];
        double randActualVal = set.getOut(randIndex)[0];
        double error_accuracy = (randComputedVal - randActualVal) / randActualVal * 100;
        System.out.println("Error Accuracy: " + error_accuracy);
    }

    // returns an array filled with random values
    public static double[] populateRandValues(int size, double upper_bound, double lower_bound) {
        if (size < 1) return null;

        double[] ar = new double[size];
        for (int i = 0; i < size; i++)
            ar[i] = Math.random() * (upper_bound - lower_bound) + lower_bound;
        return ar;
    }

    // returns a 2D array filled with random values
    public static double[][] populateRandValues(int sizeX, int sizeY, double upper_bound, double lower_bound) {
        if (sizeX < 1 || sizeY < 1) return null;

        double[][] ar = new double[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++)
            ar[i] = populateRandValues(sizeY, upper_bound, lower_bound);
        return ar;
    }

    // iterator method to go through dataset and train
    public void train(Dataset set, int iterations, double learning_rate) {
        System.out.println("[STATUS] starting training process");
        System.out.println("[INFO] learning rate: " + learning_rate);
        if (set.inLayerSize != input_size || set.outLayerSize != output_size) return;

        for (int i = 0; i <= iterations; i++) {
            for (int b = 0; b < set.size(); b++) {
                this.train(set.getIn(b), set.getOut(b), learning_rate);
            }
            System.out.print("\r[TRAINING] " + i + "/" + iterations + " iterations");
        }
        System.out.println(); // shift console to new line
    }

    // training method [feedForward, backPropagation, adjustWeights]
    public void train(double[] input, double[] target, double learning_rate) {
        if (input.length != input_size || target.length != output_size) return;
        feedForward(input);
        backPropagation(target);
        adjustWeights(learning_rate);
    }

    public double[] feedForward(double... input) {
        if (input.length != this.input_size) return null;

        this.output[0] = input; // first layer is input layer

        // for each neuron, in each hidden layer
        for (int layer = 1; layer < network_size; layer++) {
            for (int neuron = 0; neuron < layer_size[layer]; neuron++) {
                double sum = bias[layer][neuron];
                /*
                 for each neuron in previous layer that are
                 connected to current neuron, perform summation
                */
                for (int prevNeuron = 0; prevNeuron < layer_size[layer - 1]; prevNeuron++)
                    sum += output[layer - 1][prevNeuron] * weights[layer][neuron][prevNeuron];

                // run sigmoid and save as current neuron output
                output[layer][neuron] = 1d / (1 + Math.exp(-sum));
                output_derivative[layer][neuron] = output[layer][neuron] * (1 - output[layer][neuron]);
            }
        }
        // return last layer, -1 is the last hidden layer
        return output[network_size - 1];
    }

    public void backPropagation(double[] target) {
        // for each neuron in output layer
        for (int neuron = 0; neuron < layer_size[network_size - 1]; neuron++) {
            error_cost[network_size - 1][neuron] =
                    (output[network_size - 1][neuron] - target[neuron])
                            * output_derivative[network_size - 1][neuron];
        }

        /*
         for each neuron, in each hidden layer (last to last)
         -2 used in condition because that's last hidden layer
        */
        for (int layer = network_size - 2; layer > 0; layer--) {
            for (int neuron = 0; neuron < layer_size[layer]; neuron++) {
                double sum = 0;
                // get the values from next layer
                for (int nextNeuron = 0; nextNeuron < layer_size[layer + 1]; nextNeuron++)
                    sum += weights[layer + 1][nextNeuron][neuron] * error_cost[layer + 1][nextNeuron];

                this.error_cost[layer][neuron] = sum * output_derivative[layer][neuron];
            }
        }
    }

    public void adjustWeights(double learning_rate) {
        // for each neuron, in each hidden layer
        for (int layer = 1; layer < network_size; layer++) {
            for (int neuron = 0; neuron < layer_size[layer]; neuron++) {

                // update bias of each neuron
                double delta = -learning_rate * error_cost[layer][neuron];
                bias[layer][neuron] += delta;

                // adjust weights based on delta
                for (int prevNeuron = 0; prevNeuron < layer_size[layer - 1]; prevNeuron++)
                    weights[layer][neuron][prevNeuron] += delta * output[layer - 1][prevNeuron];
            }
        }
    }
}
