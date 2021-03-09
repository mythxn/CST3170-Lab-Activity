package neuralNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class csvReader {
    ArrayList<Double> ocrValues; // 64 chars, OCR value of actual number
    int actualValue; // 1 char, the actual num

    public csvReader(ArrayList<Double> ocrValues, int actualValue) {
        this.ocrValues = ocrValues;
        this.actualValue = actualValue;
    }

    static List<csvReader> readDatasetFromCSV(String fileName) throws IOException {
        // open .csv using file reader
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<csvReader> csvReader = new ArrayList<>();
        String line;

        // for each line that is not null
        while ((line = bufferedReader.readLine()) != null) {
            // create an array of each row, element separated by ','
            double[] attributes = Arrays.stream(line.split(","))
                    .mapToDouble(Integer::parseInt).toArray();

            csvReader values = readValues(attributes);
            csvReader.add(values);
        }
        bufferedReader.close();
        return csvReader;
    }

    private static csvReader readValues(double[] metadata) {
        // first 64 elements are OCR values
        ArrayList<Double> ocrValues = Arrays
                .stream(metadata, 0, metadata.length - 1)
                .boxed().collect(Collectors.toCollection(ArrayList::new));

        // last element is the actual number
        int actualValue = (int) metadata[metadata.length - 1];
        return new csvReader(ocrValues, actualValue);
    }
}
