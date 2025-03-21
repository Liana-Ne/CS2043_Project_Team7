@echo off
mkdir bin 2>nul
javac -d bin ^
src/FlightManagementSystem/Passenger.java ^
src/FlightManagementSystem/Flight.java ^
src/FlightManagementSystem/Booking.java ^
src/FlightManagementSystem/BookingService.java ^
src/FlightManagementSystem/FlightService.java ^
src/FlightManagementSystem/WeatherInfo.java ^
src/FlightManagementSystem/WeatherService.java ^
src/FlightManagementSystem/PackingList.java ^
src/FlightManagementSystem/FlightManagementGUI.java ^
src/Main.java
if %errorlevel% equ 0 (
    java -cp bin Main
) else (
    echo Compilation failed!
    pause
) 