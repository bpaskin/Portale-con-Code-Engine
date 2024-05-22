# IBM Code Engine Example

This is a simple example of using IBM's [Code Engine](https://www.ibm.com/products/code-engine) to show how to create an application and functions to showcase how easy it is to get started in the serverless world.  The example uses Node and Python for the functions and PHP for the application.  

This is a portal (application) that calls 4 functions to display the current weather in a locale, TV highlights for Italy, Serie A football standings, and a quote in English.

It is important to note that currently functions must reside in the IBM Container Registry while applications and jobs can reside outside the container registry.

All functions and applications will be created using the buuldpacks and not a Dockerfile, though it is possible for languages that are not supported for applications and jobs.

I have other repositories that also showcase Code Engine doing other tasks.

---
## Getting started

1. An [IBM Cloud account](https://cloud.ibm.com/login) is required.  Signup is free, and the example can run for free too using the Code Engine free tier.
2. The [IBM Cloud CLI](https://www.ibm.com/products/cli) needs to be downloaded and installed on your system.
3. The Code Engine and Countainer registry plugins for the CLI need to be installed.
```
ibmcloud plugin install code-engine
ibmcloud plugin install container-registry
```
4. to Utilize the football (soccer) league information, a free account must be setup on [RapidAPI](https://rapidapi.com/hub).  The API key will be used later.
---
## Creating the necessary resources
1. Logon to the IBM Cloud
```
ibmcloud login -u <username>
```
2. List resource groups
```
ibmcloud resource groups
```
3. Choose one of the resource groups fromt he last command
```
ibmcloud target -g <resource group>
```
4. List available regions
```
ibmcloud regions
```
5. Select a region to host the Code Engine project and resources
```
ibmcloud target -r <region name>
```
---
## Code Engine Time
1.  Create a Code Engine project
```
ibmcloud ce project create --name Portal
```
2. Select the project to use
```
ibmcloud ce project select --name Portal
```
3. Deploy the functions (2 are Python, 2 are NodeJS)
```
ibmcloud ce fn create --name quote    --build-source https://github.com/bpaskin/Portale-con-Code-Engine --build-context-dir functions/quote    --runtime nodejs-18 -e URL=https://api.quotable.io
ibmcloud ce fn create --name tvguide  --build-source https://github.com/bpaskin/Portale-con-Code-Engine --build-context-dir functions/tvguide  --runtime nodejs-18 -e URL=https://services.tivulaguida.it
ibmcloud ce fn create --name weather  --build-source https://github.com/bpaskin/Portale-con-Code-Engine --build-context-dir functions/weather  --runtime python    -e URL=https://api.open-meteo.com/v1   
ibmcloud ce fn create --name football --build-source https://github.com/bpaskin/Portale-con-Code-Engine --build-context-dir functions/football --runtime python    -e URL=https://v3.football.api-sports.io
```
4. Get a list of the URLs
```
ibmcloud ce fn list
```
5. Test each of the the functions using cURL from the command line
```
curl -vvv -H "Content-Type: application/json" -d '{"category":"famous-quotes"}' https://quote.<from listing>.codeengine.appdomain.cloud 
curl -vvv -H "Content-Type: application/json" https://tvguide.<from listing>.codeengine.appdomain.cloud
curl -vvv -H "Content-Type: application/json" -d '{"latitude":"41.8919","longitude":"12.5113"}' https://weather.<from listing>.codeengine.appdomain.cloud 
curl -vvv -H "Content-Type: application/json" -d '{"apiKey": "<your API Key>", "leagueId": "135"}' https://football.<from listing>.codeengine.appdomain.cloud 
```
6. Deploy the application
```
ibmcloud ce app create --name portal    --build-source https://github.com/bpaskin/Portale-con-Code-Engine --build-context-dir applications/portal --build-strategy buildpacks -e category=famous-quotes -e latitude=41.8919 -e longitude=12.5113 -e leagueId=135 -e apiKey=<API key from RapidAPI> 
```
7. Using your browser test the application
```
 https://portal.<from listing>.codeengine.appdomain.cloud
```
---
## Java application running on Liberty
There is also an example for running the application portion on WebSphere Liberty.  This showcases the use of another language, a container, mounting a secret, and Liberty itself.  The application uses the same function calls as above.  

1. Update the `apikey` [file](https://github.com/bpaskin/Portale-con-Code-Engine/blob/main/applications/java/portal/apikey) placing the API Key from Rapid API key on the the first line.
2. Create a secret from the `apikey` file
```
ibmcloud ce secret create --name apikey --from-file apikey 
```
3. Create the application from the Dockerfile provided.  Notice that the `--mount-secret` will mount the secret in `/config/variables` which will be picked up by Liberty.
```
ibmcloud ce application create --name portaljava  --build-strategy dockerfile --build-source https://github.com/bpaskin/Portale-con-Code-Engine --build-context-dir applications/java/portal --mount-secret /config/variables=apikey 
```
4. Using your browser test the application
```
 https://portaljava.<from listing>.codeengine.appdomain.cloud
```
---
## Other Configs
If the image for the portal application is going to be placed outside and not in the internal registry, then a secret needs to be setup with the userid and password
```
ibmcloud ce secret create --format registry --name myRegistry --server <server name> --username <userid> --password <password>
```

A Image Build can be used to be run to build a new image of the application
```
ibmcloud ce build create --name portalbuild --build-source https://github.com/bpaskin/Portale-con-Code-Engine --context-dir applications/portal --strategy buildpacks --image <remote repo>/portal:latest --registry-secret myRegistry
```

𐕣 Funeral Winter 𐕣
