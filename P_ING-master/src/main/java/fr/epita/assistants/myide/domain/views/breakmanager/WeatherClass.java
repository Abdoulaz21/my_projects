package fr.epita.assistants.myide.domain.views.breakmanager;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;

public class WeatherClass {
    public Weather getWeather(String city) {
        OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient("537065cedfe41eae298aa898aaa6141b");

        return openWeatherMapClient
                .currentWeather()
                .single()
                .byCityName(city)
                .unitSystem(UnitSystem.IMPERIAL)
                .retrieve()
                .asJava();
    }
}

