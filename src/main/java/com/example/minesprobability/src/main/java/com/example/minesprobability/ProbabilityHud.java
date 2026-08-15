package com.example.minesprobability;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.Locale;

public final class ProbabilityHud {

    private ProbabilityHud() {
    }

    public static void register() {

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {

            if (!MinesProbabilityClient.enabled) {
                return;
            }

            MinecraftClient client = MinecraftClient.getInstance();

            if (client.player == null) {
                return;
            }

            double probability =
                MinesProbabilityClient.getProbability() * 100.0;

            String percentage =
                String.format(Locale.US, "%.1f%%", probability);

            String message =
                "Mine : " + percentage +
                " (" +
                MinesProbabilityClient.remainingMines +
                " / " +
                MinesProbabilityClient.coveredCells +
                " cases)";

            drawContext.drawTextWithShadow(
                client.textRenderer,
                Text.literal(message),
                8,
                8,
                0xFFFFFF
            );
        });
    }
}
