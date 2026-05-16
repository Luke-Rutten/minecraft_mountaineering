package com.mountaineering.system;

import com.mountaineering.config.MountaineeringConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Random;

public class WeatherEngine extends SavedData {
    private static final String DATA_NAME = "mountaineering_weather";

    private float weatherIntensity = 15f;
    private float stormPressure = 20f;
    private float instability = 0.5f;
    private final Random random = new Random();

    public WeatherEngine() {
    }

    public static WeatherEngine get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                WeatherEngine::load, WeatherEngine::new, DATA_NAME
        );
    }

    public static WeatherEngine load(CompoundTag tag) {
        WeatherEngine engine = new WeatherEngine();
        engine.weatherIntensity = tag.getFloat("weatherIntensity");
        engine.stormPressure = tag.getFloat("stormPressure");
        engine.instability = tag.getFloat("instability");
        return engine;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putFloat("weatherIntensity", weatherIntensity);
        tag.putFloat("stormPressure", stormPressure);
        tag.putFloat("instability", instability);
        return tag;
    }

    public void tick() {
        float drift = (stormPressure - weatherIntensity)
                * MountaineeringConfig.WEATHER_DRIFT_SPEED.get().floatValue();
        float noise = (random.nextFloat() - 0.5f) * 2f * instability
                * MountaineeringConfig.WEATHER_NOISE_SCALE.get().floatValue();

        weatherIntensity += drift + noise;
        weatherIntensity = Mth.clamp(weatherIntensity, 0f, 100f);

        if (random.nextFloat() < MountaineeringConfig.STORM_FRONT_CHANCE.get().floatValue()) {
            stormPressure = random.nextFloat() * 100f;
            instability = 0.5f + random.nextFloat() * 0.5f;
        }

        instability = Math.max(0.3f, instability * 0.9999f);

        setDirty();
    }

    public float getWeatherIntensity() {
        return weatherIntensity;
    }

    public float getStormPressure() {
        return stormPressure;
    }

    public float getInstability() {
        return instability;
    }

    public float getEffectiveIntensity(double altitudeMeters) {
        double metersMin = MountaineeringConfig.METERS_MIN.get();
        float altBonus = (float) ((altitudeMeters - metersMin)
                * MountaineeringConfig.WEATHER_ALT_BONUS_SCALE.get());
        return Mth.clamp(weatherIntensity + altBonus, 0f, 100f);
    }
}
