import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DadataProvider {
    private static final String DadataHost = "https://suggestions.dadata.ru";
    private static final String SuggestPath = "/suggestions/api/4_1/rs/suggest/party";

    private final URI suggestEndpoint;
    private final String apiToken;

    public DadataProvider(String endpoint, String apiToken) {
        this.suggestEndpoint = URI.create(endpoint);
        this.apiToken = apiToken;
    }

    public DadataProvider(String apiToken) {
        this(DadataHost + SuggestPath, apiToken);
    }

    public SuggestResponse GetSuggests(String companyFullOrPartName, int limit) {
        SuggestResponse suggests = null;
        if (limit > 0 && limit <= 20) {
            HttpClient client = HttpClient.newHttpClient();

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            ObjectNode reqBody = mapper.createObjectNode();
            reqBody.put("query", companyFullOrPartName);
            reqBody.put("count", limit);

            ArrayNode statuses = mapper.createArrayNode();
            statuses.add("ACTIVE");

            reqBody.put("status", statuses);

            try {
                HttpRequest req = HttpRequest.newBuilder().
                        uri(suggestEndpoint).
                        POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(reqBody))).
                        setHeader("Content-Type", "application/json").
                        setHeader("Accept", "application/json").
                        setHeader("Authorization", "Token " + this.apiToken).build();
                HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

                suggests = mapper.readValue(resp.body(), new TypeReference<>() {
                });


            } catch (IOException | InterruptedException e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Limit must be in range from 1 to 20");
        }
        return suggests;
    }

    public void PrintAllSuggests(String companyFullOrPartName, int limit) {
        SuggestResponse suggests = GetSuggests(companyFullOrPartName, limit);
         if (suggests.companyCards.size()>0) {
             suggests.PrintSuggests();
         }
    }

    public void PrintRandomSuggest(String companyFullOrPartName, int limit) {
        SuggestResponse suggests = GetSuggests(companyFullOrPartName, limit);
        if (suggests.companyCards.size() > 0) {
            System.out.println(suggests.companyCards.get(ThreadLocalRandom.current().nextInt(0, suggests.companyCards.size() + 1)));
        } else {
            System.out.println("Result is empty");
        }
    }

    public void PrintSortedByRegistrationDateAsc(String companyFullOrPartName, int limit) {
        SuggestResponse suggests = GetSuggests(companyFullOrPartName, limit);
        suggests.companyCards.sort(Comparator.naturalOrder());
        suggests.PrintSuggests();
    }
}
