package iit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Hello {

	public static void main(String[] args) {
		try (Connection con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost:7687", "neo4j", "n5if3v");
				Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery("MATCH (n:User) with n.username as name RETURN name");
			while (rs.next()) {
				System.out.println(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}