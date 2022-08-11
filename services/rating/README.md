# Rating Service

The Rating service is a simple CQS microservice that manages product ratings, and it uses Postgres to store the ratings.

# Running the Rating Service
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -jar services/rating/build/libs/rating.jar

Optional profiles:
1. **elk** - to enable ELK logging.
2. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
3. **docker** - used when the service is run with docker.
