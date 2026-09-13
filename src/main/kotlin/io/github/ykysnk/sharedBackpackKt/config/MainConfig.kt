package io.github.ykysnk.sharedBackpackKt.config

import io.github.ykysnk.sharedBackpackKt.NameSpaces
import me.fzzyhmstrs.fzzy_config.config.Config

class MainConfig : Config(NameSpaces.MOD("main_config")) {
    var sharedBackpackEnabled = true
    var privateBackpackEnabled = true
    var sharedSmeltingFurnaceEnabled = true
    var privateSmeltingFurnaceEnabled = true
    var sharedBlastingFurnaceEnabled = true
    var privateBlastingFurnaceEnabled = true
    var sharedSmokingFurnaceEnabled = true
    var privateSmokingFurnaceEnabled = true
    var sharedUnlimitedSmeltingFurnaceEnabled = true
    var privateUnlimitedSmeltingFurnaceEnabled = true
    var sharedUnlimitedBlastingFurnaceEnabled = true
    var privateUnlimitedBlastingFurnaceEnabled = true
    var sharedUnlimitedSmokingFurnaceEnabled = true
    var privateUnlimitedSmokingFurnaceEnabled = true
    var unlimitedFurnaceMultiplier = 1
    var trashEnabled = true
}