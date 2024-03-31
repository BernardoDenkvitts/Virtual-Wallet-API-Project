#!/bin/bash
set -e
mongosh <<EOF
  use virtualwallet
  db.createUser(
      {
          user: "admin",
          pwd: "admin",
          roles: [
              {
                  role: "readWrite",
                  db: "virtualwallet"
              }
          ]
      }
)
EOF