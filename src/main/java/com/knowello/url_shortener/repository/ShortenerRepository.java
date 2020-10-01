package com.knowello.url_shortener.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.knowello.url_shortener.constants.ShortenerConstants;

/**
 * Class to interact with Database and perform various tasks
 */
public class ShortenerRepository {

	/**
	 * This method is used to get Database connection
	 */
	public Connection getConn() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sys as sysdba", "oracle");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * This method checks in database whether any such short URL exists.
	 * 
	 * @param shortUrl To check if this URL is present in database or not
	 * @return boolean returns true if short URL exists in database otherwise false.
	 */
	public boolean isShortUrlExists(String shortUrl) {
		PreparedStatement pst;
		try {
			pst = getConn().prepareStatement(ShortenerConstants.SELECT_ALL_FROM_URLMAPPING);
			pst.setString(1, shortUrl);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method is used to check if long URL exists in database. If exists then
	 * corresponding short URL is returned
	 * 
	 * @param longUrl To find corresponding short URL
	 * @return shortUrl
	 */
	public String findShortUrlByLongUrlIfExists(String longUrl) {

		try {
			PreparedStatement pst = getConn().prepareStatement(ShortenerConstants.SELECT_SHORTURL_BY_LONGURL);
			pst.setString(1, longUrl);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method is used to save entry of short URL and corresponding long URL in
	 * database
	 * 
	 * @param shortUrl
	 * @param longUrl
	 */
	public void saveEntryInDatabase(String shortUrl, String longUrl) {
		PreparedStatement pst;
		try {
			pst = getConn().prepareStatement(ShortenerConstants.INSERT_IN_URLMAPPING);
			pst.setString(1, shortUrl);
			pst.setString(2, longUrl);
			pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to find long URL corresponding to short URL keyword
	 * provided
	 * 
	 * @param shortKeyword To find corresponding long URL
	 * @return longUrl
	 */
	public String findLongUrlByShortKeyword(String shortKeyword) {

		String shortUrl = ShortenerConstants.BASE_URL + "/" + shortKeyword;
		try {
			PreparedStatement pst = getConn().prepareStatement(ShortenerConstants.SELECT_LONGURL_BY_SHORTURL);
			pst.setString(1, shortUrl);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
