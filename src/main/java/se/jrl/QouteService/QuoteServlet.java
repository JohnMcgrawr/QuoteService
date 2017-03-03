package se.jrl.QouteService;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.*;

@WebServlet("/quotepro/*")
public class QuoteServlet extends HttpServlet {
	private static final long serialVersionUID = -6315927854811316524L;

	private static final AtomicInteger ids = new AtomicInteger(1000);
	private static final Map<Integer, QuoteHandler> quotes = Collections.synchronizedMap(new HashMap<>());

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		PUT /quotes/id  Uppdaterar ett citatet som har angivet id med
//		nytt innehåll. Innehållet hämtas från body
		
			String[] parts = request.getPathInfo().split("/");

			if (parts.length > 1) {
				QuoteHandler quoteHandler = quotes.get(Integer.parseInt(parts[1]));
				if (quoteHandler == null) {
					response.setStatus(SC_NOT_FOUND);
				} else {
					String newQuote = request.getReader().lines().collect(Collectors.joining());
					quoteHandler.setQuote(newQuote);
					response.getWriter().println(quoteHandler.getQuote());
				}
			} else {
				response.setStatus(SC_BAD_REQUEST);
			}
		}
		
	
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// DELETE /quotes/id Tar bort ett citat med angivet id
		String[] parts = request.getPathInfo().split("/");

		if (parts.length > 1) {
			QuoteHandler quoteHandler = quotes.remove(Integer.parseInt(parts[1]));

			if (quoteHandler == null) {
				response.setStatus(SC_NOT_FOUND);
			} else {
				response.setStatus(SC_ACCEPTED);
			}
		} else {
			response.setStatus(SC_BAD_REQUEST);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String body = request.getReader().lines().collect(Collectors.joining());

		QuoteHandler quote = new QuoteHandler(body, ids.incrementAndGet());

		if (quote.getQuote().length() < 10) {

			response.setStatus(SC_BAD_REQUEST);

		} else {

			quotes.put(quote.getId(), quote);
			String location = request.getRequestURL().append("/").append(quote.getId()).toString();
			response.setHeader("Location", location);
			response.setStatus(SC_CREATED);
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getPathInfo() == null) {

			for (int i = 0; i < quotes.size(); i++) {
				response.getWriter().println(quotes.get(i + 1001).getQuote());
			}

		} else {
			String[] parts = request.getPathInfo().split("/");

			if (parts.length > 1) {
				QuoteHandler quoteHandler = quotes.get(Integer.parseInt(parts[1]));
				if (quoteHandler == null) {
					response.setStatus(SC_NOT_FOUND);
				} else {
					response.getWriter().println(quoteHandler.getQuote());
				}
			} else {
				response.setStatus(SC_BAD_REQUEST);
			}
		}

	}
}
