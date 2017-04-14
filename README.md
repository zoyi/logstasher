# Logstasher

[![](https://jitpack.io/v/zoyi/logstasher.svg)](https://jitpack.io/#zoyi/logstasher)

Logstasher is _high-performance_ client library for [Logstash](https://www.elastic.co/kr/products/logstash) for Java programming language.

_Note_: This project is under highly development :]

## Key Features
* High performance
* Thread-safe
* Light-weight
* Easy-to-use

## Requirements
* Supports **Java8** or higher

## Download
### Via Gradle
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
  
dependencies {
  compile ('com.github.zoyi:logstasher:v0.1.2') {
    exclude group: 'org.apache.logging.log4j'
  }
}
```

### Via Maven
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.zoyi</groupId>
  <artifactId>logstasher</artifactId>
  <version>v0.1.1</version>
</dependency>
```

## Use
The code below presents how easy to use Logstasher for logging.
```java
// Create instance easyly with factory method
Logstasher logstasher = Logstashers.create("tcp");

// Let`s make some logs
Message logMessage = new JsonMessage();
logMessage.put("something", "hot!");
logMessage.put("latitude", 37.500372);
logMessage.put("longitude", 127.0325813);
logMessage.put("exhausted", false);

// Next, put data to Logstasher.
// All of complex stuffs like making connection, sending packets.. are in the box
logstasher.put(logData);

// It`s done!
// All logs were may be sent already :]
```

### Timestamp
```java
// @timestamp field already set, but you can override this
Message logMessage = new JsonMessage();
logMessage.setTimestamp(Instant.now());

// or
logMessage.setTimestamp(LocalDateTime.now());
```

### Data Types
```java
// Any types are permitted
Map<String, Object> logData = new HashMap<>();
logData.put("something", "hot!");
logData.put("latitude", 37.500372);
logData.put("longitude", 127.0325813);
logData.put("exhausted", false);
logData.put("popularity", Integer.MAX_VALUE);
```

### Configuration
```java
Configuration configuration = new ConfigurationImpl();
configuration.set("connectionTimeout", 5000);
configuration.set("reconnectAttempts", 3);
configuration.set("host", "localhost");
configuration.set("port", 5432);
configuration.set("popSize", 10);
configuration.set("maxTraverses", 20);

Logstasher logstasher = Logstashers.create("tcp", configuration);
```

## CLI Client
This library contains executable CLI client.

### Build Client
```sh
$ gradle assembleDist
```
Then following distributions generated:
```
build
├── classes
│   ├── main
│   ...
├── distributions
│   ├── logstasher-VERSION.tar
│   └── logstasher-VERSION.zip
...
```
Unzip distribution and execute:
```sh
$ cd logstasher-VERSION
$ ./bin/logstasher --host=localhost --port=12340 --timezone=UTC
```
Following options available:
* `--host`: Host to connect. Default is `localhost`.
* `--port`: Port of host. Default is `12340`.
* `--timezone`: Timezone of `@timestamp` field. Default is _system_default_ value.

## Lisence
This project is under MIT lisence.
