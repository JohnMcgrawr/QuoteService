package se.jrl.QouteService;

public final class QuoteHandler {
	private String quote;
	private final Integer id;
	public QuoteHandler(String quote , Integer id) {
		this.id = id;
		this.quote = quote;
 }
	
	public String getQuote() {
		return quote;
	}
	public Integer getId() {
		return id;
	}

	public void setQuote(String newQuote) {
		this.quote = newQuote;

		
	}
	
}
