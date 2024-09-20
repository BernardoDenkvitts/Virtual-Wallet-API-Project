package smtp

import "gopkg.in/gomail.v2"

type ISmtpServer interface {
	SendEmail(from, to, subject, body string) error
}

type MailTrapServer struct {
	config SMTPConfig
}

func NewMailTrapServer(config SMTPConfig) *MailTrapServer {
	return &MailTrapServer{config: config}
}

func (m *MailTrapServer) SendEmail(from, to, subject, body string) error {
	msg := gomail.NewMessage()

	msg.SetHeader("From", from)
	msg.SetHeader("To", to)
	msg.SetHeader("Subject", subject)
	msg.SetBody("text/html", body)

	dialer := gomail.NewDialer(m.config.Host, m.config.Port, m.config.User, m.config.Password)

	return dialer.DialAndSend(msg)
}
