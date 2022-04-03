package me.sad.rainbow.mixins;

import me.sad.rainbow.Config;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = FontRenderer.class, priority = 99999)
public abstract class MixinFontRenderer {

    @Shadow(remap = false) protected abstract void setColor(float r, float g, float b, float a);

    @Shadow private float alpha;
    @Shadow private float blue;
    @Shadow private float green;
    @Shadow private float red;
    @Shadow private int[] colorCode;

    private char lastCode;
    private int pos = -1;
    private boolean shadow;

    @Inject(method = "renderStringAtPos", at = @At("HEAD"))
    public void renderStringAtPos(String text, boolean shadow, CallbackInfo ci) {
        this.shadow = shadow;
    }


    @Redirect(method = "renderStringAtPos", at = @At(value = "INVOKE", target = "Ljava/lang/String;charAt(I)C", ordinal = 0))
    public char text(String text, int i) {
        final char c = text.charAt(i);
        if (Config.getHexHighlight()) {
            boolean matches = false;
            if (c == '#' && i + 6 < text.length()) {
                final String hex = text.substring(i + 1, i + 7);
                if (hex.matches("-?[0-9a-fA-F]+")) {
                    matches = true;
                    for (int j = i; j > 1; j--) {
                        if (text.charAt(j - 1) == '\u00a7') {
                            lastCode = text.charAt(j);
                            break;
                        }
                    }
                    float r = Integer.parseInt(hex.substring(0, 2), 16) / 255f;
                    float g = Integer.parseInt(hex.substring(2, 4), 16) / 255f;
                    float b = Integer.parseInt(hex.substring(4, 6), 16) / 255f;
                    if (!shadow) this.setColor(r, g, b, this.alpha);
                    else this.setColor(0f, 0f, 0f, 0f);
                    pos = 0;
                }
            }
            if (pos != -1 && !matches) {
                pos++;
            }
            if (pos == 7) {
                if (lastCode != '\u0000' && lastCode != 'r') {
                    int dec = this.colorCode[shadow ? Character.digit(lastCode, 16) + 16 : Character.digit(lastCode, 16)];
                    this.setColor((float) (dec >> 16) / 255.0F, (float) (dec >> 8 & 255) / 255.0F, (float) (dec & 255) / 255.0F, this.alpha);
                    lastCode = '\u0000';
                } else {
                    this.setColor(this.red, this.green, this.blue, this.alpha);
                }
                pos = -1;
            }
        }
        return c;
    }
}
