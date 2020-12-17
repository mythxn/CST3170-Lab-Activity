package travellingSalesman;

import java.io.IOException;
import java.util.ArrayList;

import static java.util.Comparator.comparingDouble;

// this class holds the core tsp logic
public class travellingSalesman {
    // initialize core variables
    final static String filePath = "src/travellingSalesman/testData/test4-20.txt";
    static final ArrayList<Integer> path = new ArrayList<>();
    static ArrayList<Results> results = new ArrayList<>();
    static double totDist = 0.0;

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis(); // start timer
        // for each city, find the optimal path
        int numOfCities = new Cities(filePath).numOfCities; // get number of cities
        for (int i = 1; i <= numOfCities; i++) {
            path.clear();
            totDist = 0.0;
            findOptimalPath(i, new Cities(filePath));
        }
        long duration = (System.currentTimeMillis() - startTime); // end timer

        // output found by finding the result object with lowest distance
        Results bestResult = results.stream().min(comparingDouble(Results::getDistance)).get();
        System.out.println("Optimal Start City: " + bestResult.cityNum);
        System.out.println("Path: " + bestResult.path);
        System.out.println("Total Distance: " + bestResult.distance);
        System.out.println("Time Taken: " + duration + "ms");
    }

    private static void findOptimalPath(int startCity, Cities list) {
        int curCity = startCity; // starting city
        path.add(curCity);
        // nearest neighbour logic (at each point, go to the nearest city lazily)
        while (list.numOfCities != 1) {
            int closestCity = list.nearestCity(curCity);
            moveToCity(list, curCity, closestCity);
            curCity = closestCity;
        }
        moveToCity(list, curCity, startCity); // go back to start city
        results.add(new Results(startCity, totDist, path)); // add to results
    }

    /*
     go to that city, add the distance covered
     then set it as visited and reduce number of
     cities left to visit
    */
    private static void moveToCity(Cities list, int curCity, int nextCity) {
        path.add(nextCity);
        totDist += list.distance(curCity, nextCity);
        list.cities.get(curCity).visited = true;
        list.numOfCities--;
    }
}

/*
 this class is a data-structure that holds
 attributes related to result of each possible
 path, this will be used to compare and find
 the shortest path later on
*/
class Results {
    int cityNum;
    double distance;
    ArrayList<Integer> path = new ArrayList<>();

    Results(int cityNum, double distance, ArrayList<Integer> path) {
        this.cityNum = cityNum;
        this.distance = distance;
        this.path.addAll(path); // deep-copy because path is a reference object
    }

    public double getDistance() {
        return distance;
    }
}
