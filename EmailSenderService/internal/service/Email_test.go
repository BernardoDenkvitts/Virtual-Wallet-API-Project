package service

import (
	"os"
	"path/filepath"
	"strconv"
	"testing"

	"github.com/BernardoDenkvitts/EmailSenderService/internal/infrastructure/smtp"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/repository"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/types"
	"github.com/joho/godotenv"
	"github.com/stretchr/testify/assert"
)

func loadEnv() {
	path, _ := os.Getwd()
	envPath := filepath.Join(path, "..", "..", ".env")
	err := godotenv.Load(envPath)
	if err != nil {
		panic("Error to load .env in Email_test.go")
	}
}

func getSmtpConfig() smtp.SMTPConfig {
	loadEnv()

	port, err := strconv.Atoi(os.Getenv("MailTrapPort"))
	if err != nil {
		panic("Fail to get MailTrap Port from .env")
	}

	return smtp.NewSMTPConfig(port, os.Getenv("MailTrapHost"), os.Getenv("MailTrapUser"), os.Getenv("MailTrapPassword"))
}

func getEmailService() *EmailImpl {
	smtpServer := smtp.NewMailTrapServer(getSmtpConfig())
	repository := repository.NewInMemoryRepository()
	return NewEmailImpl(smtpServer, repository)
}

func TestSendEmail(t *testing.T) {
	assert := assert.New(t)
	service := getEmailService()

	email := types.NewEmailDTO("sender@gmail.com", "receiver@gmail.com", "Test Email", "Test email")
	
	assert.Nil(service.Send(email))
}
