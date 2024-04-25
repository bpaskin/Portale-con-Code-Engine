import datetime
import json
import os
import requests

def main(params):

    if "apiKey" not in params:
        print("error: apiKey not passed in as a parameter")
        return {
            "headers": { 'Content-Type': 'application/json; charset=utf-8' },
            "statusCode": 400,
            "body": '{"error": "apiKey not passed in as a parameter"}',
        }
    
    if "leagueId" not in params:
        print("error: leagueId not passed in as a parameter")
        return {
            "headers": { 'Content-Type': 'application/json; charset=utf-8' },
            "statusCode": 400,
            "body": '{"error: leagueId not passed in as a parameter"}',
        }
    
    apiKey = params['apiKey']
    leagueId = params['leagueId']
    url = os.getenv('URL')

    if not url:
        print("error: URL must be set as environment variables")
        return {
            "headers": { 'Content-Type': 'application/json; charset=utf-8' },
            "statusCode": 400,
            "body": '{"error": "URL must be set as environment variables"}',
        }

    try:

        # Serie A è 135 per il "league"
        year = datetime.date.today().year
        response = requests.get(url + "/standings?league=" + leagueId + "&season=" + str(year - 1),
            headers = {
                "Content-Type": "application/json",
                "x-rapidapi-host": "api-football-v1.p.rapidapi.com",
                "x-rapidapi-key": apiKey
            },
        )

        if response.status_code != 200:
            print("error: bad response from calling sports API:"  + response.text)
            return {
                "headers": { 'Content-Type': 'application/json; charset=utf-8' },
                "statusCode": response.status_code,
                "body": '{"error": ' + response.text + '"}',
            }
        
        responseJson = response.json()
        leagueName = responseJson["response"][0]["league"]["name"]
        standings = responseJson["response"][0]["league"]["standings"][0]
        output = []

        for team in standings:
            output.append( {"rank": str(team["rank"]), "name": str(team["team"]["name"]), "point": str(team["points"]), 
                "gamesPlayed": str(team["all"]["played"]),"gamesWon": str(team["all"]["win"]),"gamesLost": str(team["all"]["lose"]), 
                "gamesDraw":  str(team["all"]["draw"])} )

        return {
            "headers": { 'Content-Type': 'application/json; charset=utf-8' },
            "statusCode": 200,
            "body": json.dumps({"league": leagueName, "standings": output}),
        }

    except Exception as e:
        print (format(e))
        return {
            "headers": { 'Content-Type': 'application/json; charset=utf-8' },
            "statusCode": 400,
            "body": '{"error":"' + format(e) + '" }'
        }
