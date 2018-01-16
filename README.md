# Init Elasticsearch and Kibana

This program initializes the required Elasticsearch indices and populates Kibana dashboards.

It is intended to be used in more restricted environments where X-Pack security is enabled and the agents should not be
allowed to create index patterns and dashboards.

If you are not operating in such an environment, you don't need this program.

## Usage

### 1. Clone this repo

```
git clone https://github.com/stagemonitor/stagemonitor-elasticsearch-init.git
cd stagemonitor-elasticsearch-init
```

### 2. Adjust stagemonitor version

Open `gradle.properties` and set the property `stagemonitorVersion` to the version of stagemonitor you are actually using.
Note that the minimum Version is 0.87.0.

### 3. Adjust optional plug-ins

Open `build.gradle` and include the optional plug-ins you are using.

### 4. Make sure Elasticsearch and Kibana is started

Open the Kibana status page in the browser (default ist [http://localhost:5601/status](http://localhost:5601/status)) 
and make sure the status is green.

### 5. Execute initialization program 

Unix:
```
./gradlew run -Purl=http://user:pwd@localhost:9200
```

Windows:
```
gradlew.bat run -Purl=http://user:pwd@localhost:9200

```

Adjust the url argument according to your environment.

## Q&A

Q: I can't execute the script because it wants to download artifacts from maven central and that's not permitted because of
firewall rules. 

A: Build the distribution on a host with unrestricted access to the internet: Execute `./gradlew assembleDist`.
In the directory `build/distributions` you will find a `zip` and `tar` file which contain all dependencies.
Extract either the `zip` or `tar` file on the target host and execute `bin/stagemonitor-elasticsearch-init` (Unix) or `bin/stagemonitor-elasticsearch-init.bat` (Windows)

Q: I don't want to specify the Elasticsearch url as a command line argument as it would leak the credentials in the console history

A: Add the url in `stagemonitor-elasticsearch-init/gradle.properties` or `~/.gradle/gradle.properties`