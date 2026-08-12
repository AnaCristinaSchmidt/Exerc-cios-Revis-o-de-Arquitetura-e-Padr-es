@echo off
setlocal
set "MAVEN_VERSION=3.9.11"
set "MAVEN_DIR=%~dp0.mvn\apache-maven-%MAVEN_VERSION%"
if not exist "%MAVEN_DIR%\bin\mvn.cmd" (
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $d='%~dp0.mvn'; New-Item -ItemType Directory -Force -Path $d | Out-Null; $z=Join-Path $d 'maven.zip'; Invoke-WebRequest 'https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/apache-maven-%MAVEN_VERSION%-bin.zip' -OutFile $z; Expand-Archive -Force $z $d; Remove-Item $z"
  if errorlevel 1 exit /b 1
)
call "%MAVEN_DIR%\bin\mvn.cmd" %*
