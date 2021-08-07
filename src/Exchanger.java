import data.Collection;

public class Exchanger {
	public static void main(String[] args) {
		String url = "http://api.exchangeratesapi.io/v1/";
		Collection coll = new Collection(url, "YOUR_KEY");
		coll.setCustomBase("USD");
		coll.setCustomSymbols("EUR,JPY");
		coll.historicalRates("2001-08-31");
		
		String info = coll.info();
		System.out.println(info);		
	}		
}
