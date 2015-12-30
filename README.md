# JFlame
A lightweight and extremely fast JSON parser and generator. This project aims to provide all the tools necessary to quickly and effectively create applications utilizing JSON. The JFlame's main goal is simply raw power, it's designed to run as fast as possible and as a result, a lot of the ideas for the parser came from ralfstx's [minimal-json](https://github.com/ralfstx/minimal-json). The API was designed to be as simple and intuitive as possible but still include everything you need to create, and manipulate simple JSON.

##Examples:

###Manipulate JSON data
Parse a JSON string
```
JsonValue value = Json.parse(jsonString);
```

Generate a JSON string from an object
```
String json = jsonValue.toString();
```

Generate a formatted JSON string from an object
```
String jsonFormatted = jsonObj.getFormatted();
```

Customize your JSON formatting
```
String jsonFormatted = jsonObj.getFormatted("\t");
```

###Interacting with REST webservices

Read data
```
Json.readJsonFromUrl("http://example.com/users/joe/stats");
```

Post data
```
Json.postJsonToUrl("http://example.com/users/joe/stats/add", jsonObj);
```

## Maven
```
<repositories>
	<repository>
		<id>kmecpp-repo</id>
		<url>http://repo.kmecpp.com/maven</url>
	</repository>
</repositories>
	
<dependencies>
	<dependency>
		<groupId>com.kmecpp</groupId>
		<artifactId>jflame</artifactId>
		<version>1.0</version>
	</dependency>
</dependencies>
```
