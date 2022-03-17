/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ascpd.cardbook4j;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Email;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author andrea
 */
public class ExportToCSV  {

    private final List<Contatto> lista = new ArrayList<>();
    private List<VCard> vcards;

    
    public void start(Stage primaryStage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            vcards = Ezvcard.parse(file).all();
        }
        vcards.forEach((vcard) -> {
            String organizzazzione;
            // String firstName;
            // String lastName;
            organizzazzione = vcard.getOrganization().getValues().toString();
        
            for (Email email : vcard.getEmails() ){
                Contatto contatto = new Contatto();
                contatto.setOrganization(organizzazzione);
                contatto.setEmail(email.getValue());
                lista.add(contatto);
            }
        });
        
        saveFile(primaryStage);
        
        
        
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Ricerca file CARDDAV");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home")) 
        );
        
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CARDDAV", "*.vcf"),
                new FileChooser.ExtensionFilter("TEXT FILE", "*.txt") 
        );
        
    }
    
    private void saveFile(Stage stage) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva il file CSV");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );
        File file = fileChooser.showSaveDialog(stage);
        
        if (file != null) {
            PrintWriter pw = new PrintWriter(file);
            StringBuilder sb = new StringBuilder();
            
            sb.append("email");
            sb.append(",");
            sb.append("Organizzazione");
            sb.append("\n");
            
            lista.forEach(contatti -> {
                sb.append(contatti.getEmail());
                sb.append(",");
                sb.append(contatti.getOrganization());
                //pw.println(sb.toString());
                sb.append("\n");
            });
            pw.print(sb.toString());
            pw.close();
        }
    }
    
    
}
