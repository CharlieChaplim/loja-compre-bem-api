@echo off
setlocal
set "ROOT=%~dp0"
set "MAVEN_CMD=%ROOT%.mvn\wrapper\dists\apache-maven-3.10.0\bin\mvn.cmd"
if exist "%MAVEN_CMD%" goto run
where mvn >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    mvn %*
    exit /b %ERRORLEVEL%
)
echo Maven nao encontrado. Baixando automaticamente o Maven 3.10.0...
powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $dir=Join-Path '%ROOT%' '.mvn\wrapper\dists'; New-Item -ItemType Directory -Force -Path $dir ^| Out-Null; $zip=Join-Path $dir 'maven.zip'; Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.10.0/apache-maven-3.10.0-bin.zip' -OutFile $zip; Expand-Archive -LiteralPath $zip -DestinationPath $dir -Force; Remove-Item $zip"
if errorlevel 1 (
    echo Falha ao baixar Maven. Verifique a internet e o Java 21.
    exit /b 1
)
:run
call "%MAVEN_CMD%" %*
exit /b %ERRORLEVEL%
