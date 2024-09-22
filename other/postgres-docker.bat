docker run -d --name postgres-16-books -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -v D:\DockerPostgresData\db_books:/var/lib/postgresql/data -p 5432:5432 postgres:16.4-alpine3.20

docker run -d --name postgres-16-library -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -v D:\DockerPostgresData\db_library:/var/lib/postgresql/data -p 5433:5432 postgres:16.4-alpine3.20

docker run -d --name postgres-16-sales -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -v D:\DockerPostgresData\db_sales:/var/lib/postgresql/data -p 5434:5432 postgres:16.4-alpine3.20

pause
