#!/usr/bin/env bash

# Script path
SCRIPT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# cert file name
cert_file_path=${1:-certificate.crt}

jcert_pwd="changeit"
jcert_authserver="eshop-authserver"

echo "$jcert_pwd" | keytool -delete -cacerts -alias "$jcert_authserver" > /dev/null 2>&1

eshop_authserver="$(echo "$jcert_pwd" | keytool -list -cacerts -v | grep "Alias name" |grep "$jcert_authserver")"

# if eshop_authserver not exists
while [ -z "$eshop_authserver" ]
do
  echo "import certificate...."
  echo "$jcert_pwd" | keytool -importcert -noprompt -trustcacerts -cacerts -storepass changeit -keypass changeit \
  -ext SAN=DNS:"authorization-service",IP:127.0.0.1 \
  -ext CN="authorization-service" \
  -file "$SCRIPT_PATH/$cert_file_path" -alias "$jcert_authserver"

  # reset eshop_authserver certificate
  eshop_authserver="$(echo "$jcert_pwd" | keytool -list -cacerts -v | grep "Alias name" |grep "$jcert_authserver")"
done

eshop_authserver="$(echo "changeit" | keytool -list -cacerts -v | grep "Alias name" |grep "$jcert_authserver")"
echo "certificate imported as alias: $eshop_authserver"
