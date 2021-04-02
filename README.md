# TeamGG-Artifact

This application is designed to scrap data from the match history in the game League Of Legends


Requirements:

- Mongo DB Database, install it from here https://www.mongodb.com

- Riot API Key, get one from here https://developer.riotgames.com/


How to install

1. git clone repository

2. import the project as a Maven project

4. Configure MongoDB URL in the configuration file

4. set Riot API Key in configuration file

5. Run



How to configure:

Properties are located there /TeamGG-Artifact/src/main/java/resources/configuration/config.properties

- width : (Application width: pixel number)

- height: (Application height: pixel number)

- api_key: (Riot API key: string of characters)

- host_db: (URL of your MongoDB application)

- data_base: (Name of the database)