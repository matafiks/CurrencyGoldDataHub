import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//TODO:
// All the methods in this class could be refactored into single one.
public class NBPApi {
    OkHttpClient client = new OkHttpClient();
    private final String apiUrlYesterdayCurrencies = "https://api.nbp.pl/api/exchangerates/tables/A?format=json";
    private final String apiUrlLastMonthEuro = "http://api.nbp.pl/api/exchangerates/rates/a/eur/last/30?format=json";
    private final String apiUrlLastMonthDolar = "http://api.nbp.pl/api/exchangerates/rates/a/usd/last/30?format=json";
    private final String apiUrlDolar = "http://api.nbp.pl/api/exchangerates/rates/A/USD/2023-01-01/2023-12-12/";
    private final String apiUrlWarRubel = "http://api.nbp.pl/api/exchangerates/rates/A/RUB/2022-01-01/2022-12-31/";
    private final String apiUrlWarRubelB = "http://api.nbp.pl/api/exchangerates/rates/B/RUB/2022-01-01/2022-12-31/";
    private final String apiUrlGold = "http://api.nbp.pl/api/cenyzlota/2023-01-01/2023-12-12/";

    public List<Currency> getLastMonthEuroRatesList(){
        List<Currency> currencies = new ArrayList<>();

        Request request = new Request.Builder()
                .url(apiUrlLastMonthEuro)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonObject mainObject = JsonParser.parseString(responseBody).getAsJsonObject();

                String currency = mainObject.get("currency").getAsString();
                String code = mainObject.get("code").getAsString();

                JsonArray ratesArray = mainObject.get("rates").getAsJsonArray();
                for (int i = 0; i < ratesArray.size(); i++) {
                    JsonObject rateObject = ratesArray.get(i).getAsJsonObject();
                    String date = rateObject.get("effectiveDate").getAsString();
                    double rate = rateObject.get("mid").getAsDouble();

                    Currency currentCurrency = new Currency(currency, code, rate, date);
                    currencies.add(currentCurrency);
                }

            } else {
                System.err.println("Błąd: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currencies;
    }

    public List<Currency> getLastMonthDolarRatesList(){
        List<Currency> currencies = new ArrayList<>();

        Request request = new Request.Builder()
                .url(apiUrlLastMonthDolar)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonObject mainObject = JsonParser.parseString(responseBody).getAsJsonObject();

                String currency = mainObject.get("currency").getAsString();
                String code = mainObject.get("code").getAsString();

                JsonArray ratesArray = mainObject.get("rates").getAsJsonArray();
                for (int i = 0; i < ratesArray.size(); i++) {
                    JsonObject rateObject = ratesArray.get(i).getAsJsonObject();
                    String date = rateObject.get("effectiveDate").getAsString();
                    double rate = rateObject.get("mid").getAsDouble();

                    Currency currentCurrency = new Currency(currency, code, rate, date);
                    currencies.add(currentCurrency);
                }

            } else {
                System.err.println("Błąd: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currencies;
    }

    public List<Currency> getYesterdaysCurrencies(){
        Request request = new Request.Builder()
                .url(apiUrlYesterdayCurrencies)
                .get()
                .build();
        List<Currency> currencies = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
                JsonObject mainObject = jsonArray.get(0).getAsJsonObject();

                String effectiveDate = mainObject.get("effectiveDate").getAsString();
                JsonArray ratesArray = mainObject.get("rates").getAsJsonArray();

                for (int i = 0; i < ratesArray.size(); i++) {
                    JsonObject rateObject = ratesArray.get(i).getAsJsonObject();

                    String currency = rateObject.get("currency").getAsString();
                    String code = rateObject.get("code").getAsString();
                    double rate = rateObject.get("mid").getAsDouble();

                    currencies.add(new Currency(currency, code, rate, effectiveDate));
                }

            } else {
                System.err.println("Błąd: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currencies;
    }

    public List<Currency> getUSDRatesForYear(){
        Request request = new Request.Builder()
                .url(apiUrlDolar)
                .get()
                .build();

        List<Currency> currencies = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonArray ratesArray = JsonParser.parseString(responseBody).getAsJsonObject().get("rates").getAsJsonArray();

                for (int i = 0; i < ratesArray.size(); i++) {
                    JsonObject rateObject = ratesArray.get(i).getAsJsonObject();
                    String effectiveDate = rateObject.get("effectiveDate").getAsString();
                    double rate = rateObject.get("mid").getAsDouble();
                    currencies.add(new Currency("dollar", "USD", rate, effectiveDate));
                }
            } else {
                System.err.println("Błąd: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencies;
    }

    public List<Currency> getRUBRatesForYear(){
        Request request = new Request.Builder()
                .url(apiUrlWarRubel)
                .get()
                .build();

        List<Currency> currencies = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonArray ratesArray = JsonParser.parseString(responseBody).getAsJsonObject().get("rates").getAsJsonArray();

                for (int i = 0; i < ratesArray.size(); i++) {
                    JsonObject rateObject = ratesArray.get(i).getAsJsonObject();
                    String effectiveDate = rateObject.get("effectiveDate").getAsString();
                    double rate = rateObject.get("mid").getAsDouble();
                    currencies.add(new Currency("ruble", "RUB", rate, effectiveDate));
                }
            } else {
                System.err.println("Błąd: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencies;
    }

    public List<Currency> getRUBRatesForYearB(){
        Request request = new Request.Builder()
                .url(apiUrlWarRubelB)
                .get()
                .build();

        List<Currency> currencies = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonArray ratesArray = JsonParser.parseString(responseBody).getAsJsonObject().get("rates").getAsJsonArray();

                for (int i = 0; i < ratesArray.size(); i++) {
                    JsonObject rateObject = ratesArray.get(i).getAsJsonObject();
                    String effectiveDate = rateObject.get("effectiveDate").getAsString();
                    double rate = rateObject.get("mid").getAsDouble();
                    currencies.add(new Currency("ruble", "RUB", rate, effectiveDate));
                }
            } else {
                System.err.println("Błąd: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencies;
    }

    //NBP ogranicza pobieranie danych do maksymalnie jednego roku
    public List<GoldPrice> getGoldPricesForLastTenYears(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(apiUrlGold)
                .get()
                .build();

        List<GoldPrice> goldPrices = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonArray goldArray = JsonParser.parseString(responseBody).getAsJsonArray();

                for (int i = 0; i < goldArray.size(); i++) {
                    JsonObject goldObject = goldArray.get(i).getAsJsonObject();
                    String date = goldObject.get("data").getAsString();
                    double price = goldObject.get("cena").getAsDouble();
                    goldPrices.add(new GoldPrice("gold", price, date));
                }
            } else {
                System.err.println("Błąd: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return goldPrices;
    }

}