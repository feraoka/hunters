# Hunters Web App

Hunters Web is a web service for the base

## Development

### Database

Install MySQL

Create user _bbase_, and database _bbase_.

## Run as a daemon on ubuntu

https://dzone.com/articles/run-your-java-application-as-a-service-on-ubuntu

% sudo vi /etc/systemd/system/hunters.service

```$xslt
[Unit]
Description=Hunters Web Service
[Service]
User=ubuntu
# The configuration file application.properties should be here:
#change this to your workspace
WorkingDirectory=/home/monstars0/work
#path to executable. 
#executable is a bash script which calls jar file
ExecStart=/home/monstars0/work/hunters-app
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5
[Install]
WantedBy=multi-user.target
```

% vi /home/monstars0/work/hunters-app  
% chmod +x /home/monstars0/work/hunters-app
```
#!/bin/sh
sudo /usr/bin/java -jar -Dserver.port=80 /home/monstars0/hunters-0.0.1-SNAPSHOT.jar 
```

## Deployment

```
mvn clean package
scp target/hunters-0.0.1-SNAPSHOT.jar monstars0@hunters.lisfactory.com:~/.
ssh monstars0@hunters.lisfactory.com sudo systemctl restart hunters
```
