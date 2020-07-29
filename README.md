# Run Assignment Rule Programmatically
This is the starting code to run assignment rule programmatically.

## Getting Started

```
    
Downlaod the package.
```

### Prerequisites

Make sure you have an org to run this tool on. If you end up using this tool with a Salesforce Internal/Local org make sure to regenerate the jar files under resources folder. This because the jar files that are part of this repo were created with production WSDLs.

### Setup

Go to `src/main/resources`, open `config.properties`. This file will contain the org credentials the app will use.

You can configure the app to authenticate using username & password or sessionId.

Username/Password configuration.
```
username=Your username to login your salesforce account
password=Your password
endpoint=https://login.salesforce.com/
apiVersion=The API version you use.(Minimum 38.0)
sessionId=
```

SessionId configuration.
```
username=
password=
endpoint=https://login.salesforce.com/
apiVersion=The API version you use.(Minimum 38.0)
sessionId=00Dxxxxxxx001.blahblahblah
```
### Run mvn clean package
mvn clean package

### Running the app

1) Import the project to your favorite IDE (e.g. Eclipse). Open App.java and run it as a java application.

2) After compiling the project, you can run the app from command line by using:

To run assignment rule againt whole model: 
```
  java -jar target/tooling-api-tester-1.0-SNAPSHOT.jar -m [terriotryModelId]
```

To run assignment rule againt single territory: 
```
  java -jar target/tooling-api-tester-1.0-SNAPSHOT.jar -t [terriotryModelId] [territoryId]
```
