package CurrencyExchange.cache;

import CurrencyExchange.Exceptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cache {
    private final String CachePath = "./src/CurrencyExchange/cache/cache.json";

    public Cache() {
        var file = new File(CachePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private JsonArray GetJsonObjFromCache() {
        try{
            return GetJsonObj(Files.readString(Paths.get(CachePath)));
        }
        catch(IOException | NullPointerException e) { return new JsonArray(); }
    }

    private JsonArray GetJsonObj(String json) {
        var jsonElement = new Gson().fromJson(json, JsonElement.class);
        if (jsonElement.isJsonArray()) {
            return jsonElement.getAsJsonArray();
        } else if (jsonElement.isJsonObject()) {
            var jsonArray = new JsonArray();
            jsonArray.add(jsonElement.getAsJsonObject());
            return jsonArray;
        } else {
            return new JsonArray();
        }
    }

    public double GetValue(String firstCurrency, String secondCurrency) throws Exceptions.CurrencyIsNotInCache {
        for (var jsonElement : GetJsonObjFromCache()) {
            var jsonObject = jsonElement.getAsJsonObject();
            var name = jsonObject.get("name").getAsString();
            if (name.equals(firstCurrency)) {
                var valueArray = jsonObject.get("value").getAsJsonArray();
                for (var valueElement : valueArray) {
                    var valueObject = valueElement.getAsJsonObject();
                    if (valueObject.has(secondCurrency)) {
                        return valueObject.get(secondCurrency).getAsInt();
                    }
                }
            }
        }
        throw new Exceptions.CurrencyIsNotInCache();
    }

    public void addElement(String name, String key, double value) {
        if(CurrencyExists(name, key)) { return; }
        var jsonArray = GetJsonObjFromCache();
        for (var jsonElement : jsonArray) {
            var jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.get("name").getAsString().equals(name)) {
                var newValue = new JsonObject();
                newValue.addProperty(key, value);
                jsonObject.getAsJsonArray("value").add(newValue);
                try(var writer = new FileWriter(CachePath)) {
                    writer.write(jsonArray.toString());
                }
                catch(java.io.IOException ignored) { }
                return;
            }
        }

        var newObject = new JsonObject();
        newObject.addProperty("name", name);
        var newValueArray = new JsonArray();
        var newValue = new JsonObject();
        newValue.addProperty(key, value);
        newValueArray.add(newValue);
        newObject.add("value", newValueArray);
        jsonArray.add(newObject);
        try(var writer = new FileWriter(CachePath)) {
            writer.write(jsonArray.toString());
        }
        catch(java.io.IOException ignored) { }
    }

    public boolean CurrencyExists(String firstCurrency, String secondCurrency) {
        try{
            GetValue(firstCurrency, secondCurrency);
            return true;
        }
        catch(CurrencyExchange.Exceptions.CurrencyIsNotInCache e) { return false; }
    }

    public double GetRateFromParse(String json, String currencyCode) throws Exceptions.UnknownCurrency {
        currencyCode = currencyCode.toLowerCase();
        var jsonArray = GetJsonObj(json);
        var jsonObject = jsonArray.get(0).getAsJsonObject();
        if (jsonObject.has(currencyCode)) {
            var currencyObject = jsonObject.get(currencyCode).getAsJsonObject();
            return currencyObject.get("rate").getAsDouble();
        }
        throw new Exceptions.UnknownCurrency();
    }
}
