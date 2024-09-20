package service

import (
	"log"

	"github.com/BernardoDenkvitts/EmailSenderService/internal/infrastructure/smtp"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/repository"
	"github.com/BernardoDenkvitts/EmailSenderService/internal/types"
)

type IEmail interface {
	Send(email types.EmailDTO) bool
}

type EmailImpl struct {
	SmtpServer smtp.ISmtpServer
	Repository repository.IRepository
}

func NewEmailImpl(smtpServer smtp.ISmtpServer, repository repository.IRepository) *EmailImpl {
	return &EmailImpl{SmtpServer: smtpServer, Repository: repository}
}

func (e *EmailImpl) Send(email types.EmailDTO) error {
	log.Printf("Sending email from %s to %s", email.From, email.To)

	var err error
	try := 0

	for try < 3 {
		err = e.SmtpServer.SendEmail(email.From, email.To, email.Subject, formatEmailMessage(email.Message))
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

	e.Repository.Save(emailLog)

	return err
}

func formatEmailMessage(message string) string {
	return "<p>" + message + "</p>"
}

func setEmailAsFailed(emailLog *types.EmailLog, errorMessage string) *types.EmailLog {
	emailLog.Status = types.EmailStatus.FAIL
	emailLog.ErrorMessage = errorMessage

	return emailLog
}
