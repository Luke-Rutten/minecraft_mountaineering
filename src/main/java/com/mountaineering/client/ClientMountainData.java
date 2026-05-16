package com.mountaineering.client;

public class ClientMountainData {
    private static float o2Base = 100f;
    private static float o2Current = 100f;
    private static float stamina = 100f;
    private static float bodyTemperature = 80f;
    private static float coldTolerance = 100f;
    private static float acclimatizedAlt = 3440f;
    private static float weatherIntensity = 0f;
    private static float stormPressure = 0f;
    private static float instability = 0.5f;
    private static int secondWindCooldown = 0;

    public static float getO2Base() { return o2Base; }
    public static float getO2Current() { return o2Current; }
    public static float getStamina() { return stamina; }
    public static float getBodyTemperature() { return bodyTemperature; }
    public static float getColdTolerance() { return coldTolerance; }
    public static float getAcclimatizedAlt() { return acclimatizedAlt; }
    public static float getWeatherIntensity() { return weatherIntensity; }
    public static float getStormPressure() { return stormPressure; }
    public static float getInstability() { return instability; }
    public static int getSecondWindCooldown() { return secondWindCooldown; }

    public static void setO2Base(float v) { o2Base = v; }
    public static void setO2Current(float v) { o2Current = v; }
    public static void setStamina(float v) { stamina = v; }
    public static void setBodyTemperature(float v) { bodyTemperature = v; }
    public static void setColdTolerance(float v) { coldTolerance = v; }
    public static void setAcclimatizedAlt(float v) { acclimatizedAlt = v; }
    public static void setWeatherIntensity(float v) { weatherIntensity = v; }
    public static void setStormPressure(float v) { stormPressure = v; }
    public static void setInstability(float v) { instability = v; }
    public static void setSecondWindCooldown(int v) { secondWindCooldown = v; }
}
