package me.sad.rainbow.mixins.patcher;

import me.sad.rainbow.features.HighlightHex;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * I'm not sure if this is the best way to do this, but it works
 */
@Pseudo
@Mixin(targets = "club.sk1er.patcher.hooks.FontRendererHook")
public class MixinFontRendererHook {
    @Dynamic
    @Redirect(method = "renderStringAtPos", at = @At(value = "INVOKE", target = "Ljava/lang/String;charAt(I)C", ordinal = 0, remap = false), remap = false)
    public char text(String text, int i) {
        return HighlightHex.redirectText(text, i);
    }
}
