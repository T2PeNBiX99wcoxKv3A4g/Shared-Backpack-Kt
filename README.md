# Server Backpack

This mod adds shared and private storage for multiplayer servers, allowing players to easily share or organize items
without placing physical containers.

## Commands

* `/serverbackpack backpack shared {name}` or `/sbp backpack shared {name}` - Opens a shared backpack (54 slots, the
  size of a double chest).
* `/serverbackpack backpack private {name}` or `/sbp backpack private {name}` - Opens a private backpack.
* `/serverbackpack furnace shared {furnace_type} {name}` or `/sbp furnace shared {furnace_type} {name}` - Opens a shared
  furnace.
* `/serverbackpack furnace private {furnace_type} {name}` or `/sbp furnace private {furnace_type} {name}` - Opens a
  private furnace.
* `/serverbackpack unlimitedfurnace shared {furnace_type} {name}` or
  `/sbp unlimitedfurnace shared {furnace_type} {name}` - Opens a shared unlimited furnace.
* `/serverbackpack unlimitedfurnace private {furnace_type} {name}` or
  `/sbp unlimitedfurnace private {furnace_type} {name}` - Opens a private unlimited furnace.
* `/serverbackpack trash open` or `/sbp trash open` - Opens the trash container.
* `/serverbackpack trash clear` or `/sbp trash clear` - Permanently clears all items in the trash container.

No permission is required to use any of these commands.

Supported furnace types include the normal furnace, blast furnace, and smoker.

## Additional Information

This project is a complete Kotlin rewrite of the
original [Shared Backpack](https://github.com/170yt/Minecraft-Shared-Backpack).

This is a server-side mod and does not need to be installed on clients.

All datas is stored in `/config/shared-backpack-kt`

## Credits

* [170yt](https://github.com/170yt/Minecraft-Shared-Backpack) — Original creator
