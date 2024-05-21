<?php

error_reporting(E_ALL);
ini_set('display_errors', 'On');

function callRest($url, $data) {    
    $curl = curl_init();
    curl_setopt($curl, CURLOPT_POST, 1);
    curl_setopt($curl, CURLOPT_HTTPHEADER, array(
        'Content-Type: application/json'
    ));
    curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
    curl_setopt($curl, CURLOPT_URL, $url);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);

    $result = curl_exec($curl);

    if(curl_exec($curl) === false || $result == null ) {
        $result = '{"error":' . curl_error($curl) . '}';
        error_log ($result);
    }

    $httpcode = curl_getinfo($curl, CURLINFO_HTTP_CODE);
    $urlcode = curl_getinfo($curl, CURLINFO_EFFECTIVE_URL);

    curl_close($curl);
    return $result;
}

// change the node URLs to lookup in Code Engine
$url = $_SERVER['HTTP_HOST'];
$footballUrl = "https://" . str_replace("portal", "football", $url ?? "http://missing_url");
$quotelUrl = "https://" . str_replace("portal", "quote", $url ?? "http://missing_url");
$tvguidelUrl = "https://" . str_replace("portal", "tvguide", $url ?? "http://missing_url");
$weatherlUrl = "https://" . str_replace("portal", "weather", $url ?? "http://missing_url");
?>

<html>
    <head>
        <title>Portale Paskino</title>
        <link type="text/css" rel="stylesheet" href="style.css" />
    </head>
    <body>
         <h1 id="banner" class="purples">Portale Paskino</h1>
        <div id="uno">
            <?php
                // get environment variables
                $latitude = getenv('latitude');
                $longitude = getenv('longitude');

                if ($latitude && $longitude) {
                    $result = json_decode( callRest($weatherlUrl, '{"latitude": "' . $latitude . '","longitude": "'. $longitude . '"}'), true );
                    if (isset($result['error'])) {
                        ?>
                        <h3 id="error"><?php print($result['error']); ?></h3>
                        <?php
                          } else {
                        ?>
                        <h3>
                            <?php print($result['conditions']); ?>&nbsp;<?php print($result['temperature']); ?><br/>
                            <?php print($result['humidity']); ?>&nbsp;<?php print($result['windSpeed']); ?><br/>
                        </h3>
            <?php 
                    }
                } else {
            ?>
                    <h3 id="error">environment variables 'latitude' and 'longitude' must be set.</h3>
            <?php
                }
            ?>
        </div>
        <div id="due">
                <?php
                  $result = json_decode( callRest($tvguidelUrl, ""), true );

                  if (isset($result['error'])) {
                ?>
                <h3 id="error"><?php print($result['error']); ?></h3>
                <?php
                  } else {
                ?>
                <table>
                <tr>
                    <th>Canale</th>
                    <th>Programma</th>
                    <th>Quando</th>
                </tr> 
                <?php
                    foreach ($result as $program) {
                ?>
                <tr>
                    <td><image width="15" height="15" src="<?php print($program['iconUrl']); ?>"/><?php print($program['channelName']); ?></td>
                    <td><?php print($program['title']); ?></td>
                    <td><?php print($program['date']); ?></td>
                </tr>
                <?php
                    }
                  }
                ?>
                </table>
        </div>
        <div id="tre">
        <?php
                // get environment variables
                $apiKey = getenv('apiKey');
                $leagueId = getenv('leagueId');
                if ($apiKey && $leagueId) {
                    $result = json_decode( callRest($footballUrl, '{"apiKey": "' . $apiKey . '","leagueId": "'. $leagueId . '"}'), true );
                    if (isset($result['error'])) {
                        ?>
                        <h3 id="error"><?php print($result['error']); ?></h3>
                        <?php
                          } else {
                            $standings = $result['standings'];
                        ?>
                       <h3><?php print($result['league']); ?></h3>
                       <table>
                        <?php foreach ($standings as $line) { 
                        ?>
                            <tr>
                                <td><?php print($line['rank']); ?></td>
                                <td><?php print($line['name']); ?></td>
                                <td><?php print($line['point']); ?></td>
                                <td><?php print($line['gamesPlayed']); ?></td>
                                <td><?php print($line['gamesLost']); ?></td>
                                <td><?php print($line['gamesDraw']); ?></td>
                            </tr>    
                        <?php 
                        }
                        ?>
                        </table>
                   
            <?php 
                    }

                } else {
            ?>
                    <h3 id="error">environment variables 'apiKey' and 'leagueId' must be set.</h3>
            <?php
                }
            ?>
        </div>
        <div id="quattro">
            <?php
                // get environment variables
                $category = getenv('category');
                if ($category) {
                    $result = json_decode( callRest($quotelUrl, '{"category": "' . $category . '"}'), true );

                    if (isset($result['error'])) {
            ?>
             <h3 id="error"><?php print($result['error']); ?></h3>
            <?php
                          } else {
            ?>
                <h3><?php print($result["quote"]); ?> - <?php print($result["author"]); ?></h3>
            <?php
                    }
                } else {
            ?>
                    <h3 id="error">environment variable 'category' must be set.</h3>
            <?php
                }
            ?>
        </div>
    </body>
<html>