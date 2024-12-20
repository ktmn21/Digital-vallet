# Digital Wallet API
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

---

## Testing the API

### Using Postman
1. **Authenticate**:
   - Make a `POST` request to `/api/auth/login` with valid credentials to obtain a JWT token.
   - Include the token in subsequent requests using the `Authorization` header:
     ```
     Authorization: Bearer jwt_token_here
     ```

2. **Test Wallet and Transaction Endpoints**:
   - Use the documented endpoints above to interact with the API.

---

## Project Structure
- **Controller Layer**: Handles HTTP requests and responses.
- **Service Layer**: Contains business logic.
- **Repository Layer**: Interfaces with the database.
- **Entity Layer**: Defines data models.
- **DTOs**: Data Transfer Objects for request and response payloads.

---

## Security Configuration
The API uses Spring Security with JWT for stateless authentication. Update `SecurityConfig` to customize access control as needed.

