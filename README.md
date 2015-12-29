# JFlame
A lightweight and extremely fast JSON parser and generator. This project aims to provide all the tools necessary to quickly and effectively create applications utilizing JSON. To achieve this, the API was designed to be as simple and intuitive as possible. 

Also give credit to ralfstx, as a lot of the ideas for the parser came from his [minimal-json](https://github.com/ralfstx/minimal-json).

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
