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
- Docker & Docker Compose
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

- Docker and Docker Compose installed on your machine

### Steps to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/golf-club-api.git
   cd golf-club-api
   ```

2. Build and start the application using Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. The application will be available at `http://localhost:8080`

4. To stop the application:
   ```bash
   docker-compose down
   ```

### Data Persistence

All data is stored in a MySQL database. Docker is configured to persist the database data in a volume, so your data will not be lost when stopping and restarting the containers.

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