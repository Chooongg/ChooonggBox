# ViewBinding
-keep class * implements androidx.viewbinding.ViewBinding
-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
  public static * inflate(android.view.LayoutInflater);
  public static * inflate(android.view.LayoutInflater,android.view.ViewGroup,java.lang.Boolean);
}