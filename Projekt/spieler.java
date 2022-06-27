package Projekt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.json.simple.JSONObject;

public class spieler {

	public static void createtable(Connection c) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists spieler (name varchar(255), age int,beginn date,primary key(name,age,beginn));";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("create spieler was successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot create Table spieler");
		}

	}

	public static void droptable(Connection c) {
		try {
			Statement stmt = c.createStatement();
			String sql = "drop table if exists spieler;";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot drop Table spieler");
			System.exit(1);
		}
	}

	public static void insert(Connection c, String pfad) {
		try {
			// Scanner s = new Scanner(System.in);
			// String file = s.nextLine();
			// File ifile = new File(file);
			// System.out.println(s);
			File ifile = new File(pfad);

			Scanner csv = new Scanner(ifile);
			String string = "";
			String[] i = new String[3];

			while (csv.hasNextLine()) {
				string = csv.nextLine();
				i = string.split(";");
				spieler.insertIntoSpieler(c, i);
			}
			csv.close();
			// s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void insertIntoSpieler(Connection c, String[] spieler) {
		try {
			
			DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			LocalDate Datum = LocalDate.parse(spieler[2], f);
			java.sql.Date Zeit = java.sql.Date.valueOf(Datum);

			String sql = "insert into spieler(name,age,beginn) values(?,?,?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, spieler[0]);
			pstmt.setInt(2, Integer.parseInt(spieler[1]));
			pstmt.setDate(3, Zeit);
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("insertIntospieler was successful");
		}catch(java.sql.SQLIntegrityConstraintViolationException e) {
			System.out.println("Spieler gibts schon");
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could not insert this player");
		}
	}

	@SuppressWarnings("unchecked")
	public static void json(Connection c, String File) {
		try {
			FileWriter fw = new FileWriter(File);
			JSONObject json = new JSONObject();
			String d = "";

			Statement stmt = c.createStatement();
			String sql = "select * from spieler;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String beginn = rs.getString("beginn");
				json.put("name", name);
				json.put("age", age);
				json.put("beginn", beginn);
				d = d + json;
			}
			fw.write(d);
			fw.flush();
			fw.close();
			rs.close();
			stmt.close();
			System.out.println("Datei wurde befüllt");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
