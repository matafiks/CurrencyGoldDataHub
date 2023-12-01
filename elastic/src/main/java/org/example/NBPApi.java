package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NBPApi {
    OkHttpClient client = new OkHttpClient();
    String apiUrl = "https://api.nbp.pl/api/exchangerates/tables/A?format=json"; // Table A contains exchange rates for the previous business day

    Request request = new Request.Builder()
            .url(apiUrl)
            .get()
            .build();

    //metoda pobierająca srednie kursy walut z dnia poprzedniego
    public void getYesterdaysCurrencies(){
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
                JsonObject mainObject = jsonArray.get(0).getAsJsonObject();

                String table = mainObject.get("table").getAsString();
                String effectiveDate = mainObject.get("effectiveDate").getAsString();

                //System.out.println("Tabela: " + table);
                System.out.println("Data: " + effectiveDate);

                JsonArray ratesArray = mainObject.get("rates").getAsJsonArray();
                List<Currency> currencies = new ArrayList<>();

                for (int i = 0; i < ratesArray.size(); i++) {
                    JsonObject rateObject = ratesArray.get(i).getAsJsonObject();

                    String currency = rateObject.get("currency").getAsString();
                    String code = rateObject.get("code").getAsString();
                    double rate = rateObject.get("mid").getAsDouble();

                    currencies.add(new org.example.Currency(currency, code, rate));

                    System.out.println("Waluta: " + currency);
                    System.out.println("Kod: " + code);
                    System.out.println("Kurs sredni: " + rate);
                    System.out.println();
                }

            } else {
                System.err.println("Błąd: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
