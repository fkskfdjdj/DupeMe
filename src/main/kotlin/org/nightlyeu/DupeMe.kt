package org.nightlyeu

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import org.slf4j.LoggerFactory

object DupeMe : ModInitializer {
    private val logger = LoggerFactory.getLogger("dupeme")

	override fun onInitialize() {
		CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _, _ ->
			dispatcher.register(
				CommandManager.literal("dupe")
					.executes { dupe(it) }
					.then(CommandManager.argument("count", IntegerArgumentType.integer(1))
						.executes { dupe(it) }
			))
		})

		logger.info("DupeMe: Server ready!")
	}

	fun dupe(context: CommandContext<ServerCommandSource>): Int {
		val source = context.source
		val player = source.player ?: return 0
		val item = player.mainHandStack
		var amt =
		try {
			IntegerArgumentType.getInteger(context, "count")
		} catch (_: IllegalArgumentException) {
			1
		}

		if (amt > 2500) {
			amt = 2500
		}

		for (i in 1..amt) {
			player.giveItemStack(item.copy())
		}
		return 1
	}
}