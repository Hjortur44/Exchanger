# Exchange

This is an app which uses the api from a website called "Exchangerates" and can be found at https://exchangeratesapi.io/ .

Their api key is on the form "access_key=ABCDEF".

The program has a class which handles all the api usage from the website,
the name of that class is "Collection".

To use the program, you need to pass in this key into the Collection constructor, do not modify this key as the program needs the first part of the key ("access_key=") in order to work.

 Example:
    String url = "https://api.exchangeratesapi.io/v1/";
    String key = "access_key=ABCDEF";

    Collection coll = new Collection(url, key);


Depending on your subscription plan for the api key, you may or may not be able to use all the functionalities, also, you may have to use "http" rather than "https".

For the free version, you can call two methods and use "http" version of the website.

 Example:
    String url = "http://api.exchangeratesapi.io/v1/";
    String key = "access_key=ABCDEF";

    Collection coll = new Collection(url, key);
    coll.allSymbols() // This make a query for a list of all available currencies.

		String info = coll.info(); // getting the recieved data, if any.
		System.out.println(info);


For a paid version:

 Example:
    String url = "https://api.exchangeratesapi.io/v1/";
    String key = "access_key=ABCDEF";

    Collection coll = new Collection(url, key);
		coll.setCustomBase("USD");           // change the base currency
		coll.setCustomSymbols("EUR,JPY");    // change the returning currencies
		coll.historicalRates("2001-08-31");  // getting data from different dates

		String info = coll.info(); // getting the recieved data, if any.
		System.out.println(info);
