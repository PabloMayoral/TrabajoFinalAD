/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lector_dom;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Pablo Martin
 */
public class Gestionar_xpath {
     Document doc;
    XPath xpath;

    public int abrir_XML(File fichero) {
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
            xpath = XPathFactory.newInstance().newXPath();

            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public String ejecutar__XPath(String consulta) {
        String salida = "";
        String datos_nodo[] = null;
        Node node;
        try {
            XPathExpression exp = xpath.compile(consulta);
            Object resultado = exp.evaluate(doc, XPathConstants.NODESET);

            NodeList listaNodos = (NodeList) resultado;

            for (int i = 0; i < listaNodos.getLength(); i++) {
                node = listaNodos.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if (node.getNodeName() == "Persona") {
                        datos_nodo = procesarPersonas(node);
                        salida = salida + "\n " + "Nacimiento: " + datos_nodo[0];
                        salida = salida + "\n " + "DNI: " + datos_nodo[1];
                        salida = salida + "\n " + "Edad: " + datos_nodo[2];
                        salida = salida + "\n " + "Nombre: " + datos_nodo[3];
                        salida = salida + "\n " + "Apellido 1: " + datos_nodo[4];
                        salida = salida + "\n " + "Apellido 2: " + datos_nodo[5];
                        salida = salida + "\n " + "Ciudad: " + datos_nodo[6];
                        salida = salida + "\n " + "Nacionalidad: " + datos_nodo[7];
                        salida = salida + "\n " + "Sexo: " + datos_nodo[8];
                        salida = salida + "\n " + "Telefono: " + datos_nodo[9];
                        salida = salida + "\n --------------------";
                    }
                    if (node.getNodeName() == "Nombre" || node.getNodeName() == "Apellido1"|| node.getNodeName() == "Apellido2"|| node.getNodeName() == "Ciudad"
                            || node.getNodeName() == "Nacionalidad"|| node.getNodeName() == "Sexo"|| node.getNodeName() == "Telefono") {
                        salida = salida + "\n" + node.getFirstChild().getNodeValue();
                    }
                } else if (node.getNodeType() == node.ATTRIBUTE_NODE) {
                    salida = salida + "\n" + node.getFirstChild().getNodeValue();
                }

            }
            return salida;
        } catch (Exception e) {
            return "Error en la ejecucion de la consulta";
        }
    }

    private String[] procesarPersonas(Node node) {
        String datos[] = new String[10];
        Node ntemp = null;
//        Node ntempAtt = null;
        int contador = 3;
        //Se obtiene el valor del primer atributo del nodo
        //Al no haber mas de un atributo en libros, no se mete en un bucle
        datos[1] = node.getAttributes().item(0).getNodeValue();
        datos[2] = node.getAttributes().item(1).getNodeValue();
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
}
