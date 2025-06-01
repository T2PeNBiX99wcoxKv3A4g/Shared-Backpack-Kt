# Shared Backpack Kotlin Version

This mod adds a Shared Backpack to the game that can be used by all players.
It is used to share items on friendly multiplayer servers.

The Shared Backpack is the size of a double chest (54 slots) and can be accessed by using the `/sharedbackpack {name}`
or `/sbp {name}` command (no permission required).

Also add a Private Backpack can be accessed by using the `/privatebackpack {name}` or `/pbp {name}` command (no
permission required).

You can also access trash can by using the `/trash {open/clear}` command (no permission required).

Also using the `/sharedfurnace {furnace type} {name}` or `/sf {furnace type} {name}` can open furnace,
`/privatefurnace {furnace type} {name}` or `/pf {furnace type} {name}` is private version

![Shared Backpack Inventory](https://github.com/170yt/Minecraft-Shared-Backpack/blob/main/images/Shared-Backpack-Inventory.png?raw=true)<br>

### Additional Information

This is a complete remake in kotlin of [Shared Backpack](https://github.com/170yt/Minecraft-Shared-Backpack)

This is a server-side mod and does not need to be installed on the client. (But suggestion maybe will have problems)

The content of the Shared Backpack is stored in the file `/config/shared-backpack-kt/backpack-{name}.dat`.
Each time the server is started, this file is backed up to `/config/shared-backpack-kt/backpack-{name}.dat_old` before
being loaded.

### Credits

- [170yt](https://github.com/170yt/Minecraft-Shared-Backpack) - Original Creator