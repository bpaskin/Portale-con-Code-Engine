async function main(args) {

    var missingUrl = "'URL' was not in the environment";
    var missingCategory = "'category' was not passed in the request";

    // Check the URL environment variable
    if (!process.env.URL) {
        return {
            headers: { "Content-Type": "application/json;charset=utf-8" },
            statusCode: 400,
            body: JSON.stringify( { error: missingUrl } ),
        }; 
    };

    var quoteUrl = process.env.URL;

    // check if a category was passed
    if (!args.category) {
        return {
            headers: { "Content-Type": "application/json;charset=utf-8" },
            statusCode: 400,
            body: JSON.stringify( { error: missingCategory } ),
        }; 
    };

    var quoteCategory = args.category;

    try {    
        // call the quote URL and using the category and get the response
        const response = await fetch(quoteUrl + '/quotes/random?limit=1&tags=' + quoteCategory,
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

        // good response, get first element since it is an array of items, but we are requesting
        // only 1 element with the limit above
        quote = response[0];
        return {
                headers: { "Content-Type": "application/json;charset=utf-8" },
                statusCode: response.statusCode,
                body: JSON.stringify( { quote: quote.content, author: quote.author } ),
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
