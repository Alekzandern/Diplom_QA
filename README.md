 Веб-сервис "Путешествие дня"

Сервис предлагает купить тур по определённой цене, одним из 2 способов:

 - плата по дебетовой карте
 - плата по кредитной карте Само приложение не обрабатывает данные по картам, а отправляет их банковским сервисам:
 - сервису платежей, далее Payment Gate;
 - кредитному сервису, далее Credit Gate;
Приложение в собственной СУБД сохраняет информацию о том, успешно ли был совершён платёж и каким способом.

 Начало работы

GitHUB - склонировать данный проект Для запуска тестов потребуется следующее ПО:

 - IntelliJ IDEA
 - Git
 - Docker Desktop
 - Google Chrome

 Установка и запуск

Запускаем контейнеры с помощью команды в терминале

docker-compose up

Запускаем SUT.

для MySQL:
 - консоле ввести команду: java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar

для PostgreSQL:
 - консоле ввести команду: java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

Запускаем авто-тесты

для MySQL:
 - консоле ввести команду: ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app
для PostgreSQL:
 - консоле ввести команду: ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"

Генерируем отчёт по итогам тестирования с помощью Allure командой:

 - ./gradlew allureServe

Отчет автоматически откроется в браузере.

После работы с отчетом, останавливаем работу АllureServe в терминале при помощи сочетания клавиш Ctrl + C и подтверждаем в терминале клавишей Y
Согласно плана проведено автоматизированное тестирование веб-сервиса.

В ходе тестирования были реализованы позитивные и негативные сценарии, API сценарии с проверкой результатов выполнения операции в базе данных
Проверенна заявленная поддержка двух СУБД:
 MySQL;
 PostgreSQL.

Тест-кейсы

Общее колличество тест-кейсов:
 67

Общее колличество дефектов, найденных с помощью тест-кейсов:
 19

Общие рекомендации

По результатам тестирования необходимо:
 Исправить выявленные дефекты.
plan.md
 
