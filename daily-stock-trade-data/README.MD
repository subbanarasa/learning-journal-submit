Steps to run the application:
1.  mvn clean package
2. java -jar target/daily-stock-trade-data-0.0.1  <No of threads>  #Argumnet is no of threads.

Verify application.propeties file. 

#Kafka bootstrap address with PLAINTEXT
kafka.bootstrapAddress = 127.0.0.1:9093
#Kafka Topic
kafka.topic = stock-trade-data
#Directory where we are keeping the files to load
files.src.directory = files
#Directory where files will be moved once it process.
files.archive.directory = archive


To verify, please look into "Total records in the file::" log
