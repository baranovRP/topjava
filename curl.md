###### testGet
`curl http://localhost:8080/topjava/rest/meals/100002`
###### testDelete
`curl --request DELETE \
     http://localhost:8080/topjava/rest/meals/100002`
###### testUpdate
`curl --header "Content-Type: application/json" \
   --request PUT \
   --data '{"id":100002,"dateTime":"2015-05-30T10:00:00","description":"Обновленный завтрак","calories":200}' \
     http://localhost:8080/topjava/rest/meals/100002`
###### testCreate
`curl --header "Content-Type: application/json" \
   --request POST \
   --data '{"dateTime":"2015-06-01T18:00:00","description":"Созданный ужин","calories":300}' \
     http://localhost:8080/topjava/rest/meals`
###### testGetAll
`curl http://localhost:8080/topjava/rest/meals`
###### testGetBetween
`curl 'http://localhost:8080/topjava/rest/meals/between?startDate=2015-05-29&endDate=2015-05-30&startTime=10:15&endTime=22:15'`
###### testGetBetweenNotAllParams
`curl 'http://localhost:8080/topjava/rest/meals/between?startDate=&endDate=2015-05-30&startTime=10:15&endTime='`
