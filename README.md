
# URL Shortener Service

A simple, production-ready URL shortening service written in **Java 21 + Spring Boot 3**, with optional Docker deployment and PostgreSQL persistence.

The service accepts a **long URL** and returns a **short code**, which can later be used to redirect the user to the original URL.


### **Base functionality**
- REST API for creating short URLs
- Redirect endpoint for accessing the original URL
- Works locally or inside Docker

### **Advanced features**
- Configurable short-code length (via `application.properties`)
- PostgreSQL database persistence (survives restarts)
- Logging for all shorten/redirect operations
- Dockerfile + `docker-compose.yml` for full containerized launch

---


# Running 

## 1. Clone the repository

```bash
git clone https://github.com/allaberenov/URL-shortener.git
cd URL-shortener
````


## 2.  Running with Docker

The project contains:

* `Dockerfile` — builds the application
* `docker-compose.yml` — runs Postgres + the app


For building service start:

```bash
docker-compose up --build
```

To start the services:
```bash
docker-compose up
```
from directory `URL-shortener`.

### Check running containers

```bash
docker ps
```

You should see:

* `urlshortener_postgres`
* `urlshortener_app`


## 3.  Testing the service with curl

### Create a short URL:

```bash
curl -X POST http://localhost:8080/shorten \
  -H "Content-Type: application/json" \
  -d '{"url":"https://example.com/very/long/link"}'
```
As a result you will get a shortcode which is used for short link.

```bash
curl -X POST http://localhost:8080/shorten \
  -H "Content-Type: application/json" \
  -d '{"url":"https://yandex.ru"}'
{"shortCode":"4pypRdFqOJHcWLb"}%
```

### Test redirect:

For testing shortcode just type like this.

```bash
curl -v http://localhost:8080/4pypRdFqOJHcWLb
```

## 4. ⚙ Configuration

You can change the short code length in `docker-compose.yml` file by writing your size for `SHORTENER_CODE_LENGTH` variable.

