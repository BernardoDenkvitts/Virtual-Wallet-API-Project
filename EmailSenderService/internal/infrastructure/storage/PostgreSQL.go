package storage

import (
	"database/sql"
	"fmt"
	"log"
	"os"

	_ "github.com/lib/pq"

	"github.com/BernardoDenkvitts/EmailSenderService/internal/types"
)

type PostgreSQL struct {
	db *sql.DB
}

func connection_string() string {
	user := os.Getenv("databaseuser")
	host := os.Getenv("databasehost")
	password := os.Getenv("databasepswd")
	name := os.Getenv("databasename")
	connectionString := fmt.Sprintf("host=%s user=%s dbname=%s password=%s sslmode=disable", host, user, name, password)

	return connectionString
}

func NewPostgreStorage() *PostgreSQL {
	db, err := sql.Open("postgres", connection_string())
	if err != nil {
		panic(err)
	}

	return &PostgreSQL{
		db: db,
	}
}

func (s *PostgreSQL) Init() error {
	query := `CREATE TABLE IF NOT EXISTS emaillog (
		id SERIAL PRIMARY KEY,
		status integer,
		email_from varchar(50),
		email_to varchar(50),
		subject varchar(255),
		timestamp timestamp,
		errorMessage varchar(255)
	);`

	_, err := s.db.Exec(query)
	if err != nil {
		panic(err)
	}

	log.Println("emaillog table created")

	return nil
}

func (s *PostgreSQL) Save(emailLog *types.EmailLog) error {
	tx, _ := s.db.Begin()

	query := `INSERT INTO emaillog (status, email_from, email_to, subject, timestamp, errorMessage)
			   VALUES ($1, $2, $3, $4, $5, $6)`
	_, err := s.db.Exec(query, emailLog.Status, emailLog.From, emailLog.To, emailLog.Subject, emailLog.Timestamp, emailLog.ErrorMessage)
	if err != nil {
		tx.Rollback()
		return err
	}

	tx.Commit()

	log.Println("EmailLog saved")

	return nil
}
