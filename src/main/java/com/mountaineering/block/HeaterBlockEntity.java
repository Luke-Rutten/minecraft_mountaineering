package com.mountaineering.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HeaterBlockEntity extends BlockEntity {
    private int fuelTicks = 0;

    public HeaterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HEATER.get(), pos, state);
    }

    public boolean isActive() {
        return fuelTicks > 0;
    }

    public void addFuel(int ticks) {
        fuelTicks += ticks;
        setChanged();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, HeaterBlockEntity entity) {
        if (entity.fuelTicks > 0) {
            entity.fuelTicks--;
            if (entity.fuelTicks <= 0) {
                level.setBlock(pos, state.setValue(HeaterBlock.LIT, false), 3);
            }
            entity.setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("FuelTicks", fuelTicks);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fuelTicks = tag.getInt("FuelTicks");
    }
}
