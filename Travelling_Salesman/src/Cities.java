import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
this class holds the methods relating to
data extraction and formatting as well as
nearest neighbour logic for each city
*/
public class Cities {
    public int numOfCities;
    Map<Integer, Details> cities = new HashMap<>();

    // open and extract data from file
    Cities(String filePath) throws IOException {
        extractData(new BufferedReader(new FileReader(filePath)));
    }

    // identify nearest city that's not previously visited
    int nearestCity(int city) {
        int closestCity = 0;
        double closestDist = Double.MAX_VALUE;
        for (var item : cities.entrySet())
            if (!item.getValue().visited) { // if city not visited
                double dist = distance(city, item.getKey());
                if (dist < closestDist && dist != 0.0) { // and is closer than other cities
                    closestDist = dist;
                    closestCity = item.getKey(); // then, make it our closest city
                }
            }
        return closestCity;
    }

    // calculate distance between points using euclidean formula
    double distance(int firstCity, int secondCity) {
        double x = Math.pow(cities.get(secondCity).x_cord - cities.get(firstCity).x_cord, 2);
        double y = Math.pow(cities.get(secondCity).y_cord - cities.get(firstCity).y_cord, 2);
        return Math.sqrt(x + y);
    }

    /*
     data-set parser that stores cities and
     their co-ords into details data structure
    */
    void extractData(BufferedReader br) {
        br.lines().map(String::trim).map(line -> line // remove leading and trailing whitespace
                .replaceAll(" +", " ")) // multiple ws to one 1ws
//                .replaceAll("\\s", " ")) // tabs to spaces
                .filter(line -> !line.equals(""))// don't read blank lines
                .map(line -> line.split(" ")) // parts: cityNum, x_cord, y_cord
                .forEach(parts -> {
                    int cityNum = Integer.parseInt(parts[0].trim());
                    int x_cord = Integer.parseInt(parts[1].trim());
                    int y_cord = Integer.parseInt(parts[2].trim());
                    Details details = new Details(x_cord, y_cord);
                    cities.put(cityNum, details);
                    numOfCities++;
                });
    }
}

/*
this class is a data-structure that holds
attributes related to each city i.e, it's
x co-ordinate, y co-ordinate and if it has
been previously visited or not
*/
class Details {
    int x_cord;
    int y_cord;
    boolean visited;

    Details(int x_cord, int y_cord) {
        this.x_cord = x_cord;
        this.y_cord = y_cord;
        this.visited = false;
    }
}