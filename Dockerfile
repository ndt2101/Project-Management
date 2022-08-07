FROM openjdk:8
#ADD command is used to download an external file and copy it to the wanted destination.
#(COPY) avoid backward compatibility, we should use Its role is to duplicate files/directories in a specified location in their existing format.
ADD target/ProjectManagement-0.0.1-SNAPSHOT.jar projectManagement.jar
ENTRYPOINT ["java", "-jar", "projectManagement.jar"]
# -Xms -Xmx chi gioi han bo nho heap