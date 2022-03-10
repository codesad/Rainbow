package me.sad.rainbow.events

import me.sad.rainbow.Config
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.StatCollector
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class TooltipEvent {
    @SubscribeEvent
    fun onTooltip(event: ItemTooltipEvent) {
        if (Config.alwaysShowHex) {
            val nbtTagCompound: NBTTagCompound = event.itemStack.serializeNBT().getCompoundTag("tag").getCompoundTag("display")
            if (nbtTagCompound.hasKey("color", 3)) {
                if (!event.showAdvancedItemTooltips) {
                    for (i in 0 until event.toolTip.size) {
                        if (event.toolTip[i] == EnumChatFormatting.ITALIC.toString() + StatCollector.translateToLocal("item.dyed")) {
                            event.toolTip[i] = "Color: #${Integer.toHexString(nbtTagCompound.getInteger("color"))}"
                        }
                    }
                }
            }
        }
    }
}