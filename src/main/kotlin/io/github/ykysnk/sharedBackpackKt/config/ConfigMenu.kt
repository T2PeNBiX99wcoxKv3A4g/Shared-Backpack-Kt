package io.github.ykysnk.sharedBackpackKt.config

import dev.isxander.yacl3.api.controller.BooleanControllerBuilder
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder
import dev.isxander.yacl3.dsl.YetAnotherConfigLib
import io.github.ykysnk.sharedBackpackKt.Utils

object ConfigMenu {
    fun create() = YetAnotherConfigLib(Utils.MOD_ID) {
        categories.register("general") {
            groups.register("backpack") {
                options.register("shared-backpack-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.sharedBackpackEnabled },
                        { ConfigManager.config.general.sharedBackpackEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }

                options.register("private-backpack-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.privateBackpackEnabled },
                        { ConfigManager.config.general.privateBackpackEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }
            }

            groups.register("furnace") {
                options.register("shared-smelting-furnace-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.sharedSmeltingFurnaceEnabled },
                        { ConfigManager.config.general.sharedSmeltingFurnaceEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }

                options.register("private-smelting-furnace-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.privateSmeltingFurnaceEnabled },
                        { ConfigManager.config.general.privateSmeltingFurnaceEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }

                options.register("shared-blasting-furnace-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.sharedBlastingFurnaceEnabled },
                        { ConfigManager.config.general.sharedBlastingFurnaceEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }

                options.register("private-blasting-furnace-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.privateBlastingFurnaceEnabled },
                        { ConfigManager.config.general.privateBlastingFurnaceEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }

                options.register("shared-smoking-furnace-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.sharedSmokingFurnaceEnabled },
                        { ConfigManager.config.general.sharedSmokingFurnaceEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }

                options.register("private-smoking-furnace-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.privateSmokingFurnaceEnabled },
                        { ConfigManager.config.general.privateSmokingFurnaceEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }
            }

            groups.register("unlimited-furnace") {
                options.register("shared-unlimited-smelting-furnace-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.sharedSmeltingFurnaceEnabled },
                        { ConfigManager.config.general.sharedSmeltingFurnaceEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }

                options.register("private-unlimited-smelting-furnace-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.privateSmeltingFurnaceEnabled },
                        { ConfigManager.config.general.privateSmeltingFurnaceEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }

                options.register("shared-unlimited-blasting-furnace-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.sharedBlastingFurnaceEnabled },
                        { ConfigManager.config.general.sharedBlastingFurnaceEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }

                options.register("private-unlimited-blasting-furnace-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.privateBlastingFurnaceEnabled },
                        { ConfigManager.config.general.privateBlastingFurnaceEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }

                options.register("shared-unlimited-smoking-furnace-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.sharedSmokingFurnaceEnabled },
                        { ConfigManager.config.general.sharedSmokingFurnaceEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }

                options.register("private-unlimited-smoking-furnace-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.privateSmokingFurnaceEnabled },
                        { ConfigManager.config.general.privateSmokingFurnaceEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }

                options.register("unlimited-furnace-multiplier") {
                    binding(
                        1,
                        { ConfigManager.config.general.unlimitedFurnaceMultiplier },
                        { ConfigManager.config.general.unlimitedFurnaceMultiplier = it }
                    )

                    controller { IntegerFieldControllerBuilder.create(it).min(1) }
                }
            }

            groups.register("trash") {
                options.register("trash-enabled") {
                    binding(
                        true,
                        { ConfigManager.config.general.trashEnabled },
                        { ConfigManager.config.general.trashEnabled = it }
                    )

                    controller(BooleanControllerBuilder::create)
                }
            }
        }

        save {
            ConfigManager.save()
        }
    }
}