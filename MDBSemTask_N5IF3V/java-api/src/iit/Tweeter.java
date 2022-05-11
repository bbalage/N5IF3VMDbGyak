package iit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tweeter {

	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		try (Connection con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost:7687", "neo4j", "n5if3v")) {
			List<String> usernames = getUsernames(con);
			String username = promptUsername(usernames);
			String tweetText = promptTweet(username);
			Tweet tweet = buildTweet(tweetText);
			System.out.println("Your tweet: " + tweet);
			publishTweet(con, username, tweet);
			System.out.println("Succesfully tweeted!");
		}
		catch (MyException e) {
			System.out.println(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		sc.close();
	}
	
	static List<String> getUsernames(Connection con) throws MyException {
		List<String> names = new ArrayList<>();
		try(Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery("MATCH (n:User) with n.username as name RETURN name");
			while (rs.next()) {
				names.add(rs.getString("name"));
			}
			if (names.isEmpty()) {
				throw new MyException("No users.");
			}
		}
		catch (SQLException e) {
			throw new MyException("Could not retrieve users.");
		}
		return names;
	}

	static String promptUsername(List<String> usernames) {
		String username = "";
		System.out.println("Available usernames:");
		printList(usernames);
		do {
			System.out.println("Who are you?");
			username = sc.nextLine();
		} while (!usernames.contains(username));
		return username;
	}
	
	static String promptTweet(String username) {
		System.out.println("Please, dear " + username + " write a tweet!");
		String tweet = sc.nextLine();
		return tweet;
	}
	
	static Tweet buildTweet(String tweetText) {
		String[] words = tweetText.split(" ");
		int limit = Math.min(5, words.length);
		StringBuilder shortText = new StringBuilder();
		for (int i = 0; i < limit; i++) {
			shortText.append(words[i] + " ");
		}
		return new Tweet(shortText.toString().trim(), tweetText, LocalDateTime.now());
	}
	
	static void publishTweet(Connection con, String username, Tweet tweet) throws MyException{
		try(PreparedStatement stmtCreateTweet = 
				con.prepareStatement("CREATE (t:Tweet {short:?, created:datetime(?), text:?})");
			PreparedStatement stmtConnect = 
				con.prepareStatement("match (t:Tweet), (u:User {username:?}) where t.short=? create (u)-[:authored]->(t)")) {
			stmtCreateTweet.setString(1, tweet.getShortText());
			stmtCreateTweet.setString(2, tweet.getDate());
			stmtCreateTweet.setString(3, tweet.getText());
			stmtCreateTweet.executeUpdate();
			stmtConnect.setString(1, username);
			stmtConnect.setString(2, tweet.getShortText());
			stmtConnect.executeUpdate();
		}
		catch (SQLException e) {
			throw new MyException("Could not make tweet correctly.");
		}
	}
	
	static void printList(List<String> list) {
		for (String item : list) {
			System.out.println(item);
		}
	}
}

class Tweet {
	
	// "2020-06-13T18:40:32.142+0100"
	static DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	
	String shortText;
	String text;
	LocalDateTime date;
	
	public Tweet(String shortText, String text, LocalDateTime date) {
		this.shortText = shortText;
		this.text = text;
		this.date = date;
	}
	
	public String getShortText() {
		return shortText;
	}
	
	public String getText() {
		return text;
	}
	
	public String getDate() {
		return date.format(dtf);
	}
	
	@Override
	public String toString() {
		return shortText + "\n" + date.format(dtf) + "\n" + text;
	}
}