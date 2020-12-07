/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lector_dom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javapersonas.Personas;
import javapersonas.Personas.Persona;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Pablo Martin
 */
public class JAXB {

    Personas misPersonas;

    public int abrir_XML_JAXB(File fichero) {
        try {
            //crea una instancia JAXB
            JAXBContext contexto = JAXBContext.newInstance(Personas.class);
            //Crea un objeto Unmarshaller
            Unmarshaller u = contexto.createUnmarshaller();

            misPersonas = (Personas) u.unmarshal(fichero);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public String recorrerJAXB() {
        String cadena_resultados = "";

        List<Personas.Persona> lPersonas = misPersonas.getPersona();

        for (int i = 0; i < lPersonas.size(); i++) {
            Persona libro_temp = lPersonas.get(i);
            cadena_resultados = cadena_resultados + "\nNacimiento: " + libro_temp.getNacimiento();
            cadena_resultados = cadena_resultados + "\nDni: " + libro_temp.getDni();
            cadena_resultados = cadena_resultados + "\nEdad: " + libro_temp.getEdad();
            cadena_resultados = cadena_resultados + "\nNombre: " + libro_temp.getNombre();
            cadena_resultados = cadena_resultados + "\nApellido 1: " + libro_temp.getApellido1();
            cadena_resultados = cadena_resultados + "\nApellido 2: " + libro_temp.getApellido2();
            cadena_resultados = cadena_resultados + "\nCiudad: " + libro_temp.getCiudad();
            cadena_resultados = cadena_resultados + "\nNacionalidad: " + libro_temp.getNacionalidad();
            cadena_resultados = cadena_resultados + "\nSexo: " + libro_temp.getSexo();
            cadena_resultados = cadena_resultados + "\nTelefono: " + libro_temp.getTelefono();
            cadena_resultados = cadena_resultados + "\n---------------------------";

        }
        return cadena_resultados;
    }
    
   public Boolean modificarJAXB(String nombrePersona, String newElement, String oldElement) throws IOException{
        try {
         //creo la instancia de JAXB y el objeto Marshmaller
          JAXBContext context = JAXBContext.newInstance(Personas.class);
          Marshaller marsaller = context.createMarshaller();
        //declaro la arraylist de mi xml personas
          List<Personas.Persona> lPersonas = misPersonas.getPersona();
          //el bucle va leeyendo cada persona de la array y si encuentra a una persona con 
          //el nombre que se ha especificado se podra modificar la ciudad,nacionalidad o el sexo
          for (int i = 0; i < lPersonas.size(); i++){
             Persona persona_temp = lPersonas.get(i);
//              System.out.println(persona_temp.getNombre().trim());
//              System.out.println(nombrePersona);
             if(persona_temp.getNombre().trim().equals(nombrePersona)){
                 System.out.println("Aqui funciona2");
                if(persona_temp.getCiudad().equals(oldElement)||persona_temp.getNacionalidad().equals(oldElement)||persona_temp.getSexo().equals(oldElement)){
                 if(persona_temp.getCiudad().equals(oldElement)){
                     persona_temp.setCiudad(newElement);
                 }
                 if(persona_temp.getNacionalidad().equals(oldElement)){
                     persona_temp.setNacionalidad(newElement);
                 }
                 if(persona_temp.getSexo().equals(oldElement)){
                     persona_temp.setSexo(newElement);
                 }
                }
//                 System.out.println("Aqui funciona3");
             }
                
           }
           //hago que cuando se modifique se guarde en documentos en Personas.xml
            marsaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marsaller.marshal(misPersonas, System.out);
            marsaller.marshal(misPersonas, new FileWriter(new File("salidaPersonas.xml")));
         return true;
        }
        catch (JAXBException ex) {
            return false;
            
        }
           
   }
}
