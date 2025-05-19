# Products Service

## Docker

Create docker image

```shell
./gradlew bootBuildImage --imageName=mspr4-2025/customers-service
```

Deploy service with database

```shell
docker compose up
```