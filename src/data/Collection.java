package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Collection {
	private String baseUrl;
	private Collector collector;
	private String base, info, key, symbols;
	
	public Collection(String url, String key) {
		this.collector = new Collector();
		this.base = "&base=EUR";
		this.baseUrl = url;
		this.info = "";
		this.key = key;
		this.symbols = "";
	}
	
	// This returns the data which has been retrived by the api.
	public String info() {
		return info;
	}

	/*
	 * This method changes the base currency to which your output currency
	 * rates are relative to by appending the base parameter to your API request
	 * URL and setting it to the three-letter code of your preferred base currency. 
	 */
	// @param base: the base currency which other currencies are messured against.	
	// Example: EUR
	public void setCustomBase(String base) {
		this.base = "&base=" + base;
	}	

	/*
	 * In order to have reduced bandwidth, you can limit the number of output currencies
	 * to a specific set of your choice on most API endpoints.
	 * To do this, simply append the Exchangerates API's symbols parameter
	 * to your API request and set it to one or more comma-separated currency codes. 
	 */
	// @param symbols: the currencies which should be measured against the base currency.
	// Example: EUR, USD, JPY
	public void setCustomSymbols(String symbols) {
		this.symbols = "&symbols=" + symbols; 
	}

	/*
	 * The API comes with an updated endpoint that returns all of the available currencies.
	 * To receive the list, make an API request to the symbols endpoint.
	 */
	public void allSymbols() {
		 connect("", "");
	}
	
	/*
	 * This endpoint will return real-time exchange rate data which gets updated
	 * either every 60 minutes, or every 10 minutes, or every 60 seconds.
	 * The update rate dependes on the subscription plan.
	 */
	public void latestRates() {
		connect("latest", base + symbols);
	}
	
	/*
	 * With this endpoint we have the possibility to see historical
	 * rates of the currencies back to 1999, most of the currencies 
	 * data are available until 1999. You can query the Exchangerates API
	 * for historical rates by appending a date (format YYYY-MM-DD) to the base URL.
	 */
	// @param date: the format is YYYY-MM-DD
	public void historicalRates(String date) {
		connect(date, base + symbols);
	}
	
	/*
	 * This is a endpoint for currency conversion
	 * that comes with the Exchangerates API and can be used
	 * to convert an amount from one currency to another.
	 * Please use this API endpoint for the conversion of any currency.
	 */
	// @param from: the currency which shall be converted
	// @param to: the target currency
	// @param amount: the amount which shall be converted
	public void convert(String from, String to, double amount) {
		String url = "&from=" + from + "&to=" + to + "&amount=" + amount;
		connect("convert", url);
	}
	
	/*
	 * It is possible to convert currencies using historical exchange rate data.
	 * To do this, please also use the API's date parameter
	 * and set it to your preferred date. (format YYYY-MM-DD).
	 */
	// @param from: the currency which shall be converted
	// @param to: the target currency
	// @param amount: the amount which shall be converted
	// @param date: to see what the convertion whould have been in the past, format is YYYY-MM-DD
	public void convert(String from, String to, double amount, String date) {
		String url = "&from=" + from + "&to=" + to + "&amount=" + amount + "&date=" + date;
		connect("convert", url);
	}	
	
	/*
	 * The time series endpoint lets you query the API
	 * for daily historical rates between two dates of
	 * your choice, with a maximum time frame of 365 days.
	 */
	// @param startDate: the start date, format is YYYY-MM-DD
	// @param endDate: the end date, format is YYYY-MM-DD
	public void timeSeries(String startDate, String endDate) {
		String url = "&start_date=" + startDate + "&end_date=" + endDate;
		connect("timeseries", url);	
	}
	
	/*
	 * With the fluctuation API endpoint,
	 * you can receive information about the currency’s fluctuation
	 * on a day-to-day basis. To be able to use this feature,
	 * add the start_date and end_date and select which currencies (symbols)
	 * you would like to receive info for from the API.
	 * Please note that the maximum allowed timeframe is 365 days.
	 */
	// @param startDate: the start date, format is YYYY-MM-DD
	// @param endDate: the end date, format is YYYY-MM-DD
	public void fluctuation(String startDate, String endDate) {
		String url = "&start_date=" + startDate + "&end_date=" + endDate;
		connect("fluctuation", url);
	}
	
	private void connect(String endpoint, String optionals) {
		String url = baseUrl + endpoint + "?" + key + optionals;
		info = collector.connect(url);
	}
	
	private class Collector {	
		public Collector() {
		}

		public String connect(String api) {
			HttpURLConnection conn = null;
			String stream = "";
			
			try {			
				URL url = new URL(api);
				conn = (HttpURLConnection) url.openConnection();			
				conn.setRequestMethod("GET");
				conn.connect();
		
				if(!conn.getResponseMessage().equals("OK")) 
					return conn.getResponseMessage() + ": " + url;
		
				stream = readStream(conn.getInputStream());				
				conn.disconnect();
				
			}catch(RuntimeException | IOException e){
				conn.disconnect();
				System.out.println("A field is invalid.");		
			}
			
			return stream;
		}
		
		private String readStream(InputStream in) {
			StringBuilder sb = new StringBuilder();
			String nextLine = "";
			
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {	
			  while ((nextLine = reader.readLine()) != null) {
				sb.append(nextLine); 
			  }
			} catch (IOException e) {
			  e.printStackTrace();
			}
			
			return sb.toString();
		}
	}
}