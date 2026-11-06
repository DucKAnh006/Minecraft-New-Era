@echo off
setlocal enabledelayedexpansion
chcp 65001 >nul
title Minecraft New Era Mod Launcher

:: Chuyển về thư mục gốc của dự án (nơi chứa gradlew.bat)
if exist "%~dp0gradlew.bat" (
    cd /d "%~dp0"
) else if exist "%~dp0..\gradlew.bat" (
    cd /d "%~dp0.."
) else (
    echo [LỖI] Không tìm thấy file gradlew.bat!
    echo Vui lòng đảm bảo script được đặt trong thư mục dự án Minecraft.
    pause
    exit /b 1
)

:menu
cls
echo ======================================================
echo             MINECRAFT NEW ERA MOD LAUNCHER            
echo ======================================================
echo.
echo   [1] Chạy Game (Run Client - Tối ưu tốc độ Offline)
echo   [2] Chạy Game Online (Run Client - Cập nhật dependencies)
echo   [3] Đóng gói Mod (Build JAR vào build/libs/)
echo   [4] Dọn dẹp cache dự án (Clean Cache)
echo   [5] Dừng tiến trình Gradle chạy ngầm (Stop Daemon)
echo   [0] Thoát (Exit)
echo.
echo ======================================================
set "choice="
set /p "choice=Nhập lựa chọn của bạn [0-5]: "

if "%choice%"=="1" goto run_game
if "%choice%"=="2" goto run_game_online
if "%choice%"=="3" goto build_mod
if "%choice%"=="4" goto clean_project
if "%choice%"=="5" goto stop_daemon
if "%choice%"=="0" goto exit_script
if /i "%choice%"=="exit" goto exit_script
if /i "%choice%"=="q" goto exit_script

echo.
echo [!] Lựa chọn không hợp lệ, vui lòng thử lại...
ping 127.0.0.1 -n 2 >nul
goto menu

:run_game
cls
echo ======================================================
echo [1/2] Đang khởi chạy Minecraft Client (Offline mode)...
echo ======================================================
echo.
call gradlew.bat --offline runClient
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ======================================================
    echo [THÔNG BÁO] Chạy Offline không thành công (Mã lỗi: %ERRORLEVEL%).
    echo Có thể do thiếu thư viện cache hoặc trò chơi bị tắt đột ngột.
    echo ======================================================
    set "retry="
    set /p "retry=Bạn có muốn thử chạy lại với chế độ Online không? (Y/n): "
    if /i "!retry!"=="n" goto return_menu
    goto run_game_online
)
goto return_menu

:run_game_online
cls
echo ======================================================
echo Đang khởi chạy Minecraft Client (Online mode)...
echo ======================================================
echo.
call gradlew.bat runClient
goto return_menu

:build_mod
cls
echo ======================================================
echo Đang tiến hành đóng gói (Build) Mod...
echo ======================================================
echo.
call gradlew.bat build
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ======================================================
    echo [THÀNH CÔNG] File mod .jar đã được tạo tại:
    if exist "build\libs" (
        dir /b "build\libs\*.jar" 2>nul
        echo Đường dẫn thư mục: %CD%\build\libs\
    )
    echo ======================================================
) else (
    echo.
    echo ======================================================
    echo [THẤT BẠI] Quá trình Build gặp lỗi.
    echo ======================================================
)
goto return_menu

:clean_project
cls
echo ======================================================
echo Đang dọn dẹp thư mục build (gradlew clean)...
echo ======================================================
echo.
call gradlew.bat clean
echo.
echo Đã dọn dẹp hoàn tất!
goto return_menu

:stop_daemon
cls
echo ======================================================
echo Đang dừng tất cả tiến trình Gradle Daemon chạy ngầm...
echo ======================================================
echo.
call gradlew.bat --stop
echo.
echo Đã dừng tất cả Gradle Daemon!
goto return_menu

:return_menu
echo.
echo ======================================================
echo Tiến trình đã kết thúc. Nhấn phím bất kỳ để về Menu...
echo ======================================================
pause >nul
goto menu

:exit_script
echo.
echo Tạm biệt! Đang thoát...
exit /b 0
