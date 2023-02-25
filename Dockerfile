
# Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
# Click nbfs://nbhost/SystemFileSystem/Templates/Other/Dockerfile to edit this template

FROM amazoncorretto:17-alpine-jdk
MAINTAINER jrDeveloper
COPY portfolio-0.0.1-SNAPSHOT portfolio-0.0.1-SNAPSHOT
ENTRYPOINT ["java","-jar","/portfolio-0.0.1-SNAPSHOT"]
