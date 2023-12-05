package org.example;

public class Main {
    public static void main(String[] args) {
        NBPApi api = new NBPApi();
        ElasticsearchData elastic = new ElasticsearchData();
        //api.getYesterdaysCurrencies();
        //api.getLastMonthEuroRates();
        //elastic.postSampleDate();
        //api.getLastMonthEuroRatesList();
        elastic.postData(api.getLastMonthEuroRatesList());
    }
}