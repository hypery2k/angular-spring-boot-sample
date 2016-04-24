#!/usr/bin/env bash

die() {
   [[ $# -gt 1 ]] && {
        exit_status=$1
        shift
    }
    local -i frame=0; local info=
    while info=$(caller $frame)
    do
        local -a f=( $info )
        [[ $frame -gt 0 ]] && {
            printf >&2 "ERROR in \"%s\" %s:%s\n" "${f[1]}" "${f[2]}" "${f[0]}"
        }
        (( frame++ )) || :; #ignore increment errors (i.e., errexit is set)
    done

    printf >&2 "ERROR: $*\n"

    exit ${exit_status:-1}
}

#trap 'die $? "*** bootstrap failed. ***"' ERR

set -o nounset -o pipefail

sudo apt-get update
sudo debconf-set-selections <<< 'mysql-server mysql-server/root_password password root'
sudo debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password root'
sudo apt-get install -y vim curl python-software-properties
sudo apt-get update
sudo apt-get -y install mysql-server
sed -i "s/^bind-address/#bind-address/" /etc/mysql/my.cnf

########
# MySQL
########

#MYSQL CONFIG
##set mysql user
mysql -u root -proot -e "GRANT ALL PRIVILEGES ON *.* TO 'ngspring'@'%' IDENTIFIED BY 'password' WITH GRANT OPTION; FLUSH PRIVILEGES;"

##create mysql database
mysql -u ngspring -ppassword -e "CREATE DATABASE NGSPRING"

##restart
sudo /etc/init.d/mysql restart
