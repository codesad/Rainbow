package me.sad.rainbow.utils

import me.sad.rainbow.mixins.IMixinGui
import net.minecraft.client.Minecraft
import java.awt.Color

object RenderUtils {
    @JvmStatic
    fun drawItemBackground(x: Int, y: Int, colour: Color) {
        val screen = Minecraft.getMinecraft().currentScreen as IMixinGui
        screen.callDrawGradientRect(x, y, x + 16, y + 16, colour.rgb, colour.rgb)
    }
}