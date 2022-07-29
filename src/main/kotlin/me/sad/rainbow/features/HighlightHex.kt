package me.sad.rainbow.features

import me.sad.rainbow.Config.hexHighlight
import me.sad.rainbow.mixins.IMixinFontRenderer
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager

object HighlightHex {
    @JvmField
    var isCurrentTextShadow = false
    @JvmField
    var lastCode = '0'
    @JvmField
    var pos = -1

    @JvmStatic
    fun redirectText(text: String, i: Int): Char {
        if (Minecraft.getMinecraft().fontRendererObj == null) return text[i]
        val fontRendererAccessor = Minecraft.getMinecraft().fontRendererObj as IMixinFontRenderer
        val c: Char = text[i]
        if (hexHighlight) {
            var matches = false
            if (c == '#' && i + 6 < text.length) {
                val hex: String = text.substring(i + 1, i + 7)
                if (hex.matches("-?[\\da-fA-F]+".toRegex())) {
                    matches = true
                    for (j in i downTo 1) {
                        if (text[j - 1] == '\u00a7') {
                            lastCode = text[j]
                            break
                        }
                    }
                    val r = hex.substring(0, 2).toInt(16) / 255f
                    val g = hex.substring(2, 4).toInt(16) / 255f
                    val b = hex.substring(4, 6).toInt(16) / 255f
                    if (!isCurrentTextShadow) GlStateManager.color(r, g, b, fontRendererAccessor.alpha()
                    ) else GlStateManager.color(0f, 0f, 0f, 0f)
                    pos = 0
                }
            }
            if (pos != -1 && !matches) {
                pos++
            }
            if (pos == 7) {
                if (lastCode != '\u0000' && lastCode != 'r') {
                    val dec = fontRendererAccessor.colorCode()[Character.digit(lastCode, 16) + if (isCurrentTextShadow) 16 else 0]
                    GlStateManager.color(
                        (dec shr 16) / 255.0f,
                        (dec shr 8 and 255) / 255.0f,
                        (dec and 255) / 255.0f,
                        fontRendererAccessor.alpha()
                    )
                    lastCode = '\u0000'
                } else {
                    GlStateManager.color(
                        fontRendererAccessor.red(),
                        fontRendererAccessor.green(),
                        fontRendererAccessor.blue(),
                        fontRendererAccessor.alpha()
                    )
                }
                pos = -1
            }
        }
        return c
    }
}