package com.mountaineering.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class FrostbiteEffect extends MobEffect {

    public FrostbiteEffect() {
        super(MobEffectCategory.HARMFUL, 0x88CCFF);
        addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "7107DE5E-7CE8-4030-940E-514C1F160890",
                -0.15, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.hurt(entity.damageSources().freeze(), 1.0f + amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 60 == 0;
    }
}
