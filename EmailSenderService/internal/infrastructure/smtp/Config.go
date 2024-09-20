package smtp

type SMTPConfig struct {
	Host     string
	Port     int
	User     string
	Password string
}

func NewSMTPConfig(port int, host, user, password string) SMTPConfig {
	return SMTPConfig{
		Host:     host,
		Port:     port,
		User:     user,
		Password: password,
	}
}
