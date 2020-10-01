package com.knowello.url_shortener.manager;

import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.knowello.url_shortener.processor.ShortenerProcessor;

/**
 * Class to manage incoming requests to application
 * 
 */
@Path("/")
public class ShortenerManager {

	private ShortenerProcessor processor;

	public ShortenerProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(ShortenerProcessor processor) {
		this.processor = processor;
	}

	/**
	 * This method is used to fetch short URL corresponding to long URL provided
	 * 
	 * @param longUrl This parameter used to fetch respecting short URL
	 * @return shortUrl
	 */
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("processurl")
	public String getShortUrl(@QueryParam("longUrl") String longUrl) {
		return getProcessor().generateShortUrl(longUrl);
	}

	/**
	 * This method is executed when short URL is accessed on browser, with this,
	 * respective long URL is fetched and corresponding web page is displayed
	 * 
	 * @param shortkeyword to find out short URL and then corresponding long URL
	 * @return Response Web page to be displayed.
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("{shortkeyword}")
	public Response runShortUrl(@PathParam("shortkeyword") String shortkeyword) {

		String url = getProcessor().getLongUrlByShortKeyword(shortkeyword);
		if (url != null) {
			try {
				URI uri = new URI(url);
				return Response.status(Status.MOVED_PERMANENTLY).location(uri).build();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return null;

	}
}
