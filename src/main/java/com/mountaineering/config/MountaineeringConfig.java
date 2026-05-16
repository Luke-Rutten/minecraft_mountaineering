package com.mountaineering.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MountaineeringConfig {
    public static final ForgeConfigSpec SPEC;

    // Altitude
    public static final ForgeConfigSpec.IntValue Y_MIN;
    public static final ForgeConfigSpec.IntValue Y_MAX;
    public static final ForgeConfigSpec.DoubleValue METERS_MIN;
    public static final ForgeConfigSpec.DoubleValue METERS_MAX;
    public static final ForgeConfigSpec.DoubleValue DEATH_ZONE_ALTITUDE;

    // Oxygen
    public static final ForgeConfigSpec.DoubleValue O2_EXERTION_SPRINT;
    public static final ForgeConfigSpec.DoubleValue O2_EXERTION_JUMP;
    public static final ForgeConfigSpec.DoubleValue O2_EXERTION_CLIMB;
    public static final ForgeConfigSpec.DoubleValue O2_RECOVERY_RATE;
    public static final ForgeConfigSpec.DoubleValue O2_DAMAGE_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue O2_DAMAGE_AMOUNT;
    public static final ForgeConfigSpec.IntValue O2_DAMAGE_INTERVAL;
    public static final ForgeConfigSpec.DoubleValue DEATH_ZONE_O2_DECAY;

    // Stamina
    public static final ForgeConfigSpec.DoubleValue STAMINA_SPRINT_DRAIN;
    public static final ForgeConfigSpec.DoubleValue STAMINA_JUMP_DRAIN;
    public static final ForgeConfigSpec.DoubleValue STAMINA_CLIMB_DRAIN;
    public static final ForgeConfigSpec.DoubleValue STAMINA_REGEN_BASE;
    public static final ForgeConfigSpec.DoubleValue STAMINA_SPRINT_THRESHOLD;

    // Temperature
    public static final ForgeConfigSpec.DoubleValue TEMP_AT_MIN_ALT;
    public static final ForgeConfigSpec.DoubleValue TEMP_AT_MAX_ALT;
    public static final ForgeConfigSpec.DoubleValue SHELTER_TEMP_BONUS;
    public static final ForgeConfigSpec.DoubleValue HEATER_TEMP_BONUS;
    public static final ForgeConfigSpec.DoubleValue LIGHT_GEAR_INSULATION;
    public static final ForgeConfigSpec.DoubleValue HEAVY_GEAR_INSULATION;

    // Cold Tolerance
    public static final ForgeConfigSpec.DoubleValue COLD_DRAIN_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue COLD_REGEN_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue COLD_DRAIN_RATE;
    public static final ForgeConfigSpec.DoubleValue COLD_REGEN_RATE;
    public static final ForgeConfigSpec.DoubleValue COLD_DAMAGE_AMOUNT;
    public static final ForgeConfigSpec.IntValue COLD_DAMAGE_INTERVAL;

    // Weather
    public static final ForgeConfigSpec.DoubleValue WEATHER_DRIFT_SPEED;
    public static final ForgeConfigSpec.DoubleValue WEATHER_NOISE_SCALE;
    public static final ForgeConfigSpec.DoubleValue STORM_FRONT_CHANCE;
    public static final ForgeConfigSpec.DoubleValue WEATHER_ALT_BONUS_SCALE;
    public static final ForgeConfigSpec.IntValue WEATHER_CLEAR_MAX;
    public static final ForgeConfigSpec.IntValue WEATHER_LIGHT_SNOW_MAX;
    public static final ForgeConfigSpec.IntValue WEATHER_HEAVY_SNOW_MAX;

    // Acclimatization
    public static final ForgeConfigSpec.DoubleValue ACCLIM_GAIN_RATE;
    public static final ForgeConfigSpec.DoubleValue ACCLIM_DECAY_RATE;

    // Encumbrance
    public static final ForgeConfigSpec.DoubleValue FREE_CARRY_WEIGHT;
    public static final ForgeConfigSpec.DoubleValue WEIGHT_SCALE_FACTOR;
    public static final ForgeConfigSpec.DoubleValue LIGHT_GEAR_WEIGHT;
    public static final ForgeConfigSpec.DoubleValue HEAVY_GEAR_WEIGHT;

    // Oxygen Tank
    public static final ForgeConfigSpec.IntValue TANK_CAPACITY;
    public static final ForgeConfigSpec.DoubleValue TANK_DRAIN_BASE;
    public static final ForgeConfigSpec.DoubleValue TANK_O2_BOOST;
    public static final ForgeConfigSpec.DoubleValue TANK_WEIGHT;

    // Network
    public static final ForgeConfigSpec.IntValue SYNC_INTERVAL;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("altitude");
        Y_MIN = builder.comment("Minimum Y level of the map").defineInRange("y_min", -1882, -30000, 30000);
        Y_MAX = builder.comment("Maximum Y level of the map").defineInRange("y_max", 2032, -30000, 30000);
        METERS_MIN = builder.comment("Real-world altitude at Y_MIN (meters)").defineInRange("meters_min", 3440.0, 0.0, 20000.0);
        METERS_MAX = builder.comment("Real-world altitude at Y_MAX (meters)").defineInRange("meters_max", 8846.0, 0.0, 20000.0);
        DEATH_ZONE_ALTITUDE = builder.comment("Death zone altitude threshold (meters)").defineInRange("death_zone_altitude", 8000.0, 0.0, 20000.0);
        builder.pop();

        builder.push("oxygen");
        O2_EXERTION_SPRINT = builder.comment("O2 drain per tick while sprinting").defineInRange("exertion_sprint", 0.15, 0.0, 10.0);
        O2_EXERTION_JUMP = builder.comment("O2 drain per jump event").defineInRange("exertion_jump", 1.5, 0.0, 20.0);
        O2_EXERTION_CLIMB = builder.comment("O2 drain per tick while climbing").defineInRange("exertion_climb", 0.1, 0.0, 10.0);
        O2_RECOVERY_RATE = builder.comment("O2 recovery per tick toward O2_base").defineInRange("recovery_rate", 0.05, 0.0, 10.0);
        O2_DAMAGE_THRESHOLD = builder.comment("O2 level below which health damage occurs").defineInRange("damage_threshold", 10.0, 0.0, 100.0);
        O2_DAMAGE_AMOUNT = builder.comment("Damage per hit from critically low O2").defineInRange("damage_amount", 1.0, 0.0, 20.0);
        O2_DAMAGE_INTERVAL = builder.comment("Ticks between O2 damage hits").defineInRange("damage_interval", 40, 1, 200);
        DEATH_ZONE_O2_DECAY = builder.comment("O2_base decay per tick in the death zone").defineInRange("death_zone_decay", 0.002, 0.0, 1.0);
        builder.pop();

        builder.push("stamina");
        STAMINA_SPRINT_DRAIN = builder.comment("Stamina drain per tick while sprinting").defineInRange("sprint_drain", 0.3, 0.0, 10.0);
        STAMINA_JUMP_DRAIN = builder.comment("Stamina cost per jump").defineInRange("jump_drain", 3.0, 0.0, 20.0);
        STAMINA_CLIMB_DRAIN = builder.comment("Stamina drain per tick while climbing").defineInRange("climb_drain", 0.2, 0.0, 10.0);
        STAMINA_REGEN_BASE = builder.comment("Base stamina regeneration per tick").defineInRange("regen_base", 0.15, 0.0, 10.0);
        STAMINA_SPRINT_THRESHOLD = builder.comment("Stamina below which sprinting is force-stopped").defineInRange("sprint_threshold", 5.0, 0.0, 100.0);
        builder.pop();

        builder.push("temperature");
        TEMP_AT_MIN_ALT = builder.comment("Body temperature at lowest altitude (warm)").defineInRange("temp_at_min_alt", 85.0, 0.0, 100.0);
        TEMP_AT_MAX_ALT = builder.comment("Body temperature at highest altitude (cold)").defineInRange("temp_at_max_alt", 10.0, 0.0, 100.0);
        SHELTER_TEMP_BONUS = builder.comment("Temperature bonus from basic shelter").defineInRange("shelter_bonus", 20.0, 0.0, 100.0);
        HEATER_TEMP_BONUS = builder.comment("Additional temperature bonus from a heater").defineInRange("heater_bonus", 30.0, 0.0, 100.0);
        LIGHT_GEAR_INSULATION = builder.comment("Insulation bonus from light climbing gear").defineInRange("light_gear_insulation", 10.0, 0.0, 100.0);
        HEAVY_GEAR_INSULATION = builder.comment("Insulation bonus from heavy climbing gear").defineInRange("heavy_gear_insulation", 25.0, 0.0, 100.0);
        builder.pop();

        builder.push("cold_tolerance");
        COLD_DRAIN_THRESHOLD = builder.comment("Body temp below which cold tolerance drains").defineInRange("drain_threshold", 40.0, 0.0, 100.0);
        COLD_REGEN_THRESHOLD = builder.comment("Body temp above which cold tolerance regenerates").defineInRange("regen_threshold", 60.0, 0.0, 100.0);
        COLD_DRAIN_RATE = builder.comment("Cold tolerance drain per tick when cold").defineInRange("drain_rate", 0.05, 0.0, 10.0);
        COLD_REGEN_RATE = builder.comment("Cold tolerance regen per tick when warm").defineInRange("regen_rate", 0.03, 0.0, 10.0);
        COLD_DAMAGE_AMOUNT = builder.comment("Damage when cold tolerance is depleted").defineInRange("damage_amount", 1.0, 0.0, 20.0);
        COLD_DAMAGE_INTERVAL = builder.comment("Ticks between cold damage hits").defineInRange("damage_interval", 40, 1, 200);
        builder.pop();

        builder.push("weather");
        WEATHER_DRIFT_SPEED = builder.comment("How fast weather trends toward storm pressure").defineInRange("drift_speed", 0.01, 0.0, 1.0);
        WEATHER_NOISE_SCALE = builder.comment("Random noise magnitude in weather evolution").defineInRange("noise_scale", 0.5, 0.0, 10.0);
        STORM_FRONT_CHANCE = builder.comment("Per-tick chance of a sudden storm front").defineInRange("storm_front_chance", 0.0001, 0.0, 1.0);
        WEATHER_ALT_BONUS_SCALE = builder.comment("Extra weather intensity per meter above base").defineInRange("alt_bonus_scale", 0.005, 0.0, 1.0);
        WEATHER_CLEAR_MAX = builder.comment("Max intensity for Clear tier").defineInRange("clear_max", 30, 0, 100);
        WEATHER_LIGHT_SNOW_MAX = builder.comment("Max intensity for Light Snow tier").defineInRange("light_snow_max", 60, 0, 100);
        WEATHER_HEAVY_SNOW_MAX = builder.comment("Max intensity for Heavy Snow tier").defineInRange("heavy_snow_max", 90, 0, 100);
        builder.pop();

        builder.push("acclimatization");
        ACCLIM_GAIN_RATE = builder.comment("Meters of acclimatization gained per sleep").defineInRange("gain_rate", 200.0, 0.0, 2000.0);
        ACCLIM_DECAY_RATE = builder.comment("Meters of acclimatization lost per sleep while on O2").defineInRange("decay_rate", 100.0, 0.0, 2000.0);
        builder.pop();

        builder.push("encumbrance");
        FREE_CARRY_WEIGHT = builder.comment("Weight below which there is no encumbrance penalty").defineInRange("free_carry", 20.0, 0.0, 1000.0);
        WEIGHT_SCALE_FACTOR = builder.comment("Penalty multiplier per unit above free carry").defineInRange("scale_factor", 0.01, 0.0, 1.0);
        LIGHT_GEAR_WEIGHT = builder.comment("Total weight of a full light gear set").defineInRange("light_gear_weight", 5.0, 0.0, 100.0);
        HEAVY_GEAR_WEIGHT = builder.comment("Total weight of a full heavy gear set").defineInRange("heavy_gear_weight", 15.0, 0.0, 100.0);
        builder.pop();

        builder.push("oxygen_tank");
        TANK_CAPACITY = builder.comment("Max O2 units in a full tank").defineInRange("capacity", 1000, 1, 100000);
        TANK_DRAIN_BASE = builder.comment("Base O2 drain per tick from an active tank").defineInRange("drain_base", 0.5, 0.0, 100.0);
        TANK_O2_BOOST = builder.comment("O2_base boost provided by an active tank").defineInRange("o2_boost", 40.0, 0.0, 100.0);
        TANK_WEIGHT = builder.comment("Weight of a single oxygen tank").defineInRange("weight", 8.0, 0.0, 100.0);
        builder.pop();

        builder.push("network");
        SYNC_INTERVAL = builder.comment("Ticks between server-to-client data syncs").defineInRange("sync_interval", 10, 1, 100);
        builder.pop();

        SPEC = builder.build();
    }
}
