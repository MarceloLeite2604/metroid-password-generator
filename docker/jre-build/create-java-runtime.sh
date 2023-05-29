#!/bin/sh

set -e

set \
  java.base \
  java.sql \
  java.xml \
  java.desktop \
  java.naming \
  java.management

IFS=","
add_modules_value=$*
readonly add_modules_value
IFS=" "

if [ -n "$JAVA_HOME" ]; then
  jlink_bin="$JAVA_HOME/bin/jlink"
else
  jlink_bin=$(which jlink)
fi

[ -z "$jlink_bin" ] && echo >&2 "Could not find jlink binary." && exit 1

echo "Jlink found on \"$jlink_bin\" path."
echo "The following modules will be added on Java runtime: $add_modules_value"

"$jlink_bin" \
  --add-modules "$add_modules_value" \
  --strip-java-debug-attributes \
  --no-man-pages \
  --no-header-files \
  --compress=2 \
  --output /tmp/java-runtime
