# Требования к проекту Game mediator

Нужен сервак, к которому будут подключаться боты для игры в крестики-нолики на поле 19х19.

## Этапы игры:
1. В сервисе mediator создается игровая сессия через Rest вызов с данными сессии
2. Mediator ждет подключения ботов путем вызова ими Rest endpoint'а в медиаторе
3. Когда оба бота зарегистрировались, начинается игра
4. Медиатор поочередно вызывает ботов по REST API и получает от них ответ с ходом
    1. В одном из полей json запроса медиатор отправляет все состояние поля, свернув поле 19х19 в строку построчно
    2. Фигуры:
        1. Крестик – x
        2. Нолик – o
        3. Зачеркнутый крестик – X
        4. Зачеркнутый нолик – O
        5. Зачекнутые фигуры отображаются тогда, когда они составляют победную линию
    3. В теле ответа также должен получить новое состояние поля, также свернутого в строку
    4. Необходимо выполнить проверки корректности хода
5. Игра длится, пока один из ботов не соберет свои фигуры в победную линию – линия из 5 одинаковых фигур по
   вертикали, горизонтали или диагонали
6. После окончания игры медиатор вызывает ботов по REST API, сообщая о победе или проигрыше
7. Заканчивается игровая сессия
8. Параллельно может выполняться несколько сессий

# Одновременно с этим должна быть функциональность:
1. Получить все текущие и законченные игровые сессии
2. Получить текущее состояние сессии по id
3. Получить состояние сессии по id и номеру шага


## Что необходимо сделать
1. Прописать REST API контракт для медиатора и бота
2. Продумать corner cases и их решения
3. Предоставить готовый сервис с использованием JVM + Spring стэка и подробной инструкцией по запуску

## Nice to have
Функции, которые желательно сделать, но только после полной реализации
1. Подсчет очков – придумать, за какие действия и сколько давать очков. Например, за победу +100 очков, за проигрыш
   -50 очков, за неправильный ход -2 очка, за быстрый ход (например за 0.5 секунды) +3 очка и т.д.
2. WEB UI приложение для отображения игровых сессий, которое общается с сервисом медиатором
3. Дополнительные предложения приветствуются