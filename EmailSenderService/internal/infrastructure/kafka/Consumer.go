package kafka

import (
	"encoding/json"
	"log"
	"os"

	"github.com/BernardoDenkvitts/EmailSenderService/internal/service"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/types"
	"github.com/confluentinc/confluent-kafka-go/v2/kafka"
)

/*
	Is not necessary to create topic using AdminClient because
	docker configuration has KAFKA_AUTO_CREATE_TOPICS_ENABLE to TRUE
*/

type IConsumer interface {
	Consume()
}

type KafkaConsumer struct {
	Consumer     *kafka.Consumer
	EmailService service.IEmail
}

func NewKafkaConsumer(topics []string, emailService service.IEmail) *KafkaConsumer {
	consumer := createConsumer()
	consumer.SubscribeTopics(topics, nil)

	return &KafkaConsumer{Consumer: consumer, EmailService: emailService}
}

func (c *KafkaConsumer) Consume() {
	for {
		msg, err := c.Consumer.ReadMessage(-1)
		if err != nil {
			log.Printf("Error to consume message : %s", err.Error())
			continue
		}

		var emailDTO types.EmailDTO
		json.Unmarshal(msg.Value, &emailDTO)

		if err := types.ValidateStruct(emailDTO); err != nil {
			log.Printf("Invalid message : Value (%s)", string(msg.Value))
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
		"bootstrap.servers":  os.Getenv("kafkaserver"),
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
