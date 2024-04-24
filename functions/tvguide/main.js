async function main(args) {

    var missingUrl = "'URL' was not in the environment";

    // Check the URL environment variable
    if (!process.env.URL) {
        return {
            headers: { "Content-Type": "application/json;charset=utf-8" },
            statusCode: 400,
            body: JSON.stringify( { error: missingUrl } ),
        }; 
    };

    var tvguideUrl = process.env.URL;

    try {    
        // call the quote URL 
        const response = await fetch(tvguideUrl + '/api/epg/highlights.json',
            {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                },
           }
        ).then((resp) => resp.json());

        if (response.statusCode != undefined && response.statusCode != 200) {
            return {
                statusCode: response.statusCode,
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify( { error: response.statusMessage } ),
              };
        }; 

        // Get the highlights then loop through them to create a new JSON array
        // to return to the calling program
        highlights = response.highlights;

        var output = [];
        highlights.forEach(element => {
            output.push( {title: element.title, description: element.description, date: element.onair, channelName: element.channel.name, iconUrl: tvguideUrl + "/" + element.channel.icon  } );
        });

        return {
                headers: { "Content-Type": "application/json;charset=utf-8" },
                statusCode: response.statusCode,
                body: JSON.stringify(output),
        };

    } catch (e) {
        return {
            statusCode: 400,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify( { error: e.message } ),
          };
    }
}

module.exports.main = main;
