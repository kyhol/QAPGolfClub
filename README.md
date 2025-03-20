# Golf Club API

This project is a RESTful API for a golf club tournament and membership system. It allows you to manage club members and tournaments, including the ability to add members to tournaments.

## Features

- CRUD operations for Members and Tournaments
- Search functionality for Members and Tournaments
- Management of tournament participants
- Dockerized application for easy setup

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- MySQL
- Docker
- Maven

## API Endpoints

### Member Endpoints

- `GET /api/members` - Get all members
- `GET /api/members/{id}` - Get a member by ID
- `POST /api/members` - Create a new member
- `PUT /api/members/{id}` - Update a member
- `DELETE /api/members/{id}` - Delete a member

#### Member Search Endpoints

- `GET /api/members/search` - Search for members with optional parameters:
    - `name` - Search by member name (partial match)
    - `phoneNumber` - Search by phone number (partial match)
    - `membershipType` - Search by membership duration
    - `tournamentStartDate` - Search members participating in tournaments starting on this date (format: YYYY-MM-DD)

#### Member-Tournament Relationship Endpoints

- `POST /api/members/{memberId}/tournaments/{tournamentId}` - Add a member to a tournament
- `DELETE /api/members/{memberId}/tournaments/{tournamentId}` - Remove a member from a tournament

### Tournament Endpoints

- `GET /api/tournaments` - Get all tournaments
- `GET /api/tournaments/{id}` - Get a tournament by ID
- `POST /api/tournaments` - Create a new tournament
- `PUT /api/tournaments/{id}` - Update a tournament
- `DELETE /api/tournaments/{id}` - Delete a tournament

#### Tournament Search Endpoints

- `GET /api/tournaments/search` - Search for tournaments with optional parameters:
    - `startDate` - Search by start date (format: YYYY-MM-DD)
    - `location` - Search by location (partial match)

#### Tournament Participants Endpoint

- `GET /api/tournaments/{id}/members` - Get all members participating in a tournament

## Running the Application with Docker

### Prerequisites

- Docker installed on your machine
- Java 17 and Maven for building the application

### Steps to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/golf-club-api.git
   cd golf-club-api
   ```

2. Build the application with Maven:
   ```bash
   mvn clean package
   ```

3. Build the Docker image:
   ```bash
   docker build -t golf-club-api .
   ```

4. Start the MySQL container (using port 3307 to avoid conflicts with local MySQL):
   ```bash
   docker run -d --name mysql-db -p 3307:3306 -e MYSQL_DATABASE=golf_club -e MYSQL_USER=golfuser -e MYSQL_PASSWORD=golfpass -e MYSQL_ROOT_PASSWORD=rootpassword mysql:8.0
   ```

5. Start the application container:
   ```bash
   docker run -d --name golf-app -p 8081:8081 -e "SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3307/golf_club?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" -e SPRING_DATASOURCE_USERNAME=golfuser -e SPRING_DATASOURCE_PASSWORD=golfpass golf-club-api
   ```

6. The application will be available at `http://localhost:8081`

7. To stop and remove the containers:
   ```bash
   docker stop golf-app mysql-db
   docker rm golf-app mysql-db
   ```

### Data Persistence

Data is stored in the MySQL container. Note that by default, data will be lost when the container is removed unless you add volume mapping:

```bash
docker run -d --name mysql-db -p 3307:3306 -e MYSQL_DATABASE=golf_club -e MYSQL_USER=golfuser -e MYSQL_PASSWORD=golfpass -e MYSQL_ROOT_PASSWORD=rootpassword -v golf-db-data:/var/lib/mysql mysql:8.0
```

## API Request Examples

### Creating a Member

```http
POST /api/members
Content-Type: application/json

{
  "name": "John Doe",
  "address": "123 Main St, Anytown, CA",
  "email": "john.doe@example.com",
  "phoneNumber": "555-123-4567",
  "membershipStartDate": "2025-01-01",
  "membershipDuration": 12
}
```

### Creating a Tournament

```http
POST /api/tournaments
Content-Type: application/json

{
  "startDate": "2025-04-15",
  "endDate": "2025-04-18",
  "location": "Pebble Beach Golf Links",
  "entryFee": 150.00,
  "cashPrizeAmount": 5000.00
}
```

### Adding a Member to a Tournament

```http
POST /api/members/1/tournaments/1
```

### Searching for Tournaments by Location

```http
GET /api/tournaments/search?location=Pebble
```

### Searching for Members in a Tournament

```http
GET /api/members/search?tournamentStartDate=2025-04-15
```

## Development

If you want to run the application locally without Docker, you'll need:

1. Java 17 installed
2. MySQL installed and running
3. Update `application.properties` with your database connection details

Then run:
```bash
mvn spring-boot:run
```

## Docker Compose (Alternative Setup)

An alternative to running the containers individually is to use Docker Compose. Create a `docker-compose.yml` file:

```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8081:8081"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/golf_club?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
      SPRING_DATASOURCE_USERNAME: golfuser
      SPRING_DATASOURCE_PASSWORD: golfpass
      SERVER_PORT: 8081
    restart: always

  db:
    image: mysql:8.0
    ports:
      - "3307:3306"
    environment:
      MYSQL_DATABASE: golf_club
      MYSQL_USER: golfuser
      MYSQL_PASSWORD: golfpass
      MYSQL_ROOT_PASSWORD: rootpassword
    volumes:
      - mysql-data:/var/lib/mysql
    restart: always

volumes:
  mysql-data:
```

Then run:
```bash
docker-compose up -d
```

And stop with:
```bash
docker-compose down
```