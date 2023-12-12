import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.List;

public class ElasticsearchData {
    private final String userName = "elastic";
    private final String password = "fxXxLjHPI0NiLvBvEAZW";

    public void postData(List<Currency> currencies) {

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(userName, password)
        );

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                                new HttpHost("localhost", 9200, "http"))
                        .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
        );

        for (Currency currency : currencies) {
            String jsonString = new Gson().toJson(currency);
            //waluta, war <- z nich biorą obecne wykresy dane
            Request indexRequest = new Request(
                    "POST",
                    "/waluta/_doc/"); // nazwa indeksu do którego wprowadzamy nasze dane
            indexRequest.setJsonEntity(jsonString);

            try {
                Response indexResponse = client.getLowLevelClient().performRequest(indexRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postGoldData(List<GoldPrice> goldPrices) {

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(userName, password)
        );

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                                new HttpHost("localhost", 9200, "http"))
                        .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
        );

        for (GoldPrice goldPrice : goldPrices) {
            String jsonString = new Gson().toJson(goldPrice);

            Request indexRequest = new Request(
                    "POST",
                    "/gold/_doc/"); // nazwa indeksu do którego wprowadzamy nasze dane
            indexRequest.setJsonEntity(jsonString);

            try {
                Response indexResponse = client.getLowLevelClient().performRequest(indexRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}