/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ascpd.cardbook4j;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Address;
import ezvcard.property.Categories;
import ezvcard.property.Email;
import ezvcard.property.FormattedName;
import ezvcard.property.Note;
import ezvcard.property.Role;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
import ezvcard.property.Title;
import ezvcard.property.Url;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrea
 */
public class ControllVCard {

    private String vCardSorgente;
    private String vCardDestinazione;
    private List<VCard> vcards;
    private List<VCard> vcardsNew;

    public void setVCardSorgente(String vCardSorgente) {
        this.vCardSorgente = vCardSorgente;
    }

    public void setVCardDestinazione(String vCardDestinazione) {
        this.vCardDestinazione = vCardDestinazione;
    }

    public void setAllVCard() throws IOException {
        vcards = Ezvcard.parse(new File(vCardSorgente)).all();
    }

    public void createAllVCard() {
        vcardsNew = new ArrayList<>();
        vcards.forEach((VCard vcard) -> {
            VCard newVCard = new VCard();
            
            newVCard.setUid(vcard.getUid());
            
            vcard.getStructuredNames().forEach((structuredName) -> {
                newVCard.setStructuredName(structuredName);
            });
            
            newVCard.setOrganization(vcard.getOrganization());
            vcard.getFormattedNames().forEach((formattedName) -> {
                newVCard.setFormattedName(formattedName);
            });
            
            vcard.getCategoriesList().forEach((categorie) -> {
                newVCard.setCategories(categorie);
            });
            
            vcard.getRoles().forEach((role) -> {
                newVCard.addRole(role);
            });
            vcard.getTitles().forEach((title) -> {
                newVCard.addTitle(title);
            });
            
            vcard.getEmails().forEach((email) -> {
                newVCard.addEmail(email.getValue(), EmailType.WORK);
            });
            vcard.getLogos().forEach((logo) -> {
                newVCard.addLogo(logo);
            });
            
            List<Address> addresses = vcard.getAddresses();
            addresses.stream().map((address) -> {
                Address adr = new Address();
                adr.setStreetAddress(address.getStreetAddress());
                adr.setLocality(address.getLocality());
                adr.setRegion(address.getRegion());
                adr.setPostalCode(address.getPostalCode());
                adr.setCountry(address.getCountry());
                return adr;
            }).map((adr) -> {
                adr.getTypes().add(AddressType.WORK);
                return adr;
            }).forEachOrdered((adr) -> {
                newVCard.addAddress(adr);
            });
            
            vcard.getTelephoneNumbers().forEach((number) -> {
                if (number.getText().startsWith("+393") 
                        || number.getText().startsWith("+39 3") 
                        || number.getText().startsWith("3") ) {
                    newVCard.addTelephoneNumber(number.getText(), TelephoneType.CELL);
                } else {
                    newVCard.addTelephoneNumber(number.getText(), TelephoneType.WORK);
                }
            });
            vcard.getUrls().stream().map((url) -> new Url(url.getValue())).map((addUrl) -> {
                addUrl.setType("WORK");
                return addUrl;
            }).forEachOrdered((addUrl) -> {
                newVCard.addUrl(addUrl);
            });
            
            vcard.getNotes().forEach((note) -> {
                newVCard.addNote(note);
            });
            
            vcardsNew.add(newVCard);
        });

        //VCard vcard = createVCard();
        //validate vCard for version 3.0
        System.out.println("Version 3.0 validation warnings:");
        vcardsNew.forEach((vcard) -> {
            System.out.println(vcard.getFormattedName() + " " + vcard.validate(VCardVersion.V4_0) );
        });
        System.out.println();

        //write vCard
        File file = new File(vCardDestinazione);
        System.out.println("Writing " + file.getName() + "...");
        try {
            Ezvcard.write(vcardsNew).version(VCardVersion.V4_0).go(file);
        } catch (IOException ex) {
            Logger.getLogger(ControllVCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        //write xCard
        //file = new File("john-doe.xml");
        //System.out.println("Writing " + file.getName() + "...");
        //Ezvcard.writeXml(vcardsNew).indent(2).go(file);

        //write hCard
        //file = new File("john-doe.html");
        //System.out.println("Writing " + file.getName() + "...");
        //Ezvcard.writeHtml(vcard).go(file);
        //write jCard
        //file = new File("john-doe.json");
        //System.out.println("Writing " + file.getName() + "...");
        //Ezvcard.writeJson(vcard).go(file);
        //write xCard
        //file = new File("john-doe.xml");
        //System.out.println("Writing " + file.getName() + "...");
        //Ezvcard.writeXml(vcardsNew).indent(2).go(file);

        //write hCard
        //file = new File("john-doe.html");
        //System.out.println("Writing " + file.getName() + "...");
        //Ezvcard.writeHtml(vcard).go(file);
        //write jCard
        //file = new File("john-doe.json");
        //System.out.println("Writing " + file.getName() + "...");
        //Ezvcard.writeJson(vcard).go(file);
    }
}
