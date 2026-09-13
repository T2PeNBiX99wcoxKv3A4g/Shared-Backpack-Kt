package io.github.ykysnk.serverBackpack.inventory

class BackpackContainer(name: String, override val onNoPlayersOpen: () -> Unit) :
    AbstractBackpackContainer("shared-backpack-${name}")
