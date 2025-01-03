package org.nightlyeu

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager
import org.slf4j.LoggerFactory

object DupeMe : ModInitializer {
    private val logger = LoggerFactory.getLogger("dupeme")

	override fun onInitialize() {
		CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _, _ ->
			dispatcher.register(
				CommandManager.literal("dupe")
					.executes { context ->
						val source = context.source
						val player = source.player ?: return@executes 0
						val item = player.mainHandStack
						player.giveItemStack(item.copy())
						1
					}
			)
		})

		logger.info("DupeMe: Server ready!")
	}
}