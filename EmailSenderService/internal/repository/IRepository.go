package repository

import "github.com/BernardoDenkvitts/EmailSenderService/internal/types"

type IRepository interface {
	Save(emailLog *types.EmailLog) error
}