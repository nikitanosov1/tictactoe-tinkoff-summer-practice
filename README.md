# Tic-tac-toe game mediator

Сервис, сделанный по [заданию](start_task.md).

Нужен для администрирования играми в крестики-нолики между парами ботов. Боты общаются с медиатором по Restful
протоколу.
[Протокол взаимодействия](mediator/src/main/resources/static/service_bot_spec.yaml) ботов с медиатором.
Также можно посмотреть [протокол](mediator/src/main/resources/static/service_for_front_spec.yaml), по которому медиатор 
может взаимодействовать 
с Front UI. Разработка UI будет позже.

## Требования к боту
1. Завернут в Docker container
2. Через env переменные передаются следующие конфиги
   1. SESSION_ID – id сессии, в которой должен участвовать бот
   2. BOT_URL – url для обратной коммуникации медиатора с ботом. Должен передаваться в запросе на регистрацию в сессии
   3. BOT_ID – id бота. Должен передаваться в запросе на регистрацию в сессии
   4. MEDIATOR_URL – base url, по которому бот может зарегистрироваться в сессии
3. Поддерживает [контракт](mediator/src/main/resources/static/service_bot_spec.yaml) 

## Как начать игру
Запускаем Docker compose командой 
```shell
docker compose up -d
```

После этого запустится БД и сервис медиатор. 

Затем нужно собрать ботов, которые будут играть. Можно собрать [тестового бота](bot) командой 
```shell
docker build . -t tic-tae-toe-bot:0.0.1 -f bot/Dockerfile
```

После этого запустить игровую сессию командой (используется python, требуется библиотека [docker-py](https://docker-py.readthedocs.io/en/stable/index.html))
```shell
python3 start_game.py {id первого бота} {контейнер первого бота} {id второго бота} {контейнер второго бота}
```

Для проверки своего бота против тестового можно воспользоваться командой
```shell
python3 start_game.py {id первого бота} {контейнер первого бота} test-bot tic-tae-toe-bot:0.0.1
```

Для демонстрации можно запустить 2х тестовых ботов друг против друга
```shell
python3 start_game.py test-bot-1 tic-tae-toe-bot:0.0.1 test-bot-2 tic-tae-toe-bot:0.0.1

```
