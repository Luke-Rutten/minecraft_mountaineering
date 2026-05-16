package com.mountaineering.item;

import com.mountaineering.MountaineeringMod;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MountaineeringMod.MOD_ID);

    public static final RegistryObject<Item> OXYGEN_TANK = ITEMS.register("oxygen_tank",
            () -> new OxygenTankItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FORECAST_DEVICE = ITEMS.register("forecast_device",
            () -> new ForecastDeviceItem(new Item.Properties().stacksTo(1)));

    // Light Climbing Gear
    public static final RegistryObject<Item> LIGHT_HELMET = ITEMS.register("light_helmet",
            () -> new LightGearArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_CHESTPLATE = ITEMS.register("light_chestplate",
            () -> new LightGearArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_LEGGINGS = ITEMS.register("light_leggings",
            () -> new LightGearArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BOOTS = ITEMS.register("light_boots",
            () -> new LightGearArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));

    // Heavy Climbing Gear
    public static final RegistryObject<Item> HEAVY_HELMET = ITEMS.register("heavy_helmet",
            () -> new HeavyGearArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> HEAVY_CHESTPLATE = ITEMS.register("heavy_chestplate",
            () -> new HeavyGearArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> HEAVY_LEGGINGS = ITEMS.register("heavy_leggings",
            () -> new HeavyGearArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> HEAVY_BOOTS = ITEMS.register("heavy_boots",
            () -> new HeavyGearArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
