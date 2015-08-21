# -*- mode: ruby -*-
# vi: set ft=ruby :
Vagrant.configure("2") do |config|
    config.vm.box = "ubuntu/trusty64"
    # Create a forwarded port mapping which allows access to a specific port
    # within the machine from a port on the host machine. In the example below,
    # accessing "localhost:8080" will access port 80 on the guest machine.
    # config.vm.network "forwarded_port", guest: 80, host: 8080
    config.vm.network "private_network", ip: "192.168.56.13"
    config.vm.synced_folder ".", "/home/vagrant/share", create: true
    config.vm.synced_folder "logs", "/opt/moviedatabase/logs", create: true
    config.vm.provider "virtualbox" do |vb|
        vb.customize ["modifyvm", :id, "--memory", "4096", "--cpus", "2"]
        vb.name = "movie-database"
    end
    config.vm.provision :shell, :inline => "sudo ln -fs /home/vagrant/share/vagrant-etc-hosts /etc/hosts"
    config.vm.provision :shell, path: "init.sh"
end
