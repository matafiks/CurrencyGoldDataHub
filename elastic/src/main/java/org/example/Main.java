package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://api.nbp.pl/api/exchangerates/rates/A/EUR/?format=json"; // Euro exchange rate for today

        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

                String table = jsonResponse.get("table").getAsString();
                String currency = jsonResponse.get("currency").getAsString();
                String code = jsonResponse.get("code").getAsString();
                String effectiveDate = jsonResponse.get("rates").getAsJsonArray().get(0).getAsJsonObject().get("effectiveDate").getAsString();
                double rate = jsonResponse.get("rates").getAsJsonArray().get(0).getAsJsonObject().get("mid").getAsDouble();

                //System.out.println("Tabela: " + table);
                System.out.println("Waluta: " + currency);
                System.out.println("Kod: " + code);
                System.out.println("Data: " + effectiveDate);
                System.out.println("Kurs średni: " + rate);
            } else {
                System.err.println("Błąd: " + response.code() + " - " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}