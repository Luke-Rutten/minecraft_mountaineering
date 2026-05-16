package com.mountaineering.system;

import com.mountaineering.config.MountaineeringConfig;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherForecast {

    public record ForecastEntry(int dayIndex, String tier, float intensity, float confidence) {
    }

    public static List<ForecastEntry> generate(float currentIntensity, float stormPressure,
                                               float instability, int dayCount) {
        List<ForecastEntry> entries = new ArrayList<>();
        Random rng = new Random();

        float simIntensity = currentIntensity;
        float driftSpeed = MountaineeringConfig.WEATHER_DRIFT_SPEED.get().floatValue();
        float noiseScale = MountaineeringConfig.WEATHER_NOISE_SCALE.get().floatValue();

        int ticksPerDay = 24000;

        for (int day = 0; day < dayCount; day++) {
            for (int t = 0; t < ticksPerDay; t++) {
                float drift = (stormPressure - simIntensity) * driftSpeed;
                float uncertaintyScale = 1.0f + day * 0.5f;
                float noise = (rng.nextFloat() - 0.5f) * 2f * instability * noiseScale * uncertaintyScale;
                simIntensity += drift + noise;
                simIntensity = Mth.clamp(simIntensity, 0f, 100f);
            }

            String tier = AltitudeHelper.getWeatherTier(simIntensity);
            float confidence = Math.max(0.3f, 1.0f - day * 0.2f);
            entries.add(new ForecastEntry(day + 1, tier, simIntensity, confidence));
        }

        return entries;
    }
}
