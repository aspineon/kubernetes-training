## GlusterFS
- Utworzenie urządzeń blokowych na Node1, Node2
```
dd if=/dev/zero of=/home/k8s/image bs=1M count=10000
```
```
sudo losetup /dev/loop0 /home/k8s/image
```
- Instalacja klienta GlusterFS na Node1, Node2
```
modprobe dm_thin_pool
```
```
su -
apt-get update && apt-get -y install glusterfs-client
```
```
nano /etc/systemd/system/loop_gluster.service 

[Unit]
Description=Create the loopback device for GlusterFS
DefaultDependencies=false
Before=local-fs.target
After=systemd-udev-settle.service
Requires=systemd-udev-settle.service
[Service]
Type=oneshot
ExecStart=/bin/bash -c "modprobe dm_thin_pool && [ -b /dev/loop0 ] || losetup /dev/loop0 /home/k8s/image"
[Install]
WantedBy=local-fs.target
```
```
systemctl enable /etc/systemd/system/loop_gluster.service
```
- Instalacja GlusterFS na Admin
```
git clone https://github.com/heketi/heketi
```
```
cd heketi/extras/kubernetes
```
```
kubectl create -f glusterfs-daemonset.json 
```
```
kubectl label node node1 storagenode=glusterfs
kubectl label node node2 storagenode=glusterfs
```
```
kubectl create -f heketi-service-account.json
```
```
kubectl create clusterrolebinding heketi-gluster-admin --clusterrole=edit --serviceaccount=default:heketi-service-account
```
```
kubectl create secret generic heketi-config-secret --from-file=./heketi.json
```
```
kubectl create -f heketi-bootstrap.json
```