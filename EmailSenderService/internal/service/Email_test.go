package service

import (
	"os"
	"strconv"
	"testing"

	"github.com/BernardoDenkvitts/EmailSenderService/internal/infrastructure/smtp"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/infrastructure/storage"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/types"
	"github.com/BernardoDenkvitts/EmailSenderService/utils"
	"github.com/stretchr/testify/assert"
)

func getSmtpConfig() smtp.SMTPConfig {
	utils.LoadEnv("./../../.env-dev")

	port, err := strconv.Atoi(os.Getenv("emailport"))
	if err != nil {
		panic("Fail to get Port from .env")
	}

	return smtp.NewSMTPConfig(port, os.Getenv("emailhost"), os.Getenv("emailuser"), os.Getenv("emailpassword"))
}

func getEmailService() *EmailImpl {
	smtpServer := smtp.NewSmtpServer(getSmtpConfig())
	storage := storage.NewInMemoryStorage()
	storage.Init()
	return NewEmailImpl(smtpServer, storage)
}

func TestSendEmail(t *testing.T) {
	assert := assert.New(t)
	service := getEmailService()

	email := types.NewEmailDTO("bernardoarcari@gmail.com", "Test", "Test email")

	assert.Nil(service.Send(email))
}
