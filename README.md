# ChooonggBox 
[![](https://jitpack.io/v/Chooongg/ChooonggBox.svg)](https://jitpack.io/#Chooongg/ChooonggBox)

使用 Kotlin 开发，基于 Material You 风格的 Android 快速开发大礼包

## 下载

```kotlin
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

- 集成全部内容

```kotlin
dependencies {
    implementation "com.github.Chooongg.ChooonggBox:box:$boxVersion"
}
```

- 集成部分内容

```kotlin
dependencies {
    implementation "com.github.Chooongg.ChooonggBox:core:$boxVersion"
    implementation "com.github.Chooongg.ChooonggBox:http:$boxVersion"
    implementation "com.github.Chooongg.ChooonggBox:statusLayout:$boxVersion"
    implementation "com.github.Chooongg.ChooonggBox:echarts:$boxVersion"
    implementation "com.github.Chooongg.ChooonggBox:utils:$boxVersion"
}
```
