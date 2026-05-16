package com.mountaineering.client.screen;

import com.mountaineering.client.ClientMountainData;
import com.mountaineering.system.AltitudeHelper;
import com.mountaineering.system.WeatherForecast;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ForecastScreen extends Screen {
    private List<WeatherForecast.ForecastEntry> forecast;

    public ForecastScreen() {
        super(Component.translatable("screen.mountaineering.forecast"));
    }

    @Override
    protected void init() {
        super.init();
        forecast = WeatherForecast.generate(
                ClientMountainData.getWeatherIntensity(),
                ClientMountainData.getStormPressure(),
                ClientMountainData.getInstability(),
                3
        );
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        gfx.fillGradient(0, 0, this.width, this.height, 0xC0101010, 0xD0101010);

        int cx = width / 2;
        int y = height / 4;

        gfx.drawCenteredString(font, title, cx, y - 20, 0xFFFFFF);

        String current = AltitudeHelper.getWeatherTier(ClientMountainData.getWeatherIntensity());
        gfx.drawCenteredString(font, "Current: " + current, cx, y, 0xCCCCCC);

        if (forecast != null) {
            y += 24;
            for (WeatherForecast.ForecastEntry e : forecast) {
                int conf = Math.round(e.confidence() * 100);
                String line = String.format("Day %d: %s (%d%% confidence)", e.dayIndex(), e.tier(), conf);
                gfx.drawCenteredString(font, line, cx, y, tierColor(e.tier()));
                y += 16;
            }
        }

        gfx.drawCenteredString(font, "Press ESC to close", cx, height * 3 / 4, 0x888888);
        super.render(gfx, mouseX, mouseY, partialTick);
    }

    private int tierColor(String tier) {
        return switch (tier) {
            case "Clear" -> 0x88DDFF;
            case "Light Snow" -> 0xCCCCFF;
            case "Heavy Snow" -> 0xFFAA44;
            case "Blizzard" -> 0xFF4444;
            default -> 0xFFFFFF;
        };
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
