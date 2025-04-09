@echo off
cd /d "%~dp0"
if not exist "bin" mkdir bin
javac -cp "lib/json-20210307.jar" -d bin src/main/*.java src/model/routing/*.java src/model/passanger/*.java  src/model/payment/*.java src/model/vehicle/*.java   src/service/*.java src/ui/*.java src/ui/dialog/*.java 
if %errorlevel% neq 0 exit /b %errorlevel%
java -cp "bin;lib/json-20210307.jar" main.App
pause
