# awesome-langpack


[![](https://jitpack.io/v/publicLibs/awesome-langpack.svg)](https://jitpack.io/#publicLibs/awesome-langpack)

## Usage:
see [demo code](src/main/java/com/github/publiclibs/langpack/demo/LangpackDemo.java) 

```java
final var key = "HELLO";

//We get the value for the jvm language. 
//If there is no value, we try to take the value for the fallback language
final var localJvmLangData = Langpack.getData(key);

//We get the value for the German language
//If there is no value, we try to take the value for the fallback language
final var dataStringForExistLang = Langpack.getData(key, "de");

//We get the value for the Italian language
//If there is no value, we try to take the value for the fallback language
//if not, we get an error
final var dataStringForNoExistLang = Langpack.getData(key, "it");

System.out.println(localJvmLangData);
System.out.println(dataStringForExistLang);
System.out.println(dataStringForNoExistLang);
```

## Use as maven dependency:

```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```

```xml
<dependency>
	<groupId>com.github.publicLibs</groupId>
	<artifactId>awesome-langpack</artifactId>
	<version>main-SNAPSHOT</version>
</dependency>
```
