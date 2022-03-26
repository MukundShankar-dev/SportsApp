

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class getData {

	public static void main(String[] args) {
		String footballContent = getURLContents("https://www.premierleague.com/tables");
		createFile(footballContent, "football.txt");
		filterPremFile();

		String atpContent = getURLContents("https://www.atptour.com/en/rankings/singles");
		createFile(atpContent, "atp.txt");
		filterATPFile();

		String f1Content = getURLContents("https://www.formula1.com/en/results.html/2022/drivers.html");
		createFile(f1Content, "f1.txt");
		filterF1File();
	}

	public static String getURLContents(String url) {
		StringBuffer content = new StringBuffer();

		try {
			URL theURL = new URL(url);
			URLConnection urlConnection = theURL.openConnection();

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				content.append(line + "\n");
			}

			bufferedReader.close();

		} catch (Exception e) {
			System.out.println("Something went wrong when fetching webpage contents :/");
		}

		return content.toString();
	}

	public static void createFile(String content, String name) {
		File file = new File(name);

		try {
			file.createNewFile();
			writeToFile(file, content);
		} catch (IOException e) {
			System.out.println("Error when creating file");
		}

	}

	public static void writeToFile(File file, String content) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			System.out.println("Something went wrong when writing content to the file");
		}
	}

	public static ArrayList<String> toArrayList(String name) {
		ArrayList<String> content = new ArrayList<String>();
		try {
			File file = new File(name);
			Scanner sc = new Scanner(file);

			while (sc.hasNextLine()) {
				content.add(sc.nextLine());
			}

			sc.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return content;
	}

	public static void filterPremFile() {
		ArrayList<String> content = toArrayList("football.txt");
		ArrayList<String> filtered = new ArrayList<>();
		ArrayList<String> finalFiltered = new ArrayList<>();
		String c = "";

		for (String s : content) {
			if (s.contains("data-compseason=\"418\"")) {
				filtered.add(s.strip());
			}
		}

		for (String filter : filtered) {
			int startIndex = filter.indexOf("data-filtered-table-row-name=") + 30;
			int endIndex = filter.indexOf("data-filtered-table-row-opta") - 2;
			if (startIndex - 30 > 0 && endIndex + 2 > 0) {
				finalFiltered.add(filter.substring(startIndex, endIndex));
				c += filter.substring(startIndex, endIndex) + "\n";
			}

			createFile(c, "football_filtered.txt");

		}
	}

	public static void filterATPFile() {
		ArrayList<String> content = toArrayList("atp.txt");
		ArrayList<String> filtered = new ArrayList<>();
		ArrayList<String> finalFiltered = new ArrayList<>();
		String c = "";

		for (String s : content) {
//			System.out.println(s);
			if (s.contains("rankings-breakdown?team=singles")) {
				filtered.add(s.strip());
//				System.out.println(s);
			}
		}

		for (String filter : filtered) {
			int startIndex = filter.indexOf("players/") + 8;
			int endIndex = filter.indexOf("/rankings") - 5;
			finalFiltered.add(filter.substring(startIndex, endIndex));
			c += filter.substring(startIndex, endIndex) + "\n";
		}

		createFile(c, "filtered_atp.txt");

	}

	public static void filterF1File() {
		ArrayList<String> content = toArrayList("f1.txt");
		ArrayList<String> filtered = new ArrayList<>();
		ArrayList<String> finalFiltered = new ArrayList<>();
		String c = "";

		for (String s : content) {
			if (s.contains("\"hide-for-tablet\"")) {
				filtered.add(s.strip());
			}
		}
		
		for (String filter : filtered) {
			int startIndex = filter.indexOf("\"hide-for-tablet\"") + 18;
			int endIndex = filter.indexOf("</span>");
			finalFiltered.add(filter.substring(startIndex, endIndex));
			c += filter.substring(startIndex, endIndex) + "\n";
		}

		createFile(c, "filtered_f1.txt");
	}

}
