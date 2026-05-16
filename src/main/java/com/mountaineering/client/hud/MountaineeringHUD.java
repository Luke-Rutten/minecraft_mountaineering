package com.mountaineering.client.hud;

import com.mountaineering.client.ClientMountainData;
import com.mountaineering.system.AltitudeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class MountaineeringHUD implements IGuiOverlay {
    public static final MountaineeringHUD INSTANCE = new MountaineeringHUD();

    private static final int BAR_WIDTH = 80;
    private static final int BAR_HEIGHT = 5;
    private static final int PADDING = 3;
    private static final int LEFT = 5;
    private static final int TOP = 5;

    @Override
    public void render(ForgeGui gui, GuiGraphics gfx, float partialTick, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        int x = LEFT;
        int y = TOP;

        double meters = AltitudeHelper.yToMeters(mc.player.getY());
        String altText = String.format("%.0f m", meters);
        int altColor = AltitudeHelper.isDeathZone(meters) ? 0xFF4444 : 0xFFFFFF;
        gfx.drawString(mc.font, altText, x, y, altColor, true);
        y += 12;

        float o2 = ClientMountainData.getO2Current();
        drawBar(gfx, mc, x, y, "O\u2082", o2, colorForValue(o2, 0x44AAFF));
        y += BAR_HEIGHT + PADDING;

        float stamina = ClientMountainData.getStamina();
        drawBar(gfx, mc, x, y, "STA", stamina, 0xFFCC00);
        y += BAR_HEIGHT + PADDING;

        float cold = ClientMountainData.getColdTolerance();
        drawBar(gfx, mc, x, y, "COLD", cold, colorForValue(cold, 0x66DDFF));
        y += BAR_HEIGHT + PADDING;

        float temp = ClientMountainData.getBodyTemperature();
        drawBar(gfx, mc, x, y, "TEMP", temp, tempColor(temp));
        y += BAR_HEIGHT + PADDING;

        float weather = ClientMountainData.getWeatherIntensity();
        String tier = AltitudeHelper.getWeatherTier(weather);
        int wColor = weather > 90 ? 0xFF4444 : weather > 60 ? 0xFFAA00 : 0xAADDFF;
        gfx.drawString(mc.font, tier, x, y, wColor, true);
        y += 12;

        if (AltitudeHelper.isDeathZone(meters)) {
            gfx.drawString(mc.font, "\u26A0 DEATH ZONE", x, y, 0xFF4444, true);
        }

        renderVignette(gfx, mc, screenWidth, screenHeight, o2, stamina, cold);
    }

    private void renderVignette(GuiGraphics gfx, Minecraft mc, int w, int h,
                                float o2, float stamina, float cold) {
        int tick = mc.player != null ? mc.player.tickCount : 0;

        if (o2 < 25f) {
            float strength = (25f - o2) / 25f;
            float pulse = 0.6f + 0.4f * (float) Math.sin(tick * 0.15);
            int alpha = (int) (strength * pulse * 80);
            gfx.fill(0, 0, w, h, (alpha << 24) | 0xCC0000);
        }

        if (cold < 25f) {
            float strength = (25f - cold) / 25f;
            float pulse = 0.7f + 0.3f * (float) Math.sin(tick * 0.1);
            int alpha = (int) (strength * pulse * 60);
            gfx.fill(0, 0, w, h, (alpha << 24) | 0x4488FF);
        }

        if (stamina < 15f) {
            float strength = (15f - stamina) / 15f;
            float pulse = 0.5f + 0.5f * (float) Math.sin(tick * 0.2);
            int alpha = (int) (strength * pulse * 40);
            gfx.fill(0, 0, w, h, (alpha << 24) | 0x222222);
        }
    }

    private void drawBar(GuiGraphics gfx, Minecraft mc, int x, int y, String label, float value, int color) {
        int lw = mc.font.width(label) + 4;
        gfx.drawString(mc.font, label, x, y - 1, 0xCCCCCC, true);

        int bx = x + lw;
        gfx.fill(bx, y, bx + BAR_WIDTH, y + BAR_HEIGHT, 0x88000000);

        int fill = Math.round(BAR_WIDTH * (value / 100f));
        if (fill > 0) {
            gfx.fill(bx, y, bx + fill, y + BAR_HEIGHT, 0xFF000000 | color);
        }

        gfx.drawString(mc.font, String.format("%.0f", value), bx + BAR_WIDTH + 3, y - 1, 0xAAAAAA, true);
    }

    private static int colorForValue(float v, int good) {
        if (v > 60) return good;
        if (v > 25) return 0xFFAA00;
        return 0xFF4444;
    }

    private static int tempColor(float t) {
        if (t > 60) return 0x44DD44;
        if (t > 40) return 0xFFAA00;
        return 0xFF4444;
    }
}
