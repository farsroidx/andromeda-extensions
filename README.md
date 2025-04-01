# Andromeda-Extensions ![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white) ![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)

A library for using pre-built codes

> ![GitHub repo size](https://img.shields.io/github/repo-size/farsroidx/andromeda-extensions)

### Installation:

#### 1. Add Jitpack Maven:

##### in `settings.gradle`:
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        .
        .
        ------> maven { url 'https://jitpack.io' }
    }
}
```

##### in `settings.gradle.kts`:
```kotlin
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        .
        .
        ------> maven(url = "https://jitpack.io")
    }
}
```

#### 2. Copy the following line in section `dependencies` in file `build.gradle` of module `app` and replace it with `LATEST_VERSION` according to the latest version in the repository:

### LATEST_VERSION: [![](https://jitpack.io/v/farsroidx/andromeda-extensions.svg)](https://jitpack.io/#farsroidx/andromeda-extensions)

##### in `build.gradle`:
```groovy
dependencies {
    implementation 'com.github.farsroidx:andromeda-extensions:🔝LATEST_VERSION🔝'
}
```

##### in `build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.github.farsroidx:andromeda-extensions:🔝LATEST_VERSION🔝")
}
```

#### 3. Adding the following rules to use R8 and proguard in `proguard-rules.pro`:

```pro
# Keep all classes and interfaces in ir.farsroidx.m31 package
-keep class ir.farsroidx.m31.** { *; }

# Keep all class members (fields and methods) in ir.farsroidx.m31 package
-keepclassmembers class ir.farsroidx.m31.** { *; }

# Keep all public methods in ir.farsroidx.m31 package
-keepclassmembers class ir.farsroidx.m31.** {
    public <methods>;
    public <fields>;
}

# Keep all extension functions in ir.farsroidx.m31 package
-keepclassmembers class ir.farsroidx.m31.** {
    public <methods>;
    public <fields>;
}

# Keep property delegates
-keep class kotlin.Lazy { *; }
-keep class kotlin.LazyKt { *; }
-keep class kotlin.reflect.KProperty { *; }
-keep class kotlin.reflect.KProperty0 { *; }
-keep class kotlin.reflect.KProperty1 { *; }
-keep class kotlin.reflect.KProperty2 { *; }

# Prevent warnings for java.lang.invoke.StringConcatFactory
-dontwarn java.lang.invoke.StringConcatFactory
```

[![Ask Me Anything !](https://img.shields.io/badge/Ask%20me-anything-1abc9c.svg)](https://github.com/farsroidx)