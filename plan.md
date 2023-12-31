**План тестирования веб-сервиса, который предлагает купить тур по определённой цене двумя способами.**

 ### 1.Перечень автоматизируемых сценариев

Перейдя на страницу "Путешествие дня", купить билет можно двумя способами:

1 способ. Внизу страницы слева белая кнопка "Купить" приложение пересылает их банковскому сервису платежей, далее Payment Gate.

2 способ. Внизу страницы слева красная кнопка "Купить в кредит" (рядом с кнопкой "Купить") приложение пересылает их банковскому кредитному сервису, далее Credit Gate.


-API тестирование

-Позитивные проверки

-Проверка поддержки MySQL 

-Проверка корректности данных записанных в БД

-UI тестирование

**Позитивное тестирование:**
1.	Ввести валидное имя на кириллице в поле Имя например “Алик”
2.	Ввести валидный номер телефона из 11 цифр “79123456789”
3.	Нажать на кнопку "Купить"

_Ожидаемый результат - Появляется сообщение об успешной отправки заявки._

**Негативное тестирование 1.:**
1.	Ввести не валидное значение (спецсимволы) в поле Имя “$%$^%”
2.	Ввести валидный номер телефона из 11 цифр “79123456789”
3.	Нажать на кнопку "Купить"

_Ожидаемый результат - Появляется сообщение об ошибке, поле Имя не может содержать спец символы._

**Негативное тестирование 2.:**
1.	Ввести не валидное имя на латинице в поле Имя “Alik”
2.	Ввести валидный номер телефона из 11 цифр “79123456789”
3.	Нажать на кнопку "Купить"

_Ожидаемый результат - Появляется сообщение об ошибке, поле Имя заполнено некорректно._

**Негативное тестирование 3.:**
1.	Ввести не валидное имя, состоящее из 1 буквы на кириллице “Б”
2.	Ввести валидный номер телефона из 11 цифр “79123456789”
3.	Нажать на кнопку "Купить"

_Ожидаемый результат - Появляется сообщение об ошибке, поле Имя Должно быть не короче 2 Символов._

**Негативное тестирование 4.:**
1.	Оставить поле Имя пустым
2.	Оставить поле Телефон пустым
3.	Нажать на кнопку "Купить"

_Ожидаемый результат - Появляется сообщение об ошибке, обязательное поле._

**Негативное тестирование 5.:**
1.	Ввести не валидное Имя, состоящее из цифр “75675675”
2.	Ввести валидный номер телефона из 11 цифр “79123456789”
3.	Нажать на кнопку "Купить"

_Ожидаемый результат - Появляется сообщение об ошибке, поле Имя заполнено некорректно._

**Негативное тестирование 6.:**
1.	Ввести не валидное имя, состоящее из 64 букв на латинице
2.	Ввести валидный номер телефона из 11 цифр “79123456789”
3.	Нажать на кнопку "Купить"

_Ожидаемый результат - Появляется сообщение об ошибке, слишком длинное поле._

**Негативное тестирование 7.:**
1.	Ввести валидное имя на кириллице в поле Имя “Алик”
2.	Ввести не валидный номер телефона из 1 цифры “q”
3.	Нажать на кнопку "Купить"

_Ожидаемый результат - Появляется сообщение об ошибке, поле Телефон заполнено некорректно._

**Негативное тестирование 8.:**
1.	Ввести валидное имя на кириллице в поле Имя “Алик”
2.	Ввести не валидный номер телефона из 16 цифр “79123456789123456”
3.	Нажать на кнопку "Купить"

_Ожидаемый результат - Появляется сообщение об ошибке, поле Телефон заполнено некорректно._

**Негативное тестирование 9.:**
1.	Ввести валидное имя на кириллице в поле Имя “Алик”
2.	Ввести буквы кириллицы в поле Телефон “аппррпо”
3.	Нажать на кнопку "Купить"

_Ожидаемый результат - Появляется сообщение об ошибке, поле Телефон заполнено некорректно._

 ### 2.Перечень используемых инструментов с обоснованием выбора.

 - Postman - сервис для создания, тестирования, документирования, публикации и обслуживания API. 
 - Java - широко используемый, быстрый, безопасный и надежный язык программирования
 - DBeaver - программа для работы с СУБД (создание новых баз, изменение и удаление данных в уже существующих, выполнение SQL-запросов).
 - Docker - платформа для быстрой разработки, тестирования, масштабирования и развертывания приложений.
 - Intelij IDEA - интегрированная и мощная среда разработки для многих языков программирования, в частности Java, JavaScript, Python...
 - Faker - библиотека,  генерирующая случайные данные.
 - Selenide - фреймворк для написания удобных для чтения и обслуживания автоматизированных тестов на Java. Selenide позволяет намного быстрее и дешевле автоматизировать процесс тестирования.
 - Allure - фреймворк для создания простых и понятных отчётов автотестов, позволяет более точно определить локацию дефекта
 - Gradle - система автоматизации сборки с полностью открытым исходным кодом, имеет более компактный сценарий сборки.
 - ОС Windows 10 - операционная система для персональных компьютеров и рабочих станций, разработанная корпорацией Microsoft в рамках семейства Windows NT.
 - Node.js - кроссплатформенная среда исполнения с открытым исходным кодом, которая позволяет разработчикам создавать всевозможные серверные инструменты и приложения используя язык JavaScript. Среда исполнения предназначена для использования вне контекста браузера (т.е. выполняется непосредственно на компьютере или на серверной ОС).
 - CI - система непрерывной интеграции, позволяет отслеживать наличие ошибок, чтобы сборка не упала

 ### 3.Перечень и описание возможных рисков при автоматизации.

 - Некорректная работа приложения, при сырой сборке.
 - Отсутствие тестовых меток.
 - Устаревание автотестов при изменении приложения.

 ### 4.Интервальная оценка с учётом рисков в часах

 10

 ### 5.план сдачи работ: когда будут готовы автотесты, результаты их прогона.

 30.10.2023

 ### 6.отчёт по автоматизации (оформляется в виде файла с именем Plan.md и заливается в репозиторий вашего проекта)..

 31.10.2023
