package io.github.ykysnk.sharedBackpackKt.inventory

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.MinecraftServer

object TickHandler {
    init {
        ServerLifecycleEvents.SERVER_STOPPING.register { clear() }
        ServerTickEvents.END_SERVER_TICK.register(::tick)
    }

    private val containers = mutableSetOf<AbstractBackpackContainer>()

    @Suppress("unused")
    fun register(container: AbstractBackpackContainer) {
        containers += container
    }

    @Suppress("unused")
    fun unregister(container: AbstractBackpackContainer) {
        containers -= container
    }

    fun clear() {
        containers.clear()
    }

    fun tick(server: MinecraftServer) {
        containers.forEach { it.tick(server) }
    }
}