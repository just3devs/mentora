# Mentora 🚀

Mentora is a **full-stack project** that combines modern software
development practices, AI integration, and DevOps workflows. The goal is
to build a scalable, observable, and secure architecture that integrates
backend, frontend, and AI services.

------------------------------------------------------------------------

## 🚀 Setup

### 1. Requirements

-   Docker & Docker Compose
-   JDK 21
-   Maven 3.9+

### 2. Start services

``` bash
docker compose up -d --build
```

### 3. Access services

-   Backend: <http://localhost:8080>
-   PostgreSQL: `localhost:5433`
-   Zipkin UI: <http://localhost:9411>

------------------------------------------------------------------------

## 📂 Project Structure

-   **Backend** → Spring Boot 3, Java 21, PostgreSQL, Spring AI, OAuth2
    Google integration
-   **Frontend** → React + TypeScript (planned CI/CD integrations)
-   **DevOps** → Docker, Docker Compose, GitHub Actions, Kubernetes (up
    to deployment stage)
-   **Observability** → Zipkin (distributed tracing)

------------------------------------------------------------------------

## ✅ Completed Tasks

-   Backend environment setup
-   Dockerization of services and container preparation
-   Backend integration with Docker
-   Environment variables managed via GitHub Variables
-   OAuth2 Google authentication integration
-   Connecting LLM with backend using Spring AI
-   Backend testing with JUnit
-   Frontend CI workflow using GitHub Actions
-   Backend CI workflow using GitHub Actions
-   Zipkin tracing integration
-   Kubernetes deployment of containers
-   Fixed Docker Image issue in GitHub Actions pipeline

------------------------------------------------------------------------

## 🔮 Planned Improvements

-   Frontend user interface development
-   Prometheus + Grafana integration for metrics-based monitoring
-   Production-grade Kubernetes manifests

------------------------------------------------------------------------

## 🛠 Technologies Used

-   **Backend**: Spring Boot, Spring Security, Spring AI, PostgreSQL
-   **Frontend**: React, TypeScript
-   **CI/CD**: GitHub Actions
-   **Containerization & Deployment**: Docker, Docker Compose,
    Kubernetes
-   **Tracing & Monitoring**: Zipkin
-   **Testing**: JUnit

------------------------------------------------------------------------

## 👥 Team

- Yunus Emre Şenyiğit
- Emir Enes Akalın
- Göktuğ Berke Güngören
