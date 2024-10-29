# lab-1

## Запуск сервиса

Для запуска лабы:
* Запустить базу postgres через docker-compose.yml
* Запустить сервер `gradle clean war appRun`

сервер который разворачивает war - tomcat10

## Как понять, что сервер готов работать?

http://localhost:9090/ClientService?wsdl

переходите и тут должен быть wsdl
