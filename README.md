<h1 align="center">  Carousel Recyclerview </h1>

<p align="center">
Create carousel effect in recyclerview with the CarouselRecyclerview in a simple way.
</p>
</br>
<p align="center">
  <a href="https://github.com/sparrow007/CarouselRecyclerview/actions"><img alt="Build Status"src="https://github.com/sparrow007/CarouselRecyclerview/workflows/Android%20CI/badge.svg"/></a> 
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="http://developer.android.com/index.html"><img alt="License" src="https://img.shields.io/badge/platform-android-green.svg"/></a>
    <a href="https://android-arsenal.com/api?level=16"><img alt="License" src="https://img.shields.io/badge/API-16%2B-brightgreen.svg"/></a>
    <a href="https://mailchi.mp/kotlinweekly/kotlin-weekly-242"><img alt="AndroidWeekly" src="https://skydoves.github.io/badges/kotlin-weekly.svg"/></a>
    <a href="https://androidweekly.net/issues/issue-468"><image src="https://androidweekly.net/issues/issue-468/badge"/></a>

</p>
</br>

![Layout manager](./preview/lm.jpg)


## Including in your project

[![Maven Central with version prefix filter](https://img.shields.io/maven-central/v/com.github.sparrow007/carouselrecyclerview/1.2.5)](https://search.maven.org/artifact/com.github.sparrow007/carouselrecyclerview/1.2.5/aar/)
[![CarouselRecyclerview](https://jitpack.io/v/sparrow007/CarouselRecyclerview.svg)](https://jitpack.io/#sparrow07/CarouselRecyclerview)

### Gradle 
Add below codes to your **root** `build.gradle` file (not your module build.gradle file).
```Gradle
allprojects {
    repositories {
          mavenCentral()
    }
}
```
And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
   implementation 'com.github.sparrow007:carouselrecyclerview:1.2.5'
}
```

## Usage

### Basic Example for Kotlin
Here is a basic example of implementing carousel recyclerview in koltin files (activity or fragment) with attribute.

```kotlin
  binding.carouselRecyclerview.adapter = adapter
  binding.carouselRecyclerview.apply {
   set3DItem(true)
   setAlpha(true)
   setInfinite(true)
  }
```
### Basic Example for XML
Here is a basic example of implementing carousel recyclerview in layout xml.

```xml
<com.jackandphantom.carouselrecyclerview.CarouselRecyclerview
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:id="@+id/carouselRecyclerview"/>
```

Property infinite and 3D on Item enabled |Property infinite and alpha on Item enabled |
| :---------------: | :---------------: | 
| <img src="./preview/3ditem.gif" align="center" width="70%"/> | <img src="./preview/alpha.gif" align="center" width="70%"/> |

## API Methods

Method | Description | Default
---|--- |---
  fun set3DItem(is3DItem: Boolean) | Make the view tilt according to their position, middle position does not tilt. | false
  fun setInfinite(isInfinite: Boolean) | Create the loop of the given view means there is no start or end, but provided position in the interface will be correct. | false
  fun setFlat(isFlat: Boolean) | Make the flat layout in the layout manager of the reyclerview | false
  fun setAlpha(isAlpha: Boolean) | Set the alpha for each item depends on the position in the layout manager | false
  fun setIntervalRatio(ratio: Float) | Set the interval ratio which is gap between items (views) in layout manager | 0.5f (value of gap, it should in range (0.4f - 1f))
  fun getCarouselLayoutManager(): CarouselLayoutManager | Get the carousel layout manager instance | 
  fun getSelectedPosition(): Int | Get selected position from the layout manager | center view Position
  fun setIsScrollingEnabled(isScrollingEnabled: Boolean) | Set scrolling disabled or enabled | true

  ## API Methods Usage
  ```Kotlin
  val carouselRecyclerview = findViewById<CarouselRecyclerview>(R.id.recycler)
        carouselRecyclerview.adapter = adapter
        carouselRecyclerview.set3DItem(true)
        carouselRecyclerview.setInfinite(true)
        carouselRecyclerview.setAlpha(true)
        carouselRecyclerview.setFlat(true)
        carouselRecyclerview.setIsScrollingEnabled(true)

        val carouselLayoutManager = carouselRecyclerview.getCarouselLayoutManager()
        val currentlyCenterPosition = carouselRecyclerview.getSelectedPosition()
  
  ```
  ## Item Position Listener
  You can listen to the position whenever the scroll happens you will get notified about the position, following are codes for listener

  ```Kotlin
   carouselRecyclerview.setItemSelectListener(object : OnSelected {
            override fun onItemSelected(position: Int) {
                //Cente item
            }
        })
  
  ```

## Reflection ImageView
<img src="./preview/reflectionImageVIew.jpg" align="right" width="22%"/>
You see in the demo that there is a mirror image (reflection imageview), for this i already created custom imageview for this.


Use ReflectionImageView in xml layout and provide src

```xml
 <com.jackandphantom.carouselrecyclerview.view.ReflectionImageView
     android:layout_width="120dp"
     android:layout_height="120dp"
     android:scaleType="fitXY"
     android:src="@drawable/hacker"
    />
```

<Br>

## Reflection Layout

<img src= "https://user-images.githubusercontent.com/7668602/114284225-14244980-9a6c-11eb-8e49-aa7b191a6d1d.png" align="right" width="22%"/>

Now you can show reflection in more efficient way and 3x faster than ReflectionImageView see the codes below

```XML
<?xml version="1.0" encoding="utf-8"?>
<com.jackandphantom.carouselrecyclerview.view.ReflectionViewContainer
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    xmlns:android="http://schemas.android.com/apk/res/android"
    app:reflect_relativeDepth="0.5"
    app:reflect_gap="0dp"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    >
    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/guypro"
        android:scaleType="fitXY"
        android:id="@+id/image" />
</com.jackandphantom.carouselrecyclerview.view.ReflectionViewContainer>

```
<Br>
  
### Notes about Reflection imageview
I would recommend you to use image loading library like Glide for loading image in reflection image for better performance
<BR>

### What's New Version 1.2.5 🎉🎉  
Fix Adapter notifyItemChanged and notifyItemRemoved  
Bugs Fix (Empty List swipe)<br>
Add setIsScrollingEnabled <br>
Remove hardcoded alpha value  <br>
Fix divide by zero arithmetic exception <br>
  
### What's New Version 1.2.1  
Bugs Fix (ScrollToPosition)

### Version 1.2.0
Adding reflection container

### Version 1.1.0 
Adding Support for orientation changes

## Contribute 🤝
If you like the project and somehow wants to contribute, you are welcome to contribute by either submitting issues, refactor, pull request Thankyou.  
  
## Contributors ✨

Thanks go to these wonderful people :  

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
  <td align="center"><a href="https://github.com/snehilrx"><img src="https://avatars.githubusercontent.com/u/7668602?v=4" width="100px;" alt=""/><br /><sub><b>Snehil</b></sub></a><br /><a href="#Contributor-Snehil" title="Contributor">💻</a></td>
      <td align="center"><a href="https://github.com/mattiasrosberg"><img src="https://avatars.githubusercontent.com/u/261872?v=4" width="100px;" alt=""/><br /><sub><b>Mattias Rosberg
</b></sub></a><br /><a href="#Contributor-Mattias" title="Contributor">💻</a></td>
          <td align="center"><a href="https://github.com/akashmi"><img src="https://avatars.githubusercontent.com/u/11882168?v=4" width="100px;" alt=""/><br /><sub><b>AKASH PATEL</b></sub></a><br /><a href="#Contributor-AKASH" title="Contributor">💻</a></td>
  </tr>
  </table>  
 

## Find this repository useful? ❤️
Support it by joining __[stargazers](https://github.com/sparrow007/CarouselRecyclerview/stargazers)__ for this repository. :star: <br>
 And __[follow](https://github.com/sparrow007)__  me for next creation 🤩

## License
```xml
Copyright 2021 Sparrow007 (Ankit kumar)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
