## Konfiguracja
- Nazwa hosta
    - debian
- Użytkownicy
    - nazwa: k8s, hasło: k8s
    - nazwa: administrator, hasło: administrator
- Karty sieciowe
    - 192.168.1.0/24 (Virtual NAT)
    - 192.168.56.0/24 (host-only)
- Zainstalowane pakiety
    - Server SSH
    - Podstawowe narzędzia systemowe
- Uwaga pod windows należy wyłączyć Hyper-V
```
bcdedit /set hypervisorlaunchtype off
```    
## Przygotowanie bazowej maszyny
- Usunąć cdrom jako źródło pakietów
```
nano /etc/apt/sources.list
```
- Zaktualizować system i zainstalować podstawowe narzędzia
```
apt update 
```
```
apt install sudo net-tools
```
- Dodać użytkownika k8s do grupy super użytkowników
```
usermod -aG sudo k8s
```
- Jeśli trzeba dodać i podnieść drugi interfejs sieciowy (enp0s8) 
```
echo auto enp0s8 >> /etc/network/interfaces
```
```
echo iface enp0s8 inet dhcp >> /etc/network/interfaces
```
```
ifup enp0s8
```
```
ip a
```
- Zainstalować Dockera https://docs.docker.com/v17.12/install/linux/docker-ce/debian
```
sudo apt-get install \
     apt-transport-https \
     ca-certificates \
     curl \
     gnupg2 \
     software-properties-common
```
```
curl -fsSL https://download.docker.com/linux/debian/gpg | sudo apt-key add -
```
```
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/debian \
   $(lsb_release -cs) \
   stable"
```
```
sudo apt-get update
```
```
sudo apt-get install docker-ce
```
```
cat > /etc/docker/daemon.json <<EOF
{
  "exec-opts": ["native.cgroupdriver=systemd"],
  "log-driver": "json-file",
  "log-opts": {
    "max-size": "100m"
  },
  "storage-driver": "overlay2"
}
EOF
```
```
mkdir -p /etc/systemd/system/docker.service.d
```
```
sudo usermod -aG docker $USER
```
```
sudo apt-mark hold docker-ce
```
- Zainstalować docker-compose https://docs.docker.com/compose/install
```
sudo curl -L "https://github.com/docker/compose/releases/download/1.25.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```
```
sudo chmod +x /usr/local/bin/docker-compose
```
## Instalacja klastra - wersja uproszczona
- Sklonować maszynę bazową i nadać jej nazwę master
- Wyłączyć swap
```
swapoff -a
```
```
sed -i '/ swap / s/^/#/' /etc/fstab
```
- Zainstalować kubeadm https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/install-kubeadm
```
sudo apt-get update && sudo apt-get install -y apt-transport-https curl
```
```
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
```
```
cat <<EOF | sudo tee /etc/apt/sources.list.d/kubernetes.list
deb https://apt.kubernetes.io/ kubernetes-xenial main
EOF
```
```
sudo apt-get update
```
```
sudo apt-get install -y kubelet kubeadm kubectl
```
```
sudo apt-mark hold kubelet kubeadm kubectl
```
- Skonfigurować stałe adresy ip
```
sudo nano /etc/hosts
```
```
192.168.1.10 master    
192.168.1.11 node1    
192.168.1.12 node2    
192.168.1.100 admin
```
- Ustawić dns na 192.168.1.1 w /etc/resolv.conf
- Skonfigurować ip tables
```
sudo sysctl net.bridge.bridge-nf-call-iptables=1
```
```
sudo update-alternatives --set iptables /usr/sbin/iptables-legacy
```
```
sudo update-alternatives --set ip6tables /usr/sbin/ip6tables-legacy
```
```
sudo update-alternatives --set arptables /usr/sbin/arptables-legacy
```
```
sudo update-alternatives --set ebtables /usr/sbin/ebtables-legacy
```
- Wyłączyć dhcp na podstawowym interfejsie sieciowym
```
sudo nano /etc/network/interfaces
```
```
# iface enp0s3 inet dhcp
```
- Ustawić adres ip dla pierwszej maszyny (master)
```
iface enp0s3 inet static
      address 192.168.1.10
      netmask 255.255.255.0
      gateway 192.168.1.1
```
- Ustawić nazwę hosta dla pierwszej maszyny (master)
```
sudo hostnamectl set-hostname master
```
- Wyłączyć maszynę
```
sudo shutdown -h now
```
- Sklonować maszynę 2 razy i zmienić adresy oraz nazwy odpowiednio dla node1 i node2
- Zainicjalizować klaster (master)
```
sudo kubeadm init
```
- Dołączyć pozostałe maszyny (node1, node2) postępując zgodnie z wyświetlaną instrukcją
- Zainstalować warstwę sieciową
```
kubectl apply -f "https://cloud.weave.works/k8s/net?k8s-version=$(kubectl version | base64 | tr -d '\n')"
```
- Umożliwić logowanie użytkownika root na master
```
nano /etc/ssh/sshd_config  (ustawić PermitRootLogin yes)
```
```
/etc/init.d/ssh restart
```
- Sklonować maszynę bazową i utworzyć stację administracyjną
```
sudo apt-get update && sudo apt-get install -y apt-transport-https curl
```
```
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
```
```
cat <<EOF | sudo tee /etc/apt/sources.list.d/kubernetes.list
deb https://apt.kubernetes.io/ kubernetes-xenial main
EOF
```
```
sudo apt-get update
```
```
sudo apt-get install -y kubectl
```
```
mkdir ~/.kube
```
```
scp root@192.168.1.10:/etc/kubernetes/admin.conf ~/.kube/local
```
```
echo export KUBECONFIG=~/.kube/local >> ~/.bash_profile
```
- Dodać ładowanie .bashrc w .bash_profile
```
if [ -f "$HOME/.bashrc" ]; then
   . "$HOME/.bashrc"
fi
```
- Skonfigurować bash completion
```
echo "source <(kubectl completion bash)" >> ~/.bashrc
```
```
source .bash_profile
```
- Wyświetlenie wszystkich logów na Master
```
journalctl -u kubelet --no-pager|less
```
- Opcjonalna wymiana certyfikatu jeśli nie użyliśmy --apiserver-cert-extra-sans
```
rm /etc/kubernetes/pki/apiserver.*
kubeadm phase certs all --apiserver-advertise-address=0.0.0.0 --apiserver-cert-extra-sans=x.x.x.x,x.x.x.x
docker rm -f `docker ps -q -f 'name=k8s_kube-apiserver*'`
systemctl restart kubelet
```
- Opcjonalnie wyświetlenie tokenów
```
kubeadm token list
```