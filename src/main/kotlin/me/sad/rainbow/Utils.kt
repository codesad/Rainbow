package me.sad.rainbow

import me.sad.rainbow.mixins.IMixinGui
import net.minecraft.client.Minecraft
import java.awt.Color

object Utils {
    @JvmStatic
    fun drawItemBackground(x: Int, y: Int, colour: Color) {
        val screen = Minecraft.getMinecraft().currentScreen as IMixinGui
        screen.callDrawGradientRect(x, y, x + 16, y + 16, colour.rgb, colour.rgb)
    }
}