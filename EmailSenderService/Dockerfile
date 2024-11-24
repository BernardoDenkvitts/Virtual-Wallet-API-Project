FROM golang:1.22.1

WORKDIR /app

RUN apt-get update && apt-get install -y librdkafka-dev

COPY . /app

RUN go build -o /app/main

CMD ["/app/main"]



