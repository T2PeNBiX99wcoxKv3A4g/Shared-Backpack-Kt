package io.github.ykysnk.sharedBackpackKt.config

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val general: General = General()
) {
    @Serializable
    data class General(
        var sharedBackpackEnabled: Boolean = true,
        var privateBackpackEnabled: Boolean = true,
        var sharedSmeltingFurnaceEnabled: Boolean = true,
        var privateSmeltingFurnaceEnabled: Boolean = true,
        var sharedBlastingFurnaceEnabled: Boolean = true,
        var privateBlastingFurnaceEnabled: Boolean = true,
        var sharedSmokingFurnaceEnabled: Boolean = true,
        var privateSmokingFurnaceEnabled: Boolean = true,
        var sharedUnlimitedSmeltingFurnaceEnabled: Boolean = true,
        var privateUnlimitedSmeltingFurnaceEnabled: Boolean = true,
        var sharedUnlimitedBlastingFurnaceEnabled: Boolean = true,
        var privateUnlimitedBlastingFurnaceEnabled: Boolean = true,
        var sharedUnlimitedSmokingFurnaceEnabled: Boolean = true,
        var privateUnlimitedSmokingFurnaceEnabled: Boolean = true,
        var unlimitedFurnaceMultiplier: Int = 1,
        var trashEnabled: Boolean = true
    )

}