#!/bin/sh

sed -i "s/SERVERPORT/$SERVER_PORT/g" /opt/scratch/jars/application.properties
sed -i "s/SPRINGDATAMONGODBDATABASE/$SPRING_DATA_MONGODB_DATABASE/g" /opt/scratch/jars/application.properties
sed -i "s/SPRINGDATAMONGODBHOST/$SPRING_DATA_MONGODB_HOST/g" /opt/scratch/jars/application.properties
sed -i "s/SPRINGDATAMONGODBPASSWORD/$SPRING_DATA_MONGODB_PASSWORD/g" /opt/scratch/jars/application.properties
sed -i "s/SPRINGDATAMONGODBPORT/$SPRING_DATA_MONGODB_PORT/g" /opt/scratch/jars/application.properties
sed -i "s/SPRINGDATAMONGODBREPOSITORIESENABLED/$SPRING_DATA_MONGODB_REPOSITORIES_ENABLED/g" /opt/scratch/jars/application.properties
sed -i "s/SPRINGDATAMONGODBUSERNAME/$SPRING_DATA_MONGODB_USERNAME/g" /opt/scratch/jars/application.properties
sed -i "s/SECURITYOAUTH2RESOURCEFILTER-ORDER/$SECURITY_OAUTH2_RESOURCE_FILTERORDER/g" /opt/scratch/jars/application.properties
sed -i "s/SECURITYSIGNING-KEY/$SECURITY_SIGNINGKEY/g" /opt/scratch/jars/application.properties
sed -i "s/SECURITYENCODING-STRENGTH/$SECURITY_ENCODINGSTRENGTH/g" /opt/scratch/jars/application.properties
sed -i "s/SECURITYSECURITY-REALM/$SECURITY_SECURITYREALM/g" /opt/scratch/jars/application.properties
sed -i "s/SECURITYJWTCLIENT-ID/$SECURITY_JWT_CLIENTID/g" /opt/scratch/jars/application.properties
sed -i "s/SECURITYJWTCLIENT-SECRET/$SECURITY_JWT_CLIENTSECRET/g" /opt/scratch/jars/application.properties
sed -i "s/SECURITYJWTGRANT-TYPE/$SECURITY_JWT_GRANTTYPE/g" /opt/scratch/jars/application.properties
sed -i "s/SECURITYJWTSCOPE-READ/$SECURITY_JWT_SCOPEREAD/g" /opt/scratch/jars/application.properties
sed -i "s/SECURITYJWTSCOPE-WRITE/$SECURITY_JWT_SCOPEWRITE/g" /opt/scratch/jars/application.properties
sed -i "s/SECURITYJWTRESOURCE-IDS/$SECURITY_JWT_RESOURCEIDS/g" /opt/scratch/jars/application.properties

java -Dspring.config.location=/opt/scratch/jars/application.properties -jar /opt/scratch/jars/app.jar