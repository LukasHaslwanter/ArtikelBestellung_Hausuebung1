package JSON;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import org.json.simple.JSONObject;

public class json_write {
	
	@SuppressWarnings("unchecked")
	static void write(Connection c, String file) {
		try {
			FileWriter fw = new FileWriter(file);
			JSONObject json = new JSONObject();
			String d = "";

			Statement stmt = c.createStatement();
			String sql = "select Produkt, preis, age, beschreibung from json;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String Produkt = rs.getString("Produkt");
				String preis = rs.getString("preis");
				int age = rs.getInt("age");
				String beschreibung = rs.getString("beschreibung");
				
				json.put("Produkt", Produkt);
				json.put("preis", preis);
				json.put("age", age);
				json.put("beschreibung", beschreibung);	
				d = d + json;
			}
			fw.write(d);
			fw.flush();
			fw.close();
			rs.close();
			stmt.close();
			System.out.println("Datei wurde befüllt!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}