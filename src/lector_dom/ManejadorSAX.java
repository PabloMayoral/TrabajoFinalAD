/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lector_dom;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Pablo Martin
 */
public class ManejadorSAX {

    SAXParser parser;

    ManejaSAX sh;
    File ficheroXML;

    public int abrir_XML_SAX(File fichero) {
        try {
            //se crea un objeto SAXParser para interpretar el documento XML
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();
            //se crea una instancia del manejador que sera el que recorra
            //el documento XML secuencialmente
            sh = new ManejaSAX();
            ficheroXML = fichero;
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    String recorrerSAX() {
        try {
            sh.cadena_resultados = "";
            parser.parse(ficheroXML, sh);
            return sh.cadena_resultados;
        } catch (SAXException ex) {
            return "Error al parsear con SAX";
        } catch (IOException ex) {
            return "Error al parsear con SAX";
        }
    }
}

class ManejaSAX extends DefaultHandler {

    String cadena_resultados = "";

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        for (int i = start; i < length + start; i++) {
            cadena_resultados = cadena_resultados + ch[i];
        }
        cadena_resultados = cadena_resultados.trim() + "\n";
    }

    @Override
    public void endElement(String uri, String localName, String gName)
            throws SAXException {
        if (gName.equals("Persona")) {
            cadena_resultados = cadena_resultados + "--------------------\n";
        }
    }

    @Override
    public void startElement(String uri, String localName, String gName, Attributes attributes) throws SAXException {
        if (gName.equals("Personas")) {
            cadena_resultados = cadena_resultados + "Se van a mostrar las personas de este documento\n";
            cadena_resultados = cadena_resultados + "---------------------------------------------------------\n";

        } else if (gName.equals("Persona")) {
            cadena_resultados = cadena_resultados + "Nacimiento: " + attributes.getValue(attributes.getQName(0).trim())+"\n";
            cadena_resultados = cadena_resultados + "Dni: " + attributes.getValue(attributes.getQName(1).trim())+"\n";
            cadena_resultados = cadena_resultados + "Edad: " + attributes.getValue(attributes.getQName(2).trim())+"\n";

        } else if (gName.equals("Nombre")) {

            cadena_resultados = cadena_resultados + "El nombre es: ".trim();
        } else if (gName.equals("Apellido1")) {
            cadena_resultados = cadena_resultados + "El primer apellido es: ".trim();
        } else if (gName.equals("Apellido2")) {
            cadena_resultados = cadena_resultados + "El segundo apellido es: ".trim();
        } else if (gName.equals("Ciudad")) {
            cadena_resultados = cadena_resultados + "La ciudad es: ".trim();
        } else if (gName.equals("Nacionalidad")) {
            cadena_resultados = cadena_resultados + "La nacionalidad es: ".trim();
        } else if (gName.equals("Sexo")) {
            cadena_resultados = cadena_resultados + "El sexo es: ".trim();
        } else if (gName.equals("Telefono")) {
            cadena_resultados = cadena_resultados + "El telefono es: ".trim();
        }
    }
}
