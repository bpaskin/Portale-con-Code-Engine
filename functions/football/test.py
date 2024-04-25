import requests

url = "https://api-football-v1.p.rapidapi.com/v3/standings"

querystring = {"season":"2023","league":"135"}

headers = {
	"X-RapidAPI-Key": "153c22314cmsha4c448618868d1ap1039cajsn4201b1ffd4f3",
	"X-RapidAPI-Host": "api-football-v1.p.rapidapi.com"
}

response = requests.get(url, headers=headers, params=querystring)

print(response.json())
