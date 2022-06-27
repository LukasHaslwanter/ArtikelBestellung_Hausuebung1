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

public class instrument_spieler {
	public static void createTable(Connection c) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table IF NOT EXISTS instrument_spieler (name varchar(255), instrument varchar(255),age int,beginn date,insertDate date,foreign key(name,age) references spieler(name,age),foreign key(instrument)references instrument(name),primary key(name,instrument,age));";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("create i_s was not succesfull");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insert(Connection c, String pfad) {
		try {
			File ifile = new File(pfad);
			Scanner csv = new Scanner(ifile);
			String string = "";
			String[] i = new String[7];

			while (csv.hasNextLine()) {
				string = csv.nextLine();
				i = string.split(";");
				instrument_spieler.insertIntoSpieler(c, i);
			}
			csv.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertIntoSpieler(Connection c, String[] i) {
		try {
			DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			LocalDate Datum = LocalDate.parse(i[3], f);
			LocalDate Datum2 = LocalDate.now();
			java.sql.Date Zeit = java.sql.Date.valueOf(Datum);
			java.sql.Date Zeit2 = java.sql.Date.valueOf(Datum2);

			String sql = "insert into instrument_spieler(name,instrument,age,beginn,insertDate) values(?,?,?,?,?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, i[0]);
			pstmt.setString(2, i[1]);
			pstmt.setInt(3, Integer.parseInt(i[2]));
			pstmt.setDate(4, Zeit);
			pstmt.setDate(5, Zeit2);
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("insertIntospieler was successful");
		}catch(java.sql.SQLIntegrityConstraintViolationException e) {
			System.out.println("ist schon im table/Foreign key nicht gegeben");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could not insert this player");
		}

	}

	public static void droptable(Connection c) {
		try {
			Statement stmt = c.createStatement();
			String sql = "drop table if exists instrument_spieler;";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot drop Table instrument");
			System.exit(1);
		}
	}

	@SuppressWarnings("unchecked")
	public static void json(Connection c, String File) {
		try {
			FileWriter fw = new FileWriter(File);
			JSONObject json = new JSONObject();
			String d = "";

			Statement stmt = c.createStatement();
			String sql = "select * from instrument_spieler;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String name = rs.getString("name");
				String instrument = rs.getString("instrument");
				int age = rs.getInt("age");
				String date = rs.getString("beginn");
				String insertDate = rs.getString("insertDate");
				json.put("name", name);
				json.put("instrument" ,instrument);
				json.put("age", age);
				json.put("date", date);
				json.put("insertDate",insertDate);
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
