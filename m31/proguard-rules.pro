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