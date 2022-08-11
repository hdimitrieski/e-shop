# Config Service

[Configuration Service](https://cloud.spring.io/spring-cloud-config/) provides support for managing externalized 
configuration in a distributed system. This configuration store ideally, should be versioned under Git version control 
and can be modified at runtime. The server provides an API which the micro services can use to retrieve their 
configuration.

Currently, the configuration files are placed on classpath.

# Encryption and Decryption

The config server is configured to support encryption and decryption of property values. By default, the config server
will use symmetric cryptography, and if the config server is started with the "docker" profile, it will use asymmetric
cryptography.

Since the config server is supporting encryption and decryption of property values, you can use a public repository as storage
for sensitive data like usernames, passwords and secrets. Encrypted values are prefixed with the string {cipher}.

To use symmetric cryptography, you have to set the property "encrypt.key".

To use asymmetric cryptography, you can configure a keystore. You can use Java's keytool utility to create a key store:

    ~ keytool -genkeypair -alias configkey -keyalg RSA 
      -dname "CN=Web Server,C=MK,S=OH" -keypass cfg-password 
      -keystore keystore.jks -storepass secure-keystore-password

To encrypt a value, you can make a REST-call to "/encrypt" endpoint and pass the value that you want to encrypt in
the body.

    ~ curl -X POST --data-urlencode somepassword
      http://cfguser:cfgpassword@localhost:8888/encrypt

To decrypt a value, you can make a REST-call to "/decrypt" endpoint and pass the value that you want to decrypt in
the body.

    ~ curl -X POST --data-urlencode 0b614920e1774c088cfdf453a49dc1cf774272a50c2661cf304d21080041fd84 \
      http://cfguser:cfgpassword@localhost:8888/decrypt

# Running the Config Service
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -Dspring.profiles.active=native,dev -jar infrastructure/config/build/libs/config.jar

Required profiles:
1. **native** - to load the config files from the local classpath.

Optional profiles: 
1. **dev** - to enable encryption and decryption.
2. **elk** - to enable ELK logging.
3. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
4. **docker** - used when the service is run with docker.
