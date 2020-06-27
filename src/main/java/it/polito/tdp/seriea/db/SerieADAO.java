package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.seriea.model.PuntiCampionato;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listAllSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> listTeams() {
		String sql = "SELECT Distinct(team) FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Team(res.getString("team")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public List<PuntiCampionato> getPuntiCampionaot(Team t){
		
		String sql= "SELECT s.season AS s,s.description AS d,3*COUNT(m1.match_id)+m2.pareggi+m3.fuori AS totale FROM seasons AS s, matches AS m1,(SELECT Season,COUNT(*) AS pareggi FROM matches WHERE (HomeTeam=? OR AwayTeam=?) AND FTR='D' GROUP BY season) AS m2,(SELECT Season,3*COUNT(*) AS fuori FROM matches WHERE AwayTeam=? AND FTR='A' GROUP BY season) AS m3 WHERE m1.HomeTeam=? AND m1.FTR='H' AND m1.Season=m2.Season AND m1.Season=m3.Season and m1.Season=s.season GROUP BY m1.season";
		List<PuntiCampionato> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, t.getTeam());
			st.setString(2, t.getTeam());
			st.setString(3, t.getTeam());
			st.setString(4, t.getTeam());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Season s= new Season(res.getInt("s"), res.getString("d"));
				PuntiCampionato pc= new PuntiCampionato(s, res.getInt("totale"));
				result.add(pc);
				
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	

}

