package com.mountaineering.item;

import com.mountaineering.config.MountaineeringConfig;
import com.mountaineering.system.AltitudeHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OxygenTankItem extends Item {
    private static final String TAG_OXYGEN = "OxygenRemaining";

    public OxygenTankItem(Properties props) {
        super(props);
    }

    public static float getOxygen(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(TAG_OXYGEN)) {
            return (float) MountaineeringConfig.TANK_CAPACITY.get();
        }
        return tag.getFloat(TAG_OXYGEN);
    }

    public static void setOxygen(ItemStack stack, float value) {
        stack.getOrCreateTag().putFloat(TAG_OXYGEN, Math.max(0, value));
    }

    public static void drainTick(ItemStack stack, Player player) {
        float remaining = getOxygen(stack);
        if (remaining <= 0) return;

        double meters = AltitudeHelper.getMeters(player);
        double altFraction = AltitudeHelper.getAltitudeFraction(meters);
        float drain = MountaineeringConfig.TANK_DRAIN_BASE.get().floatValue()
                * (1f + (float) altFraction);
        setOxygen(stack, remaining - drain);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getOxygen(stack) < MountaineeringConfig.TANK_CAPACITY.get();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int max = MountaineeringConfig.TANK_CAPACITY.get();
        return Math.round(13.0f * getOxygen(stack) / max);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        float fraction = getOxygen(stack) / MountaineeringConfig.TANK_CAPACITY.get();
        if (fraction > 0.5f) return 0x00AAFF;
        if (fraction > 0.2f) return 0xFFAA00;
        return 0xFF4444;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        float remaining = getOxygen(stack);
        int max = MountaineeringConfig.TANK_CAPACITY.get();
        float pct = remaining / max * 100f;
        tooltip.add(Component.translatable("item.mountaineering.oxygen_tank.remaining",
                String.format("%.0f", remaining), max, String.format("%.0f", pct)));
    }
}
