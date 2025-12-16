.PHONY: help build up down restart logs clean test db-only app-only

help:
	@echo "Available commands:"
	@echo "  make build      - Build Docker images"
	@echo "  make up         - Start all services"
	@echo "  make down       - Stop all services"
	@echo "  make restart    - Restart all services"
	@echo "  make logs       - Show logs"
	@echo "  make clean      - Stop services and remove volumes"
	@echo "  make test       - Run tests"
	@echo "  make db-only    - Start only PostgreSQL"
	@echo "  make app-only   - Run app locally with Docker DB"

build:
	docker-compose build

up:
	docker-compose up --build -d

down:
	docker-compose down

restart: down up

logs:
	docker-compose logs -f

logs-app:
	docker-compose logs -f app

logs-db:
	docker-compose logs -f postgres

clean:
	docker-compose down -v
	@echo "All volumes removed. Database data deleted."

test:
	./mvnw clean test

db-only:
	docker-compose up postgres -d
	@echo "PostgreSQL is running on localhost:5432"

app-only: db-only
	@echo "Starting Spring Boot application..."
	./mvnw spring-boot:run

status:
	docker-compose ps

db-shell:
	docker-compose exec postgres psql -U device_user -d device_db