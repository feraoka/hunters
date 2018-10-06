# Hunters Web App

Hunters Web is a web service for the base

## Development

### Database

Install MySQL, and create user _bbase_ and database _bbase_.
See application.properties for MySQL user settings.
After initially running app, there will be tables in the database.
If you need production data, the latest data whose name is data.sql can be found at hunters-data repository.
https://github.com/feraoka/hunters-data

We use flyway for migrations, please be aware of it when you need to modify database.

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

use _deploy.sh_ script in the repository.
