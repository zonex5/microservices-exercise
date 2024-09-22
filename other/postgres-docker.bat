docker run -d --name postgres-16 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -v D:\PostgresData:/var/lib/postgresql/data -p 5432:5432 postgres:16.4-alpine3.20

pause
