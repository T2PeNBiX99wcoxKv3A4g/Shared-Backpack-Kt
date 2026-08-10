package io.github.ykysnk.sharedBackpackKt.config

import io.github.ykysnk.sharedBackpackKt.Utils
import net.minecraft.Util
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import java.net.URI

class ConfigFallbackScreen(
    private val parent: Screen,
    private val error: Throwable? = null
) : Screen(Component.literal(Utils.MOD_NAME)) {
    override fun init() {
        super.init()

        addRenderableWidget(
            Button.builder(Component.translatable("screen.shared-backpack-kt.config-fallback.install-yacl-button")) {
                Util.getPlatform().openUri(URI.create("https://modrinth.com/mod/yacl"))
            }.bounds(
                width / 2 - 75,
                height / 2 + 50,
                150,
                20
            ).build()
        )

        addRenderableWidget(
            Button.builder(CommonComponents.GUI_OK) { onClose() }.bounds(
                width / 2 - 50,
                height / 2 + 75,
                100,
                20
            ).build()
        )

        if (error == null) return
        Utils.Logger.error("Failed to create config screen", error)
    }

    override fun render(
        guiGraphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float
    ) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick)

        guiGraphics.drawCenteredString(
            font,
            title,
            width / 2,
            20,
            0xFFFFFF
        )

        guiGraphics.drawCenteredString(
            font,
            Component.translatable("screen.shared-backpack-kt.config-fallback.yacl-required"),
            width / 2,
            height / 2 - 30,
            0xFFFFFF
        )

        guiGraphics.drawCenteredString(
            font,
            Component.translatable("screen.shared-backpack-kt.config-fallback.install-yacl"),
            width / 2,
            height / 2 - 10,
            0xAAAAAA
        )

        if (error != null) {
            guiGraphics.drawCenteredString(
                font,
                Component.literal("${error::class.java.name}: ${error.message ?: "Unknown error"}"),
                width / 2,
                height / 2 + 10,
                0xFF0000
            )

            guiGraphics.drawCenteredString(
                font,
                Component.translatable("screen.shared-backpack-kt.config-fallback.check-logs"),
                width / 2,
                height / 2 + 30,
                0xAAAAAA
            )
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick)
    }

    override fun onClose() {
        minecraft?.setScreen(parent)
    }
}