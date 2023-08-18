# bank-app-microservices
Spring Boot 2.7, Spring Cloud/Data JPA/Security, Docker, Kubernetes, Helm

This project represents simple bank system with accounts, cards and loans models. The main goal of the app is to establish interaction between the services and to deploy them into Docker or Kubernetes cluster. So, the features are:

- Microservice architecture,
- Interaction between services through Eureka server and OpenFeign,
- Routing through Gateway server,
- Storing settings with Config server in a separate public repository: https://github.com/valchukav/bank-app-config,
- Log tracing via RabbitMQ and Zipkin,
- Authentication and authorization using OAuth 2 and Keycloak (client credentials grant type),
- Deployment to Docker with docker-compose.yml,
- Deployment to Kubernetes cluster (on local machine with Minikube) with Helm charts,
- Monitor perfomance via Prometheus and Grafana.

Based on course: https://www.udemy.com/course/master-microservices-with-spring-docker-kubernetes/
