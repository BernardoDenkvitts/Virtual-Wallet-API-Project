package smtp

import (
	"gopkg.in/gomail.v2"
)

type SmtpServer struct {
	config SMTPConfig
}

func NewSmtpServer(config SMTPConfig) *SmtpServer {
	return &SmtpServer{config: config}
}

func (s *SmtpServer) SendEmail(to, subject, body string) error {
	msg := gomail.NewMessage()

	msg.SetHeader("From", s.config.User)
	msg.SetHeader("To", to)
	msg.SetHeader("Subject", subject)
	msg.SetBody("text/html", body)

	dialer := gomail.NewDialer(s.config.Host, s.config.Port, s.config.User, s.config.Password)

	return dialer.DialAndSend(msg)
}
