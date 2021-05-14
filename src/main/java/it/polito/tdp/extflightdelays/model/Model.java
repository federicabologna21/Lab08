package it.polito.tdp.extflightdelays.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	private SimpleWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	private ExtFlightDelaysDAO dao;
	private Map <Integer, Airport> idMap;
	
	public Model() {
		dao = new ExtFlightDelaysDAO();
		idMap = new HashMap <Integer, Airport>();
		dao.loadAllAirports(idMap);
	}
	
	public void creaGrafo(int x) {
		grafo = new SimpleWeightedGraph <>( DefaultWeightedEdge.class);
		
		// AGGIUNGO TUTTI I VERTICI
		Graphs.addAllVertices(grafo, idMap.values());
		
		
		// AGGIUNGO GLI ARCHI
		for(Rotte r: dao.getRotte(x, idMap)) {
			
			// se il grafo contiene questi vertici 
			if(this.grafo.containsVertex(r.getA1()) && this.grafo.containsVertex(r.getA2())) {
				
				// prendi l'arco che li collega
				DefaultWeightedEdge e = this.grafo.getEdge(r.getA1(), r.getA2());
				
				// se questo arco non c'è 
				if(e==null) {
					// allora lo creo, con il relativo peso
					Graphs.addEdgeWithVertices(grafo, r.getA1(), r.getA2(), r.getPeso());	
				}
				// se l'arco esiste già
				else {
					// prendo il peso attuale dell'arco 
					double pesoAttuale = this.grafo.getEdgeWeight(e);
					// prendo il peso di questa rotta che sto analizzando
					// (per cui esiste già un arco)
					double pesoNuovo = r.getPeso();
					
					// calcolo il peso finale dell'arco e lo setto
					double pesoFinito = (pesoAttuale+pesoNuovo)/2;
					this.grafo.setEdgeWeight(e, pesoFinito);
				}
				
			}
		}
		
	}
	
	public int getNumeroVertici() {
		if(this.grafo!=null) {
			return this.grafo.vertexSet().size();
		}
		return 0;
	}
	
	public int getNumeroArchi() {
		if(this.grafo!=null) {
			return this.grafo.edgeSet().size();
		}
		return 0;
	}
	
	// creo metodo per stampare elenco degli archi con distanza
	public List<Rotte> getRotte(){
		List<Rotte> rotte = new LinkedList<>();
		
		for (DefaultWeightedEdge e: this.grafo.edgeSet()) {
			Rotte r = new Rotte (this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), 
					this.grafo.getEdgeWeight(e));
			rotte.add(r);
		}
		return rotte;
		
	}
}
	
