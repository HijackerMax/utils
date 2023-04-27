# Utils [![GitHub CI Status](https://github.com/hijackermax/utils/workflows/CI/badge.svg)](https://github.com/hijackermax/utils/actions) [![Maven Central](https://img.shields.io/maven-central/v/com.hijackermax/utils)](https://search.maven.org/search?q=g:com.hijackermax%20AND%20a:utils) ![](https://img.shields.io/github/license/HijackerMax/utils)
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

#### Boolean utils
* Methods that can help work with booleans

#### Date utils
* Methods that can help work with dates

#### Math utils
* Methods that can help with computations

#### Random utils
* Methods that can provide some random values or randomize arrays

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

#### IO

##### TemporaryFile

* Closeable wrapper for temporary files that can be used with try-with-resources

#### Encoders / decoders

##### Base32

* Binary-to-text encoding. Based on charset [variant](https://www.crockford.com/base32.html) of Douglas Crockford,
  without check

##### Base58

* Binary-to-text encoding. Character set includes all uppercase and lowercase letters except for "0", "O", "I", and "l"
  to improve human readability.

##### Base85

* Binary-to-text encoding. Resulting data is ~25% bigger than source, while base64 is typically ~33%. Description can be
  found [here](https://en.wikipedia.org/wiki/Ascii85)

##### Base122

* Binary-to-text encoding inspired by [idea](https://blog.kevinalbs.com/base122)
  and [JS library](https://github.com/kevinAlbs/Base122) of Kevin Albertson. Resulting data is ~13% bigger than source,
  while base64 is typically ~33%

### How to use it

Just add this to your **pom.xml**

```xml

<dependency>
    <groupId>com.hijackermax</groupId>
  <artifactId>utils</artifactId>
  <version>0.0.7</version>
</dependency>
```