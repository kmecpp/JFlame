# JFlame
A lightweight, yet extremely fast JSON parser and generator

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
