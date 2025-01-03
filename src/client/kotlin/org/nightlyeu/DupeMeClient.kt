package org.nightlyeu

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import org.lwjgl.glfw.GLFW
import org.slf4j.LoggerFactory

object DupeMeClient : ClientModInitializer {
	private val logger = LoggerFactory.getLogger("dupeme")

	val dupeMeKey: KeyBinding = KeyBindingHelper.registerKeyBinding(
		KeyBinding(
			"key.dupeme.keybind",
			GLFW.GLFW_KEY_EQUAL,
			"key.categories.dupeme"
		)
	)

	override fun onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client ->
			if (dupeMeKey.wasPressed()) {
				client.player?.networkHandler?.sendChatCommand("dupe")
			}
		})

		logger.info("DupeMe: Client ready!")
	}
}