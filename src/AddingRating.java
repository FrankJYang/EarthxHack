import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AddingRating {
	
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

	public static void main(String[] args) throws Exception{
		AddingRating main = new AddingRating();
		java.io.InputStream stream = null;
		try {
			stream = new FileInputStream(
					"//Users//frank//Desktop//untitled folder//untitled folder//countryoutput.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

			main.readFilename(in); // read file
			OutputFile = new PrintWriter(
					new FileWriter("//Users//frank//Desktop//untitled folder//untitled folder//testing.txt"));
			OutputFile.printf("id\tcountry_id\tname\turl\t%n");

			stream.close();
		} catch (FileNotFoundException e) {
			System.out.println("No more files");
			OutputFile.close();

		}

	}
	
	private void readFilename(BufferedReader input) throws IOException {

		String line = input.readLine();
		char[] parse = { '	' };
		String delims = new String(parse);

		while (line != null) {
			String[] lineWords = line.split(delims);
			//System.out.println(lineWords[0]);
			countriesid.add(lineWords[0]);
			countries_name.add(lineWords[2]);
			countries_url.add(lineWords[3]);

			line = input.readLine();

		}

	}

}
