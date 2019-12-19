## Deployment
```
kubectl apply -f echo-server-deployment.yml --record
```
```
kubectl rollout history deployment echo-server-deployment
```
```
kubectl rollout status deployment echo-server-deployment
```
```
while true;do curl -s http://192.168.1.240;sleep 1;done;
```
```
kubectl set image deployment echo-server-deployment echo-server-app=landrzejewski/echo-server:v2
```
```
kubectl rollout status deployment echo-server-deployment -w
```
```
kubectl rollout history deployment echo-server-deployment
```
```
kubectl rollout undo deployment echo-server-deployment --to-revision=1
```