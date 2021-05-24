package keray.domain;

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

    private final String myApiKey;
    private final HttpClient connection;

    public FoodDataConnection() {
        this.myApiKey = "nn7WaPzGuNCVANMKQ4C5wVF1Tg0SfgEkC9E6KnhV";
        this.connection = HttpClient.newHttpClient();
    }

    //method will search for a given food
    public String searchFood(String food) {

        //method might throw exception
        try {
            //creating URI
            URI searchUri = convertQueryIntoUri(food);

            //establishing connection
            //HttpClient connection = HttpClient.newHttpClient();

            //defining request
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Accept-Encoding", "gzip")
                    .uri(searchUri)
                    .GET()
                    .build();

            //sending request and returning the result in the form of String
            //creating a Stream of bytes of GZip stream
            InputStream myStream = this.connection.send(request, HttpResponse.BodyHandlers.ofInputStream()).body();

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
