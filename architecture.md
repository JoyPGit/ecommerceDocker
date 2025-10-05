# System Architecture Overview

## Clients
- Web frontend (desktop)
- Mobile app (iOS/Android)
- Other channels (kiosk, API partners)

↓

## Edge / CDN / API Gateway / Load Balancer

↓

## Microservices / Backend Services
- Catalog service (products, categories)
- Search & indexing service
- Cart service
- Checkout / Payment service
- Order service
- Inventory service
- User service (auth, profile)
- Promotions & Discount service
- Recommendation / Personalization service
- Notification service
- Admin / Vendor portal service

↓

## Supporting Services / Infrastructure
- Databases (SQL / NoSQL)
- Caches (Redis, Memcached)
- Message queues / Event streaming (Kafka, RabbitMQ)
- CDN
- Object storage (images, static assets)
- Search engine (Elasticsearch, Algolia, etc.)
- Monitoring / logging / tracing (Prometheus, ELK / EFK, Jaeger, etc.)
- Identity / auth / security layer
- Deployment & CI/CD, infrastructure automation

↓

## External / Third-Party Integrations
- Payment gateways
- Shipping carriers / logistics APIs
- Third-party promotions / coupons
- Tax / compliance services
- Fraud detection services

---

## Best Practices
- Load testing (simulate scenarios via multithreading)
- Idempotent APIs
- Circuit breaker (exponential backoff, thundering herd)
- Docker
- File upload
- Distributed job scheduler (notifications, file upload)
- Batch processing (bulk file upload)
- Analytics (selecting best vendor)
- Design user profile page
    - `w1 * views + w2 * rating + w3 * experience`
    - Profile read/write optimization
    - Frequent reads, infrequent writes
    - Handling view counts
    - Caching strategy per profile

| Area         | What to Show                          |
|--------------|---------------------------------------|
| Scalability  | Can your design handle 100M+ users?    |
| Reliability  | How does the system recover from failures? |
| Trade-offs   | Can you evaluate consistency vs availability? |
| Component Design | API gateway, load balancers, databases |
| Data Modeling | How you design DB schema and indexes  |
| API Design   | REST vs gRPC, versioning, rate limits  |
| Chaos monkey |                                       |

---

## System Design Interview Scenarios

1. **URL Shortener (e.g., Bitly)**
    - Unique short code generation
    - Handling collisions
    - Expiration and analytics
    - Scaling reads and writes
    - Database design

2. **Web Crawler (e.g., Googlebot)**
    - BFS/DFS crawling
    - Duplicate URL handling
    - Rate limiting & politeness
    - Distributed architecture

3. **News Feed System (e.g., Facebook/LinkedIn Feed)**
    - Push vs pull model
    - Fanout on write/read
    - Feed ranking
    - Caching and pagination

4. **Chat/Messaging System (e.g., WhatsApp or LinkedIn Chat)**
    - 1:1 and group messaging
    - Message delivery guarantees
    - Offline support
    - Message storage & read receipts

5. **Notification System**
    - Real-time and batch notifications
    - Email, push, and in-app channels
    - Deduplication and retries
    - Rate limiting per user

6. **Scalable File Storage System (e.g., Dropbox, Google Drive)**
    - Upload/download APIs
    - Chunking large files
    - Storage backend (S3, GFS, etc.)
    - Metadata storage and access control

7. **API Rate Limiter**
    - Token bucket / leaky bucket
    - Sliding window algorithms
    - Global and per-user limits
    - Stateless or Redis-based implementations

8. **Search Engine (e.g., People/Job Search in LinkedIn)**
    - Full-text search
    - Ranking and autocomplete
    - Inverted indexing
    - Elasticsearch/Solr

9. **YouTube/Instagram**
    - Video upload and encoding
    - CDN integration
    - View count consistency
    - Comment and like systems

10. **E-Commerce Platform (e.g., Amazon)**
    - Product catalog
    - Shopping cart
    - Payment gateway
    - Order tracking
    - Inventory management

### Honorable Mentions
- Distributed cache (e.g., Redis, Memcached)
- Recommendation system (Netflix/LinkedIn Jobs)
- Real-time collaboration tool (e.g., Google Docs)
- Uber/Lyft (geo-spatial + matching)
- Metrics/monitoring system (like Prometheus)

---

## Technical Implementation Details

### Authentication & Authorization
- JWT-based authentication
- OAuth2/OpenID Connect integration
- Role-based access control (RBAC)
- Session management
- Refresh token rotation

### Database Design
- Sharding strategies
  - Horizontal sharding by user_id
  - Vertical sharding for large tables
- Indexing best practices
  - Covering indexes
  - Composite indexes
  - Partial indexes
- Schema evolution
  - Using Flyway migrations
  - Zero-downtime schema changes

### Caching Strategy
- Multi-level caching
  - L1: Application cache
  - L2: Redis/Memcached
  - L3: CDN
- Cache invalidation patterns
  - Write-through
  - Write-behind
  - Cache-aside
- TTL strategies
- Cache coherence solutions

### API Design Patterns
- RESTful endpoints
  - Resource naming conventions
  - Versioning strategy (URI vs Header)
  - HATEOAS implementation
- GraphQL considerations
  - Schema design
  - Resolvers organization
  - N+1 problem solutions
- API documentation
  - OpenAPI/Swagger
  - API changelog

### Resilience Patterns
- Circuit Breaker
  ```java
  @CircuitBreaker(name = "serviceA", fallbackMethod = "fallback")
  public Response serviceCall() {}
  ```
- Retry with exponential backoff
- Rate limiting
- Bulkhead pattern
- Timeout handling

### Monitoring & Observability
- Metrics collection
  - Business metrics
  - Technical metrics
  - SLA/SLO tracking
- Distributed tracing
  - Trace correlation
  - Span management
- Log aggregation
  - Structured logging
  - Log levels strategy
- Alerting rules
  - Threshold-based
  - Anomaly detection

### Performance Optimization
- Connection pooling
  ```properties
  spring.datasource.hikari.maximum-pool-size=10
  spring.datasource.hikari.minimum-idle=5
  ```
- Thread pool tuning
- JVM optimization
  ```shell
  JAVA_OPTS="-Xms2g -Xmx4g -XX:+UseG1GC"
  ```
- Query optimization
  - Explain plan analysis
  - Index usage monitoring

### Security Considerations
- Input validation
- SQL injection prevention
- XSS protection
- CSRF tokens
- Security headers
  ```properties
  security.headers.xss-protection=1
  security.headers.frame-options=DENY
  ```
- Rate limiting
- Data encryption
  - At rest
  - In transit
- Audit logging

### Deployment Strategy
- Blue-Green deployment
- Canary releases
- Feature flags
- Rolling updates
- Deployment manifest example:
  ```yaml
  deployment:
    strategy:
      rollingUpdate:
        maxSurge: 1
        maxUnavailable: 0
      type: RollingUpdate
  ```

### Testing Strategy
- Unit testing
  - Service layer
  - Repository layer
  - Controller layer
- Integration testing
  - API contracts
  - Database interactions
- Performance testing
  - Load tests
  - Stress tests
  - Endurance tests
- Security testing
  - SAST
  - DAST
  - Dependency scanning

## Implementation-Specific Details

### Docker Configuration
- Multi-stage builds
  ```dockerfile
  # Build stage
  FROM maven:3.8.4-openjdk-17 AS builder
  COPY . .
  RUN mvn clean package

  # Run stage
  FROM openjdk:17-slim
  COPY --from=builder /target/*.jar app.jar
  ```
- Container health checks
- Resource limits
- Network configuration
- Volume management

### Spring Boot Features
- Profiles management
  ```properties
  spring.profiles.active=dev,docker
  ```
- Actuator endpoints
  - Health monitoring
  - Metrics exposure
  - Environment details
- Custom error handling
- Bean validation
- Async processing

### Database Migration
- Flyway versioning
  ```sql
  V1__initial_schema.sql
  V2__add_indexes.sql
  V3__alter_columns.sql
  ```
- Migration patterns
- Rollback strategies
- Schema versioning

### Service Layer Architecture
- Domain-driven design
- Repository pattern
- Service abstractions
- Event-driven patterns
- CQRS implementation

### API Documentation
- OpenAPI 3.0 specifications
- API versioning strategy
- Error response standards
- Rate limit documentation
- Authentication flows

### CI/CD Pipeline
- Build stages
  - Compile
  - Test
  - Package
  - Security scan
  - Deploy
- Quality gates
- Automated testing
- Deployment environments

### Development Practices
- Code review guidelines
- Branch strategy
- Commit message format
- Testing requirements
- Documentation standards

### Monitoring Setup
- Prometheus metrics
- Grafana dashboards
- Log aggregation
- Tracing configuration
- Alert rules

### Security Implementation
- Authentication flow
- Authorization rules
- Data validation
- API security
- Audit logging

### Performance Tuning
- JVM settings
- Connection pooling
- Cache configuration
- Query optimization
- Load balancing
