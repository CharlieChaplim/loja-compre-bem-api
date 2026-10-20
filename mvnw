#!/bin/sh
# Maven launcher simples. Usa Maven instalado ou baixa a distribuicao do projeto.
set -eu
ROOT=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
MAVEN_CMD="$ROOT/.mvn/wrapper/dists/apache-maven-3.10.0/bin/mvn"
if [ -x "$MAVEN_CMD" ]; then
  exec "$MAVEN_CMD" "$@"
fi
if command -v mvn >/dev/null 2>&1; then
  exec mvn "$@"
fi
DIST="$ROOT/.mvn/wrapper/dists"
mkdir -p "$DIST"
ZIP="$DIST/maven.zip"
URL="https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.10.0/apache-maven-3.10.0-bin.zip"
if command -v curl >/dev/null 2>&1; then
  curl -fLsS "$URL" -o "$ZIP"
elif command -v wget >/dev/null 2>&1; then
  wget -q "$URL" -O "$ZIP"
else
  echo 'Instale Maven ou curl/wget para baixar o Maven automaticamente.' >&2
  exit 1
fi
unzip -q -o "$ZIP" -d "$DIST"
rm -f "$ZIP"
exec "$MAVEN_CMD" "$@"
