# Utils [![GitHub CI Status](https://github.com/hijackermax/utils/workflows/CI/badge.svg)](https://github.com/hijackermax/utils/actions) [![Maven Central](https://img.shields.io/maven-central/v/com.hijackermax/utils)](https://search.maven.org/search?q=g:com.hijackermax%20AND%20a:utils)
A set of utils that can help in app development

### Prerequisites
* Java 11+

### License
Licensed under the Apache 2.0 License

#### Geographical utils
* Mercator(EPSG:3857) to WSG84 convertor
* WSG84 to Mercator(EPSG:3857) convertor
* Distance calculation between two WSG84 points 

#### Collections utils
* Null-safe methods to work with collections

#### Optional utils
* Optional providers for different situations

#### String Utils
* Null-safe methods to work with Strings

#### Object utils
* Methods that can help work with objects

#### Functional interfaces
* TriConsumer
* TriFunction
* TriPredicate
* QuadConsumer
* QuadFunction
* QuadPredicate

#### Wrappers
* Single
* Tuple
* Triple

### How to use it
Just add this to your **pom.xml**
```xml
<dependency>
    <groupId>com.hijackermax</groupId>
    <artifactId>utils</artifactId>
    <version>0.0.3</version>
</dependency>
```