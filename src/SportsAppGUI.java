/*
 * TO IMPLEMENT/CHECK: 
 * 1) Will the rankings/standings update themselves or will they always stay the same? 
 * 2) 
 */

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.table.*;
//import java.net.*;

public class SportsAppGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private static int FRAME_WIDTH = 800, FRAME_HEIGHT = 900;
	private SportsAppGUI m = this;

	public SportsAppGUI() {
		// Set layout for the frame
		setLayout(new BorderLayout());

		// Creates/updates required files
		getData.main(null);

		// Buttons to be used in the panel
		JButton tennisMen = new JButton("ATP Men's Singles");
		tennisMen.addActionListener((e) -> {
			frame.setContentPane(new ATPGUI());
			frame.setVisible(true);
			frame.setTitle("Sports News");
		});

		JButton f1 = new JButton("F1 Standings");
		f1.addActionListener((e) -> {
			frame.setContentPane(new F1GUI());
			frame.setVisible(true);
		});

		JButton premLeague = new JButton("Premier League Standings");
		premLeague.addActionListener((e) -> {
			frame.setContentPane(new FootballGUI());
			frame.setVisible(true);
		});

		// Add buttons to the panel containing buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(tennisMen);
		buttonsPanel.add(f1);
		buttonsPanel.add(premLeague);
		add(buttonsPanel, BorderLayout.CENTER);
	}

	// Set the parameters for the frame
	public void defineFrame() {
		frame = new JFrame();

		this.setOpaque(true);
		frame.setContentPane(this);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
	}

	public static class ATPFileReader {

		// Convert content of the file to an ArrayList, line by line
		public static ArrayList<String> toArrayList() {
			ArrayList<String> content = new ArrayList<String>();
			try {
				File file = new File("filtered_atp.txt");
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

		// Class that converts the ArrayList to an Array
		public static Object[] toArray() {
			ArrayList<String> content = toArrayList();
			Object[] toReturn = new Object[content.size()];

			for (int i = 0; i < content.size(); i++) {
				toReturn[i] = content.get(i);
			}

			return toReturn;
		}

	}

	// Main method which initializes the GUI
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			new SportsAppGUI().defineFrame();
		});
	}

	public class ATPGUI extends JPanel {

		private static final long serialVersionUID = 1L;
		private TableDisplay tableDisplay;
		private Object[] columnNames = { "Position", "Player" };
		private Object[][] data = new Object[ATPFileReader.toArray().length][2];

		public ATPGUI() {

			setLayout(new BorderLayout());
			frame.setTitle("ATP Men's Singles Standings");

			tableDisplay = new TableDisplay();
			add(tableDisplay, BorderLayout.NORTH);

			Object[] teams = ATPFileReader.toArray();
			for (int i = 0; i < teams.length; i++) {
				Object[] temp = new Object[2];
				temp[0] = String.valueOf(i + 1);
				temp[1] = teams[i];
				data[i] = temp;
			}

			tableDisplay.updateTable(data, columnNames);

			JButton f1 = new JButton("F1 Standings");
			f1.addActionListener((e) -> {
//				frame.setContentPane(new F1GUI());
				frame.setVisible(true);
			});

			JButton premLeague = new JButton("Premier League Standings");
			premLeague.addActionListener((e) -> {
//				frame.setContentPane(new FootballGUI());
				frame.setVisible(true);
			});

			JButton main = new JButton("Home Page");
			main.addActionListener((e) -> {
				frame.setContentPane(m);
				frame.setVisible(true);
			});

			JPanel buttonsPanel = new JPanel();
			buttonsPanel.add(f1);
			buttonsPanel.add(premLeague);
			buttonsPanel.add(main);
			add(buttonsPanel, BorderLayout.SOUTH);

		}

		private class TableDisplay extends JPanel {
			private static final long serialVersionUID = 1L;
			private JTable table;
			private DefaultTableModel tableModel;

			public TableDisplay() {
				table = new JTable();
				table.setPreferredScrollableViewportSize(new Dimension(8 * FRAME_WIDTH / 10, FRAME_HEIGHT / 2));
				table.getTableHeader().setForeground(Color.BLUE);
				table.getTableHeader().setBackground(Color.WHITE);
				table.setGridColor(Color.BLACK);
				table.setForeground(Color.BLACK);
				table.setSelectionBackground(Color.GREEN);

				JScrollPane scrollPane = new JScrollPane(table);
				add(scrollPane);

			}

			public void updateTable(Object[][] data, Object[] columnNames) {
				tableModel = new DefaultTableModel(data, columnNames);
				table.setModel(tableModel);

			}
		}
	}

	public static class F1FileReader {

		public static ArrayList<String> toArrayList() {
			ArrayList<String> content = new ArrayList<String>();
			try {
				File file = new File("filtered_f1.txt");
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

		public static Object[] toArray() {
			ArrayList<String> content = toArrayList();
			Object[] toReturn = new Object[19];

			for (int i = 0; i < 19; i++) {
				toReturn[i] = content.get(i);
			}
			return toReturn;
		}

	}

	public class F1GUI extends JPanel {

		private static final long serialVersionUID = 1L;
		private TableDisplay tableDisplay;
		private Object[] columnNames = { "Position", "Driver" };
		private Object[][] data = new Object[19][2];

		public F1GUI() {
			setLayout(new BorderLayout());

			tableDisplay = new TableDisplay();
			add(tableDisplay, BorderLayout.NORTH);

			Object[] teams = F1FileReader.toArray();
			for (int i = 0; i < teams.length; i++) {
				Object[] temp = new Object[2];
				temp[0] = String.valueOf(i + 1);
				temp[1] = teams[i];
				data[i] = temp;
			}

			tableDisplay.updateTable(data, columnNames);

			JButton mainPage = new JButton("Home Page");
			mainPage.addActionListener((e) -> {
				frame.setContentPane(m);
			});

			JButton tennisMen = new JButton("ATP Men's Singles");
			tennisMen.addActionListener((e) -> {
				frame.setContentPane(new ATPGUI());
				frame.setVisible(true);
			});

			JButton premLeague = new JButton("Premier League Standings");
			premLeague.addActionListener((e) -> {
				frame.setContentPane(new FootballGUI());
				frame.setVisible(true);
			});

			JPanel buttonsPanel = new JPanel();
			buttonsPanel.add(premLeague);
			buttonsPanel.add(tennisMen);
			buttonsPanel.add(mainPage);
			add(buttonsPanel, BorderLayout.SOUTH);

		}

		private class TableDisplay extends JPanel {
			private static final long serialVersionUID = 1L;
			private JTable table;
			private DefaultTableModel tableModel;

			public TableDisplay() {
				table = new JTable();
				table.setPreferredScrollableViewportSize(new Dimension(8 * FRAME_WIDTH / 10, FRAME_HEIGHT / 2));
				table.getTableHeader().setForeground(Color.BLUE);
				table.getTableHeader().setBackground(Color.WHITE);
				table.setGridColor(Color.BLACK);
				table.setForeground(Color.BLACK);
				table.setSelectionBackground(Color.GREEN);

				JScrollPane scrollPane = new JScrollPane(table);
				add(scrollPane);
			}

			public void updateTable(Object[][] data, Object[] columnNames) {
				tableModel = new DefaultTableModel(data, columnNames);
				table.setModel(tableModel);
			}
		}
	}

	public class FootballGUI extends JPanel {

		private static final long serialVersionUID = 1L;
		private TableDisplay tableDisplay;
		private Object[] columnNames = { "Position", "Team" };
		private Object[][] data = new Object[20][2];

		public FootballGUI() {
			setLayout(new BorderLayout());

			tableDisplay = new TableDisplay();
			add(tableDisplay, BorderLayout.NORTH);

			Object[] teams = PremFileReader.toArray();
			for (int i = 0; i < 20; i++) {
				Object[] temp = new Object[2];
				temp[0] = String.valueOf(i + 1);
				temp[1] = teams[i];
				data[i] = temp;
			}

			tableDisplay.updateTable(data, columnNames);

			JButton mainPage = new JButton("Home Page");
			mainPage.addActionListener((e) -> {
				frame.setContentPane(m);
				frame.setVisible(true);
			});

			JButton tennisMen = new JButton("ATP Men's Singles");
			tennisMen.addActionListener((e) -> {
				frame.setContentPane(new ATPGUI());
				frame.setVisible(true);
			});

			JButton f1 = new JButton("F1 Standings");
			f1.addActionListener((e) -> {
				frame.setContentPane(new F1GUI());
				frame.setVisible(true);
			});

			JPanel buttonsPanel = new JPanel();
			buttonsPanel.add(f1);
			buttonsPanel.add(tennisMen);
			buttonsPanel.add(mainPage);
			add(buttonsPanel, BorderLayout.SOUTH);

		}

		private class TableDisplay extends JPanel {
			private static final long serialVersionUID = 1L;
			private JTable table;
			private DefaultTableModel tableModel;

			public TableDisplay() {
				table = new JTable();
				table.setPreferredScrollableViewportSize(new Dimension(8 * FRAME_WIDTH / 10, FRAME_HEIGHT / 2));
				table.getTableHeader().setForeground(Color.BLUE);
				table.getTableHeader().setBackground(Color.WHITE);
				table.setGridColor(Color.BLACK);
				table.setForeground(Color.BLACK);
				table.setSelectionBackground(Color.GREEN);

				JScrollPane scrollPane = new JScrollPane(table);
				add(scrollPane);

			}

			public void updateTable(Object[][] data, Object[] columnNames) {
				tableModel = new DefaultTableModel(data, columnNames);
				table.setModel(tableModel);

			}
		}
	}

	public static class PremFileReader {

		public static ArrayList<String> toArrayList() {
			ArrayList<String> content = new ArrayList<String>();
			try {
				File file = new File("football_filtered.txt");
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

		public static Object[] toArray() {
			ArrayList<String> content = toArrayList();
			Object[] toReturn = new Object[20];

			for (int i = 0; i < 20; i++) {
				toReturn[i] = content.get(i);
			}

			return toReturn;

		}

	}

}