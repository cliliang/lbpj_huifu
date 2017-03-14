# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/chenliliang/Documents/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#指定代码的压缩级别
-optimizationpasses 5

#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

 #优化  不优化输入的类文件
-dontoptimize

-dontshrink

 #预校验
-dontpreverify

 #混淆时是否记录日志
-verbose

 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#保护注解
-keepattributes *Annotation*

-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keepattributes InnerClasses
-keepattributes EnclosingMethod
#可查看源文件和行号信息
-keepattributes SourceFile,LineNumberTable

# 保持哪些类不被混淆
-keep interface android.support.v4.app.** { *; }

-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keep public class * extends com.squareup.retrofit2.**
-keep public class * extends com.nostra13.universalimageloader.**
-keep public class * extends com.squareup.**
-keep public class * extends com.wang.avi.**
-keep public class * extends com.nineoldandroids.**
-keep public class * extends com.android.support.**
-keep public class * extends com.xiaomi.market.sdk.**
-keep public class * extends android.os.**
-keep public class * extends com.baluobo.app.model.BaseModel
-keep public class * extends com.baluobo.app.model.BaseParcelableModel
-keep public class * extends android.support.v4.app.Fragment

-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**
-keep public class com.tencent.** {*;}

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep public interface com.baluobo.user.adapter.OnRedeemButtonClickListener {*;}

#-libraryjars libs/xiaomi_sdk.jar

#忽略警告
-ignorewarning
-dontwarn org.apache.http.**
-dontwarn android.net.http.**
-dontwarn com.ut.mini.**
-dontwarn com.xiaomi.**
-dontwarn com.squareup.wire.**
-dontwarn android.webkit.**
-dontwarn retrofit2.**
-dontwarn retrofit2.Retrofit.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn uk.co.senab.photoview.**
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.nostra13.universalimageloader.**
-dontwarn com.handmark.pulltorefresh.library.**
-dontwarn retrofit2.converter.gson.GsonConverterFactory.**
-dontwarn com.baluobo.**
-dontwarn android.support.v4.**
-dontwarn android.os.**
-dontwarn android.support.**
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-dontwarn twitter4j.**
-dontwarn com.tencent.**
-dontwarn com.sina.**

-keep enum com.facebook.**

-keep class org.apache.commons.codec.** { *; }
-keep class org.apache.commons.logging.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.android.agoo.service.* {*;}
-keep class org.android.spdy.**{*;}
-keep class android.net.compatibility.** { *; }
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** {*;}
-keep class android.net.http.** { *; }
-keep class android.support.**{*;}
-keep class okhttp3.** {*;}
-keep class okio.** {*;}
-keep class uk.co.senab.photoview.**{*;}
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.squareup.retrofit2.**{ *; }
-keep class com.nostra13.universalimageloader.** { *; }
-keep class com.squareup.**{ *; }
-keep class com.wang.avi.**{ *; }
-keep class com.nineoldandroids.**{ *; }
-keep class com.android.support.**{ *; }
-keep class com.alipay.share.sdk.** {*;}
-keep class com.xiaomi.market.sdk.**{ *;}
-keep class com.squareup.wire.** {*;}
-keep class com.google.gson.**{*;}
-keep class com.handmark.pulltorefresh.library.** { *;}
-keep class com.baluobo.app.model.**{ *;}
-keep class com.baluobo.home.model.**{*;}
-keep class com.baluobo.product.bean.**{*;}
-keep class com.baluobo.user.model.**{*;}
-keep class com.baluobo.find.model.**{*;}
-keep class com.baluobo.app.flux.**{*;}
-keep class com.baluobo.home.stores.**{*;}
-keep class com.baluobo.product.stores**{*;}
-keep class com.baluobo.user.sotres.**{*;}
-keep class com.tencent.** {*;}
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
-keep class com.sina.** {*;}
-keep class com.linkedin.** { *; }
-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.umeng.scrshot.**
-keep class com.umeng.socialize.sensor.**
-keep class twitter4j.** { *; }
-keep class retrofit2.** { *; }
-keep class retrofit2.Retrofit.**{*;}
-keep class retrofit2.converter.gson.GsonConverterFactory.**{*;}

#保留一个完整的包
-keep class com.baluobo.common.** {
    *;
}

-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}

-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}


-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep public class * extends android.view.SurfaceView {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep public class com.baluobo.R$*{
    public static final int *;
}

-keep class com.umeng.message.protobuffer.* {
	 public <fields>;
     public <methods>;
}

-keep public class com.umeng.soexample.R$*{
     public static final int *;
 }
 -keep public class com.umeng.soexample.R$*{
     public static final int *;
 }

-keep class com.umeng.message.* {
	 public <fields>;
     public <methods>;
}

-keep class org.android.agoo.impl.* {
	 public <fields>;
     public <methods>;
}

-keep class com.baluobo.wxapi.WXEntryActivity{
}

-keepclassmembers class * {
    public static <fields>;
    private static <fields>;
    native <methods>;
    public <methods>;
    public static <methods>;
    public <init>(org.json.JSONObject);
}

-keepclasseswithmembernames class * {       # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.support.v7.app.AppCompatActivity { #保持类成员
    public void *(android.view.View);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements android.os.Parcelable {
     public static final ** CREATOR;
}

-keepnames class * implements java.io.Serializable

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class **.R$* {
    *;
}