package kafka

import (
	"encoding/json"
	"log"

	"github.com/BernardoDenkvitts/EmailSenderService/internal/service"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/types"
	"github.com/confluentinc/confluent-kafka-go/v2/kafka"
)

type IConsumer interface {
	Consume()
}

type KafkaConsumer struct {
	Consumer     *kafka.Consumer
	EmailService service.IEmail
}

func NewKafkaConsumer(topics []string, emailSerivce service.IEmail) *KafkaConsumer {
	consumer := createConsumer()
	consumer.SubscribeTopics(topics, nil)

	return &KafkaConsumer{Consumer: consumer, EmailService: emailSerivce}
}

func (c *KafkaConsumer) Consume() {
	for {
		msg, err := c.Consumer.ReadMessage(-1)
		if err != nil {
			log.Printf("Error to consume message : %s", err.Error())
			continue
		}

		var emailDTO types.EmailDTO
		if err := json.Unmarshal(msg.Value, &emailDTO); err != nil {
			log.Printf("Invalid message : Value (%s) | Error (%s)", err.Error(), string(msg.Value))
			c.Consumer.CommitMessage(msg)
			continue
		}

		err = c.EmailService.Send(emailDTO)
		if err != nil {
			log.Printf("Error to Send Email : %s", err.Error())
			continue
		}

		c.Consumer.CommitMessage(msg)
	}
}

func createConsumer() *kafka.Consumer {
	config := &kafka.ConfigMap{
		"bootstrap.servers":  "localhost:9092",
		"group.id":           "email-id",
		"auto.offset.reset":  "earliest",
		"enable.auto.commit": false,
	}

	consumer, err := kafka.NewConsumer(config)
	if err != nil {
		panic("Error to create Kafka Consumer")
	}

	return consumer
}
