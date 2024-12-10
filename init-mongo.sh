#!/usr/bin/bash

set -e

mongosh -u admin -p admin --authenticationDatabase admin <<EOF

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