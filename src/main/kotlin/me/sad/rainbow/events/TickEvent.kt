package me.sad.rainbow.events

import me.sad.rainbow.Rainbow
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

class TickEvent {
    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (Rainbow.displayScreen != null) {
            Minecraft.getMinecraft().displayGuiScreen(Rainbow.displayScreen)
            Rainbow.displayScreen = null
        }
    }
}