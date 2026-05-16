package com.mountaineering.effect;

import com.mountaineering.MountaineeringMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MountaineeringMod.MOD_ID);

    public static final RegistryObject<MobEffect> FROSTBITE = EFFECTS.register("frostbite",
            FrostbiteEffect::new);

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }
}
