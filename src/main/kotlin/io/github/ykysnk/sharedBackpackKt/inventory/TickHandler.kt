package io.github.ykysnk.sharedBackpackKt.inventory

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.MinecraftServer

object TickHandler {
    init {
        ServerLifecycleEvents.SERVER_STOPPING.register { clear() }
        ServerTickEvents.END_SERVER_TICK.register(::tick)
    }

    private val containers = hashSetOf<AbstractBackpackContainer>()
    private val pendingRemove = hashSetOf<AbstractBackpackContainer>()

    @Suppress("unused")
    fun register(container: AbstractBackpackContainer) {
        pendingRemove -= container
        containers += container
    }

    @Suppress("unused")
    fun unregister(container: AbstractBackpackContainer) {
        pendingRemove += container
    }

    fun clear() {
        containers.clear()
        pendingRemove.clear()
    }

    fun tick(server: MinecraftServer) {
        containers.forEach { it.tick(server) }
        containers.removeAll(pendingRemove)
        pendingRemove.clear()
    }
}