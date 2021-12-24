# Apache James Version 3.3.x
- Scripts and configuration files


## Run Apache James in Systemd
```shell
> copy ${BASE_DIR}/systemd/mvg-sky-apache-james.service > /etc/systemd/system/
$ systemctl daemon-reload

> start service
$ systmctl start mvg-sky-apache-james.service

> check status service
$ systmctl status mvg-sky-apache-james.service

> stop service
$ systmctl stop mvg-sky-apache-james.service
```
