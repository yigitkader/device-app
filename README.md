# Getting Started

### Project use Postgresql database.

### You dont need to install database manually, because project use containerization and we are using Makefile for running app.

	"Available commands:"
	make build      - Build Docker images"
	make up         - Start all services"
	make down       - Stop all services"
	make restart    - Restart all services"
	make logs       - Show logs"
	make clean      - Stop services and remove volumes"
	make test       - Run tests"
	make db-only    - Start only PostgreSQL"
	make app-only   - Run app locally with Docker DB"


- Simply run make up command in terminal. and wait until all services will be started.
### SWAGGER
Swagger UI: http://localhost:8080/swagger-ui.html

## For future improvements:

- Migration system (Flyway or etc.)
- Increase environments