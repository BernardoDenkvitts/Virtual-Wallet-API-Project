package types

import (
	"os"
	"time"

	"github.com/go-playground/validator/v10"
)

type EmailStatusType int

var EmailStatus = struct {
	SUCCESS EmailStatusType
	FAIL    EmailStatusType
}{
	SUCCESS: 0,
	FAIL:    1,
}

type EmailDTO struct {
	To      string `validate:"min=3"`
	Subject string
	Message string `validate:"min=1"`
}

func NewEmailDTO(to, subject, message string) EmailDTO {
	return EmailDTO{
		To:      to,
		Subject: subject,
		Message: message,
	}
}

type EmailLog struct {
	Id           int
	Status       EmailStatusType
	From         string
	To           string
	Subject      string
	Timestamp    time.Time
	ErrorMessage string
}

func NewEmailLog(status EmailStatusType, to, subject string, errorMessage string) *EmailLog {
	return &EmailLog{
		Id:           -1,
		Status:       status,
		From:         os.Getenv("emailuser"),
		To:           to,
		Subject:      subject,
		Timestamp:    time.Now(),
		ErrorMessage: errorMessage,
	}
}

func ValidateStruct(object any) error {
	validate := validator.New()
	if err := validate.Struct(object); err != nil {
		return err
	}

	return nil
}
