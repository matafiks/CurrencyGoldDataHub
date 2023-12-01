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
    String apiUrlYesterdayCurrencies = "https://api.nbp.pl/api/exchangerates/tables/A?format=json"; // Table A contains exchange rates for the previous business day
    String apiUrlLastMonthEuro = "http://api.nbp.pl/api/exchangerates/rates/a/eur/last/30?format=json";



    //metoda pobierająca srednie kursy walut z dnia poprzedniego
    public void getYesterdaysCurrencies(){
        Request request = new Request.Builder()
                .url(apiUrlYesterdayCurrencies)
                .get()
                .build();
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


    // Metoda pobierająca kurs waluty euro z ostatnich 30 dni
    public void getLastMonthEuroRates(){
        Request request = new Request.Builder()
                .url(apiUrlLastMonthEuro)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonObject mainObject = JsonParser.parseString(responseBody).getAsJsonObject();

                // Wyodrebnij i drukuje informacje o walucie
                String currency = mainObject.get("currency").getAsString();
                String code = mainObject.get("code").getAsString();
                System.out.println("Waluta: " + currency);
                System.out.println("Kod: " + code + "\n");

                // Wyodrebniaj i drukuj kursy z ostatnich 30 dni
                JsonArray ratesArray = mainObject.get("rates").getAsJsonArray();
                for (int i = 0; i < ratesArray.size(); i++) {
                    JsonObject rateObject = ratesArray.get(i).getAsJsonObject();
                    String date = rateObject.get("effectiveDate").getAsString();
                    double rate = rateObject.get("mid").getAsDouble();
                    System.out.println("Data: " + date + ", Kurs: " + rate);
                }

            } else {
                System.err.println("Błąd: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
