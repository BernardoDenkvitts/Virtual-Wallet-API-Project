package main

import (
	"log"
	"os"
	"strconv"
	"time"

	"github.com/BernardoDenkvitts/EmailSenderService/internal/infrastructure/kafka"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/infrastructure/smtp"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/infrastructure/storage"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/service"
	"github.com/BernardoDenkvitts/EmailSenderService/utils"
)

func main() {
	utils.LoadEnv("/.env-prod")

	// Avoid problems with kafka and postgres initialization
	time.Sleep(time.Second * 10)

	port, _ := strconv.Atoi(os.Getenv("emailport"))
	
	config := smtp.NewSMTPConfig(port, os.Getenv("emailhost"), os.Getenv("emailuser"), os.Getenv("emailpassword"))
	smtpServer := smtp.NewSmtpServer(config)
	str := storage.NewPostgreStorage()
	str.Init()
	
	kafkaConfig := kafka.NewKafkaConfig()
	kafkaConfig.CreateTopic()
	kafkaConsumer := kafkaConfig.CreateEmailConsumer()

	service := service.NewEmailImpl(smtpServer, str)

	consumer := kafka.NewKafkaEmailConsumer(kafkaConsumer, service)

	log.Println("Starting consumers")

	// 2 consumers
	go consumer.Consume()
	consumer.Consume()
}
