package llosa.joel.springboot.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

@SpringBootApplication
public class Main implements CommandLineRunner {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	public void run(String... arg0) throws Exception {
		System.out.println("Building tables");
		jdbcTemplate.execute("DROP TABLE movies IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE movies(id SERIAL, title VARCHAR(255), description VARCHAR(255))");
		
		System.out.println("\nCreating 3 movie records...");
		jdbcTemplate.update("INSERT INTO movies(title, description) VALUES (?, ?)", "Mr. Deeds", "Comedy");
		jdbcTemplate.update("INSERT INTO movies(title, description) VALUES (?, ?)", "Mad Max Fury Road", "Science Fiction");
		jdbcTemplate.update("INSERT INTO movies(title, description) VALUES (?, ?)", "We Were Soldiers", "War");
		
		readRecords();
		
		System.out.println("\nUpdating Mad Max Fury Road record...");
		jdbcTemplate.update("UPDATE movies SET description = ? WHERE title = ?", "Action/Adventure", "Mad Max Fury Road");
		
		readRecords();
		
		System.out.println("\nDeleting Mr. Deeds record...");
		jdbcTemplate.update("DELETE FROM movies WHERE title = ?", "Mr. Deeds");
		
		readRecords();
	}
	
	private void readRecords() {
		System.out.println("Reading movie records...");
		System.out.printf("%-30.30s  %-30.30s%n", "Title", "Description");
		jdbcTemplate.query("SELECT * FROM movies", new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				System.out.printf("%-30.30s  %-30.30s%n", rs.getString("title"), rs.getString("description"));
			}
			
		});
	}

}
