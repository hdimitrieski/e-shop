#!/usr/bin/env bash

# Script path
SCRIPT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Cert file name
file="certificate.crt"

# exist
if [ -f "$file" ];then
  echo "certificate exists, will not download"
else
  # Download certs
  "$SCRIPT_PATH"/download-authserver-certs.sh "$file"
fi


# Import auth server certs.sh
"$SCRIPT_PATH"/import-authserver-certs.sh "$file"
