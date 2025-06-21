# KMPCode-coordinates Library Documentation

## Introduction

`KMPCode-coordinates` is a Kotlin Multiplatform library that helps developers more conveniently handle coordinates, and more.

## Resource Links

- **时间文档**：[datetime](https://github.com/cjfsss/KMPCode/blob/main/datetime)
- **工具文档**：[utils](https://github.com/cjfsss/KMPCode/blob/main/utils)
- **经纬度文档**：[coordinates](https://github.com/cjfsss/KMPCode/blob/main/coordinates)
- **GitHub 仓库**：[KMPCode](https://github.com/cjfsss/KMPCode)

## Compatibility

The `minSdk` is 26.

## Dependency Introduction

Add the following dependencies to your project:

```kotlin

plugins {
    kotlin("plugin.serialization") version "2.1.0"
}

commonMain.dependencies {
    // datetime
    implementation("io.github.cjfsss:hos-datetime:1.0.2")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")

    // utils
    implementation("io.github.cjfsss:hos-utils:1.0.0")

    // coordinates
    implementation("io.github.cjfsss:hos-coordinates:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
}

```

## Test Examples


- **DateTime 测试**：[DateTimeTest](https://github.com/cjfsss/KMPCode/blob/master/datetime/src/commonTest/kotlin/DateTimeTest.kt)
- **DateTimeFormats 测试**：[DateTimeFormatsTest](https://github.com/cjfsss/KMPCode/blob/master/datetime/src/commonTest/kotlin/DateTimeFormatsTest.kt)
- **LocalDateTimePlus 测试**：[LocalDateTimePlusTest](https://github.com/cjfsss/KMPCode/blob/master/datetime/src/commonTest/kotlin/LocalDateTimePlusTest.kt)
- **AirUtils 测试**：[AirUtilsTest](https://github.com/cjfsss/KMPCode/blob/master/utils/src/commonTest/kotlin/AirUtilsTest.kt)
- **ColorUtils 测试**：[ColorUtilsTest](https://github.com/cjfsss/KMPCode/blob/master/utils/src/commonTest/kotlin/ColorUtilsTest.kt)
- **FileUtils 测试**：[FileUtilsTest](https://github.com/cjfsss/KMPCode/blob/master/utils/src/commonTest/kotlin/FileUtilsTest.kt)
- **AnyExt 测试**：[AnyTest](https://github.com/cjfsss/KMPCode/blob/master/utils/src/commonTest/kotlin/AnyTest.kt)
- **CollectionExt 测试**：[CollectionTest](https://github.com/cjfsss/KMPCode/blob/master/utils/src/commonTest/kotlin/CollectionTest.kt)
- **IDExt 测试**：[IDTest](https://github.com/cjfsss/KMPCode/blob/master/utils/src/commonTest/kotlin/IDTest.kt)
- **IFExt 测试**：[IFTest](https://github.com/cjfsss/KMPCode/blob/master/utils/src/commonTest/kotlin/IFTest.kt)
- **MapExt 测试**：[MapTest](https://github.com/cjfsss/KMPCode/blob/master/utils/src/commonTest/kotlin/MapTest.kt)
- **RegexUtils 测试**：[RegexUtilsTest](https://github.com/cjfsss/KMPCode/blob/master/utils/src/commonTest/kotlin/RegexUtilsTest.kt)
- **StringExt 测试**：[StringTest](https://github.com/cjfsss/KMPCode/blob/master/utils/src/commonTest/kotlin/StringTest.kt)
- **UrlUtils 测试**：[UrlUtilsTest](https://github.com/cjfsss/KMPCode/blob/master/utils/src/commonTest/kotlin/UrlUtilsTest.kt)
- **AreaUnit 测试**：[AreaUnitTest](https://github.com/cjfsss/KMPCode/blob/master/coordinates/src/commonTest/kotlin/AreaUnitTest.kt)
- **DistanceUnit 测试**：[DistanceUnitTest](https://github.com/cjfsss/KMPCode/blob/master/coordinates/src/commonTest/kotlin/DistanceUnitTest.kt)
- **WeightUnit 测试**：[WeightUnitTest](https://github.com/cjfsss/KMPCode/blob/master/coordinates/src/commonTest/kotlin/WeightUnitTest.kt)
- **GeoUtils 测试**：[GeoUtilsTest](https://github.com/cjfsss/KMPCode/blob/master/coordinates/src/commonTest/kotlin/GeoUtilsTest.kt)
- **JsonConverter 测试**：[JsonConverterTest](https://github.com/cjfsss/KMPCode/blob/master/coordinates/src/commonTest/kotlin/JsonConverterTest.kt)
- **LatLngBounds 测试**：[LatLngBoundsTest](https://github.com/cjfsss/KMPCode/blob/master/coordinates/src/commonTest/kotlin/LatLngBoundsTest.kt)
- **LatLngCollection 测试**：[LatLngCollectionTest](https://github.com/cjfsss/KMPCode/blob/master/coordinates/src/commonTest/kotlin/LatLngCollectionTest.kt)
- **LatLng 测试**：[LatLngTest](https://github.com/cjfsss/KMPCode/blob/master/coordinates/src/commonTest/kotlin/LatLngTest.kt)
- **LocationData 测试**：[LocationDataTest](https://github.com/cjfsss/KMPCode/blob/master/coordinates/src/commonTest/kotlin/LocationDataTest.kt)

---

## License

```
Copyright 2025

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