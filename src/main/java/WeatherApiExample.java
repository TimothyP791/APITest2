import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherApiExample {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        String apiKey = "3cb7311ba5dd57a76cbbd465a6217e38"; // Replace with your OpenWeather API key
        String city = "Norwalk"; // Replace with the city name for which you want to get weather data

        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + city+ "&appid=" + apiKey;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                ParseForecastData(responseData);
            } else {
                System.out.println("Error: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void ParseForecastData(String jsonData){
        JSONObject jsonObject = new JSONObject(jsonData);

        JSONArray forecastList = jsonObject.getJSONArray("list");
        for (int i = 0; i < 5; i++) {
            JSONObject forecast = forecastList.getJSONObject(i);
            long timeStamp = forecast.getLong("dt");
            JSONObject main = forecast.getJSONObject("main");
            JSONObject wind = forecast.getJSONObject("wind");
            double tempK = main.getDouble("temp");
            double windSpeedM = wind.getDouble("speed");
            int humidity = main.getInt("humidity");

            double windSpeedF = windSpeedM * 3.281;
            double tempF = 1.8 * (tempK - 273.0) + 32.0;
            double tempC = (tempF - 32.0) * (5.0 / 9.0);

            System.out.println("Day " + (i+1));
            System.out.println("Temperature Fahrenheit: " + String.format("%.2f", tempF) + "°");
            System.out.println("Temperature Celsius: " + String.format("%.2f",tempC) + "°");
            System.out.println("Wind Speed: " + String.format("%.2f", windSpeedM) + "m/s");
            System.out.println("Wind Speed: " + String.format("%.2f", windSpeedF) + "f/s");
            System.out.println("Humidity: " + humidity + "%");
            System.out.println();
        }
    }
}

