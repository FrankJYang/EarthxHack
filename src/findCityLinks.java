import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class findCityLinks {
	// for saving objects
	public static int country_id = 0;
	public static String check_page = "";
	// public static int num_city = 0;

	public static ArrayList<String> name = new ArrayList<String>();
	public static ArrayList<String> url = new ArrayList<String>();
	public static ArrayList<String> proxy_host = new ArrayList<String>();
	public static ArrayList<String> proxy_port = new ArrayList<String>();
	static int temp_proxy = 0;
	public static String current_page = "";
	public static String next_page = "";
	public static int page_num = 1;
	public static int num_city = 0;

	public static void main(String[] args) throws IOException {
		findCityLinks main = new findCityLinks();

		// System.out.print(System.getProperty("file.encoding"));
		java.io.InputStream stream = null;
		try {
			stream = new FileInputStream("//Users//frank//Desktop//untitled folder//Favorites.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			main.readproxy(in); // read file

		} catch (FileNotFoundException e) {
			System.out.println("no more proxy");

		}

		try {

			Class.forName("//Users//frank//Desktop//untitled folder//Favorites.txt");
			Connection conn = DriverManager.getConnection(current_page, null);

			Statement statement = conn.createStatement();
			String queryString = "SELECT * FROM country WHERE numCities is null"; // WHERE
																					// isError
																					// is
																					// null
			ResultSet rs = statement.executeQuery(queryString);

			while (rs.next()) {

				try {
					country_id = rs.getInt(1);
					current_page = rs.getString(3);

					System.out.println(rs.getInt(1) + "\t" + rs.getString(2));

					// System.out.println(next_page);

					String read_page = current_page;

					while (!next_page.contains("#disabled")) {

						if (next_page != "")
							current_page = read_page + next_page;
						System.out.println(current_page);
						Document read = Jsoup.connect(current_page).get();
						main.readHTML(read);

						if (temp_proxy == 5) {
							TimeUnit.SECONDS.sleep(10);
							Random generator = new Random();
							int px = generator.nextInt(proxy_host.size());
							System.setProperty("https.proxyHost", proxy_host.get(px));
							System.setProperty("https.proxyPort", proxy_port.get(px));
							System.out.println(proxy_host.get(px) + ":" + proxy_port.get(px));
							temp_proxy = 0;

						} else {
							temp_proxy++;

						}
					}

					// main.printWords(conn);

					main.reset();

				} catch (Exception e) {
					PreparedStatement prep = conn.prepareStatement("UPDATE country set isError = 1 WHERE id = ?");
					prep.setInt(1, country_id);
					prep.executeUpdate();
					main.reset();
				}

			}
		} catch (Exception e) {

			System.out.println("SERVER DISCONNECTED");

		}

		System.out.println("FILE END");

	}

	private void readproxy(BufferedReader input) throws IOException {

		String line = input.readLine();
		char[] parse = { ':' };
		String delims = new String(parse);

		while (line != null) {
			String[] lineWords = line.split(delims);

			proxy_host.add(lineWords[0]);
			proxy_port.add(lineWords[1]);

			line = input.readLine();

		}

		Random generator = new Random();
		int px = generator.nextInt(proxy_host.size());
		System.setProperty("https.proxyHost", proxy_host.get(px));
		System.setProperty("https.proxyPort", proxy_port.get(px));
		System.out.println(proxy_host.get(px) + ":" + proxy_port.get(px));

	}

	private void reset() {
		country_id = 0;
		current_page = "";
		next_page = "";
		num_city = 0;
		name.clear();
		page_num = 1;
		url.clear();

	}

	private void readHTML(Document read) throws IOException {

		Elements city_list = read.select("li.item").select("dl.caption").select("dt").select("a");
		for (int i = 0; i < city_list.size(); i++) {
			// System.out.println(city_list.get(i));
			// System.out.println(city_list.get(i).select("strong").text());
			// System.out.println("http://www.mafengwo.cn"+city_list.get(i).attr("href"));
			name.add(city_list.get(i).select("strong").text());
			url.add("http://www.mafengwo.cn" + city_list.get(i).attr("href"));
		}

		page_num++;
		if (read.select("div[id=citylistpagination]").select("a.pg-next").isEmpty()) {
			next_page = "#disabled";
			page_num = 1;
		} else
			next_page = "?page=" + String.valueOf(page_num);
		// System.out.println(next_page);

		num_city = name.size();

	}

	private void printWords(Connection conn) {

		PreparedStatement prep;
		try {
			for (int i = 0; i < name.size(); i++) {
				prep = conn.prepareStatement("INSERT INTO city (country_id,name,url) VALUES(?, ?, ?)");

				prep.setInt(1, country_id);
				prep.setString(2, name.get(i));
				prep.setString(3, url.get(i));
				prep.executeUpdate();

			}
			PreparedStatement update = conn.prepareStatement("UPDATE country set isError = 0 WHERE id = ?");
			update.setInt(1, country_id);
			update.executeUpdate();

			PreparedStatement update1 = conn.prepareStatement("UPDATE country set numCities = ? WHERE id = ?");
			update1.setInt(1, num_city);
			update1.setInt(2, country_id);
			update1.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQL detail error");
		}

	}

}