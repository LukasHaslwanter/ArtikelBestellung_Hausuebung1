package CsV_DateienEinlesen;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;


public class Runner {				

	static ArrayList<String[]> columns = new ArrayList<>();	//String[0] = name; String[1] = dataType; (falls vorhanden) String[2] = varcharGr��e


	public static Connection getConnection(String url, String user, String password)  {
		//Class.forName("com.mysql.jdbc.Driver");
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static void dropTables(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "SET FOREIGN_KEY_CHECKS = 0;";
			stmt.executeUpdate(sql);
			
			sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'infi04_Schuelerverwaltung';";
			ResultSet rs = stmt.executeQuery(sql);
			sql = "";
			String temp = "";
			while(rs.next()) {
				temp += String.format("DROP TABLE IF EXISTS %s;", rs.getString("table_name"));
			}
			String[] moreSqls = temp.split(";");
			for (int i = 0; i < moreSqls.length; i++) {
				if(!moreSqls[i].equals("")) stmt.executeUpdate(moreSqls[i]);
			}
			
			sql = "SET FOREIGN_KEY_CHECKS = 1;";
			stmt.executeUpdate(sql);
			
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static String onlyName(String origin, int startPos) {
		String result = "";
		for (int i = startPos; i < origin.length(); i++) {
			char c = origin.charAt(i);
			if(c != '#' && c != '*') result += c;
			else break;
		}
		return result;
	}	//nimmt die sonderzeichen und das was danach ist, nicht



	public static String primKeys(String[] contents) {
		String primKeys = "PRIMARY KEY(";
		int numOfPrimKey = 0;
		for (int i = 0; i < contents.length; i++) {
			if(contents[i].contains("#")) {
				numOfPrimKey++;
				if(numOfPrimKey > 1) primKeys += ", ";
				primKeys += onlyName(contents[i], 0);
			}
		}
		return primKeys + ")";
	}


	public static String forKey(String[] contents) {
		String forKeys = "";

		for (int i = 0; i < contents.length; i++) {
			if(contents[i].contains("*")) {
				String name = onlyName(contents[i], 0);
				String reference = onlyName(contents[i], name.length() + 2); //2 hinter #* steht die reference

				forKeys += String.format(", FOREIGN KEY(%s) references %s(%s)", name, reference, name);
			}
		}
		return forKeys;
	}

	
	public static void createTable(Connection conn, String tableName, String[] contents) {
		try {
			String[] column;
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].contains("id")) column = new String[] {"", "INT"};
				else if (contents[i].contains("preis")) column = new String[] {"", "DOUBLE"};
				else if (contents[i].contains("datum")) column = new String[] {"", "DATE"};
				else column = new String[] {"", "VARCHAR(" + contents[i].length() + ")", contents[i].length() + ""};		//nicht so optimal
				
				column[0] = onlyName(contents[i], 0);
				columns.add(column);
			}
			String keys = primKeys(contents) + forKey(contents);

			Statement stmt = conn.createStatement();

			String sql = String.format("CREATE TABLE IF NOT EXISTS %s(", tableName);
			for (int i = 0; i < columns.size(); i++) {
				sql += String.format("%s %s, ", columns.get(i)[0], columns.get(i)[1]);
			}
			sql += String.format("%s);", keys);

			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	
	public static void insertInto(Connection conn, String tableName, String[] contents) {
		try {
			String sql = String.format("INSERT INTO %s VALUES(", tableName);
			for (int i = 0; i < columns.size(); i++) {
				sql += "?";
				if(i < columns.size() - 1) sql += ", ";
			}
			sql += ");";

			PreparedStatement preStmt = conn.prepareStatement(sql);
			for (int i = 0; i < columns.size(); i++) {
				//columns.get(i)[1) -> Datentyp
				if(columns.get(i)[1].equals("INT")) preStmt.setInt((i + 1), Integer.parseInt(contents[i]));
				else if(columns.get(i)[1].equals("DOUBLE")) preStmt.setDouble((i + 1), Double.parseDouble(contents[i]));
				else if(columns.get(i)[1].equals("DATE")) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					LocalDate datum = LocalDate.parse(contents[i], formatter);
					preStmt.setDate((i + 1), java.sql.Date.valueOf(datum));
				}
				else { //VARCHAR
					if(contents[i].length() > Integer.parseInt(columns.get(i)[2])) {	//alter Table wenn contents > laenge des VARCHAR()
						columns.get(i)[2] = contents[i].length() + "";

						Statement stmt = conn.createStatement();
						String alterSql = String.format("ALTER TABLE %s MODIFY %s VARCHAR(%d);", tableName, columns.get(i)[0], contents[i].length());

						stmt.executeUpdate(alterSql);
						stmt.close();
					}
					preStmt.setString((i + 1), contents[i]);
				}
			}
			preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//f�r etwas sch�nere ausgabe --> zuerst alles im String speichern, dann println
	public static void select(Connection conn, String tableName) {
		try {
			for (int i = 0; i < columns.size(); i++) {
				System.out.printf("%s, ", columns.get(i)[0]);
			}
			System.out.println();

			String sql = String.format("SELECT * FROM %s;", tableName);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String row = "";
				for (int i = 0; i < columns.size(); i++) {
					switch (columns.get(i)[1]) {
					case "INT": row += String.format("%d, ",rs.getInt(columns.get(i)[0])); break;
					case "DOUBLE": row += String.format("%f, ", rs.getDouble(columns.get(i)[0])); break;
					case "DATE": row += " " + rs.getDate(columns.get(i)[0]) + ", "; break;
					default: row += String.format("%s, ", rs.getString(columns.get(i)[0])); break;
					}
				}
				System.out.println(row);		//eventuell in select-methode einbauen
			}
			System.out.println();
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	
	
	public static void main(String[] args) {
		try {
				String url = "jdbc:mysql://localhost:3306/schuelerverwaltung";//localhost:3306/infi04_schuelerverwaltung
				String user="root";
				String password="";
				String csvFilePath = "C://Users//LukasHaslwanter//Documents//swp//Schuelerverwaltung.csv";
				
			/*String configFilePath = "Hausaufgaben/H04_CsvEinlesen/config.properties";
            FileInputStream propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            prop.load(propsInput);*/
            

			boolean alreadySelectet = false;
			int newTable = 0;
			String tableName = "";
		
			

			Connection conn = getConnection(url, user, password);
			System.out.println("Connection erfolgreich\n");
			conn.setAutoCommit(true);

			dropTables(conn);

			Scanner scanner = new Scanner (new File(csvFilePath));
			while(scanner.hasNextLine()) {
//				String row = scanner.nextLine();
				String[] contents = scanner.nextLine().split(";");

				if(contents.length < 1 ) {			//ist die Zeile leer, ist die Tabelle vorbei
					newTable = 0;
					if(alreadySelectet == false) { //tabelle soll nur 1mal ausgegeben werden
						select(conn, tableName);
						alreadySelectet = true;
					}
					while(columns.size() > 0) {
						columns.remove(0);
					}
				}
				else {
					//"unsichtbare Tippfehler" entfernen
					for (int i = 0; i < contents.length; i++) {
						contents[i].trim();
					}
					
					if (newTable == 0) {				//0 -> name der Tabelle 
						tableName = contents[0];
						alreadySelectet = false; //f�r sp�tere ausgabe/select zur�cksetzen
						newTable++;
					}
					else if(newTable == 1) {			//1 -> spaltennamen
						createTable(conn, tableName, contents);
						newTable++;
					}
					else {								//sonst contents inserten Tabelle
						insertInto(conn, tableName, contents);
					}
				}
			}
			if(alreadySelectet == false) { //letzte tabelle, falls danach die CSV-Datei endet
				select(conn, tableName);
				alreadySelectet = true;
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}