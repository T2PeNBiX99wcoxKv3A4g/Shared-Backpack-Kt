@file:Suppress("unused")

package io.github.ykysnk.sharedBackpackKt.config

import io.github.ykysnk.sharedBackpackKt.NameSpaces
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigSection

class MainConfig : Config(NameSpaces.MOD("main_config")) {
    val backpack = BackpackSection()

    class BackpackSection : ConfigSection() {
        var sharedBackpackEnabled = true
        var privateBackpackEnabled = true
    }

    val furnace = FurnaceSection()

    class FurnaceSection : ConfigSection() {
        var sharedSmeltingFurnaceEnabled = true
        var privateSmeltingFurnaceEnabled = true
        var sharedBlastingFurnaceEnabled = true
        var privateBlastingFurnaceEnabled = true
        var sharedSmokingFurnaceEnabled = true
        var privateSmokingFurnaceEnabled = true
    }

    val unlimitedFurnace = UnlimitedFurnaceSection()

    class UnlimitedFurnaceSection : ConfigSection() {
        var sharedUnlimitedSmeltingFurnaceEnabled = true
        var privateUnlimitedSmeltingFurnaceEnabled = true
        var sharedUnlimitedBlastingFurnaceEnabled = true
        var privateUnlimitedBlastingFurnaceEnabled = true
        var sharedUnlimitedSmokingFurnaceEnabled = true
        var privateUnlimitedSmokingFurnaceEnabled = true
        var unlimitedFurnaceMultiplier = 1
    }

    val trash = TrashSection()

    class TrashSection : ConfigSection() {
        var trashEnabled = true
    }
}