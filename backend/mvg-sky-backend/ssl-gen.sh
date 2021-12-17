#!/bin/bash

set -x

make_ssl() {
  mkdir -p ./ssl/"$1"/
  chmod 700 ./ssl/"$1"/

  openssl req \
    -x509 -nodes -days 365 -sha256 -newkey rsa:2048 \
    -subj "/C=VI/ST=Danang/L=Danang/CN=$1" \
    -keyout ./ssl/"$1"/key.key -out ./ssl/"$1"/crt.crt
}

make_ssl mvg-sky.com
make_ssl www.mvg-sky.com

make_ssl admin.mvg-sky.com
make_ssl www.admin.mvg-sky.com

make_ssl api.mvg-sky.com
