package CurrencyExchange.parser;

import CurrencyExchange.Exceptions;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import CurrencyExchange.cache.Cache;

public class Parser {

    public double GetCurrency(String firstCurrency, String secondCurrency) throws Exceptions.UnknownCurrency {
        var url = "http://www.floatrates.com/daily/" + firstCurrency + ".json";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            var request = new HttpGet(url);

            try (var response = httpClient.execute(request)) {
                return new Cache().GetRateFromParse(EntityUtils.toString(response.getEntity()), secondCurrency);
            }
        } catch (Exception e) {
            throw new Exceptions.UnknownCurrency();
        }
    }
}
