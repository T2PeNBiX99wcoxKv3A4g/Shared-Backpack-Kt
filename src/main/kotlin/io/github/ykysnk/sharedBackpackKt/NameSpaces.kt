@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.github.ykysnk.sharedBackpackKt

import net.minecraft.resources.ResourceLocation

enum class NameSpaces(val id: String) {
    MOD("server-backpack"),
    FORGE("c"),
    MINECRAFT("minecraft");

    fun path(path: String) = ResourceLocation(id, path)

    override fun toString(): String = id
    operator fun invoke() = id
    operator fun invoke(path: String) = path(path)
}