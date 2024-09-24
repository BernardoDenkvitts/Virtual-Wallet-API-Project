package repository

import "github.com/BernardoDenkvitts/EmailSenderService/internal/types"

type ILogRepository interface {
	Save(emailLog *types.EmailLog) error
}