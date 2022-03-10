package me.sad.rainbow.mixins;

import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * by far the most useless thing I've ever made
 */
@Pseudo
@Mixin(targets = "skytils.skytilsmod.asm.transformers.SplashProgressTransformer")
public abstract class MixinSplashProgressTransformer {
    @Dynamic
    @Inject(method = "setForgeGif", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectSetForgeGif(ResourceLocation resourceLocation, CallbackInfoReturnable<ResourceLocation> cir) {
        cir.setReturnValue(new ResourceLocation("rainbow", "melon.gif"));
    }
}
