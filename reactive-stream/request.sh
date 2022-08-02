curl -v --request POST 'localhost:8081/upload' \
--header 'Content-Type: application/json' \
--data-raw '{
    "age" : "37",
    "name": "fabricio jacob9"
}'