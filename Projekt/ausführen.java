package Projekt;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

//C:\Users\LukasHaslwanter\Documents\Infi\Projekt\instrument.csv
//C:\Users\LukasHaslwanter\Documents\Infi\Projekt\spieler.csv
//C:\Users\LukasHaslwanter\Documents\Infi\Projekt\instrument_spieler.csv
//C:\\Users\\LukasHaslwanter\\Documents\\Infi\\Projekt\\json.txt
public class ausführen {
	static String url = "jdbc:mysql://localhost:3306/instrumente";
	static String user = "root";
	static String password = "";

	public static void main(String[] args) {
		Connection c = getConnection(url, user, password);
		
		 /* instrument_spieler.droptable(c); 
		  instrument.droptable(c);
		  spieler.droptable(c);
		  
		  instrument.createtable(c); spieler.createtable(c);
		  instrument_spieler.createTable(c);
		  spieler.createtable(c);*/

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("[p-pfadeingabe/ a-ausgabe/ exit ist immer möglich]");
			System.out.print("Home>");
			String usereingabe = scanner.next();
			System.out.println();
			if (usereingabe.equals("p")) {
				System.out.println("[s-spieler/ i-instrument/ i_s-instrument-spieler");
				System.out.print("Speicherort>");
				usereingabe = scanner.next();
				if (usereingabe.equals("s")) {
					System.out.println("Bitte Pfad eingeben:");
					String pfad = scanner.next();
					if (pfad.equals("exit")) {
					} else {
						spieler.insert(c, pfad);
					}
				}
				if (usereingabe.equals("i")) {
					System.out.println("Bitte Pfad eingeben:");
					String pfad = scanner.next();
					if (pfad.equals("exit")) {
					} else {
						instrument.insert(c, pfad);
					}
				}
				if (usereingabe.equals("i_s")) {
					System.out.println("Bitte Pfad eingeben:");
					String pfad = scanner.next();
					if (pfad.equals("exit")) {
					} else {
						instrument_spieler.insert(c, pfad);
					}
				}
				
			}
			if (usereingabe.equals("a")) {
				System.out.println("[i-instrument/ s-spieler/ i_s-instrument/spieler");
				System.out.print("Was soll ausgegeben werden?>");
				usereingabe = scanner.next();
				if (usereingabe.equals("i")) {
					System.out.println("Bitte Pfad eingeben>");
					String pfad = scanner.next();
					if (pfad.equals("exit")) {
					} else {
						instrument.json(c, pfad);
					}
				}
				if (usereingabe.equals("s")) {
					System.out.println("Bitte Pfad eingeben>");
					String pfad = scanner.next();
					if (pfad.equals("exit")) {
					} else {
						spieler.json(c, pfad);
					}
				}
				if (usereingabe.equals("i_s")) {
					System.out.println("Bitte Pfad eingeben>");
					String pfad = scanner.next();
					if (pfad.equals("exit")) {
					} else {
						instrument_spieler.json(c, pfad);
					}
				}
			}
		}
	}

	static Connection getConnection(String url, String user, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}