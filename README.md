# Tic-tac-toe game mediator

Сервис, сделанный по [заданию](start_task.md).

Нужен для администрирования играми в крестики-нолики между парами ботов. Боты общаются с медиатором по Restful
протоколу.
[Протокол взаимодействия](src/main/resources/static/service_bot_spec.yaml) ботов с медиатором.
Также можно посмотреть [протокол](src/main/resources/static/service_for_front_spec.yaml), по которому медиатор может взаимодействовать 
с Front UI. Разработка UI будет позже.

## Как начать игру
Запускаем Docker compose командой 
```shell
docker compose up -d
```

После этого запустится БД и сервис медиатор. 