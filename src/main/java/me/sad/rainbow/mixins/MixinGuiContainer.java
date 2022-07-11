package me.sad.rainbow.mixins;

import me.sad.rainbow.Colours;
import me.sad.rainbow.Config;
import me.sad.rainbow.Utils;
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
        if (nbt.getCompoundTag("tag").getCompoundTag("ExtraAttributes").hasKey("item_dye")) return;
        if (!nbt.getCompoundTag("tag").getCompoundTag("ExtraAttributes").hasKey("id")) return;
        int colour = nbt.getCompoundTag("tag").getCompoundTag("display").getInteger("color");
        String id = nbt.getCompoundTag("tag").getCompoundTag("ExtraAttributes").getString("id");
        int x = slotIn.xDisplayPosition;
        int y = slotIn.yDisplayPosition;

        if (Colours.getFairy().contains(colour) && !Colours.getFairyIds().contains(id)) {
            System.out.println(id);
            if (Config.getFairyHighlight()) {
                Utils.drawItemBackground(x, y, Config.getFairyColour());
            }
            return;
        }

        if (Colours.getCrystal().contains(colour) && !Colours.getCrystalIds().contains(id)) {
            if (Config.getCrystalHighlight()) {
                Utils.drawItemBackground(x, y, Config.getCrystalColour());
            }
            return;
        }

        if (Colours.getGlitched().containsKey(id) && Colours.getGlitched().get(id).contains(colour)) {
            if (Config.getGlitchedHighlight()) {
                Utils.drawItemBackground(x, y, Config.getGlitchedColour());
            }
            return;
        }

        if (Colours.getExotic().containsKey(id) && !Colours.getExotic().get(id).contains(colour)) {
            if (Config.getExoticHighlight()) {
                Utils.drawItemBackground(x, y, Config.getExoticColour());
            }
        }
    }
}