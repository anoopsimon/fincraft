# Variables
COMPOSE=docker-compose
APP_NAME=app
TEST_RUNNER=test-runner
DB_NAME=h2-db

# Targets
.PHONY: build up down restart logs clean db-console test wait help

# Build the Docker images
build:
	@echo "Building Docker Compose services..."
	$(COMPOSE) build

# Start the application
up:
	@echo "Starting Docker Compose services..."
	$(COMPOSE) up -d $(APP_NAME) $(DB_NAME)

# Stop the application
down:
	@echo "Stopping Docker Compose services..."
	$(COMPOSE) down

# Restart the application
restart: down up
	@echo "Restarted services successfully."

# View logs
logs:
	@echo "Showing logs for the application..."
	$(COMPOSE) logs -f $(APP_NAME)

# Remove containers, networks, and volumes
clean:
	@echo "Cleaning up Docker Compose services..."
	$(COMPOSE) down -v --remove-orphans

# Access the H2 database console
db-console:
	@echo "Accessing the H2 database console..."
	@echo "Open your browser and navigate to http://localhost:8081"

# Wait for the API container to be ready
wait:
	@echo "Waiting for the API container to be ready..."
	@powershell -Command " \
		$retryCount = 0; \
		while ($retryCount -lt 30) { \
			try { \
				$response = Invoke-WebRequest -Uri http://localhost:8080/actuator/health -UseBasicParsing; \
				if ($response.StatusCode -eq 200 -and $response.Content -match '\"status\":\"UP\"') { \
					Write-Output 'API is ready!'; \
					exit 0; \
				} \
			} catch { \
				Start-Sleep -Seconds 5; \
			} \
			$retryCount++; \
		} \
		Write-Error 'API container did not become healthy in time'; \
		exit 1;"

# Run tests against the running API container
test: build up
	@echo "Running integration tests using the test-runner container..."
	$(COMPOSE) run --rm -e TEST_API_URL=$(TEST_API_URL) $(TEST_RUNNER)

# Show help
help:
	@echo "Available commands:"
	@echo "  build       Build the application container"
	@echo "  up          Start the application"
	@echo "  down        Stop the application"
	@echo "  restart     Restart the application"
	@echo "  logs        Show logs for the application"
	@echo "  clean       Remove containers, networks, and volumes"
	@echo "  db-console  Access the H2 database console"
	@echo "  wait        Wait for the API container to be healthy"
	@echo "  test        Run integration tests using the test-runner container"
