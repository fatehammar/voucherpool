# Voucher Pool Application

A Spring Boot application for managing voucher codes and special offers. This application allows you to generate voucher codes for special offers, validate them, and check available vouchers for recipients.

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Technology Stack

- Spring Boot 3.2.3
- Spring Data JPA
- MySQL Database
- Swagger/OpenAPI for API documentation
- Maven for dependency management

## Setup Instructions

1. **Clone the Repository**
   bash:
   git clone <repository-url>
   cd voucherpool

2. **Database Setup**
   - Create a MySQL database named 'voucherpool':git
   sql:
   CREATE DATABASE voucherpool;
   - Update database configuration in `src/main/resources/application.yaml` if needed:
   yaml:
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/voucherpool
       username: root
       password: password
   

3. **Build the Application**
   bash:
   ./mvnw clean install
   

4. **Run the Application**
   bash:
   ./mvnw spring-boot:run
   

## API Documentation

Once the application is running, you can access the Swagger UI at:
- http://localhost:8085/swagger-ui/index.html

The OpenAPI specification is available at:
- http://localhost:8085/v3/api-docs

## Available Endpoints

1. **Generate Vouchers**
   - Endpoint: `POST /api/vouchers/generate`
   - Creates voucher codes for specified recipients and special offer
   json:
   {
     "specialOfferId": 1,
     "expirationDate": "2025-12-31T23:59:59",
     "recipientEmails": ["user@example.com"]
   }
   

2. **Get Valid Vouchers**
   - Endpoint: `GET /api/vouchers/valid/{email}`
   - Returns all valid vouchers for a recipient

3. **Validate Voucher**
   - Endpoint: `POST /api/vouchers/validate`
   - Validates a voucher code for a specific recipient
   json:
   {
     "code": "SUMMER2025",
     "email": "user@example.com"
   }

## Sample Data

The application comes with pre-configured sample data including:
- 5 Recipients
- 5 Special Offers (discounts ranging from 15% to 40%)
- 10 Voucher Codes (2 per recipient)

Sample voucher codes for testing:
- SUMMER2025, SUMMER25A (25% off)
- WELCOME15, WELCOME1A (15% off)
- HOLIDAY30, HOLIDAY3A (30% off)
- BDAY2025, BDAY25A1 (20% off)
- FLASH404, FLASH4A1 (40% off)

## Database Schema

The application uses three main tables:
- `recipients`: Stores recipient information
- `special_offers`: Stores available special offers
- `voucher_codes`: Stores generated voucher codes

Tables are automatically created and populated with sample data on application startup.

## Error Handling

The application includes comprehensive error handling for:
- Invalid voucher codes
- Expired vouchers
- Already used vouchers
- Invalid recipient emails
- Non-existent special offers

## Development

To modify the sample data:
1. Edit `src/main/resources/data.sql`
2. Restart the application

To modify the database schema:
1. Edit `src/main/resources/schema.sql`
2. Update corresponding entity classes in `src/main/java/com/codeassmnt/voucherpool/model`
3. Restart the application
