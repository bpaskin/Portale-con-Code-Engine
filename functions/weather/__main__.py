import json
import os
import requests

def main(params):

    conditionsDict =  { "0": "Clear Skies", "1": "Mainly Clear", "2": "Partly Cloudy", "3": "Overcast",
        "45": "Fog", "48": "Dense Fog", "51": "Drizzle", "53": "Moderate Drizzle", "55": "Heavy Drizzle",
        "56": "Freezing Drizzle", "57": "Heavy Freezing Drizzle", "61": "Slight Rain", "63": "Moderate Rain",
        "65": "Heavy Rain", "66": "Freezing Rain", "67": "Heavy Freezing Rain", "71": "Some Snow",
        "73": "Moderate Snow", "75": "Heavy Snow", "77": "Snow", "80": "Rain Showers", "81": "Moderate Rain Showers",
        "82": "Heavy Rain Showers", "85": "Snow Showers", "86": "Heavy Snow Showers", "95": "Thunderstorms",
        "96": "Thunderstorms with Hail", "99": "Thunderstorms with Heavy Hail" }

    if "latitude" not in params:
        print("error: latitude not passed in as a parameter")
        return {
            "headers": { 'Content-Type': 'application/json; charset=utf-8' },
            "statusCode": 400,
            "body": '{"error": "latitude not passed in as a parameter"}',
        }
    
    if "longitude" not in params:
        print("error: longitude not passed in as a parameter")
        return {
            "headers": { 'Content-Type': 'application/json; charset=utf-8' },
            "statusCode": 400,
            "body": '{"error: longitude not passed in as a parameter"}',
        }
    
    latitude = params['latitude']
    longitude = params['longitude']
    url = os.getenv('URL')

    if not url:
        print("error: URL must be set as environment variables")
        return {
            "headers": { 'Content-Type': 'application/json; charset=utf-8' },
            "statusCode": 400,
            "body": '{"error": "URL must be set as environment variables"}',
        }

    try:

        response = requests.get(url + "/forecast?latitude=" + latitude + "&longitude=" + longitude + 
            "&current=temperature_2m,relative_humidity_2m,precipitation,weather_code,wind_speed_10m,wind_direction_10m&forecast_days=1",
            headers = {
                "Content-Type": "application/json",
            },
        )

        if response.status_code != 200:
            print("error: bad response from calling weather API:"  + response.text)
            return {
                "headers": { 'Content-Type': 'application/json; charset=utf-8' },
                "statusCode": response.status_code,
                "body": '{"error": ' + response.text + '"}',
            }
        
        responseJson = response.json()
        conditions = responseJson["current"]
        tempUnits = responseJson["current_units"]["temperature_2m"]
        WindUnits = responseJson["current_units"]["wind_speed_10m"]
        percipUnits = responseJson["current_units"]["precipitation"]
        weatherCode = str(conditions["weather_code"])
        currentWeather = conditionsDict[weatherCode]

        output = json.dumps({"conditions": currentWeather, "temperature": str(conditions["temperature_2m"]) + tempUnits,
             "humidity": str(conditions["relative_humidity_2m"]) + "%", "percipitation": str(conditions["precipitation"]) + percipUnits, 
             "windSpeed": str(conditions["wind_speed_10m"]) + WindUnits})

        return {
            "headers": { 'Content-Type': 'application/json; charset=utf-8' },
            "statusCode": 200,
            "body": output,
        }

    except Exception as e:
        print (format(e))
        return {
            "headers": { 'Content-Type': 'application/json; charset=utf-8' },
            "statusCode": 400,
            "body": '{"error":"' + format(e) + '" }'
        }
