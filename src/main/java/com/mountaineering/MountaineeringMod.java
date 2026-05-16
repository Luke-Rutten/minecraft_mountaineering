package com.mountaineering;

import com.mountaineering.block.ModBlockEntities;
import com.mountaineering.block.ModBlocks;
import com.mountaineering.config.MountaineeringConfig;
import com.mountaineering.data.MountainDataCapability;
import com.mountaineering.effect.ModEffects;
import com.mountaineering.item.ModItems;
import com.mountaineering.network.ModNetwork;
import com.mountaineering.sound.ModSounds;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MountaineeringMod.MOD_ID)
public class MountaineeringMod {
    public static final String MOD_ID = "mountaineering";

    public MountaineeringMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modBus);
        ModBlocks.register(modBus);
        ModBlockEntities.register(modBus);
        ModEffects.register(modBus);
        ModSounds.register(modBus);

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::addCreativeTabItems);
        modBus.addListener(MountainDataCapability::register);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MountaineeringConfig.SPEC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModNetwork::register);
    }

    private void addCreativeTabItems(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.OXYGEN_TANK.get());
            event.accept(ModItems.FORECAST_DEVICE.get());
            event.accept(ModBlocks.TENT.get().asItem());
            event.accept(ModBlocks.HEATER.get().asItem());
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.LIGHT_HELMET.get());
            event.accept(ModItems.LIGHT_CHESTPLATE.get());
            event.accept(ModItems.LIGHT_LEGGINGS.get());
            event.accept(ModItems.LIGHT_BOOTS.get());
            event.accept(ModItems.HEAVY_HELMET.get());
            event.accept(ModItems.HEAVY_CHESTPLATE.get());
            event.accept(ModItems.HEAVY_LEGGINGS.get());
            event.accept(ModItems.HEAVY_BOOTS.get());
        }
    }
}
