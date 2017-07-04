mvn clean package
cp ~/workplace/dr-spark/web-admin/target/dr-spark.war ~/app/soft/apache-tomcat-7.0.72/webapps/
~/app/soft/apache-tomcat-7.0.72/bin/shutdown.sh
~/app/soft/apache-tomcat-7.0.72/bin/startup.sh
