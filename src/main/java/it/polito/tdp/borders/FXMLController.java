
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	String s = this.txtAnno.getText();
    	int anno;
    	
    	try {
    		anno = Integer.parseInt(s);
    	}catch(NumberFormatException ne) {
    		txtResult.setText("Devi inserire un numero intero per l'anno");
    		return;
    	}
    	
    	if(anno< 1815 || anno > 2017) {
    		txtResult.setText("Devi inserire un anno compreso tra 1816 e 2016");
    		return;
    	}
    	
    	this.model.creaGrafo(anno);
    	
    	this.txtResult.appendText("grafo creato con #vertici = "+ this.model.getNVertici() +
    			" , #archi = " + this.model.getNArchi() + "\n");
    	
    	this.txtResult.appendText("numero componenti connesse = " +this.model.getNComponenti() + "\n");
    	
    	for(Country c : this.model.getVertici()) {
        	this.txtResult.appendText(c.getNome() + "   "+ this.model.getGrado(c) + "\n");
    	}
    		

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
