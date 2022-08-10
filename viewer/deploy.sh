eval $(minikube -p minikube docker-env)
mvn verify -Dquarkus.kubernetes.deploy=true