package it.polito.tdp.seriea;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Team> boxSquadra;

    @FXML
    private Button btnSelezionaSquadra;

    @FXML
    private Button btnTrovaAnnataOro;

    @FXML
    private Button btnTrovaCamminoVirtuoso;

    @FXML
    private TextArea txtResult;

    @FXML
    void doSelezionaSquadra(ActionEvent event) {
    	txtResult.clear();
    	if(boxSquadra.getValue()==null)
    		txtResult.appendText("seleziona una squadra");
    	for(int i=0 ;i< model.getPuntiCampionato(boxSquadra.getValue()).size();i++)
    		txtResult.appendText(model.getPuntiCampionato(boxSquadra.getValue()).get(i).getSeason().getDescription()+"   "+model.getPuntiCampionato(boxSquadra.getValue()).get(i).getPunti()+"\n");
    }

    @FXML
    void doTrovaAnnataOro(ActionEvent event) {
    	txtResult.clear();
    	model.creaGrafo(boxSquadra.getValue());
    	txtResult.appendText(model.getAnnataOro());
    	
    }

    @FXML
    void doTrovaCamminoVirtuoso(ActionEvent event) {
    	txtResult.clear();
    	for(int i=0; i<model.trovaRicorsivo().size();i++)
    		txtResult.appendText(model.trovaRicorsivo().get(i).getSeason()+"  "+model.trovaRicorsivo().get(i).getPunti()+"\n");
    }

    @FXML
    void initialize() {
        assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnSelezionaSquadra != null : "fx:id=\"btnSelezionaSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaAnnataOro != null : "fx:id=\"btnTrovaAnnataOro\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaCamminoVirtuoso != null : "fx:id=\"btnTrovaCamminoVirtuoso\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		boxSquadra.getItems().addAll(model.getTeams());
		txtResult.setEditable(false);
	}
}
