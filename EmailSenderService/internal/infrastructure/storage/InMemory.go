package storage

import (
	"math/rand/v2"

	"github.com/BernardoDenkvitts/EmailSenderService/internal/types"
)

type InMemoryStorage struct {
	Db *[]types.EmailLog
}

func NewInMemoryStorage() *InMemoryStorage {
	return &InMemoryStorage{}
}

func (rp *InMemoryStorage) Init() error {
	rp.Db = &[]types.EmailLog{}
	return nil
}

func (rp *InMemoryStorage) Save(emailLog *types.EmailLog) error {
	emailLog.Id = rand.IntN(1001-1+1) + 1
	*rp.Db = append(*rp.Db, *emailLog)

	return nil
}
