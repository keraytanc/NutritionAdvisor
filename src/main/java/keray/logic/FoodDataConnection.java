package keray.logic;

import com.google.gson.Gson;
import keray.domain.Food;
import keray.domain.FoodSearchResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;

//class will be used to establish connection and request data from Food database
public class FoodDataConnection {

    private static final String myApiKey = "nn7WaPzGuNCVANMKQ4C5wVF1Tg0SfgEkC9E6KnhV";
    private static final HttpClient connection = HttpClient.newHttpClient();


    //method will search for a given food
    public FoodSearchResult searchFood(String food) {

        //creating URI
        URI searchUri = convertQueryIntoUri(food);
        String result = sendQuery(searchUri);

        //converting json data into Java objects
        Gson gson = new Gson();
        FoodSearchResult searchedFood = gson.fromJson(result, FoodSearchResult.class);

        return searchedFood;

    }
    //METODA FUNKCJONUJE NIEPOPRAWNIE POPRZEZ SYNTAX JSONA
/*
    //the method will get the food according to its ID
    public static Food getFood(int ID) {

        //creating URI with proper request
        URI getFoodUri = URI.create("https://api.nal.usda.gov/fdc/v1/food/" + ID + "?api_key=" + myApiKey);

        //result as a string
        String stringResult = sendQuery(getFoodUri);

        //converting json data into Java objects
        Gson gson = new Gson();
        System.out.println("1");
        System.out.println(stringResult);
        Food result = gson.fromJson(stringResult, Food.class);
        System.out.println("2");
        System.out.println(result.getDescription());

        return result;

    }

 */

    //method will efficiently send a query and return result as a String
    private String sendQuery(URI uri) {

        //method might throw exception
        try {

            //defining request
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Accept-Encoding", "gzip")
                    .uri(uri)
                    .GET()
                    .build();

            //sending request and returning the result in the form of String
            //creating a Stream of bytes of GZip stream
            InputStream myStream = connection.send(request, HttpResponse.BodyHandlers.ofInputStream()).body();

            //branding the stream as Gzip Stream
            GZIPInputStream response = new GZIPInputStream(myStream);

            //creating converter from bytes to characters
            InputStreamReader converter = new InputStreamReader(response, StandardCharsets.UTF_8);

            //Creating object reading input streams and a StringBuilder
            BufferedReader reader = new BufferedReader(converter);
            StringBuilder builder = new StringBuilder();

            //loop progressively appends lines to the StringBuilder
            String line = "";
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }

            //closing all the streams
            reader.close();
            response.close();

            //returning String
            return builder.toString();

        } catch (Exception e) {
            return "incorrect query or connection error";
        }

    }

    //the method will convert inputted food query into a part of URI address for a food search
    private URI convertQueryIntoUri(String query) {

        //dividing input in to an array consisting of words
        String[] foodArray = query.split(" +");

        //converting array into a list that can be treated as a stream
        List<String> targetStatementAsAList = Arrays.asList(foodArray);

        //stream converting words on the list into the part of URI address
        String searchQuery = targetStatementAsAList.stream().map((word) -> word.trim())
                .reduce("", (previousString, word) -> {

                    //two conditions transform all the spaces between words into "%20" signs
                    if (previousString.isEmpty()) {
                        return previousString + word;
                    } else {
                        return previousString + "%20" + word;
                    }
                });

        //joining query statement with the rest of the address and converting it into URI
        URI finalAddress = URI.create("https://api.nal.usda.gov/fdc/v1/foods/search?api_key="
                + myApiKey + "&query=" + searchQuery + "&dataType=SR%20Legacy&pageSize=1000");

        //returning ready address
        return finalAddress;
    }
}
