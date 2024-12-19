# Variables
COMPOSE=docker-compose
APP_NAME=fincraft-app
DB_NAME=h2-db

# Targets
.PHONY: build up down restart logs clean db-console help

# Build the application container
build:
	@echo "Building Docker Compose services..."
	$(COMPOSE) build

# Start the application
up:
	@echo "Starting Docker Compose services..."
	$(COMPOSE) up -d

# Stop the application
down:
	@echo "Stopping Docker Compose services..."
	$(COMPOSE) down

# Restart the application
restart: down up
	@echo "Restarted services successfully."

# View logs
logs:
	@echo "Showing logs for all services..."
	$(COMPOSE) logs -f

# Remove containers, networks, and volumes
clean:
	@echo "Cleaning up Docker Compose services..."
	$(COMPOSE) down -v --remove-orphans

# Access the H2 database console
db-console:
	@echo "Accessing the H2 database console..."
	@echo "Open your browser and navigate to http://localhost:8081"

# Show help
help:
	@echo "Available commands:"
	@echo "  build       Build the application container"
	@echo "  up          Start the application"
	@echo "  down        Stop the application"
	@echo "  restart     Restart the application"
	@echo "  logs        Show logs for all services"
	@echo "  clean       Remove containers, networks, and volumes"
	@echo "  db-console  Access the H2 database console"
