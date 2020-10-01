package com.knowello.url_shortener.constants;

public class ShortenerConstants {

	public static final String BASE_URL = "localhost:8085/url_shortener";

	public static final String SELECT_ALL_FROM_URLMAPPING = "select * from urlmapping where shorturl=?";

	public static final String SELECT_SHORTURL_BY_LONGURL = "select shorturl from urlmapping where longurl=?";

	public static final String INSERT_IN_URLMAPPING = "insert into urlmapping values(?,?)";

	public static final String SELECT_LONGURL_BY_SHORTURL = "select longurl from urlmapping where shorturl=?";
}
