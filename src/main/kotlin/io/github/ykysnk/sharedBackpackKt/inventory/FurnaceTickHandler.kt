package io.github.ykysnk.sharedBackpackKt.inventory

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.level.ServerLevel

object FurnaceTickHandler {
    init {
        ServerLifecycleEvents.SERVER_STOPPING.register { clear() }
        ServerTickEvents.END_WORLD_TICK.register(::tick)
    }

    private val furnaces = mutableSetOf<AbstractFurnaceContainer>()

    @Suppress("unused")
    fun register(furnace: AbstractFurnaceContainer) {
        furnaces += furnace
    }

    @Suppress("unused")
    fun unregister(furnace: AbstractFurnaceContainer) {
        furnaces -= furnace
    }

    fun clear() {
        furnaces.clear()
    }

    fun tick(world: ServerLevel) {
        furnaces.forEach { it.tick(world) }
    }
}