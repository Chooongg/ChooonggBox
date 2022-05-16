# ViewBinding
-keep class * implements androidx.viewbinding.ViewBinding
-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
  public static * inflate(android.view.LayoutInflater);
  public static * inflate(android.view.LayoutInflater,android.view.ViewGroup,java.lang.Boolean);
}

-keep class com.chooongg.core.activity.BoxActivity
-keep class com.chooongg.core.activity.BoxBindingActivity
-keep class * extends com.chooongg.core.activity.BoxActivity
-keep class * extends com.chooongg.core.activity.BoxBindingActivity