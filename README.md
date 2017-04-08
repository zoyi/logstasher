# Logstasher
Logstasher is _high-performance_ client library for [Logstash](https://www.elastic.co/kr/products/logstash) for Java programming language.

_Note_: This project is under highly development :]

## Key Features
* High performance
* Thread-safe
* Light-weight
* Easy-to-use

## Use
The code below presents how easy to use Logstasher for logging.
```java
// Create instance easyly with factory method
Logstasher logstasher = Logstashers.newLogstasher("tcp");

// Let`s make some logs
Map<String, Object> logData = new HashMap<>();
logData.put("something", "hot!");
logData.put("latitude", 37.500372);
logData.put("longitude", 127.0325813);
logData.put("exhausted", false);
logData.put("@timestamp", LocalDateTime.now().toString());

// Next, put data to Logstasher.
// All of complex stuffs like making connection, sending packets.. are in the box
logstasher.put(logData);

// It`s done!
// All logs were may be sent already :]
```

## Types
```java
// Any types are permitted
Map<String, Object> logData = new HashMap<>();
logData.put("something", "hot!");
logData.put("latitude", 37.500372);
logData.put("longitude", 127.0325813);
logData.put("exhausted", false);
logData.put("popularity", Integer.MAX_VALUE);

// Custom @timestamp
logData.put("@timestamp", LocalDateTime.now().toString());
```

## Configuration
```java
Configuration configuration = new ConfigurationImpl();
configuration.set("connectionTimeout", 5000);
configuration.set("reconnectAttempts", 3);
configuration.set("host", "localhost");
configuration.set("port", 5432);
configuration.set("popSize", 10);
configuration.set("maxTraverses", 20);

Logstasher logstasher = Logstashers.newLogstasher("tcp", configuration);
```

## Lisence
This project is under MIT lisence.
