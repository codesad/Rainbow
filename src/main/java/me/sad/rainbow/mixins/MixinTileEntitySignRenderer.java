package me.sad.rainbow.mixins;

import me.sad.rainbow.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntitySign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntitySignRenderer.class)
public abstract class MixinTileEntitySignRenderer extends TileEntitySpecialRenderer<TileEntitySign> {
    private boolean isNotActive(TileEntitySign te) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiEditSign) {
            GuiEditSign guiEditSign = (GuiEditSign) Minecraft.getMinecraft().currentScreen;
            return ((IMixinGuiEditSign) guiEditSign).getTileSign() != te;
        }
        return true;
    }

    @Inject(method = "renderTileEntityAt(Lnet/minecraft/tileentity/TileEntitySign;DDDFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthMask(Z)V", ordinal = 0, shift = At.Shift.AFTER))
    public void disableLight(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage, CallbackInfo ci) {
        if (isNotActive(te) && Config.getDisableSignLighting()) {
            GlStateManager.disableLighting();
        }
    }

    @Inject(method = "renderTileEntityAt(Lnet/minecraft/tileentity/TileEntitySign;DDDFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthMask(Z)V", ordinal = 1, shift = At.Shift.BEFORE))
    public void enableLight(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage, CallbackInfo ci) {
        if (isNotActive(te) && Config.getDisableSignLighting()) {
            GlStateManager.enableLighting();
        }
    }
}
