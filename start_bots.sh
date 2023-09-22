session_id="$1"
command_1_id="$2"
command_1_image="$3"
command_1_port=$((10000 + $RANDOM % 10000))
command_2_id="$4"
command_2_image="$5"
command_2_port=$((10000 + $RANDOM % 10000))
mediator="http://mediator:8080"

docker run -d -t -i \
  -e SESSION_ID="$session_id" \
  -e BOT_URL="http://$command_1_id:${command_1_port}" \
  -e MEDIATOR_URL="$mediator" \
  -e BOT_ID="$command_1_id" \
  -e SERVER_PORT="${command_1_port}" \
  --net internal \
  --name "$command_1_id" \
  "$command_1_image" &

sleep 1

docker run -d -t -i \
  -e SESSION_ID="$session_id" \
  -e BOT_URL="http://$command_2_id:${command_2_port}" \
  -e MEDIATOR_URL="$mediator" \
  -e BOT_ID="$command_2_id" \
  -e SERVER_PORT="${command_2_port}" \
  --net internal \
  --name "$command_2_id" \
  "$command_2_image" &
