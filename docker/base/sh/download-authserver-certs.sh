#!/usr/bin/env bash

# Script path
SCRIPT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

cert_file_path=${1:-certificate.crt}

# if file not exists or is empty
while [ ! -s "$SCRIPT_PATH/$cert_file_path" ]
do
  echo "download certificate..."
  echo | openssl s_client -servername authorization-service -connect authorization-service:8443 |\
  sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > "$SCRIPT_PATH/$cert_file_path"
done
