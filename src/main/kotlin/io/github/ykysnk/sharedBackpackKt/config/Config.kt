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
        var trashEnabled: Boolean = true
    )

}