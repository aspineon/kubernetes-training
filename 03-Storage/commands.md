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
- Instalacja klienta z Master
```
wget https://github.com/heketi/heketi/releases/download/v9.0.0/heketi-client-v9.0.0.linux.amd64.tar.gz
```
```
tar -xzvf heketi-client-v9.0.0.linux.amd64.tar.gz
```
```
sudo cp ./heketi-client/bin/heketi-cli /usr/local/bin/
```
```
heketi-cli 
```
```
kubectl describe pod deploy-heketi-7bcd7888b-4mjtn    (10.32.0.2)
```
```
curl http://10.32.0.2:8080/hello
```
```
export HEKETI_CLI_SERVER=http://10.32.0.2:8080
export HEKETI_CLI_USER=admin
export HEKETI_CLI_KEY="My Secret"
```
```
nano topology.json

{
    "clusters": [
        {
            "nodes": [
                {
                    "node": {
                        "hostnames": {
                            "manage": [
                                "node1"
                            ],
                            "storage": [
                                "192.168.1.11"
                            ]
                        },
                        "zone": 1
                    },
                    "devices": [
                        "/dev/loop0"
                    ]
                },
                {
                    "node": {
                        "hostnames": {
                            "manage": [
                                "node2"
                            ],
                            "storage": [
                                "192.168.1.12"
                            ]
                        },
                        "zone": 1
                    },
                    "devices": [
                        "/dev/loop0"
                    ]
                }
            ]
        }
    ]
}
```
```
/usr/local/bin/heketi-cli topology load -- json=topology.json
```
```
/usr/local/bin/heketi-cli volume create --durability=none --size=2 --replica=2
```
```
/usr/local/bin/heketi-cli setup-openshift-heketi-storage --replica=2
```
```
sudo kubectl --kubeconfig /etc/kubernetes/admin.conf create -f heketi-storage.json
```
- Z Admin
```
kubectl delete all,service,jobs,deployment,secret --selector="deploy-heketi"
```
```
kubectl create -f heketi-deployment.json
```
