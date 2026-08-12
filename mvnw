#!/bin/sh
set -e
MAVEN_VERSION=3.9.11
BASE_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
MAVEN_DIR="$BASE_DIR/.mvn/apache-maven-$MAVEN_VERSION"
if [ ! -x "$MAVEN_DIR/bin/mvn" ]; then
  mkdir -p "$BASE_DIR/.mvn"
  ARCHIVE="$BASE_DIR/.mvn/maven.tar.gz"
  URL="https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/$MAVEN_VERSION/apache-maven-$MAVEN_VERSION-bin.tar.gz"
  if command -v curl >/dev/null 2>&1; then curl -fsSL "$URL" -o "$ARCHIVE"; else wget -q "$URL" -O "$ARCHIVE"; fi
  tar -xzf "$ARCHIVE" -C "$BASE_DIR/.mvn"
  rm "$ARCHIVE"
fi
exec "$MAVEN_DIR/bin/mvn" "$@"
