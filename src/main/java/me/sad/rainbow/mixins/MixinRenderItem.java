package me.sad.rainbow.mixins;

import me.sad.rainbow.Config;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderItem.class)
public abstract class MixinRenderItem {
    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasEffect()Z"))
    public boolean redirectHasEffect(ItemStack stack) {
        if (stack.serializeNBT().hasKey("tag") && stack.serializeNBT().getCompoundTag("tag").getCompoundTag("display").hasKey("color") && Config.getDisableEnchantGlint()) return false;
        else return stack.hasEffect();
    }
}
