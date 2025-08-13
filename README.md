# Spring Boot Microservices with Keycloak OAuth2

This project is an **E-Commerce Microservices Application** built with **Spring Boot**, secured using **OAuth2 authentication and authorization** via **Keycloak**.

---

## Features

- Microservices architecture with **API Gateway**
- Services:
  - **User Service**
  - **Product Service**
  - **Order Service**
  - **Inventory Service**
- **OAuth2 Resource Server** with JWT tokens via Keycloak
- Secure endpoints based on roles and permissions
- PostgreSQL database integration
- Docker support for services and Keycloak

---

## Tech Stack

- Java 17  
- Spring Boot 3.1  
- Spring Cloud Gateway  
- Spring Security (OAuth2 & JWT)  
- PostgreSQL  
- Keycloak (Authentication & Authorization)  
- Docker & Docker Compose  
- Maven

---

## Getting Started

### Prerequisites

- Java 17  
- Maven  
- Docker  
- Keycloak server (running at `http://localhost:8186`)

---

## Keycloak Setup

### 1. Run docker-compose.yml file of API Gateway on Git Bash

* Hit http://localhost:8186 on the browser then keycloak Admin Console page will open as shown below

<img width="1402" height="809" alt="image" src="https://github.com/user-attachments/assets/bc3d7daa-16f3-4f4f-ae1f-33fde5d9b74f" />

<br><br>

* Admin credentials: username `admin`, password `admin`
  
<img width="1389" height="802" alt="image" src="https://github.com/user-attachments/assets/266d0220-44d7-4e9f-8527-4eaea7b2bc56" />

<br><br>

---

### 2. Create Realm

* Go to **Add Realm** → Name: `spring-microservices-security-realm`

---

### 3. Create Client

* Go to **Clients → Create**:

  * Client ID: `api-gateway`
  * Client Protocol: `openid-connect`
  * Root URL: `http://localhost:8080`
  * Access Type: `confidential`
* Go to **Credentials tab** → Copy **Client Secret**
* In **Settings tab** → Enable **Standard Flow**
* Optional: Enable **Direct Access Grants** if testing via Postman

---

### 4. Create Roles

* Go to **Roles → Add Role**
* Example roles:

  * `ROLE_USER`
  * `ROLE_ADMIN`

---

### 5. Create Users and Assign Roles

* Go to **Users → Add User** → Set username, email, password
* Go to **Role Mappings** → Assign user one or more roles

---

### 6. Configure JWT Issuer in Spring Boot

In `application.yml` of API Gateway:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8186/realms/spring-microservices-security-realm
```

---

## Running the Application

### 1. Build Microservices

```bash
mvn clean install
```

### 2. Start Services

* Run each service in IntelliJ **OR** use Docker Compose:

```bash
docker-compose up
```

---

## Testing API Gateway using Postman
### Auth Type : No Auth and hit API: http://localhost:8080/api/product/1
<img width="1241" height="569" alt="image" src="https://github.com/user-attachments/assets/72207920-b017-4330-ba85-8072d8ca3a80" />

### Auth Type : OAuth 2.0 
* **Step 1**: Copy Access Token URL from realm setting as shown below by clicking on OpenID Endpoint Configuration
<img width="1795" height="901" alt="image" src="https://github.com/user-attachments/assets/c6347024-9275-44e7-8c3a-0e3adc264a83" />

<br><br>

* **Step 2**: After clicking on OpenID Endpoint Configuration, a new URL will open as below-
<img width="1026" height="926" alt="image" src="https://github.com/user-attachments/assets/17b67f6e-2078-4338-884c-6dd1c46901b6" />

<br><br>

* **Step 3**: Copy token_endpoint from JSON data present in the URL as below-
<img width="1004" height="925" alt="image" src="https://github.com/user-attachments/assets/3a127418-f587-4e13-bd44-3c09df5091bd" />

<br><br>

* **Step 4**: Goto Postman and paste this token_endpoint under Access Token URL as shonw below-
<img width="1254" height="424" alt="image" src="https://github.com/user-attachments/assets/dbb94b49-5f4e-473a-99dc-dc67a768b055" />

<br><br>

* **Step 5**: Copy Client ID from keycloak and paste to Postman
Goto keycloak, click on Clients and then click on spring-client-credentials-id as shown below-
<img width="1836" height="854" alt="image" src="https://github.com/user-attachments/assets/a0cedab5-5eec-41ac-bcd1-734875096b45" />

<br><br>

After clicking spring-client-credentials-id, a new page will open, copy Client ID as shown below-
<img width="1164" height="850" alt="image" src="https://github.com/user-attachments/assets/82d292ac-324a-4603-84b8-648ea41ddc87" />

<br><br>

Paste this Client ID in Postman as shown below-
<img width="1255" height="401" alt="image" src="https://github.com/user-attachments/assets/0bb99697-0ca7-4758-8f4c-2ade55295500" />

<br><br>

* **Step 6**: Copy Client Secret from keycloak and paste in Postman as shown below-
<img width="1607" height="722" alt="image" src="https://github.com/user-attachments/assets/3cdebdc8-af49-47c6-8299-8ca20419874b" /><br><br>
<img width="1244" height="553" alt="image" src="https://github.com/user-attachments/assets/23acf2bf-c9e7-47e4-8865-9dbdebb22b7d" /><br><br>

<br><br>

* **Step 7**: Goto Postman and click on Get New Access Token as shown below-
<img width="1257" height="603" alt="image" src="https://github.com/user-attachments/assets/1b3f70de-1383-49c4-95e7-530acdc77236" />

<br><br>

<img width="1055" height="529" alt="image" src="https://github.com/user-attachments/assets/a70743cd-62e6-49de-a42e-307971bf795e" />

<br><br>

<img width="1222" height="639" alt="image" src="https://github.com/user-attachments/assets/8d18035d-6429-42fb-a27f-2b2c2db759a0" />

<br><br>


- Click on Use Token and then Token will be added in the Postman.

<br><br>

* ***Step 8**: Now hit this API http://localhost:8080/api/product/1 again and we will get the result as shown below-
<img width="1283" height="692" alt="image" src="https://github.com/user-attachments/assets/75ca93f8-b40b-48ec-aab9-3999393067a0" />

<br><br>



## Security Overview

* All services are **OAuth2 JWT protected**
* **API Gateway** validates JWT tokens before forwarding requests
* Public endpoints (like `/actuator/health`) are accessible without authentication
* Role-based access ensures only authorized users can access sensitive APIs

---

## Docker

* Docker Compose handles **all services and Keycloak**
* Volumes persist MySQL and Keycloak data

```bash
docker-compose up
```


