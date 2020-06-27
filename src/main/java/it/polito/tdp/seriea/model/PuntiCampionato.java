package it.polito.tdp.seriea.model;

public class PuntiCampionato {
	
	private Season season;
	private int punti;
	public PuntiCampionato(Season season, int punti) {
		super();
		this.season = season;
		this.punti = punti;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public int getPunti() {
		return punti;
	}
	public void setPunti(int punti) {
		this.punti = punti;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + punti;
		result = prime * result + ((season == null) ? 0 : season.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PuntiCampionato other = (PuntiCampionato) obj;
		if (punti != other.punti)
			return false;
		if (season == null) {
			if (other.season != null)
				return false;
		} else if (!season.equals(other.season))
			return false;
		return true;
	}
	

	
	
	
	

}
