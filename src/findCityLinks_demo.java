
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class findCityLinks_demo {

	// for saving objects
	public static String country_id = "";
	public static String check_page = "";
	public static ArrayList<String> countryid = new ArrayList<String>();
	public static ArrayList<String> country_name = new ArrayList<String>();
	public static ArrayList<String> country_url = new ArrayList<String>();
	public static int city_id = 1;
	public static ArrayList<String> city_name = new ArrayList<String>();
	public static ArrayList<String> city_url = new ArrayList<String>();

	public static String current_page = "";
	public static String next_page = "";
	public static int page_num = 1;
	public static int num_city = 0;
	static String InputFile = "country.txt";
	static String OutputFilename = "output.txt";
	private static PrintWriter OutputFile;

	public static void main(String[] args) throws IOException {

		findCityLinks_demo main = new findCityLinks_demo();

		java.io.InputStream stream = null;
		try {
			stream = new FileInputStream("//Users//frank//Desktop//untitled folder//untitled folder//continentinput.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

			main.readFilename(in); // read file
			OutputFile = new PrintWriter(
					new FileWriter("//Users//frank//Desktop//untitled folder//untitled folder//countryoutput.txt"));
			//OutputFile.printf("id\tcountry_id\tname\turl\t%n");

			stream.close();
		} catch (FileNotFoundException e) {
			System.out.println("no more files");
			OutputFile.close();
		}

		for (int i = 0; i < countryid.size(); i++) {

			country_id = countryid.get(i);
			current_page = country_url.get(i);

			System.out.println(countryid.get(i) + "\t" + country_name.get(i));

			// System.out.println(next_page);

			String read_page = current_page;

			while (!next_page.contains("#disabled")) {

				if (next_page != "")
					current_page = read_page + next_page;
				System.out.println(current_page);
				Document read = Jsoup.connect(current_page).get();
				main.readHTML(read);

			}

			main.printWords();

			main.reset();

		}

		OutputFile.close();
		System.out.println("FILE END");

	}

	private void readFilename(BufferedReader input) throws IOException {

		String line = input.readLine();
		char[] parse = { '	' };
		String delims = new String(parse);

		while (line != null) {
			String[] lineWords = line.split(delims);
			System.out.println(lineWords[0]);
			countryid.add(lineWords[0]);
			country_name.add(lineWords[1]);
			country_url.add(lineWords[2]);

			line = input.readLine();

		}

	}

	private void reset() {
		country_id = "";
		current_page = "";
		next_page = "";
		num_city = 0;
		city_name.clear();
		page_num = 1;
		city_url.clear();

	}

	private void readHTML(Document read) throws IOException {

		Elements city_list = read.select("div[id=row-nohover]").select("div[id=cell300left]").select("ul").select("li");
		for (int i = 0; i < city_list.size(); i++) {
			// System.out.println(city_list.get(i));
			// System.out.println(city_list.get(i).select("strong").text());
			// System.out.println("http://www.mafengwo.cn"+city_list.get(i).attr("href"));
			city_name.add(city_list.get(i).select("a.redglow").text());
			city_url.add("http://www.mafengwo.cn" + city_list.get(i).attr("href"));
		}

		page_num++;
		if (read.select("div[id=citylistpagination]").select("a.pg-next").isEmpty()) {
			next_page = "#disabled";
			page_num = 1;
		} else
			next_page = "?page=" + String.valueOf(page_num);
		// System.out.println(next_page);

		num_city = city_name.size();

	}

	private void printWords() {

		for (int i = 0; i < city_name.size(); i++) {
			OutputFile.printf("%s\t%s\t%s\t%s%n", city_id, country_id, city_name.get(i), city_url.get(i));
			city_id++;
		}

	}

}