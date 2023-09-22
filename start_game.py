import sys
from random import randint
from http.client import HTTPConnection
import docker
from json import loads
from time import sleep

game_field_size = 19

docker_container = docker.from_env()


class MediatorClient:
    def __init__(self):
        self.connection = HTTPConnection(host='localhost', port=8080)

    def create_new_session(self):
        self.connection.request('POST', '/sessions')
        session_id = loads(self.connection.getresponse().read().decode(encoding='utf-8'))['session_id']
        self.session_id = session_id
        return session_id

    def watch_game(self):
        while True:
            self.connection.request('GET', f'/sessions/{self.session_id}')
            response = loads(self.connection.getresponse().read().decode(encoding='utf-8'))
            status = response['status']
            last_turn = response['last_turn']
            last_turn_num = last_turn['turn']
            print(f'Status {status}, last turn {last_turn_num}')
            counter = 0
            game_field = last_turn['game_field']
            while counter < game_field_size * game_field_size:
                print(game_field[counter:(counter + game_field_size)])
                counter += game_field_size
            print(f"============================================================")
            if status == 'FINISHED':
                self.winner = response['win_bot']
                break
            sleep(1)


mediator_client = MediatorClient()

session_id = mediator_client.create_new_session()
mediator_url = "http://mediator:8080"


class BotContainerParams:
    def __init__(
            self,
            id,
            image,
            port
    ):
        self.id = id
        self.image = image
        self.port = port
        self.url = f"http://{id}:{port}"


def start_bot_container(bot_container_params: BotContainerParams):
    return docker_container.containers.run(
        bot_container_params.image,
        detach=True,
        network='internal',
        name=bot_container_params.id,
        environment={
            'SESSION_ID': session_id,
            'BOT_URL': bot_container_params.url,
            'MEDIATOR_URL': mediator_url,
            'BOT_ID': bot_container_params.id,
            'SERVER_PORT': bot_container_params.port
        }
    )


print(f"================STARTING SESSION {session_id}===============")
bot_1_params = BotContainerParams(
    sys.argv[1],
    sys.argv[2],
    randint(10_000, 20_000)
)
bot_2_params = BotContainerParams(
    sys.argv[3],
    sys.argv[4],
    randint(10_000, 20_000)
)

bot_1_container = start_bot_container(bot_1_params)
sleep(1)
bot_2_container = start_bot_container(bot_2_params)
mediator_client.watch_game()
print(f"========SESSION {session_id} OVER, bot {mediator_client.winner} wins========")

bot_1_container.stop()
bot_2_container.stop()
bot_1_container.remove()
bot_2_container.remove()
