FROM eclipse-temurin:17

RUN apt-get update && apt-get -y upgrade

ENV HOME=/app

RUN mkdir -p $HOME

WORKDIR $HOME

COPY target/VirtualWalletAPI-0.0.1-SNAPSHOT.jar $HOME/

CMD ["java", "-jar", "/app/VirtualWalletAPI-0.0.1-SNAPSHOT.jar"]