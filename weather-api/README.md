# weather-api
Weather Restful APIs

Description:
1.	Enforce API Key scheme. An API Key is rate limited to 5 weather reports an hour. After that your service should respond in a way which communicates that the hourly limit has been exceeded. Create 5 API Keys. Pick a convention for handling them that you like; using simple string constants is fine. This is NOT an exercise about generating and distributing API Keys. Assume that the user of your service knows about them.
2.	Have a URL that accepts both a city name and country name. Based upon these inputs, and the API Key, your service should decide whether or not to call the OpenWeatherMap name service. If it does, the only weather data you need to return to the client is the description field from the weather JSON result. Whether it does or does not, it should respond appropriately to the client. 
3.	Reject requests with invalid input or missing API Keys.
4.	Store the data from openweathermap.org into H2 DB.
5.	The API will query the data from H2
6.	Clear Spring Layers are needed.
7.	Follow Rest API convention.