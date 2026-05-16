package com.mountaineering.system;

import com.mountaineering.block.HeaterBlock;
import com.mountaineering.block.TentBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ShelterHelper {

    public static boolean isSheltered(Player player) {
        Level level = player.level();
        BlockPos pos = player.blockPosition();

        boolean hasRoof = false;
        for (int dy = 1; dy <= 5; dy++) {
            BlockPos above = pos.above(dy);
            BlockState state = level.getBlockState(above);
            if (state.canOcclude() || state.getBlock() instanceof TentBlock) {
                hasRoof = true;
                break;
            }
        }
        if (!hasRoof) return false;

        int wallCount = 0;
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos wallPos = pos.relative(dir);
            BlockState state = level.getBlockState(wallPos);
            if (state.canOcclude() || state.getBlock() instanceof TentBlock) {
                wallCount++;
            }
        }

        return wallCount >= 2;
    }

    public static boolean isNearHeater(Player player) {
        Level level = player.level();
        BlockPos pos = player.blockPosition();

        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -1; dy <= 2; dy++) {
                for (int dz = -3; dz <= 3; dz++) {
                    BlockPos check = pos.offset(dx, dy, dz);
                    if (level.getBlockState(check).getBlock() instanceof HeaterBlock) {
                        if (HeaterBlock.isLit(level, check)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
