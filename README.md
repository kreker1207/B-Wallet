How to run:
1. Get and install docker https://docs.docker.com/get-docker/
2. Pack application: `mvn package`
3. Start docker-compose: `docker-compose up <service>`
   1. MySql service should start first, it takes too long time for sip application: `docker-compose up mysql`
   2. SIP application: `docker-compose up sip`
   3. If you need to remove all services and networks use commands: `docker-compose down`