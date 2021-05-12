package keray.domain;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

//class will be used to establish connection and request data from Food database
public class FoodDataConnection {

    private final String myApiKey;

    public FoodDataConnection() {
        myApiKey = "nn7WaPzGuNCVANMKQ4C5wVF1Tg0SfgEkC9E6KnhV";
    }

    //method will search for a given food
    public String searchFood(String food) {

        //method might throw exception
        try {
            //creating URI
            URI searchUri = convertQueryIntoUri(food);

            //establishing connection
            HttpClient connection = HttpClient.newHttpClient();

            //defining request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(searchUri)
                    .GET()
                    .build();

            //sending request and returning the result in the form of String
            return connection.send(request, HttpResponse.BodyHandlers.ofString())
                    .body();
        } catch (Exception e) {
            return "incorrect query or connection error";
        }
    }

    //the method will convert inputted food query into a part of URI address
    public URI convertQueryIntoUri(String query) {

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
