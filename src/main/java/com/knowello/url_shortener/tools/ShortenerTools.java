package com.knowello.url_shortener.tools;

import java.util.Random;

/**
 * This class has helper methods to do various tasks
 */
public class ShortenerTools {

	/**
	 * This method is used to fetch short URL keyword corresponding to long URL
	 * provided
	 * 
	 * @param longUrl This parameter used to fetch respecting short URL keyword
	 * @return short URL keyword
	 */
	public String generateShortKeyword(String longUrl) {

		longUrl = getPureWebsiteUrl(longUrl);
		if (longUrl == null)
			return "";
		if (longUrl.length() <= 3) {
			return longUrl.toLowerCase();
		}

		String shortUrl = "";
		shortUrl += (longUrl.substring(0, 3)).toLowerCase();
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			shortUrl += String.valueOf(random.nextInt(10));
		}
		return shortUrl;
	}

	/**
	 * A method to extract the website url excluding the default format 'http://'
	 * and 'www' url inputted http://www.google.com returns the string 'google.com'
	 * 
	 * @param websiteName
	 * @return Website url
	 */
	private static String getPureWebsiteUrl(String websiteName) {
		websiteName = websiteName.toLowerCase();
		if (websiteName.contains("http") || websiteName.contains("www")) {
			websiteName = websiteName.substring(websiteName.indexOf(".") + 1);
		}
		return websiteName;
	}
}
