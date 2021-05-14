/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Model;
import it.polito.tdp.extflightdelays.model.Rotte;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	txtResult.clear();
    	txtResult.setEditable(false);
    	
    	String inserisciS = distanzaMinima.getText();
    	int inserisci; 
    	
    	if(inserisciS.length()==0) {
    		txtResult.setText("ERRORE: inserisci un valore");
    		return;
    	}
    	
    	try {
    		inserisci = Integer.parseInt(inserisciS);
    	}catch(NumberFormatException ne) {
    		txtResult.setText("ERRORE: la distanza deve essere un numero");
    		return; 
    	}catch(NullPointerException e) {
    		txtResult.setText("ERRORE: inserire un numero");
    		return;
    	}
    	
    	this.model.creaGrafo(inserisci);
    	
    	txtResult.appendText("GRAFO CREATO!"+"\n");
    	txtResult.appendText("# VERTICI: "+this.model.getNumeroVertici()+"\n");
    	txtResult.appendText("# ARCHI: "+this.model.getNumeroArchi()+"\n");

    	txtResult.appendText("ELENCO ROTTE (CON PESO): "+"\n");
    	for (Rotte r: this.model.getRotte()) {
    		txtResult.appendText(r.toString()+"\n");
    	}
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
    
}
