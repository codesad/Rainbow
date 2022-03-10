package me.sad.rainbow.mixins;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.SplashProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SplashProgress.class)
public abstract class MixinSplashProgress {
    @ModifyVariable(method = "start", at = @At(value = "STORE"), ordinal = 2, remap = false)
    private static ResourceLocation setForgeGif(ResourceLocation resourceLocation) {
        return new ResourceLocation("rainbow", "melon.gif");
    }
}