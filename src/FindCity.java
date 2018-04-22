//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//
//public class FindCity extends FindCountry{
//	public static String countries_id = "";
//	
//	
//	public static ArrayList<String> countriesid = new ArrayList<String>();
//	public static ArrayList<String> countries_name = new ArrayList<String>();
//	public static ArrayList<String> countries_url = new ArrayList<String>();
//	
//	public static String only_page ="";
//	
//	public static int city_id = 1;
//
//	public static ArrayList<String> city_name = new ArrayList<String>();
//	public static ArrayList<String> city_url = new ArrayList<String>();
//
//	public static int num_city = 0;
//
//	public static String read_page = "";
//
//	static String InputFile = "input.txt";
//	static String OutputFilename = "output.txt";
//	private static PrintWriter OutputFile;
//
//	public static void main(String[] args) throws IOException {
//
//		FindCity main = new FindCity();
//
//		java.io.InputStream stream = null;
//		try {
//			stream = new FileInputStream(
//					"//Users//frank//Desktop//testing2.txt");
//			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
//
//			main.readFilename(in); // read file
//			OutputFile = new PrintWriter(
//					new FileWriter("//Users//frank//Desktop//testout.txt"));
//			OutputFile.printf("id\tcountry_id\tname\turl\t%n");
//
//			stream.close();
//		} catch (FileNotFoundException e) {
//			System.out.println("No more files");
//			OutputFile.close();
//
//		}
//
//		for (int i = 0; i < countriesid.size(); i++) {
//
//			countries_id = countriesid.get(i);
//			only_page = countries_url.get(i);
//			
//			System.out.println(countriesid.get(i) + "\t" + countries_name.get(i));
//				
//		
//			//System.out.println(current_page);
//			Document read = Jsoup.connect(only_page).userAgent("sadfsadfv").timeout(10000).get();
//			main.readHTML(read);
//			
//			
//			main.printWords();
//
//			main.reset();
//
//		}
//
//		OutputFile.close();
//		System.out.println("End of File");
//
//	}
//
//	// ---------------------------------------------------------------
//
//	private void readFilename(BufferedReader input) throws IOException {
//
//		String line = input.readLine();
//		char[] parse = { '	' };
//		String delims = new String(parse);
//
//		while (line != null) {
//			String[] lineWords = line.split(delims);
//			//System.out.println(lineWords[0]);
//			countriesid.add(lineWords[0]);
//			countries_name.add(lineWords[1]);
//			countries_url.add(lineWords[2]);
//
//			line = input.readLine();
//
//		}
//
//	}
//
//	private void reset() {
//		countries_id = "";
//		num_city = 0;
//		city_name.clear();
//		city_url.clear();
//		only_page = "";
//
//	}
//
//	private void readHTML(Document read) throws IOException {
//
//		Elements country_list = read.select("div#row-nohover").select("div[id=cell300left]").select("ul").select("li");
//		System.out.println(country_list.isEmpty());
//		for (int i = 0; i < country_list.size(); i++) {
//			city_name.add(country_list.get(i).select("a.redglow").text());
//			city_url.add("http://www.weatherbase.com/" + country_list.get(i).select("a.redglow").attr("href"));
//			System.out.println(country_list.get(i).select("a.redglow").text());
//			System.out.println(country_list.get(i).select("a.redglow").attr("href"));
//		}
//
//		num_city = city_name.size();
//
//	}
//
//	private void printWords() {
//
//		for (int i = 0; i < city_name.size(); i++) {
//			OutputFile.printf("%s\t%s\t%s\t%s%n", city_id, countries_id, city_name.get(i), city_url.get(i));
//			city_id++;
//		}
//
//	}
//
//}
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FindCity extends FindCountry{
	public static String countries_id = "";
	
	
	public static ArrayList<String> countriesid = new ArrayList<String>();
	public static ArrayList<String> countries_name = new ArrayList<String>();
	public static ArrayList<String> countries_url = new ArrayList<String>();
	
	public static String only_page ="";
	
	public static int city_id = 1;

	public static ArrayList<String> city_name = new ArrayList<String>();
	public static ArrayList<String> city_url = new ArrayList<String>();

	public static int num_city = 0;

	public static String read_page = "";

	static String InputFile = "input.txt";
	static String OutputFilename = "output.txt";
	private static PrintWriter OutputFile;

	public static void main(String[] args) throws IOException {

		FindCity main = new FindCity();

		java.io.InputStream stream = null;
		try {
			stream = new FileInputStream(
					"//Users//frank//Desktop//testing.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

			main.readFilename(in); // read file
			OutputFile = new PrintWriter(
					new FileWriter("//Users//frank//Desktop//testout.txt"));
			OutputFile.printf("id\tcountry_id\tname\turl\t%n");

			stream.close();
		} catch (FileNotFoundException e) {
			System.out.println("No more files");
			OutputFile.close();

		}

		for (int i = 0; i < countriesid.size(); i++) {

			countries_id = countriesid.get(i);
			only_page = countries_url.get(i);
			
			System.out.println(countriesid.get(i) + "\t" + countries_name.get(i));
				
		
			//System.out.println(current_page);
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
			//System.out.println(lineWords[0]);
			countriesid.add(lineWords[0]);
			countries_name.add(lineWords[1]);
			countries_url.add(lineWords[2]);

			line = input.readLine();

		}

	}

	private void reset() {
		countries_id = "";
		num_city = 0;
		city_name.clear();
		city_url.clear();
		only_page = "";

	}

	private void readHTML(Document read) throws IOException {
		Elements country_list = read.select("a.product-search-item");
//		Elements country_list = read.select("div#row-nohover").select("div[id=cell300left]").select("ul").select("li");
		System.out.println(country_list.isEmpty());
		for (int i = 0; i < country_list.size(); i++) {
			city_name.add(country_list.get(i).select("a.redglow").text());
			city_url.add("http://www.weatherbase.com/" + country_list.get(i).select("a.redglow").attr("href"));
			System.out.println(country_list.get(i).select("a.redglow").text());
			System.out.println(country_list.get(i).select("a.redglow").attr("href"));
		}

		num_city = city_name.size();

	}

	private void printWords() {

		for (int i = 0; i < city_name.size(); i++) {
			OutputFile.printf("%s\t%s\t%s\t%s%n", city_id, countries_id, city_name.get(i), city_url.get(i));
			city_id++;
		}

	}

}
