helm repo add strimzi https://strimzi.io/charts
helm install strimzi strimzi/strimzi-kafka-operator -n strimzi --set watchNamespaces={kafka}