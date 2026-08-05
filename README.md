# Shared Backpack Kotlin Version

This mod adds shared and private storage for multiplayer servers, allowing players to easily share or organize items
without placing physical containers.

## Commands

* `/sharedbackpack {name}` or `/sbp {name}` - Opens a shared backpack (54 slots, the size of a double chest).
* `/privatebackpack {name}` or `/pbp {name}` - Opens a private backpack.
* `/sharedfurnace {furnace_type} {name}` or `/sf {furnace_type} {name}` - Opens a shared furnace.
* `/privatefurnace {furnace_type} {name}` or `/pf {furnace_type} {name}` - Opens a private furnace.
* `/trash open` - Opens the trash container.
* `/trash clear` - Permanently clears all items in the trash container.

No permission is required to use any of these commands.

Supported furnace types include the normal furnace, blast furnace, and smoker.

![Shared Backpack Inventory](https://github.com/170yt/Minecraft-Shared-Backpack/blob/main/images/Shared-Backpack-Inventory.png?raw=true)

## Additional Information

This project is a complete Kotlin rewrite of the
original [Shared Backpack](https://github.com/170yt/Minecraft-Shared-Backpack).

This is a server-side mod and does not need to be installed on clients.

Shared backpack data is stored in:

`/config/shared-backpack-kt/backpack-{name}.dat`

Before loading, the existing file is automatically backed up to:

`/config/shared-backpack-kt/backpack-{name}.dat_old`

## Credits

* [170yt](https://github.com/170yt/Minecraft-Shared-Backpack) — Original creator
