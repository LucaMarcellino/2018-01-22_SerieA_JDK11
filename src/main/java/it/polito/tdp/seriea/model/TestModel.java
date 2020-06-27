package it.polito.tdp.seriea.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		m.creaGrafo(new Team("juventus"));
		m.getPuntiCampionato(new Team("juventus"));
		m.getAnnataOro();
		System.out.println(m.trovaRicorsivo());
	}

}
