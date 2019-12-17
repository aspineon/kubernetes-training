## Podstawy administracji
- Wyświetlenie informacji dotyczących elementu konfiguracyjnego
```
kubectl explain pod.spec
```
## Pods
- Zastosowanie konfiguracji
```
kubectl create -f echo-server-pod.yaml
```
- Pobranie konfiguracji w formacie json/yaml
```
kubectl get po echo-server-pod -o yaml
```
```
kubectl describe echo-server-pod
```
- Wyświetlenie logów
```
kubectl logs echo-server-pod [-c $CONTAINER_NAME]
```
- Forward portów
```
kubectl port-forward echo-server-pod 8888:8080
```
- Wykonanie polecenia na kontenerze / pod
```
kubectl exec echo-server-pod -- env
```
## Labels
- Pokazanie etykiet
```
kubectl get pods --show-labels
```
- Filtrowanie po etykietach
```
kubectl get pods -l version
```
```
kubectl get pods -l version=1
```
```
kubectl get pods -l version!=1
```
```
kubectl get pods -l '!version'
```
```
kubectl get pods -l 'env in (production,development)'
```
```
kubectl get pods -l 'env notin (production,development)'
```
```
kubectl get pods -l 'env=production,version=2'
```
- Dodanie etykiety
```
kubectl label pod echo-server-pod type=forntend
```
```
kubectl label node node1.k8s type=primary
```
- Zmiana etykiety
```
kubectl label pod echo-server-pod type=backend --overwrite
```
- Usunięcie etykiety
```
kubectl label pod echo-server-pod type-
```
## Annotations
- Dodanie adnotacji
```
kubectl annotate pod echo-server-pod training=true
```
- Wyświetlenie adnotacji
```
kubectl get pod echo-server-pod -o yaml
```
## Namespaces
- Pobranie przestrzeni nazw
```
kubectl get namespaces
```
- Pobranie podów z danej przestrzeni nazw
```
kubectl get pods --namespace=kube-system
```
- Utworzenie przestrzeni nazw
```
kubectl create namespace training
```
- Usunięcie przestrzeni nazw
```
kubectl delete namespace training
```
## Usuwanie 
```
kubectl delete pod echo-server-pod
```
```
kubectl delete pod -l version=v1
```
```
kubectl delete namesapces training
```
```
kubectl delete pods --all
```
```
kubectl delete all --all
```