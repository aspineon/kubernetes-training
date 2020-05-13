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
## MetalLB
```
kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.9.3/manifests/namespace.yaml
kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.9.3/manifests/metallb.yaml
# On first install only
kubectl create secret generic -n metallb-system memberlist --from-literal=secretkey="$(openssl rand -base64 128)"
```
```
kubectl create -f metallb-config-map.yml
```
## Ingress 
```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-0.32.0/deploy/static/provider/baremetal/deploy.yaml
```
```
kubectl get pods -n ingress-nginx -l app.kubernetes.io/name=ingress-nginx --watch
```
```
kubectl apply -f ingress-service.yml
```
```
kubectl apply -f ingress.yml
```
## Dashboard
```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0-beta1/aio/deploy/recommended.yaml
```
```
kubectl create serviceaccount cluster-admin-dashboard-sa
```
```
kubectl create clusterrolebinding cluster-admin-dashboard-sa \
  --clusterrole=cluster-admin \
  --serviceaccount=default:cluster-admin-dashboard-sa
```
```
kubectl get secret | grep cluster-admin-dashboard-sa
```
```
kubectl describe secret $SECRET_NAME
```
```
kubectl proxy --address=0.0.0.0 (add port forward host to admin)
```
```
http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/.
```