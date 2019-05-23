jar cfm DVDSearchApp.jar manifest.txt lib dvdsearchapp
mkdir C:\DVDSearchApp
copy DVDSearchApp.jar C:\DVDSearchApp\DVDSearchApp.jar
copy config.properties C:\DVDSearchApp\config.properties
mkdir C:\DVDSearchApp\lib
copy lib\mysql-connector-java.jar C:\DVDSearchApp\lib\mysql-connector-java.jar
copy UnInstall.bat C:\DVDSearchApp\UnInstall.bat