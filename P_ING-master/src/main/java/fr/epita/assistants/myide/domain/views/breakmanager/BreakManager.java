package fr.epita.assistants.myide.domain.views.breakmanager;

import fr.epita.java.log.Logged;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BreakManager implements Logged {
    private ScheduledExecutorService breakTimer;

    private String weatherCity;

    public void setWeatherCity(String weatherCity) {
        logger().info("Next Break will check weather for: " + weatherCity);
        this.weatherCity = weatherCity;
    }

    public void init(int delay) {
        WeatherClass weatherClass = new WeatherClass();
        TimerTask task = new TimerTask() {
            public void run() {
                String weatherState = weatherClass.getWeather(weatherCity).getWeatherState().toString();
                logger().debug(weatherState);
                BreakNotificationApp breakNotification = new BreakNotificationApp();
                if (weatherState.contains("rain") || weatherState.contains("Rain"))
                    breakNotification.initInside();
                else
                    breakNotification.initOutside();
            }
        };

        breakTimer = Executors.newSingleThreadScheduledExecutor();
        breakTimer.scheduleAtFixedRate(task, 0, delay * 60000L, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (breakTimer == null)
            return;
        breakTimer.shutdownNow();
    }
}
