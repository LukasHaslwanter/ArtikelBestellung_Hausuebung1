package Projekt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.json.simple.JSONObject;

public class instrument {
	static String checkname;
	
	public static void insert(Connection c,String pfad) {
		try {
		//Scanner s = new Scanner(System.in);
		//String file = s.nextLine();
		//File ifile = new File(file);
		//System.out.println(s);
			File ifile = new File(pfad);
			
		Scanner csv = new Scanner(ifile);
		String string = "";
		String[] i = new String[3];
		while (csv.hasNextLine()) {
			string = csv.nextLine();
			i = string.split(";");
			instrument.insertIntoInstrument(c, i);
		}
		csv.close();
		//s.close();

	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	public static void createtable(Connection c) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists instrument (name varchar(255) PRIMARY KEY);";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("create instrument was successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot create Table instrument");
			System.exit(1);
		}
	}
	
	public static void droptable(Connection c) {
		try {
			Statement stmt = c.createStatement();
			String sql = "drop table if exists instrument;";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot drop Table instrument");
			System.exit(1);
		}
	}
	
	public static void insertIntoInstrument(Connection c,String[] name) {
		
		try {
			Statement stmt = c.createStatement();
			String sql1 = "select name from instrument;";
			ResultSet rs = stmt.executeQuery(sql1);
			while(rs.next()) {
			 checkname = rs.getString("name");
			}
			if(name[0].equals(checkname)) {
				System.out.println("Instrument befindet sich schon in der Datenbank");
			}else {
			String sql = "insert into instrument(name) values(?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, name[0]);
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("insertIntoinstrument was successful");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could not insert this instrument");
		}
	}

	@SuppressWarnings("unchecked")
	public static void json(Connection c, String File){
		try {
			FileWriter fw = new FileWriter(File);
			JSONObject json = new JSONObject();
			String d = "";
			
			Statement stmt = c.createStatement();
			String sql = "select * from instrument;";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String name = rs.getString("name");
				json.put("name",name);
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
