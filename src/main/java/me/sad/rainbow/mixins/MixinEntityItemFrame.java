package me.sad.rainbow.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityItemFrame.class)
public abstract class MixinEntityItemFrame extends EntityHanging {
    public MixinEntityItemFrame(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "getDisplayedItem", at = @At("HEAD"), cancellable = true)
    public void getDisplayedItem(CallbackInfoReturnable<ItemStack> cir) {
        ItemStack stack = this.getDataWatcher().getWatchableObjectItemStack(8);
        try {
            NBTTagCompound nbt = stack.serializeNBT();
            String id = nbt.getCompoundTag("tag").getCompoundTag("ExtraAttributes").getString("id");
            if (id.equals("SPOOKY_PIE")) {
                String name = stack.getDisplayName();
                for (String line: stack.getTooltip(Minecraft.getMinecraft().thePlayer, false)) {
                    if (EnumChatFormatting.getTextWithoutFormattingCodes(line).startsWith("Player: ")) {
                        name = line.substring(14);
                        break;
                    }
                }
                stack.setStackDisplayName(name);
            }
        } catch (Exception e) {
            cir.setReturnValue(stack);
        }
    }
}
