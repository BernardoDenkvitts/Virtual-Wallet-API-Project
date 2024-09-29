package storage

import "github.com/BernardoDenkvitts/EmailSenderService/internal/types"

type IStorage interface {
	Init() error
	Save(emailLog *types.EmailLog) error
}
