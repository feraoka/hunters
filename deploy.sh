mvn clean package
scp target/hunters-0.0.1-SNAPSHOT.jar monstars0@hunters.lisfactory.com:~/.
ssh monstars0@hunters.lisfactory.com sudo systemctl restart hunters

