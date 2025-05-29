package io.github.yky.sharedBackpackKt.suggestion

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import io.github.yky.sharedBackpackKt.argument.TrashType
import net.minecraft.server.command.ServerCommandSource
import java.util.concurrent.CompletableFuture

class TrashTypeSuggestion : SuggestionProvider<ServerCommandSource> {
    override fun getSuggestions(
        context: CommandContext<ServerCommandSource>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        for (type in TrashType.entries) {
            builder.suggest(type.name.lowercase())
        }
        return builder.buildFuture()
    }
}