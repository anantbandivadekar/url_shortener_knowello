package com.knowello.url_shortener.processor;

import com.knowello.url_shortener.constants.ShortenerConstants;
import com.knowello.url_shortener.repository.ShortenerRepository;
import com.knowello.url_shortener.tools.ShortenerTools;

/**
 * Class having methods to help Manager class
 */
public class ShortenerProcessor {

	private ShortenerRepository repository;
	private ShortenerTools shortenerTools;

	public ShortenerRepository getRepository() {
		return repository;
	}

	public void setRepository(ShortenerRepository repository) {
		this.repository = repository;
	}

	public ShortenerTools getShortenerTools() {
		return shortenerTools;
	}

	public void setShortenerTools(ShortenerTools shortenerTools) {
		this.shortenerTools = shortenerTools;
	}

	/**
	 * This method is used to fetch/generate short URL corresponding to long URL provided
	 * 
	 * @param longUrl This parameter used to fetch/generate respecting short URL
	 * @return shortUrl if exists otherwise null is returned.
	 */
	public String generateShortUrl(String longUrl) {

		String shortUrl = getRepository().findShortUrlByLongUrlIfExists(longUrl);
		if (shortUrl != null) {
			return shortUrl;
		}
		String shortUrlKeyword = getShortenerTools().generateShortKeyword(longUrl);
		while (getRepository().isShortUrlExists(ShortenerConstants.BASE_URL + "/" + shortUrlKeyword)) {
			shortUrlKeyword = getShortenerTools().generateShortKeyword(longUrl);
		}
		shortUrl = ShortenerConstants.BASE_URL + "/" + shortUrlKeyword;
		getRepository().saveEntryInDatabase(shortUrl, longUrl);
		return shortUrl;
	}

	/**
	 * This method is used to fetch long URL corresponding to short URL keyword
	 * provided
	 * 
	 * @param shortKeyword This parameter used to fetch respecting long URL
	 * @return long URL
	 */
	public String getLongUrlByShortKeyword(String shortKeyword) {
		return getRepository().findLongUrlByShortKeyword(shortKeyword);
	}

}
