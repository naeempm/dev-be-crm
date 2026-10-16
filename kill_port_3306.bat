@echo off
:: Self-elevation to Administrator
net session >nul 2>&1
if %errorlevel% neq 0 (
    echo Requesting Administrator privileges...
    powershell -Command "Start-Process '%~f0' -Verb RunAs"
    exit /b
)

echo ======================================================
echo Stopping MySQL Services and Freeing Port 3306...
echo ======================================================
echo.

echo [1/3] Stopping MySQL services...
net stop MySQL80 >nul 2>&1
net stop MySQL84 >nul 2>&1
net stop MySQL >nul 2>&1

echo [2/3] Terminating any remaining mysqld processes...
taskkill /F /IM mysqld.exe >nul 2>&1

echo [3/3] Searching and freeing port 3306...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :3306') do (
    if not "%%a"=="" (
        echo Killing PID %%a on port 3306...
        taskkill /F /PID %%a >nul 2>&1
    )
)

echo.
echo ======================================================
echo Done! Port 3306 has been cleared.
echo ======================================================
timeout /t 4
