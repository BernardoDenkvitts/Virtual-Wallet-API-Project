package types

import "time"

type EmailStatusType int
var EmailStatus = struct {
	SUCCESS EmailStatusType
	FAIL    EmailStatusType
}{
	SUCCESS: 0,
	FAIL:    1,
}

type EmailDTO struct {
	From    string
	To      string
	Subject string
	Message string
}

func NewEmailDTO(from, to, subject, message string) EmailDTO {
	return EmailDTO{
		From:    from,
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

func NewEmailLog(status EmailStatusType, from, to, subject string, errorMessage string) *EmailLog {
	return &EmailLog{
		Id:           -1,
		Status:       status,
		From:         from,
		To:           to,
		Subject:      subject,
		Timestamp:    time.Now(),
		ErrorMessage: errorMessage,
	}
}
