package com.mountaineering.data;

import net.minecraft.nbt.CompoundTag;

public class PlayerMountainData {
    private float o2Base = 100f;
    private float o2Current = 100f;
    private float stamina = 100f;
    private float bodyTemperature = 80f;
    private float coldTolerance = 100f;
    private float acclimatizedAlt = 3440f;
    private int deathZoneTicks = 0;
    private boolean usingOxygenTank = false;
    private boolean wasOnGround = true;
    private int shelterCheckCooldown = 0;
    private boolean cachedSheltered = false;
    private boolean cachedNearHeater = false;
    private int secondWindCooldown = 0;
    private int frostbiteAccum = 0;

    public float getO2Base() { return o2Base; }
    public float getO2Current() { return o2Current; }
    public float getStamina() { return stamina; }
    public float getBodyTemperature() { return bodyTemperature; }
    public float getColdTolerance() { return coldTolerance; }
    public float getAcclimatizedAlt() { return acclimatizedAlt; }
    public int getDeathZoneTicks() { return deathZoneTicks; }
    public boolean isUsingOxygenTank() { return usingOxygenTank; }
    public boolean wasOnGround() { return wasOnGround; }
    public int getShelterCheckCooldown() { return shelterCheckCooldown; }
    public boolean isCachedSheltered() { return cachedSheltered; }
    public boolean isCachedNearHeater() { return cachedNearHeater; }
    public int getSecondWindCooldown() { return secondWindCooldown; }
    public int getFrostbiteAccum() { return frostbiteAccum; }

    public void setO2Base(float v) { this.o2Base = clamp(v); }
    public void setO2Current(float v) { this.o2Current = clamp(v); }
    public void setStamina(float v) { this.stamina = clamp(v); }
    public void setBodyTemperature(float v) { this.bodyTemperature = clamp(v); }
    public void setColdTolerance(float v) { this.coldTolerance = clamp(v); }
    public void setAcclimatizedAlt(float v) { this.acclimatizedAlt = v; }
    public void setDeathZoneTicks(int v) { this.deathZoneTicks = v; }
    public void setUsingOxygenTank(boolean v) { this.usingOxygenTank = v; }
    public void setWasOnGround(boolean v) { this.wasOnGround = v; }
    public void setShelterCheckCooldown(int v) { this.shelterCheckCooldown = v; }
    public void setCachedSheltered(boolean v) { this.cachedSheltered = v; }
    public void setCachedNearHeater(boolean v) { this.cachedNearHeater = v; }
    public void setSecondWindCooldown(int v) { this.secondWindCooldown = v; }
    public void setFrostbiteAccum(int v) { this.frostbiteAccum = v; }

    private static float clamp(float v) {
        return Math.max(0f, Math.min(100f, v));
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("o2Base", o2Base);
        tag.putFloat("o2Current", o2Current);
        tag.putFloat("stamina", stamina);
        tag.putFloat("bodyTemperature", bodyTemperature);
        tag.putFloat("coldTolerance", coldTolerance);
        tag.putFloat("acclimatizedAlt", acclimatizedAlt);
        tag.putInt("deathZoneTicks", deathZoneTicks);
        tag.putBoolean("usingOxygenTank", usingOxygenTank);
        tag.putInt("secondWindCooldown", secondWindCooldown);
        tag.putInt("frostbiteAccum", frostbiteAccum);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("o2Base")) o2Base = tag.getFloat("o2Base");
        if (tag.contains("o2Current")) o2Current = tag.getFloat("o2Current");
        if (tag.contains("stamina")) stamina = tag.getFloat("stamina");
        if (tag.contains("bodyTemperature")) bodyTemperature = tag.getFloat("bodyTemperature");
        if (tag.contains("coldTolerance")) coldTolerance = tag.getFloat("coldTolerance");
        if (tag.contains("acclimatizedAlt")) acclimatizedAlt = tag.getFloat("acclimatizedAlt");
        if (tag.contains("deathZoneTicks")) deathZoneTicks = tag.getInt("deathZoneTicks");
        if (tag.contains("usingOxygenTank")) usingOxygenTank = tag.getBoolean("usingOxygenTank");
        if (tag.contains("secondWindCooldown")) secondWindCooldown = tag.getInt("secondWindCooldown");
        if (tag.contains("frostbiteAccum")) frostbiteAccum = tag.getInt("frostbiteAccum");
    }

    public void copyFrom(PlayerMountainData other) {
        this.o2Base = other.o2Base;
        this.o2Current = other.o2Current;
        this.stamina = other.stamina;
        this.bodyTemperature = other.bodyTemperature;
        this.coldTolerance = other.coldTolerance;
        this.acclimatizedAlt = other.acclimatizedAlt;
        this.deathZoneTicks = other.deathZoneTicks;
        this.usingOxygenTank = other.usingOxygenTank;
        this.secondWindCooldown = other.secondWindCooldown;
        this.frostbiteAccum = other.frostbiteAccum;
    }
}
