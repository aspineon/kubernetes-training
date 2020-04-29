## Instalacja 
```
kubectl apply -f warmup-exercise/1-istio-init.yaml
kubectl apply -f warmup-exercise/2-istio-minikube.yaml
kubectl apply -f warmup-exercise/3-kiali-secret.yaml
kubectl label namespace default istio-injection=enabled 
kubectl apply -f warmup-exercise/4-application-full-stack.yaml
kubectl port-forward webapp-7fcc98f6d5-msbq2 8080:80 --address=0.0.0.0
kubectl port-forward kiali-76f556db6d-7k4g8 8080:20001 --address=0.0.0.0 -n=istio-system 
kubectl port-forward istio-tracing-8584b4d7f9-27n5h 8080:16686 --address=0.0.0.0 -n=istio-system
```

