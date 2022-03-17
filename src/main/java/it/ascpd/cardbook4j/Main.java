/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ascpd.cardbook4j;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author andrea
 */
public class Main extends Application {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       // Application.launch(args);
       ControllVCard controllVCard = new ControllVCard();
       
       controllVCard.setVCardSorgente("Generale.vcf");
       controllVCard.setVCardDestinazione("GeneraleNew.vcf");
        try {
            controllVCard.setAllVCard();
            controllVCard.createAllVCard();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       ExportToCSV exportToCSV = new ExportToCSV();
       exportToCSV.start(primaryStage);

        
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    
}
