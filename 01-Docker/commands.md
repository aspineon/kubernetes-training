## Podstawy Dockera
- Wyświetlenie lokanych obrazów
```
docker images
```
- Wyświetenie działających kontenerów
```
docker ps
```
- Wyświetlenie wszystkich kontenerów (nawet zatrzymanych)
```
docker ps -a
```
- Pobranie obrazu
```
docker pull busybox
```
- Uruchomienie kontenera
```
docker run busybox echo "Hello Docker"
```
- Uruchomienie kontenera i podpięcie terminala (tryb interakktywny)
```
docker run -it ubuntu
```
```
docker exec -it ubuntu bash
```
- Zbudowanie obrazu
```
docker build -t echo-server .
```
- Uruchomienie kontenera z jednoczesnym mapowaniem portów
```
docker run --name echo-server -p 8080:8080 -d echo-server
```
```
curl localhost:8080
```
- Wyświetlenie informacji o kontenerze
```
docker inspect echo-server
```
- Zatrzymanie kontenera
```
docker stop echo-server
```
- Usunięcie kontenera
```
docker rm echo-server
```
- Wysłanie lokanlnego obrazu do Docker Hub
```
docker tag echo-server landrzejewski/echo-server:v1
```
```
docker images | head
```
```
docker login
```
```
docker push landrzejewski/echo-server:v1
```
- Usunięcie obrazu
```
docker rmi echo-server
```
- Zatrzymanie wszystkich kontenerów
```
docker stop $(docker ps -q)
```
- Usunięcie wszystkich kontenerów
```
docker rm $(docker ps -a -q)
```
## Podstawy docker-compose
- Uruchomienie wszystkich usług
```
docker-compose up
```
- Uruchomienie wybranych usług
```
docker-compose up echo-server postgres
```
- Zatrzymanie i usunięcie wszystkich usług / kontenerów
```
docker-compose down
```