# Changelog

## [0.9.0](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.7.1..v0.9.0) - 2026-08-11

### 🚜 Refactor

- *(inventory)* Remove `Player` dependency from containers and update `onNoPlayersOpen` signature - ([fe3c2b7](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/fe3c2b7863b5b63c8f338da3c1d803366e8c14a8))
- *(inventory)* Simplify `AbstractFurnaceContainer` lifecycle handling and remove `Player` dependency - ([7b6a160](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/7b6a1605c212573dacceb90fcef60b6a8716ba48))
- *(inventory)* Update `onNoPlayersOpen` signature to remove `Player` parameter for consistency - ([17dce70](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/17dce709259f8627c7cafe9fcdc9a52d7898224e))

### Action

- Update version in `gradle.properties` - ([80e004e](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/80e004ed1d1979a398bc8b4660a3314ebb142749))


## [0.7.1](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.7.0..v0.7.1) - 2026-08-11

### ⚙️ Miscellaneous Tasks

- *(gradle)* Bump `mod_version` to 0.4.0 for test - ([baff7ad](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/baff7adca9951f60bf185b04e0efcb38653bbb91))

### Action

- Update version in `gradle.properties` - ([5d3b48c](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/5d3b48cebffc936b53a3627b8b7d70a8b5d0ea22))


## [0.7.0](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.5.0..v0.7.0) - 2026-08-11

### ⛰️  Features

- *(inventory)* Add `clearContentOfTrashContainer` to handle trash container cleanup - ([2c4caba](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/2c4caba063ac9678f80e1e58e587ee2a25a9a027))
- *(inventory)* Add `onNoPlayersOpen` and `onStopOpen` callbacks to containers for improved lifecycle handling - ([feb25c5](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/feb25c5b34f78b2a7c11dc7e66d7a8b32d237ded))
- *(inventory)* Add `viewerCount` and `onNoPlayersOpen` to support player-specific lifecycle handling - ([8245e7f](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/8245e7fc8ffb6027e1551320776f1c9b1becc613))
- *(inventory)* Add `FurnaceTickHandler` to manage furnace lifecycle and ticking events - ([5c835bd](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/5c835bddfac37f13c5e071ee817d130054b9fffd))

### 🐛 Bug Fixes

- *(config)* Use `localizedMessage` for improved error message consistency - ([f71df7c](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/f71df7cef0ff26646bab2d3f52e406741442a09c))
- *(inventory)* Prevent `stopOpen` from closing non-empty trash containers and refactor `clearContent` handling - ([693d42e](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/693d42e0fe23bb1e7c6f8f0378562b6d64a5d325))
- *(workflow)* Enable GitHub auto-merge in Dependabot action - ([4f1a90a](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/4f1a90a8f54d6cb30dbaed558741242e1c6e7ee1))

### 🚜 Refactor

- *(commands)* Replace `Utils` container methods with `ContainerManager` methods for consistency and modularity - ([08c35d8](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/08c35d8343ec57488f3f5d3290b6e0e7a9e3afa2))
- *(core)* Simplify initialization by removing `onServerStarted` handler and integrating lifecycle components - ([509a35e](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/509a35e881b05d8af961ab410f39bf5172a80ca9))
- *(inventory)* Rename `onStopOpen` to `onEmpty` in `TrashContainer` for clarity - ([de5d06e](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/de5d06e49e63c144b724eab7bd0d226b422848e0))
- *(utils)* Extract container caching logic into `ContainerManager` for better modularity and clarity - ([8fe26e5](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/8fe26e5866c44dea39840502e3857843e2961b64))

### Action

- Update version in `gradle.properties` - ([740105a](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/740105ac4ccbafbfe9c8487ac3c1cb688ac8e7c1))


## [0.5.0](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.3.4..v0.5.0) - 2026-08-10

### ⛰️  Features

- *(config)* Add configuration system with YACL support and fallback screen - ([0acabfe](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/0acabfeb91b7ad72eabc058fdf442009e7c3f8bc))
- *(gradle)* Add ParchmentMC Maven repository - ([6c82746](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/6c8274683cf0198aad81ab5227863c7cdd976f16))
- *(gradle)* Add YACL and ModMenu dependencies, update Gradle build script with new repositories - ([a20a8e3](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/a20a8e3da776627e0eb5ca413bf7bfe2b84b9d7b))
- *(gradle)* Add `yamlkt` dependency and serialization plugin, update group ID in `gradle.properties` - ([48063c9](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/48063c9d5172c33a4e86b928b83ae55e2479281a))
- *(gradle)* Add YACL and ModMenu dependencies, update Gradle build script with new repositories - ([4db273a](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/4db273ae7099b90055ffa7721751f9e2a0a4109b))
- *(localization)* Add LOLCAT, Upside Down English, and Shakespearean English translations - ([a2358db](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/a2358dba85539d0ad7f7cc206502e8c20ae69268))
- *(localization)* Add mod description translations and improve description grammar - ([33fa6d9](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/33fa6d93cd73216c8cc5ee5caf9fc204d1ce644e))
- *(localization)* Add Pirate English localization and improve Trash Can naming consistency - ([569d5d0](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/569d5d0d079bd9c7d4264ed2fab5385f7234d2e6))
- *(localization)* Add Traditional and Simplified Chinese translations for shared backpack mod - ([89877b4](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/89877b49eb296a4837fd7f386cddc8a458664e14))
- *(localization)* Add Japanese localization for shared backpack mod - ([97a6a78](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/97a6a782b86eef99e8e5d99043c513e39baa3b1c))
- *(utils)* Add lazy initialization and directory creation for ConfigDir - ([60a2f3e](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/60a2f3e45e2953fadd8bef9cde098773fddf8f7c))
- Add `modmenu` entrypoint and update dependencies in `gradle.properties` - ([bd852b9](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/bd852b9cc1519112a84da8d7806a1bcec06f3f13))

### 🐛 Bug Fixes

- *(CI)* Exclude dependabot branches from triggering workflows - ([d5e4e73](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/d5e4e7384646f8e9002e26c59483f21b3c6b9e3b))
- *(command)* Update `FurnaceMenu` to use `propertyDelegate` instead of `dataAccess` for consistency - ([0bc6253](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/0bc62533a717c69f9608aa768e8ffdb705672a09))
- *(config)* Remove redundant `renderBackground` call in `ConfigFallbackScreen` - ([52d3d0b](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/52d3d0b575bc3abd2a98af901cad69e167fa6066))
- *(config)* Adjust `render` method call order for proper background rendering - ([cede87a](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/cede87aa9a5bfc72c79c9ac335e3e709b158f22b))
- *(config)* Update `renderBackground` to accept additional parameters - ([6197567](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/6197567ec8c61b9ac5f86ad41521e03589a1666c))
- *(config)* Enhance error message clarity by including exception class name - ([3ba37ba](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/3ba37ba8397ba2957210d9b5c0286f0eeffdc5a8))
- *(localization)* Standardize grammar and simplify Pirate English translations - ([80d79d3](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/80d79d3a1746313d16c40965efe5078c5b42298d))
- *(localization)* Improve grammar and consistency in LOLCAT translations - ([6ad6804](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/6ad68043c62e55e1fedc40bb277aac13ae8b00ad))
- *(localization)* Correct grammar and improve furnace names in config messages - ([68e1d83](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/68e1d83d1097b88492575f57c50abb4e02de0eac))

### 🚜 Refactor

- Update package structure and improve localization handling - ([876e939](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/876e939f60248f9989f7d339bc94a4b547f7bc3f))

### ⚙️ Miscellaneous Tasks

- *(gradle)* Update Gradle wrapper to 9.7.0 - ([c663aae](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/c663aae254309a146b9bbdfb2e6e52c4450b3226))

### Action

- Update version in `gradle.properties` - ([a372fbd](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/a372fbd11f87ef46a945f21b7c3130ce4e648266))


## [0.3.4](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.3.3..v0.3.4) - 2026-08-05

### 🐛 Bug Fixes

- *(CI)* Exclude README.md and LICENSE files from triggering workflows - ([0ff53cd](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/0ff53cdf0bee653d9b43ad070ca97957d03cb513))
- Fix registry access calls in `AbstractBackpackContainer` for 1.21.5 version - ([193eed2](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/193eed2994f8139729d14fab3a02486a1a9e3ec3))

### 🚜 Refactor

- Remove unused `superChargeLevel` property from `AbstractFurnaceContainer` - ([edf0fb6](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/edf0fb678d63b62d9aba572c6df7570bc9a25dbd))

### 📚 Documentation

- Update storage path details in README.md - ([76de502](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/76de502c91059eb3e71ec16db13fed90546dccb5))

### ⚙️ Miscellaneous Tasks

- *(dependencies)* Update loader, Kotlin loader, and Fabric versions in `gradle.properties` - ([10e1510](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/10e15101678d86213a5fb41b6eaf89cc72f14cf5))
- Parameterize Minecraft version in `fabric.mod.json` - ([2d2448f](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/2d2448f0d693a343b948865e25af10fe126d9b67))

### Action

- Update version in `gradle.properties` - ([867775d](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/867775d52afba6c9ef174dbcfcb32fee520014aa))


## [0.3.3](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.3.2..v0.3.3) - 2026-08-05

### 🐛 Bug Fixes

- *(CI)* Update pre_release_branches path in release and pre-release workflows - ([bd256ff](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/bd256ffa1252b383967f4c50a49dfa6a6d85100f))
- *(CI)* Add tag verification and dynamic pre-release branch support in workflows - ([1583ac0](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/1583ac06d118df34277b4824100ad3002dcb9d61))
- *(CI)* Add tag existence verification in release and pre-release workflows - ([bba9555](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/bba9555df07734f490495841a8bffd8b642b1319))


## [0.3.2](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.3.1..v0.3.2) - 2026-08-05

### ⚙️ Miscellaneous Tasks

- *(CI)* Add `git fetch --tags` step to release and pre-release workflows for tag synchronization - ([1b7dae9](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/1b7dae9f349c80dd55e959c6bf21fbe6901b90cf))

### Action

- Update version in `gradle.properties` - ([04fce52](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/04fce52e61e60fe6067005e6c5c75a0997ac4327))


## [0.3.1](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.3.0..v0.3.1) - 2026-08-05

### 🐛 Bug Fixes

- *(CI)* Update branch references in `release-25w14craftmine.yml` to `25w14craftmine` - ([bf97016](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/bf97016cdd0560c130f28f3cc352d9d3642fb810))

### 📚 Documentation

- *(README)* Update usage commands and enhance documentation for shared/private storage features - ([7188df6](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/7188df67c65bd0a048fc76b2dd8858565fb367c5))

### ⚙️ Miscellaneous Tasks

- *(CI)* Update Java version matrix to `23` in release workflows - ([b079903](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/b0799039878630d64649bbc4043f132c53b2a76c))
- *(CI)* Remove `release-1.21.5.yml` and standardize workflows with branch-aware configurations - ([aab2cc5](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/aab2cc536d66739bd1e72024f957e57a787c3d81))
- *(CI)* Update GitHub Actions versions in `release-25w14craftmine.yml` for improved compatibility and maintenance - ([71d2e5e](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/71d2e5e287bcdc1feab37bba7a91951fb48a99c6))
- *(CI)* Update GitHub Actions versions in `release-25w14craftmine.yml` for improved compatibility and maintenance - ([8727961](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/87279615b845bb3131d531653fb0166040a72097))
- *(version)* Downgrade mod version to `0.3.0` - ([4b793fc](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/4b793fc9fc5b8e9bd5b89a691f8beefdb3308ac7))

### Action

- Update version in `gradle.properties` - ([7cc9b50](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/7cc9b50dccf1af542fd51de22cfd0cdc0339fb61))
- Update version in `gradle.properties` - ([6137a4f](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/6137a4ff5c3f5e2ded62a5e806e30a3f7202dc65))
- Update version in `gradle.properties` - ([d8d0549](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/d8d05491d8a8f7444b721bffb0ff1551b309e97e))
- Update version in `gradle.properties` - ([d1324b7](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/d1324b7cc5017c04b06b78a2add762e06877eb35))
- Update version in `gradle.properties` - ([64e9abf](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/64e9abf5c4769b97ebe211a0d682aa1a0ba65709))


## [0.3.0](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.25..v0.3.0) - 2026-08-05

### ⛰️  Features

- *(Container)* Refactor and rename inventory classes to container classes for enhanced clarity and compatibility across the mod - ([3f7d0af](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/3f7d0afc4af033f14495564a3389fbfd32938016))
- *(Container)* Rename and refactor `AbstractBackpackInventory` to `AbstractBackpackContainer` for improved extensibility and Minecraft compatibility - ([2ee80af](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/2ee80af1d3cfc18845c5b0d797e8661d1208f834))
- *(Container)* Rename and update `AbstractFurnaceInventory` to `AbstractFurnaceContainer` for improved extensibility and Minecraft compatibility - ([276eaee](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/276eaee93d5e7b859b24d78615b54ebe0fbdab72))

### 🐛 Bug Fixes

- *(Container)* Initialize lifecycle hooks and improve `AbstractFurnaceContainer` null safety for recipe handling - ([dd1ae47](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/dd1ae474a595f4462d97d6a8328e8a604340fb97))
- *(NBT)* Update registry manager access to use `gameInstance` for 25w14craftmine - ([a6b22d8](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/a6b22d81585cca7c739dba5e9a3e121ccc889b5b))

### ⚙️ Miscellaneous Tasks

- *(CI)* Update GitHub Actions versions in workflows for improved maintenance and compatibility - ([dd5c5e2](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/dd5c5e203981b5f408d2811b737921a52e1976ce))
- *(CI)* Update GitHub Actions versions in `release.yml` for improved compatibility and maintenance - ([4ff1061](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/4ff10615a4665a6bb1616c9144e1afc5f72d13b7))
- *(CI)* Simplify `dependabot-auto-merge.yml` by replacing custom logic with reusable action - ([2778432](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/277843204c84b63d3a237b935c7a824f059adf87))
- *(Gradle)* Switch mappings to official Mojang mappings in `build.gradle.kts` - ([729ef45](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/729ef45a6407b843664f6b28ad99670adc9a3977))
- *(Gradle)* Update Minecraft, Fabric, and Yarn mappings versions in `gradle.properties` for snapshot `25w14craftmine` - ([1368293](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/1368293ad0b34085ca58e0521f9fd02dec7dd424))
- *(Gradle)* Update `fabric-loom` version and improve property access in `build.gradle.kts` - ([9da513a](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/9da513a4c487c728f40f18a54d3bf6ef685df847))
- *(Mod)* Update `minecraft` version in `fabric.mod.json` to `1.21.6-alpha.25.14.craftmine` - ([c5686a0](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/c5686a062e1350b4fbcffb4ca71b99edd19b1376))

### Action

- Update version in `gradle.properties` - ([07e5a57](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/07e5a57103131e19f15f9e7273a4fc639f3f2010))


## [0.2.25](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.24..v0.2.25) - 2026-08-04

### Action

- Update version in `gradle.properties` - ([89ecfc4](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/89ecfc42f0645d9547e65693a03667d1fb5b176f))

### Build

- *(deps)* Bump gradle-wrapper from 9.6.0 to 9.6.1 - ([a589a19](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/a589a196b75b359f1982755104175a325fab3385))


## [0.2.24](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.23..v0.2.24) - 2026-07-14

### Action

- Update version in `gradle.properties` - ([85b091e](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/85b091ea408f9a741ca33b1a4841b15be4c782c3))

### Build

- *(deps)* Bump jvm from 2.4.0 to 2.4.10 - ([db87ece](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/db87ece1312d03d2fed2a84607c14d6b92ee268f))


## [0.2.23](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.22..v0.2.23) - 2026-06-19

### Action

- Update version in `gradle.properties` - ([b161c6e](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/b161c6e295e894cc8bc60dc63c5ae9738ce1bbf2))

### Build

- *(deps)* Bump jvm from 2.3.21 to 2.4.0 - ([47c6947](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/47c69478c523482028cf75eca17267d80ef967af))


## [0.2.22](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.21..v0.2.22) - 2026-06-19

### Action

- Update version in `gradle.properties` - ([0e82c73](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/0e82c734f3ed5a7b9a5eb69ab8c7e87f1441bc41))

### Build

- *(deps)* Bump gradle-wrapper from 9.5.1 to 9.6.0 - ([9b55f97](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/9b55f97c499258b63e6527824b2083a4ac4b6999))


## [0.2.21](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.20..v0.2.21) - 2026-05-14

### Action

- Update version in `gradle.properties` - ([f60e9c9](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/f60e9c986bd6ac2eaf59fcc978d94deb008c8c3c))

### Build

- *(deps)* Bump gradle-wrapper from 9.5.0 to 9.5.1 - ([760abea](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/760abea0ee925ee0531b33285cab56e8d4298dd0))


## [0.2.20](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.19..v0.2.20) - 2026-04-28

### Action

- Update version in `gradle.properties` - ([91e901e](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/91e901ede2b9b695e24b921af70a451c88c7c762))

### Build

- *(deps)* Bump gradle-wrapper from 9.4.1 to 9.5.0 - ([3006cb2](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/3006cb29233437ff7f4dbeb7486bcc833a62a739))


## [0.2.19](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.18..v0.2.19) - 2026-04-26

### Build

- *(deps)* Bump jvm from 2.3.10 to 2.3.21 - ([ddd3b30](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/ddd3b30925fe726c4f7492021fa31f33a52ece52))


## [0.2.18](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.17..v0.2.18) - 2026-04-26

### Action

- Update version in `gradle.properties` - ([a29abb0](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/a29abb025db9c9ecf141449a85d035b36a426741))

### Build

- *(deps)* Bump gradle-wrapper from 9.4.0 to 9.4.1 - ([c1e21e0](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/c1e21e0b1142eee09b47bfdb134467ec2aeae25a))


## [0.2.17](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.16..v0.2.17) - 2026-03-04

### Action

- Update version in `gradle.properties` - ([c46c077](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/c46c0773b796275c2dd78191738fbaf549a30bfb))

### Build

- *(deps)* Bump gradle-wrapper from 9.3.1 to 9.4.0 - ([58f65c6](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/58f65c6e7ee514ae0f05f02bcce893538bc3e63e))


## [0.2.16](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.15..v0.2.16) - 2026-02-05

### Action

- Update version in `gradle.properties` - ([198df66](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/198df660dda538e4d55d47bdb1c4daa3422ab448))

### Build

- *(deps)* Bump jvm from 2.3.0 to 2.3.10 - ([95401d1](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/95401d116de2ef676d88c8c9d03e4a909ff75d01))


## [0.2.15](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.14..v0.2.15) - 2026-01-31

### Action

- Update version in `gradle.properties` - ([a993bc6](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/a993bc62d2e178f8c92d3b568a1e58efcc9a170a))

### Build

- *(deps)* Bump gradle-wrapper from 9.3.0 to 9.3.1 - ([332fbeb](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/332fbeb20513250dbb26b9924668628f2635e602))


## [0.2.14](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.13..v0.2.14) - 2026-01-26

### Action

- Update version in `gradle.properties` - ([7605ae9](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/7605ae9c90fb66061fa07a719d0b192a751c7860))

### Build

- *(deps)* Bump gradle-wrapper from 8.14 to 9.3.0 - ([bf4ed9d](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/bf4ed9d5bc185773037d72e779c61465af5c00d5))


## [0.2.13](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.12..v0.2.13) - 2025-12-16

### Build

- *(deps)* Bump jvm from 2.2.21 to 2.3.0 - ([9bb14f8](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/9bb14f82dfaed28661591becd285d330a19171fe))


## [0.2.12](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.11..v0.2.12) - 2025-10-23

### Action

- Update version in `gradle.properties` - ([d03d990](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/d03d990c8a4f35170fa0f004a74b9b403816ddb1))


## [0.2.11](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.10..v0.2.11) - 2025-10-23

### ⚙️ Miscellaneous Tasks

- *(CI)* Remove unused `change_version.yml` configuration - ([2817967](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/2817967efc51d1cd9711900155c1fbe7c8a8b843))


## [0.2.10](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.9..v0.2.10) - 2025-10-23

### ⚙️ Miscellaneous Tasks

- *(CI)* Remove unused `change_version.yml` configuration - ([2766730](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/276673013fa5c54bdab9ad2ee2a0ded2b195d1d3))

### Action

- Update version in `gradle.properties` - ([7fd31e2](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/7fd31e251f46981ee16c8e5b8c62ede7ccadf254))
- Update version in `gradle.properties` - ([1cdb032](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/1cdb032aa120d05512c64ffc2396b4ff5d1fc5ab))
- Update version in `gradle.properties` - ([0657cfd](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/0657cfdd5a467523089a15714d07678ba729dc98))


## [0.2.9](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.8..v0.2.9) - 2025-10-23

### 🐛 Bug Fixes

- *(Gradle)* Fix `gradle.properties` in `1.21.5` - ([047492c](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/047492c5f0faa40299b4061f9d9a4049ffc7f2e6))
- *(Utils)* Clean code - ([002e5c4](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/002e5c48cbd0c54b672ec2b3494c4c0674e183e0))
- Fix issue in `1.21.5` - ([c3ac24b](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/c3ac24b051a83e5c1fa561db4f1482e85637933a))
- Fix furnace in `1.21.5` - ([6051949](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/60519490bac0d1352b1f3d485c7738b7def7f23e))

### ⚙️ Miscellaneous Tasks

- *(CI)* Enhance workflows with changelog, artifact handling, and auto-merge setup - ([4995bfa](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/4995bfa98a0a9af2649060bb1ba961a113f5b630))

### Action

- Update version in `gradle.properties` - ([e8132aa](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/e8132aa7d68167297c5d4c541639c1448b94f59f))


## [0.2.8](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.7..v0.2.8) - 2025-10-23

### Action

- Update version in `gradle.properties` - ([a250cc2](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/a250cc2fa715ba7ac61f99ce37aad035ed67db6f))

### Build

- *(deps)* Bump jvm from 2.2.20 to 2.2.21 - ([c88324c](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/c88324cb9ebd7caac7bde31c6efd0164cecd3a53))


## [0.2.7](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.6..v0.2.7) - 2025-09-11

### Action

- Update version in `gradle.properties` - ([45a3afd](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/45a3afdc9aede6e7752fe340a67fa04da0313a98))

### Build

- *(deps)* Bump jvm from 2.2.0 to 2.2.20 - ([b20e1a8](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/b20e1a8ca93eba38ecf469e21f41b62cae308057))


## [0.2.6](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.5..v0.2.6) - 2025-06-25

### 🐛 Bug Fixes

- *(deps)* Merge pull request #1 from T2PeNBiX99wcoxKv3A4g/dependabot/gradle/jvm-2.2.0 - ([1d69040](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/1d69040078992d218c7ca131072f3adcc0831a66))

### Action

- Update version in `gradle.properties` - ([04e6365](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/04e6365d8d4b9c9bc0fc372a549fc047c06318c9))

### Build

- *(deps)* Bump jvm from 2.1.21 to 2.2.0 - ([dcd4679](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/dcd467914a8ab2998ecacd86387043aaf98626d1))

## New Contributors ❤️

* @dependabot[bot] made their first contribution

## [0.2.5](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.4..v0.2.5) - 2025-06-01

### 🐛 Bug Fixes

- *(Gradle)* Fix `gradle.properties` in `1.21.5` - ([4bc5ef7](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/4bc5ef7ee12dea3b7e8e43f9db53716f58457e61))
- *(TrashType)* Clean code - ([11bb1cc](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/11bb1cc15550391c11be826004a10071f9e523e1))
- *(TrashType)* Clean code - ([884f014](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/884f014b4c32fe81c5ae8e62f5e0764b83a73821))
- *(Utils)* Clean code - ([82abffa](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/82abffafe5f8b1508309df3892a284935b8d6a35))
- Fix issue in `1.21.5` - ([bad9706](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/bad970618ec430f5374f3012efea2ee85a5f6ba5))

### ⚙️ Miscellaneous Tasks

- *(CI)* Enhance workflows with changelog, artifact handling, and auto-merge setup - ([61c8789](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/61c8789144c328e87c6fcc2f35070513b7536995))

### Action

- Update version in `gradle.properties` - ([c98bef1](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/c98bef12dcacd8ae0229b84ad271a9fb48e0b32b))
- Update version in `gradle.properties` - ([bd4f6f5](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/bd4f6f55b2f335028c6a7993652cfcbb22fadd6d))
- Update version in `gradle.properties` - ([09020f1](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/09020f104c1ae3f206b243690982e263882637b6))


## [0.2.4](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.1..v0.2.4) - 2025-06-01

### 🐛 Bug Fixes

- Fix furnace in `1.21.5` - ([5f5a5bb](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/5f5a5bbf1cfc7c9f4f31774e2b69b094c1e2e5a1))

### Action

- Update version in `gradle.properties` - ([49b5921](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/49b5921ea92f0720f2ee5e60118335cfc34a4115))


## [0.2.1](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.2.0..v0.2.1) - 2025-06-01

### 🐛 Bug Fixes

- Fix furnace can't open issue - ([fb212b4](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/fb212b4b68862e5aaf57fbe936cb583245286a0f))

### Action

- Update version in `gradle.properties` - ([4122fcc](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/4122fcc0ae7da8414ca8c54d45b01dd29869d8ed))


## [0.2.0](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.1.0..v0.2.0) - 2025-06-01

### ⛰️  Features

- Add new furnace inventory feature - ([8de7747](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/8de774715103418fda75101123e1f979d973d32d))

### 🐛 Bug Fixes

- *(AbstractBackpackInventory)* Fix `registryManager` get - ([c4f1d8e](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/c4f1d8e729d81909879048bfc776db0a24844b48))
- *(TrashCommand)* Stop register argument type `TrashTypeArgument` - ([46ed70e](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/46ed70ec61e9cd1e2bc3d9ca90e4df91ac32d18b))
- *(TrashCommand)* Stop using `TrashTypeArgument` - ([b72dab2](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/b72dab23b7957d56e6d2adf543d5f94f3d792ad0))
- *(Workflow)* Fix branch name - ([52fa078](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/52fa07817047b5b8f8ccf8a4c3a38837e6d6d233))
- *(Workflow)* Add new file `release-1.21.5.yml` - ([19a869f](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/19a869fec2764318303b66a8035939465fc7c03a))
- *(fabric.mod.json)* Fix `fabric.mod.json` - ([ceb8560](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/ceb856091a3a8ac1d8c2a24b2ed9a0537c326763))
- Make `trash` command using vanilla way - ([7b49023](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/7b490235b5eef6656483a3efb68379d90a6d35c0))
- Add `FurnaceInventory` for test - ([dabed19](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/dabed1981a2cdb6a30ff23d57484ccf79072c27f))
- Change to version `1.21.5` - ([f4301f1](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/f4301f1b5fd343487eb961e0832d8bbe544eb207))

### Action

- Update version in `gradle.properties` - ([16aabde](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/16aabdedb86269eb8131cc5974cd3fa2c6517908))
- Update version in `gradle.properties` - ([a2ba9a8](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/a2ba9a8c8179cbbd5f5200cac047e0d74eee0ae1))
- Update version in `gradle.properties` - ([9fe60c8](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/9fe60c8b51726e9faf07a0c04be386723fe1b01c))
- Update version in `gradle.properties` - ([e88e89a](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/e88e89a9caa31bdc920074dc85dd9962f9b7d245))


## [0.1.0](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.0.9..v0.1.0) - 2025-05-29

### Action

- Update version in `gradle.properties` - ([12f1410](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/12f14101b9983ddd8fc7c409dbea5bc4856fbf50))


## [0.0.9](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.0.8..v0.0.9) - 2025-05-29

### 🐛 Bug Fixes

- *(TrashCommand)* Stop register argument type `TrashTypeArgument` - ([bef807f](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/bef807fb64196a7e20395c5dc78cb88c41973c0c))
- *(TrashCommand)* Stop using `TrashTypeArgument` - ([95b1a77](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/95b1a7782f14b2a4817bd6825141c0c80b6f1855))

### Action

- Update version in `gradle.properties` - ([02f7682](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/02f76822e5dfdc354b49ecbf8a13d4b81312d1aa))
- Update version in `gradle.properties` - ([c27b7ff](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/c27b7ffba2384ebd38dca277e1931dcfb3b59bc5))
- Update version in `gradle.properties` - ([0c8a35c](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/0c8a35c00ee24e8f24f25460cf4f7430012e2b8c))


## [0.0.8](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.0.5..v0.0.8) - 2025-05-29

### Action

- Update version in `gradle.properties` - ([ee250bf](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/ee250bfb8d529a0741c86ab837fff67484bf37be))


## [0.0.5](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.0.4..v0.0.5) - 2025-05-29

### 🐛 Bug Fixes

- *(LICENSE)* Add `LICENSE` - ([dd0fd00](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/dd0fd00eb57a1c2db172b7b72d12b91ad8a9ce21))

### Action

- Update version in `gradle.properties` - ([2f74122](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/2f74122a79d579196bff76315bdf0207f50fbd9f))


## [0.0.4](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.0.3..v0.0.4) - 2025-05-29

### 🐛 Bug Fixes

- *(Gradle)* Update `gradle-wrapper` - ([bc570e2](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/bc570e255f276b893a02b231bc3511585b7e63d5))

### Action

- Update version in `gradle.properties` - ([b13e118](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/b13e118a3bdd4c80c02e356948b619dba370173c))


## [0.0.3](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.0.2..v0.0.3) - 2025-05-29

### 🐛 Bug Fixes

- *(Gradle)* Add `gradle-wrapper.jar` - ([f000512](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/f0005124ef622e01c0d0e5f34eef6264dee358fe))

### Action

- Update version in `gradle.properties` - ([24d14eb](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/24d14eb86ab32c173753026da49e5cd4375eb426))


## [0.0.2](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/compare/v0.0.1..v0.0.2) - 2025-05-29

### 🐛 Bug Fixes

- *(Gradle)* Add `gradlew` scripts - ([6fbe814](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/6fbe814a6ed1ef3b85313f4554a71c297a5faf85))
- *(Workflow)* Fix workflow - ([ced270e](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/ced270e00c3bdb44456ee55a84a1ee798f5c38ad))

### Action

- Update version in `gradle.properties` - ([799a382](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/799a382b70ab29679228755e64dd2ecb6d8016a7))

## New Contributors ❤️

* @github-actions[bot] made their first contribution

## [0.0.1] - 2025-05-29

### ⛰️  Features

- Create new inventory, now `/privatebackpack` also can add a name - ([0054259](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/00542592db50d5b6644986b17aacf621075e72c6))
- Add `/trash` command, clean code - ([20b46c5](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/20b46c537b487266ba420972e1dc8d1aaa04caa5))

### 🐛 Bug Fixes

- *(Gradle)* Rename - ([57c2f84](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/57c2f84f09684f37bf9091ee79e3737e0522c3b3))
- *(LICENSE)* Copy `LICENSE` form `https://github.com/170yt/Minecraft-Shared-Backpack` - ([7f75285](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/7f75285ca3415a596b9ccce463541903588dbd3f))
- *(LICENSE)* Remove `LICENSE.txt` - ([925a8bc](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/925a8bcd2ca58f22114ff81834bf496f15ee4d1b))
- *(README)* Update - ([6880dd6](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/6880dd6f750c18627c864503fead8b894365318a))
- *(README)* Add `README.md` - ([d4b96fc](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/d4b96fc276580f1d372c4c0cb3b09c2f2d908ba5))
- *(Workflow)* Setup workflow - ([2c6843b](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/2c6843b00442b039da15c3e384e1c6bee2759256))
- Rename `fabric.mod.json` - ([173dad2](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/173dad2ec5e9e5b2dab01d5e956def61e63e9511))
- Rename and remove client side - ([c5f05f6](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/c5f05f6054f96f81bdbb49364c044e305d267a74))
- Change inventory text - ([d18c673](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/d18c6732b612198103026e02e994aa44de4111dc))
- Change the command name to `/sharedbackpack` - ([d7788c5](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/d7788c57bba0df7cc6de50376b41552e943675d4))
- Add `icon.png` - ([6d47875](https://github.com/T2PeNBiX99wcoxKv3A4g/Shared-Backpack-Kt/commit/6d47875870b9acc6b5e93bf264214a365d5f47da))

## New Contributors ❤️

* @T2PeNBiX99wcoxKv3A4g made their first contribution

<!-- generated by git-cliff -->
