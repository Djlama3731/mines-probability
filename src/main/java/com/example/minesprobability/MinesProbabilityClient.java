package com.example.minesprobability;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class MinesProbabilityClient implements ClientModInitializer {

    public static boolean enabled = true;

    // 3 mines parmi 24 cases couvertes au départ
    public static int coveredCells = 24;
    public static int remainingMines = 3;

    private KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {

        toggleKey = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                "key.mines_probability.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                "category.mines_probability"
            )
        );

        ProbabilityHud.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                enabled = !enabled;

                if (client.player != null) {
                    client.player.sendMessage(
                        Text.literal(
                            "Probabilités : " +
                            (enabled ? "ON" : "OFF")
                        ),
                        true
                    );
                }
            }
        });
    }

    public static double getProbability() {
        if (coveredCells <= 0) {
            return 0.0;
        }

        return (double) remainingMines / coveredCells;
    }
}
