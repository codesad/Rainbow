package me.sad.rainbow.mixins;

import me.sad.rainbow.Colours;
import me.sad.rainbow.Config;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer {
    @Inject(method = "drawSlot", at = @At("TAIL"))
    public void drawSlot(Slot slotIn, CallbackInfo ci) {
        ItemStack stack = slotIn.getStack();
        if (stack == null) return;
        NBTTagCompound nbt = stack.serializeNBT();
        if (!nbt.hasKey("tag")) return;
        if (!nbt.getCompoundTag("tag").getCompoundTag("display").hasKey("color")) return;
        if (!nbt.getCompoundTag("tag").hasKey("ExtraAttributes")) return;
        if (!nbt.getCompoundTag("tag").getCompoundTag("ExtraAttributes").hasKey("id")) return;
        int color = nbt.getCompoundTag("tag").getCompoundTag("display").getInteger("color");
        String id = nbt.getCompoundTag("tag").getCompoundTag("ExtraAttributes").getString("id");
        if (Config.getFairyHighlight()) {
            if (Colours.getFairy().contains(color) && !id.startsWith("FAIRY_")) {
                Gui.drawRect(slotIn.xDisplayPosition, slotIn.yDisplayPosition, slotIn.xDisplayPosition + 16, slotIn.yDisplayPosition + 16, Config.getFairyColour().getRGB());
                return;
            }
        }
        if (Config.getCrystalHighlight()) {
            if (Colours.getCrystal().contains(color) && !id.startsWith("CRYSTAL_")) {
                Gui.drawRect(slotIn.xDisplayPosition, slotIn.yDisplayPosition, slotIn.xDisplayPosition + 16, slotIn.yDisplayPosition + 16, Config.getCrystalColour().getRGB());
                return;
            }
        }
        if (Config.getExoticHighlight()) {
            if (Colours.getExotic().get(id) != null) {
                if (!Colours.getExotic().get(id).contains(color)) {
                    Gui.drawRect(slotIn.xDisplayPosition, slotIn.yDisplayPosition, slotIn.xDisplayPosition + 16, slotIn.yDisplayPosition + 16, Config.getExoticColour().getRGB());
                }
            }
        }
    }
}
