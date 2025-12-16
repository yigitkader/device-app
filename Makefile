.PHONY: up up-prod down dev test

up:
	docker-compose up --build -d

up-prod:
	docker-compose -f docker-compose.prod.yml up --build -d

dev:
	docker-compose up postgres -d
	./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

test:
	./mvnw clean test -Dspring.profiles.active=test

test-prod:
	./mvnw clean test -Dspring.profiles.active=prod

down:
	docker-compose down

down-prod:
	docker-compose -f docker-compose.prod.yml down