package utils

import (
	"os"

	"github.com/joho/godotenv"
)

func LoadEnv(path_folder_to_env string) {
	path, _ := os.Getwd()
	err := godotenv.Load(path + path_folder_to_env)
	if err != nil {
		panic("Error to load .env")
	}
}
