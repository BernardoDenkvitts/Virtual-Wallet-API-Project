package service

import (
	"log"

	"github.com/BernardoDenkvitts/EmailSenderService/internal/infrastructure/smtp"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/infrastructure/storage"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/types"
)

type IEmail interface {
	Send(email types.EmailDTO) error
}

type EmailImpl struct {
	SmtpServer *smtp.SmtpServer
	Storage    storage.IStorage
}

func NewEmailImpl(smtpServer *smtp.SmtpServer, storage storage.IStorage) *EmailImpl {
	return &EmailImpl{SmtpServer: smtpServer, Storage: storage}
}

func (e *EmailImpl) Send(email types.EmailDTO) error {
	log.Printf("Sending email from %s to %s", email.From, email.To)

	var err error
	try := 0

	for try < 3 {
		err = e.SmtpServer.SendEmail(email.From, email.To, email.Subject, formatEmailMessage(email.Subject, email.Message))
		if err == nil {
			log.Println("Email sent sucessfully")
			break
		}

		log.Printf("Fail to send email : %s", err.Error())
		try += 1
	}

	emailLog := types.NewEmailLog(types.EmailStatus.SUCCESS, email.From, email.To, email.Subject, "")
	if try == 3 {
		setEmailAsFailed(emailLog, err.Error())
	}

	e.Storage.Save(emailLog)

	return err
}

func formatEmailMessage(subject, message string) string {
	return `
		<div style="font-family: Arial, sans-serif; font-size: 16px; color: #333; background-color: #f9f9f9; padding: 20px; border: 1px solid #ddd; border-radius: 8px; max-width: 600px; margin: auto;">
			<h2 style="text-align: center;">` + subject + `</h2>
			<p style="text-align: center;">` + message + `</p>
			<hr style="border: 0; height: 1px; background-color: #ddd;">
			<p style="text-align: center; font-size: 12px; color: #999;">This is an automated message. Please do not reply.</p>
		</div>
	`
}

func setEmailAsFailed(emailLog *types.EmailLog, errorMessage string) *types.EmailLog {
	emailLog.Status = types.EmailStatus.FAIL
	emailLog.ErrorMessage = errorMessage

	return emailLog
}
