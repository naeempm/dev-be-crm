@echo off
echo ============================================
echo   MySQL Root Password Reset Script
echo   New password will be: bmsroot
echo ============================================
echo.

set "INIT_FILE=C:\ProgramData\MySQL\mysql-init.txt"
echo ALTER USER 'root'@'localhost' IDENTIFIED BY 'bmsroot'; > "%INIT_FILE%"

echo [1/3] Stopping MySQL80 service...
net stop MySQL80
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Could not stop MySQL. Are you running as Administrator?
    echo Please right-click this file and select "Run as administrator".
    pause
    exit /b 1
)

echo.
echo [2/3] Resetting root password using official MySQL init-file...
start "" /B "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqld.exe" --defaults-file="C:\ProgramData\MySQL\MySQL Server 8.0\my.ini" --init-file="%INIT_FILE%" --console
timeout /t 5 /nobreak >nul

taskkill /f /im mysqld.exe >nul 2>&1
timeout /t 3 /nobreak >nul

if exist "%INIT_FILE%" del "%INIT_FILE%"

echo.
echo [3/3] Starting MySQL80 service normally...
net start MySQL80

echo.
echo ============================================
echo   SUCCESS! MySQL root password is now: bmsroot
echo ============================================
echo.
echo You can now close this window.
pause