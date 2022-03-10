package me.sad.rainbow

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender


class Command : CommandBase() {
    override fun getCommandName(): String {
        return "rainbow"
    }

    override fun getRequiredPermissionLevel(): Int {
        return 0
    }

    override fun getCommandUsage(sender: ICommandSender?): String {
        return "/rainbow"
    }

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        Rainbow.displayScreen = Config.gui()
    }
}