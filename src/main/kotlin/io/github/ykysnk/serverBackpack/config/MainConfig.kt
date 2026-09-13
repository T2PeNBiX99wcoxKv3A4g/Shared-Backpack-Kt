@file:Suppress("unused")

package io.github.ykysnk.serverBackpack.config

import io.github.ykysnk.serverBackpack.NameSpaces
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigSection
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt

class MainConfig : Config(NameSpaces.MOD("main_config")) {
    var backpack = BackpackSection()

    class BackpackSection : ConfigSection() {
        var sharedBackpackEnabled = true
        var privateBackpackEnabled = true
    }

    var furnace = FurnaceSection()

    class FurnaceSection : ConfigSection() {
        var sharedSmeltingFurnaceEnabled = true
        var privateSmeltingFurnaceEnabled = true
        var sharedBlastingFurnaceEnabled = true
        var privateBlastingFurnaceEnabled = true
        var sharedSmokingFurnaceEnabled = true
        var privateSmokingFurnaceEnabled = true
    }

    var unlimitedFurnace = UnlimitedFurnaceSection()

    class UnlimitedFurnaceSection : ConfigSection() {
        var sharedUnlimitedSmeltingFurnaceEnabled = true
        var privateUnlimitedSmeltingFurnaceEnabled = true
        var sharedUnlimitedBlastingFurnaceEnabled = true
        var privateUnlimitedBlastingFurnaceEnabled = true
        var sharedUnlimitedSmokingFurnaceEnabled = true
        var privateUnlimitedSmokingFurnaceEnabled = true
        var unlimitedFurnaceMultiplier = ValidatedInt(1, Int.MAX_VALUE, 1)
    }

    var trash = TrashSection()

    class TrashSection : ConfigSection() {
        var trashEnabled = true
    }
}