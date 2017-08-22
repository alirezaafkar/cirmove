# CirMove

![](demo.gif)

## Gradle

Add it in your root build.gradle at the end of repositories:
```
maven { url 'https://jitpack.io' }
```

Add the dependency
```
compile 'com.github.alirezaafkar:cirmove:v1.0.0'
```

## Usage
```xml
<com.alirezaafkar.cirmove.CirMove
            android:layout_width="12dp"
            android:layout_height="12dp"
            app:cirmove_color="@color/white"
            app:cirmove_duration="3000"
            app:cirmove_max_alpha="1"
            app:cirmove_min_alpha="1"
            app:cirmove_repeat_count="0"
            app:cirmove_repeat_mode="reverse"
            app:cirmove_start_delay="500"
            app:cirmove_x="130"
            app:cirmove_y="-40"/>
```
