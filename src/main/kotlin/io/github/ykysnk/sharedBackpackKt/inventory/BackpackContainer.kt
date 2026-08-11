package io.github.ykysnk.sharedBackpackKt.inventory

class BackpackContainer(name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractBackpackContainer("shared-backpack-${name}")
