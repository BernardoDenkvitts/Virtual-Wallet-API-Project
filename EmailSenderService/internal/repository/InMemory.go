package repository

import (
	"math/rand/v2"

	"github.com/BernardoDenkvitts/EmailSenderService/internal/types"
)

type InMemory struct {
	Db *[]types.EmailLog
}

func NewInMemoryRepository() *InMemory {
	return &InMemory{
		Db: &[]types.EmailLog{},
	}
}

func (rp *InMemory) Save(emailLog *types.EmailLog) error {
	emailLog.Id = rand.IntN(1001-1+1) + 1
	*rp.Db = append(*rp.Db, *emailLog)

	return nil
}
