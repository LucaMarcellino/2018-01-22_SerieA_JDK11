package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {

	private List<Team> teams;
	private SerieADAO  dao;
	private List<PuntiCampionato> pc;
	private Graph<Season,DefaultWeightedEdge> grafo;
	private Map<Season,PuntiCampionato > mappa;
	private List<PuntiCampionato> parziale;
	private List<PuntiCampionato> best;
	
	
	public Model() {
		this.dao= new SerieADAO();
		this.teams= new ArrayList<Team>(dao.listTeams());
		this.mappa=new HashMap<Season, PuntiCampionato>();
	}

	public List<Team> getTeams() {
		return teams;
	}

	public List<PuntiCampionato> getPuntiCampionato(Team t){
		pc=new ArrayList<PuntiCampionato>(dao.getPuntiCampionaot(t));
		for(int i=0; i<pc.size();i++) {
			mappa.put(pc.get(i).getSeason(), pc.get(i));
		}
		return pc;
	}
	
	public void creaGrafo(Team t) {
		this.grafo= new SimpleDirectedWeightedGraph<Season, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<PuntiCampionato> r1 = new ArrayList<PuntiCampionato>(dao.getPuntiCampionaot(t));
		for(int i=0; i< r1.size();i++)
			grafo.addVertex(r1.get(i).getSeason());
		
		
		for(PuntiCampionato pc1 : r1) {
			for(PuntiCampionato pc2  : r1 ) {
				if(pc1.getPunti()<pc2.getPunti())
					Graphs.addEdgeWithVertices(grafo, pc1.getSeason(), pc2.getSeason(), (pc2.getPunti()-pc1.getPunti()));
				if(pc1.getPunti()>pc2.getPunti())
					Graphs.addEdgeWithVertices(grafo, pc2.getSeason(), pc1.getSeason(), (pc1.getPunti()-pc2.getPunti()));
			}
				
		}
		//System.out.print(grafo.outgoingEdgesOf(new Season(2007, "2006/2007")));
	}
	
	public String getAnnataOro() {
		int differenza=0;
		int massimo=0;
		Season s1=null;
		for(Season s : grafo.vertexSet()) {
			differenza = pesi(grafo.incomingEdgesOf(s)) - pesi(grafo.outgoingEdgesOf(s)); 
			if(massimo<differenza) {
				massimo=differenza;
				s1=s;
			}
			
		}
		return"La migliore Stagione è "+s1.getDescription()+" La differenza di pesi è "+massimo; 
	}

	private int pesi(Set<DefaultWeightedEdge> edge) {
		int peso=0;
		for(DefaultWeightedEdge dwe : edge) {
			peso+=Math.abs(grafo.getEdgeWeight(dwe));
		}
		return peso;
	}
	
	public List<PuntiCampionato> trovaRicorsivo() {
		this.parziale= new ArrayList<PuntiCampionato>();
		this.best= new ArrayList<PuntiCampionato>();
		int livello = 0;
		for(PuntiCampionato pc1: pc) {
			parziale.add(pc1);
			ricorsione(livello+1,parziale);
			parziale.remove(parziale.size()-1);
		}
		return best;
	}

	private void ricorsione(int livello, List<PuntiCampionato> parziale) {
		Season ultimo =parziale.get(parziale.size()-1).getSeason();
		boolean trovato=false;
		
		for(Season s : Graphs.successorListOf(grafo, ultimo)) {
			if(!parziale.contains(mappa.get(s))) {
				if(pc.indexOf(mappa.get(ultimo))+1 == pc.indexOf(mappa.get(s))) {
					trovato=true;
					parziale.add(mappa.get(s));
					ricorsione(livello+1, parziale);
					parziale.remove(parziale.size()-1);
				}
			}	
		}
		if(!trovato) {
			if(parziale.size()>best.size()) {
				best=new ArrayList<PuntiCampionato>(parziale);
			}
		}

	}
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
