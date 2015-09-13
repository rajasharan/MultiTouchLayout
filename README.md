# Android MultiTouch Layout
because its fun!!!

## Demo
![](/screencast.gif)

## Usage
```xml
<com.rajasharan.layout.MultiTouchLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/touch"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:enableTouches="true"
    app:randomizeColors="true"
    tools:context=".MainActivity"
    >

    <WebView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/webview"
        />

</com.rajasharan.layout.MultiTouchLayout>
```

## [License](/LICENSE)
    The MIT License (MIT)
