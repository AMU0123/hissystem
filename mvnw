#!/bin/sh

# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
# Apache Maven Wrapper startup script, version 3.2.0
#
# Required ENV vars:
# JAVA_HOME - location of a JDK home dir
#
# Optional ENV vars
# MAVEN_BATCH_ECHO - set to 'on' to enable the echoing of the batch commands
# MAVEN_BATCH_PAUSE - set to 'on' to wait for a keystroke before ending
# MAVEN_OPTS - parameters passed to the Java VM when running Maven
#     e.g. to debug Maven itself, use
# MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000
# MAVEN_SKIP_RC - flag to disable loading of mavenrc files
# ----------------------------------------------------------------------------

if [ -z "$MAVEN_SKIP_RC" ] ; then
  if [ -f ~/.mavenrc ] ; then
    . ~/.mavenrc
  fi
fi

MAVEN_PROJECTBASEDIR=$(pwd)

if [ -z "$MAVEN_PROJECTBASEDIR" ] ; then
  MAVEN_PROJECTBASEDIR=$(pwd)
fi

WRAPPER_DIR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper"
WRAPPER_JAR="$WRAPPER_DIR/maven-wrapper.jar"
WRAPPER_LAUNCHER="org.apache.maven.wrapper.MavenWrapperMain"

if [ ! -r "$WRAPPER_JAR" ]; then
  echo "Downloading Maven wrapper..."
  mkdir -p "$WRAPPER_DIR"
  
  if command -v curl > /dev/null; then
    curl -s -o "$WRAPPER_JAR" "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
  elif command -v wget > /dev/null; then
    wget -O "$WRAPPER_JAR" "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
  else
    echo "Error: Neither curl nor wget is available to download the Maven wrapper." >&2
    exit 1
  fi
  
  if [ $? -ne 0 ]; then
    echo "Error: Failed to download Maven wrapper." >&2
    exit 1
  fi
fi

# If specified, validate the SHA-256 sum of the Maven wrapper jar
if [ -n "$MAVEN_WRAPPER_SHA_256_SUM" ]; then
  if command -v sha256sum > /dev/null; then
    SHA_256_SUM=$(sha256sum "$WRAPPER_JAR" | cut -d' ' -f1)
  elif command -v shasum > /dev/null; then
    SHA_256_SUM=$(shasum -a 256 "$WRAPPER_JAR" | cut -d' ' -f1)
  else
    echo "Warning: Unable to validate SHA-256 sum of Maven wrapper jar - neither sha256sum nor shasum is available." >&2
    SHA_256_SUM=""
  fi
  
  if [ "$SHA_256_SUM" != "$MAVEN_WRAPPER_SHA_256_SUM" ]; then
    echo "Error: SHA-256 sum of Maven wrapper jar doesn't match expected value." >&2
    exit 1
  fi
fi

# Determine Java executable
if [ -n "$JAVA_HOME" ]; then
  JAVA_EXE="$JAVA_HOME/bin/java"
else
  JAVA_EXE="java"
fi

# Verify Java executable exists
if [ ! -x "$JAVA_EXE" ]; then
  echo "Error: JAVA_HOME is not set correctly." >&2
  echo "Please set the JAVA_HOME variable in your environment to match the location of your Java installation." >&2
  exit 1
fi

# Run Maven wrapper
"$JAVA_EXE" "-Dmaven.multiModuleProjectDirectory=$MAVEN_PROJECTBASEDIR" -classpath "$WRAPPER_JAR" "$WRAPPER_LAUNCHER" "$@"