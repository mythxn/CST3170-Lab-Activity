package neuralNetwork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dataset {
    public final int inLayerSize; // size of input layer
    public final int outLayerSize; // size of output layer

    private final ArrayList<double[][]> data = new ArrayList<>(); // [input][desiredValue]

    public Dataset(int inLayerSize, int outLayerSize) {
        this.inLayerSize = inLayerSize;
        this.outLayerSize = outLayerSize;
    }

    static Dataset createDataSet(String filepath) throws IOException {
        System.out.println("[STATUS] importing csv file");
        List<csvReader> reader = csvReader.readDatasetFromCSV(filepath);
        Dataset dataset = new Dataset(64, 10);

        for (csvReader value : reader) {
            // get input layer of each row
            double[] in = value.ocrValues.stream().mapToDouble(Double::doubleValue).toArray();

            // setup output layer based on desiredValue
            double[] out = new double[10];
            out[value.actualValue] = 1d;
            dataset.addData(in, out);
        }
        System.out.println("[STATUS] csv file successfully imported");
        System.out.println("[INFO] dataset size: " + dataset.size());
        System.out.println("[INFO] input size: " + dataset.inLayerSize);
        System.out.println("[INFO] output size: " + dataset.outLayerSize);
        return dataset;
    }

    // add row to our dataset
    public void addData(double[] in, double[] desiredVal) {
        if (in.length != inLayerSize || desiredVal.length != outLayerSize) return;
        data.add(new double[][]{in, desiredVal});
    }

    // returns ocr values
    public double[] getIn(int index) {
        if (index >= 0 && index < size())
            return data.get(index)[0];
        else return null;
    }

    // returns actual value in array format
    public double[] getOut(int index) {
        if (index >= 0 && index < size())
            return data.get(index)[1];
        else return null;
    }

    // returns size of dataset
    public int size() {
        return data.size();
    }

    // override print method for output formatting
    public String toString() {
        int index = 0;
        StringBuilder s = new StringBuilder();
        for (double[][] r : data) {
            s.append("[INFO] index: ").append(index).append("\n");
            s.append("[INFO] input: ").append(Arrays.toString(r[0])).append("\n");
            s.append("[INFO] output: ").append(Arrays.toString(r[1])).append("\n\n");
            index++;
        }
        return s.toString();
    }
}
