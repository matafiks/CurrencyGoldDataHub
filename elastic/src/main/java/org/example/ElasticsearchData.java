package org.example;

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
    public void postSampleDate() {
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

        // Utworzenie dokumentu JSON
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"siema\"" +
                "}";

        // Utworzenie nowego indeksu
        Request indexRequest = new Request(
                "PUT",
                "/my_index/_doc/2");
        indexRequest.setJsonEntity(jsonString);

        try {
            Response indexResponse = client.getLowLevelClient().performRequest(indexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

            Request indexRequest = new Request(
                    "POST",
                    "/nbpapi2/_doc/"); // Używamy indeksu NBPApi
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