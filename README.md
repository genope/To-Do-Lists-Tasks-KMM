# To-Do-Lists-Tasks-KMM

* Kotlin/Compose Multiplatform Mobile [KMM]
* [Compose]
* [Voyager]
* [Compose Multiplatform Wizard]
* [MVI]
* [Flow]
* [Sqldelight]
* [Ktor]
* [Koin]

## Android, iOS, Desktop


## Architecture
### Clean Architecture KMM

<p align="center">    
  <img src="./cleanArchitectureKMM.png">
<p>

### KMM Life Cycle

<p align="center">    
  <img src="./kmmLifeCycle.png">
<p>

## Run project

### Android
To run the application on android device/emulator:
- open project in Android Studio and run imported android run configuration

To build the application bundle:
- run `./gradlew :composeApp:assembleDebug`
- find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`

### Desktop
Run the desktop application: `./gradlew :composeApp:run`

### iOS
To run the application on iPhone device/simulator:
- Open `iosApp/iosApp.xcworkspace` in Xcode and run standard configuration
- Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio














[KMM]: https://kotlinlang.org/lp/mobile/
[Flow]: https://github.com/Kotlin/kotlinx.coroutines
[Sqldelight]: https://cashapp.github.io/sqldelight/
[Ktor]: https://ktor.io/
[Koin]: https://insert-koin.io
[Compose]: https://www.jetbrains.com/lp/compose-multiplatform/
[MVI]: https://blog.stackademic.com/mvi-architecture-explained-on-android-e36ee66bceaa
[Voyager]: https://voyager.adriel.cafe/
[Compose Multiplatform Wizard]: https://terrakok.github.io/Compose-Multiplatform-Wizard/

