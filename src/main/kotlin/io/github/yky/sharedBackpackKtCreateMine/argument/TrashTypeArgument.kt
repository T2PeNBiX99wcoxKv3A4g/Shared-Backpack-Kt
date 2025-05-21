package io.github.yky.sharedBackpackKtCreateMine.argument

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException

class TrashTypeArgument : ArgumentType<String> {
    companion object {
        const val ID = "trash_type"

        fun getTrashType(context: CommandContext<*>, name: String = ID): TrashType {
            return context.getArgument(name, String::class.java).let { TrashType.valueOf(it.uppercase()) }
        }
    }

    override fun parse(reader: StringReader): String {
        return runCatching {
            val input = reader.readString().uppercase()
            TrashType.valueOf(input).name
        }.getOrElse {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherParseException().create("Invalid Trash type.")
        }
    }
}