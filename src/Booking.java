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

public class Booking {
	public static String hotel_id = "";

	public static ArrayList<String> hotelid = new ArrayList<String>();
	public static ArrayList<String> hotel_name = new ArrayList<String>();
	public static ArrayList<String> hotel_url = new ArrayList<String>();

	public static String only_page = "";

	public static int id = 1;
	public static ArrayList<String> name = new ArrayList<String>();
//	public static ArrayList<String> stars = new ArrayList<String>();
//	public static ArrayList<String> address = new ArrayList<String>();
//	public static ArrayList<String> description = new ArrayList<String>();
//	public static ArrayList<String> info = new ArrayList<String>();
//	public static ArrayList<String> popular = new ArrayList<String>();

	public static String read_page = "";

	static String InputFile = "/Users/frank/Desktop/testing.txt";
	static String OutputFilename = "/Users/frank/Desktop/testout.txt";
	private static PrintWriter OutputFile;

	public static void main(String[] args) throws IOException {

		Booking main = new Booking();
		
		java.io.InputStream stream = null;
		try {
			stream = new FileInputStream(InputFile);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

			main.readFilename(in); // read file
			OutputFile = new PrintWriter(new FileWriter(OutputFilename));
			
			stream.close();
		} catch (FileNotFoundException e) {
			System.out.println("No more files");
			OutputFile.close();

		}

		for (int i = 0; i < hotelid.size(); i++) {

			hotel_id = hotelid.get(i);
			only_page = hotel_url.get(i);

			Document read = Jsoup.connect(only_page).userAgent("jioio").timeout(100000).get();

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

			hotelid.add(lineWords[0]);
			hotel_name.add(lineWords[1]);
			hotel_url.add(lineWords[2]);

			line = input.readLine();

		}

	}

	private void reset() {
		hotel_id = "";

		name.clear();
//		stars.clear();
//		address.clear();
//		description.clear();
//		info.clear();
//		popular.clear();
		only_page = "";

	}

	private void readHTML(Document read) throws IOException {

		Elements name_list = read.select("ul#product-search-results").select("a[href=https://www.barcodelookup.com/791484283729].product-search-item").select("li");
		System.out.println(name_list.isEmpty());
		System.out.println(hotel_url);
		//				.select("div#mCSB_1_container.mCSB_container");
//		Elements address_list = read.select("div#right.rlt-right").select("div#wrap-hotelpage-top.wrap-hotelpage-top")
//				.select("p#showMap2.address.address_clean");
//		Elements stars_list = read.select("div#right.rlt-right").select("div#wrap-hotelpage-top.wrap-hotelpage-top")
//				.select("h1.item");
//		Elements popular_list = read.select("div.hotel_description_wrapper_exp.hp-description");
//		Elements description_list = read.select("div.hotel_description_wrapper_exp.hp-description");
//		Elements info_list = read.select("div.hotel_description_wrapper_exp.hp-description");

		// name
		for (int i = 0; i < name_list.size(); i++) {
			if (name_list.get(i).select("a[class=product-search-item]").isEmpty()) {
				name.add("name=null");
				System.out.println("hi");
			}
			name.add(name_list.get(i).select("a[class=product-search-item]").text());
			System.out.println(name.get(i));
			System.out.println("bye");

		}
		// address
//		for (int j = 0; j < address_list.size(); j++) {
//			if (address_list.get(j).select("span.hp_address_subtitle.jq_tooltip").isEmpty()) {
//				address.add("address=null");
//			}
//			address.add(address_list.get(j).select("span.hp_address_subtitle.jq_tooltip").text());
//			System.out.println(address.get(j));
//		}
//		// stars
//		for (int k = 0; k < stars_list.size(); k++) {
//			if (stars_list.get(k).select("span.nowrap.hp__hotel_ratings").select("span.hp__hotel_ratings__stars")
//					.select("i.b-sprite").isEmpty()) {
//				stars.add("stars=null");
//			}
//			stars.add(stars_list.get(k).select("span.nowrap.hp__hotel_ratings").select("span.hp__hotel_ratings__stars")
//					.select("i.b-sprite").text());
//			System.out.println(stars.get(k));
//		}
//		// popular
//		for (int l = 0; l < popular_list.size(); l++) {
//			if (popular_list.get(l).select("div.hp_desc_important_facilities")
//					.select("div.cc_hp_highlight_important_facilities")
//					.select("div.important_facility.hp-desc-facility").isEmpty()) {
//				popular.add("popular=null");
//			}
//			popular.add(popular_list.get(l).select("div.hp_desc_important_facilities")
//					.select("div.cc_hp_highlight_important_facilities")
//					.select("div.important_facility.hp-desc-facility").text());
//			System.out.println(popular.get(l));
//		}
//		// description
//		for (int m = 0; m < description_list.size(); m++) {
//			if (description_list.get(m).select("div#summary").isEmpty()) {
//				description.add("description=null");
//			}
//			description.add(description_list.get(m).select("div#summary").text());
//			description.add(description_list.get(m).select("p.summary.hotel_meta_style").text());
//			System.out.println(description.get(m));
//		}
//		// info
//		for (int n = 0; n < info_list.size(); n++) {
//			if (info_list.get(n).select("p.summary.hotel_meta_style").isEmpty()) {
//				info.add("info=null");
//			}
//
//			info.add(info_list.get(n).select("p.summary.hotel_meta_style").text());
//			System.out.println(info.get(n));
//		}

	}

	private void printWords() {
 
		for (int i = 0; i < name.size(); i++) {
		
			OutputFile.printf("%s\t"
//					+ "%s\t%s\t%s\t%s\t%s\t%s\t%s%n"
					+ "", id, hotel_id, name.get(i)
//					, address.get(i),
//					stars.get(i), popular.get(i), description.get(i), info.get(i)
					);
			id++;
		}

	}

}
