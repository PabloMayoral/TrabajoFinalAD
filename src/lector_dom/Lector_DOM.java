/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lector_dom;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/**
 *
 * @author Pablo Martin
 */
public class Lector_DOM {

    Document doc;

    public int abrir_XML_DOM(File fichero) {
        doc = null;

        try {
            //se crea un documento tipo DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Indica que el modelo DOM no debe contemplar los comentarios que tenga el XML
            factory.setIgnoringComments(true);
            //Ignora los espacios en blancos del documento
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            doc = builder.parse(fichero);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public String recorrerDOMyMostrar() {

        String salida = "";
        Node node;
        String datos_nodo[] = null;

        Node raiz = doc.getFirstChild();
        NodeList nodelist = raiz.getChildNodes();
        //este bucle hace que salga la info 
        for (int i = 0; i < nodelist.getLength(); i++) {
            node = nodelist.item(i);
            //mostramos los datos del fichero
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                datos_nodo =  procesarPersonas(node);
                salida = salida + "\n " + "Nacimiento: " + datos_nodo[0];
                salida = salida + "\n " + "DNI: " + datos_nodo[8];
                salida = salida + "\n " + "Edad: " + datos_nodo[9];
                salida = salida + "\n " + "Nombre: " + datos_nodo[1];
                salida = salida + "\n " + "Apellido 1: " + datos_nodo[2];
                salida = salida + "\n " + "Apellido 2: " + datos_nodo[3];
                salida = salida + "\n " + "Ciudad: " + datos_nodo[4];
                salida = salida + "\n " + "Nacionalidad: " + datos_nodo[5];
                salida = salida + "\n " + "Sexo: " + datos_nodo[6];
                salida = salida + "\n " + "Telefono: " + datos_nodo[7];
                salida = salida + "\n --------------------";
            }
        }
        return salida;
    }

    private String[] procesarPersonas(Node node) {
        String datos[] = new String[10];
        Node ntemp = null;
//        Node ntempAtt = null;
        int contador = 1;
        //Se obtiene el valor del primer atributo del nodo
        //Al no haber mas de un atributo en libros, no se mete en un bucle
       datos[8] = node.getAttributes().item(0).getNodeValue();
        datos[9] = node.getAttributes().item(1).getNodeValue();
        datos[0] = node.getAttributes().item(2).getNodeValue();
        //Obtiene los hijos del titulo y del autor
        NodeList nodos = node.getChildNodes();
//        NodeList nodoAtt = (NodeList) node.getAttributes();
        for (int i = 0; i < nodos.getLength(); i++) {
            ntemp = nodos.item(i);
//            ntempAtt = nodoAtt.item(i);
            if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                datos[contador] = ntemp.getFirstChild().getNodeValue();
                contador++;
            }
//            if(ntempAtt.getNodeType() == Node.ATTRIBUTE_NODE){
//                datos[contador] = ntempAtt.getNodeValue();
//                contador++;
//            }
        }
        return datos;
    }

    public int annadirDOM(String Nombre, String Apellido1, String Apellido2,String Ciudad,String Nacionalidad,
            String sexo,String telefono,String dni,String Nacimiento,String edad) {

        try {
            //creamos el nodo hijo titulo
            Node nNombre = doc.createElement("Nombre");
            Node nNombre_text = doc.createTextNode(Nombre);
            nNombre.appendChild(nNombre_text);

            //creamos el nodo hijo Autor
            Node napellido1 = doc.createElement("Apellido1");
            Node napellido1_text = doc.createTextNode(Apellido1);
            napellido1.appendChild(napellido1_text);
            
            Node napellido2 = doc.createElement("Apellido2");
            Node napellido2_text = doc.createTextNode(Apellido2);
            napellido2.appendChild(napellido2_text);
            
            Node nciudad = doc.createElement("Ciudad");
            Node nciudad_text = doc.createTextNode(Ciudad);
            nciudad.appendChild(nciudad_text);
            
            Node nNacionalidad = doc.createElement("Nacionalidad");
            Node nNacionalidad_text = doc.createTextNode(Nacionalidad);
            nNacionalidad.appendChild(nNacionalidad_text);
            
            Node nSexo = doc.createElement("Sexo");
            Node nSexo_text = doc.createTextNode(sexo);
            nSexo.appendChild(nSexo_text);
            
            Node ntelefono = doc.createElement("Telefono");
            Node ntelefono_text = doc.createTextNode(telefono);
            ntelefono.appendChild(ntelefono_text);
            //creamos el nodo hijo libro
            Node npersona = doc.createElement("Persona");
            ((Element) npersona).setAttribute("Nacimiento", Nacimiento);
            ((Element) npersona).setAttribute("Dni", dni);
            ((Element) npersona).setAttribute("Edad", edad);
           
            npersona.appendChild(nNombre);
            npersona.appendChild(napellido1);
            npersona.appendChild(napellido2);
            npersona.appendChild(nciudad);
            npersona.appendChild(nNacionalidad);
            npersona.appendChild(nSexo);
            npersona.appendChild(ntelefono);

            Node raiz = doc.getFirstChild();
            raiz.appendChild(npersona);

            return 0;

        } catch (Exception e) {
            return -1;

        }

    }

    public int guardarDOM() {

        try {
            //creo el fichero donde se va a guardar las modificaciones
            File archivo_xml = new File("salida.xml");
            OutputFormat format = new OutputFormat(doc);
            format.setIndenting(true);
            //se especifica la extension del fichero
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(archivo_xml), format);
            serializer.serialize(doc);

            return 0;

        } catch (Exception e) {
            return -1;
        }
    }

    public int ModificarDOM(String tituloAntiguo, String tituloNuevo) {

        try {
            //especifico que el item que quiero modificar 

            Node root = doc.getDocumentElement();
            Node temp;
            NodeList nodeList = root.getChildNodes();
            //el primer bucle lee de arriba a bajo  
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList subNodes = nodeList.item(i).getChildNodes();
                int k = 0;
                // el segundo bucle lee de izq a dcha
                for (int j = 0; j < subNodes.getLength(); j++) {
                    temp = subNodes.item(j);
                    if (temp.getNodeType() == Node.ELEMENT_NODE) {
                        if (k == 0 && temp.getFirstChild().getNodeValue().equals(tituloAntiguo)) {
                            temp.setTextContent(tituloNuevo);
                        }
                        k++;
                    }
                }
            }

            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
    
  
}
