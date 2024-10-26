package kafka

import (
	"context"
	"log"
	"os"

	"github.com/confluentinc/confluent-kafka-go/v2/kafka"
)

func getConfig() *kafka.ConfigMap {
	return &kafka.ConfigMap{
		"bootstrap.servers":  os.Getenv("kafkaserver"),
		"group.id":           "email-id",
		"auto.offset.reset":  "earliest",
		"enable.auto.commit": false,
	}
}

type KafkaConfig struct {
	admin *kafka.AdminClient
}

func NewKafkaConfig() *KafkaConfig {
	admin, _ := kafka.NewAdminClient(getConfig())
	return &KafkaConfig{
		admin: admin,
	}
}

func (k *KafkaConfig) CreateTopic() {
	topics, _ := k.admin.GetMetadata(nil, true, 1000)

	if _, exists := topics.Topics[os.Getenv("emailtopic")]; !exists {
		ctx, cancel := context.WithCancel(context.Background())
		defer cancel()

		_, err := k.admin.CreateTopics(
			ctx,
			[]kafka.TopicSpecification{{Topic: os.Getenv("emailtopic"), NumPartitions: 3, ReplicationFactor: 1}},
		)
		if err != nil {
			panic("Fail to create topic")
		}

		k.admin.Close()
		log.Println("Email topic created")
	}
}

func (k *KafkaConfig) CreateEmailConsumer() *kafka.Consumer {
	consumer, err := kafka.NewConsumer(getConfig())
	if err != nil {
		panic("Error to create Kafka Consumer")
	}

	consumer.SubscribeTopics([]string{os.Getenv("emailtopic")}, nil)

	return consumer
}
