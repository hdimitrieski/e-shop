# Image Service

This service is only used for uploading catalog item images to [Minio](https://github.com/minio/minio). Only the admin
user is allowed to upload images when editing catalog items.

All catalog item images are stored in Mino's **catalog-images** bucket. The images can be accessed via 
[imgproxy](https://imgproxy.net/) (ex. http://localhost:8887/insecure/fit/300/200/no/0/plain/s3://catalog-images/adidas-shoes-1.png).

# Running the Image Service
The best way to run the service is with IDE like IntelliJ IDEA or Eclipse. Alternatively, after you build the service,
you can run it with the following command:

    ~ java -jar infrastructure/image-service/build/libs/image-service.jar

Optional profiles:
1. **dev** - to upload test images on start-up.
2. **elk** - to enable ELK logging.
3. **distributed-tracing** - to enable distributed tracing with Sleuth and Zipking.
4. **docker** - used when the service is run with docker.
