public class Main {
    public static void main(String[] args) {
        NBPApi api = new NBPApi();
        ElasticsearchData elastic = new ElasticsearchData();
        elastic.postData(api.getLastMonthEuroRatesList());
        elastic.postData(api.getYesterdaysCurrencies());
        elastic.postData(api.getUSDRatesForYear());
        elastic.postData(api.getRUBRatesForYear());
        elastic.postData(api.getRUBRatesForYearB());
        elastic.postData(api.getRUBRatesForYearB23());
        elastic.postData(api.getLastMonthDolarRatesList());
        elastic.postGoldData(api.getGoldPricesForLastTenYears());
    }
}