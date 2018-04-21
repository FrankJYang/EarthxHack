import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Weather extends FindCity {
	public static String cities_id = "";

	public static ArrayList<String> citiesid = new ArrayList<String>();
	public static ArrayList<String> cities_name = new ArrayList<String>();
	public static ArrayList<String> cities_url = new ArrayList<String>();

	public static String only_page = "";

	public static int weather_id = 1;

	public static ArrayList<String> weather_numb = new ArrayList<String>();
	public static ArrayList<String> weather_name = new ArrayList<String>();
	// public static ArrayList<String> city_url = new ArrayList<String>();

	public static int num_weather = 0;

	public static String read_page = "";

	static String InputFile = "input.txt";
	static String OutputFilename = "output.txt";
	private static PrintWriter OutputFile;

	public static void main(String[] args) throws IOException {

		Weather main = new Weather();

		java.io.InputStream stream = null;
		try {
			stream = new FileInputStream("//Users//frank//Desktop//untitled folder//untitled folder//practice.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

			main.readFilename(in); // read file
			OutputFile = new PrintWriter(
					new FileWriter("//Users//frank//Desktop//untitled folder//untitled folder//practiceOutput.txt"));
			//OutputFile.printf("pls work");

			stream.close();
		} catch (FileNotFoundException e) {
			System.out.println("No more files");
			OutputFile.close();

		}

		for (int i = 0; i < citiesid.size(); i++) {

			cities_id = citiesid.get(i);
			only_page = cities_url.get(i);

			System.out.println(citiesid.get(i) + "\t" + cities_name.get(i));

			// System.out.println(current_page);
			Document read = Jsoup.connect(only_page).userAgent("sadfsadfv").timeout(10000).get();
			main.readHTML(read);

			main.printWords();

			main.reset();

		}

		OutputFile.close();
		System.out.println("End of File");
		
	
		
	}

	// ---------------------------------------------------------------

	private void readFilename(BufferedReader input) throws IOException {

		String line = input.readLine();
		char[] parse = { '	' };
		String delims = new String(parse);

		while (line != null) {
			String[] lineWords = line.split(delims);
			// System.out.println(lineWords[0]);
			citiesid.add(lineWords[0]);
			cities_name.add(lineWords[2]);
			cities_url.add(lineWords[3]);

			line = input.readLine();

		}

	}

	private void reset() {
		cities_id = "";
		num_weather = 0;
		weather_numb.clear();
		weather_name.clear();
		// city_url.clear();
		only_page = "";

	}

	private void readHTML(Document read) throws IOException {

		Elements cities_list = read.select("div.p402_premium").select("table").select("tbody").select("tr");
		
		System.out.println(cities_list.size());

			
		
		for (int i = 0; i < cities_list.size(); i++) {

			weather_name.add(cities_list.get(i).select("td.h4line").select("div[id=h4font]").text());
			//System.out.println(weather_name);
			
			if (cities_list.get(i).select("td.data").text() != ""){

			weather_numb.add(cities_list.get(i).select("td.data").text());
			
			weather_name.removeAll(Arrays.asList("", ","));
			weather_numb.removeAll(Arrays.asList(null,""));
			//weather_name.removeAll(Arrays.asList(","));
			//
			}
			
			// add(cities_list.get(i).select("td.data").toString());
			// city_url.add("http://www.weatherbase.com/" +
			// country_list.get(i).select("a.redglow").attr("href"));
			// System.out.println(cities_list.get(i).select("td.data").toString());
			// System.out.println(country_list.get(i).select("td.data").attr("href"));
		}

		num_weather = weather_numb.size();

	}

	private void printWords() {

		for (int i = 0; i < weather_numb.size(); i++) {
			
			
			OutputFile.printf("%s\t%s\t%s\t%s%n", weather_id, cities_id, weather_name.get(i), weather_numb.get(i));
			
			

			weather_id++;
		}

	}

}
