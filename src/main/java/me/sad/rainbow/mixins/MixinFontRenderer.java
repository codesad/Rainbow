package me.sad.rainbow.mixins;

import me.sad.rainbow.features.HighlightHex;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = FontRenderer.class, priority = 999)
public abstract class MixinFontRenderer {
    @Inject(method = "renderStringAtPos", at = @At("HEAD"))
    public void renderStringAtPos(String text, boolean shadow, CallbackInfo ci) {
        HighlightHex.isCurrentTextShadow = shadow;
    }


    @Redirect(method = "renderStringAtPos", at = @At(value = "INVOKE", target = "Ljava/lang/String;charAt(I)C", ordinal = 0))
    public char text(String text, int i) {
        return HighlightHex.redirectText(text, i);
    }
}
