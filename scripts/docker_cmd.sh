# download postgres test img
docker run --name some-postgres -e POSTGRES_PASSWORD=pass -p 5433:5432 -d postgres
