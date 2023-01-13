FROM openjdk:11
RUN mkdir /opt/app
RUN mkdir /opt/app/lib
COPY target/*.jar /opt/app/app.jar
ENV TZ=Asia/Jakarta
CMD ["java", "-jar", "/opt/app/app.jar","-Xmx256M","-Xms100M","-XX:+UseG1GC","-XX:MaxGCPauseMillis=20","-XX:InitiatingHeapOccupancyPercent=35","-XX:+ExplicitGCInvokesConcurrent","-Djava.awt.headless=true"]
