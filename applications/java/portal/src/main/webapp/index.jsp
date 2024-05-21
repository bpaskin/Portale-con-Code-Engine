<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
    <head>
        <title>Portale Paskino</title>
        <style>
            html, body { height: 100%; margin: 0; padding: 0 }

            div { position: fixed; width: 50%; height: 50%; overflow-x: scroll; }

            #uno { top: 20%;   left: 5%;   background: white;}
            #due { top: 20%;   left: 55%; background: white; }
            #tre { top: 70%; left: 5%;   background: white;  }
            #quattro { top: 70%; left: 55%; background: white; }

            @font-face {
                font-family: 'Rocher';
                src: url(https://assets.codepen.io/9632/RocherColorGX.woff2);
            }

            #banner {
                font-family: 'Rocher';
                text-align: center;
                font-size: 50px;
                height: 100vh;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: top;
                font-palette: --Purples
            }

            @font-palette-values --Purples {
                font-family: Rocher;
                base-palette: 6;
            }

            h1 {
                margin: 0;
                text-align: center;
            }

            #error {
                color: red;
            }
        </style>
    </head>
    <body>
        <h1 id="banner" class="purples">Portale Paskino</h1>
        <div id="uno">
            <pageContext class="session">
                <h3>
                    ${weather.conditions}&nbsp;${weather.temperature}<br/>
                    ${weather.humidity}&nbsp;${weather.windSpeed}
                </h3>
            </pageContext>
        </div>

        <div id="due">
            <pageContext class="session">
                <table>
                    <tr>
                        <th>Canale</th>
                        <th>Programma</th>
                        <th>Quando</th>
                    </tr>
                    <c:forEach items="${tvGuide.program}" var="item">
                        <tr>
                            <td><image width="15" height="15" src="${item.iconUrl}"/>&nbsp;${item.channelName}</td>
                            <td>${item.title}</td>
                            <td>${item.date}</td>
                        </tr>
                    </c:forEach>
                </table>
            </pageContext>
        </div>

        <div id="tre">
            <pageContext class="session">
                <h3>${football.league}</h3>
                <table>
                    <c:forEach items="${football.standings}" var="item">
                    <tr>
                        <td>${item.rank}</td>
                        <td>${item.name}</td>
                        <td>${item.point}</td>
                        <td>${item.gamesPlayed}</td>
                        <td>${item.gamesLost}</td>
                        <td>${item.gamesDraw}</td>
                    </tr>
                    </c:forEach>
                </table>
            </pageContext>
        </div>

        <div id="quattro">
            <pageContext class="session">
                <h3>${quote.quote} - ${quote.author}</h3>
            </pageContext>
        </div>
    </body>
</html>