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

type KafkaEmailConsumer struct {
	Consumer     *kafka.Consumer
	EmailService service.IEmail
}

func NewKafkaEmailConsumer(consumer *kafka.Consumer, emailService service.IEmail) *KafkaEmailConsumer {
	return &KafkaEmailConsumer{Consumer: consumer, EmailService: emailService}
}

func (c *KafkaEmailConsumer) Consume() {
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
