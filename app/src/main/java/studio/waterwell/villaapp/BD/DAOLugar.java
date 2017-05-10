package studio.waterwell.villaapp.BD;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import studio.waterwell.villaapp.Modelo.Lugar;
import studio.waterwell.villaapp.R;

/**
 * Created by AdeFr on 10/05/2017.
 */

public class DAOLugar {

    private Context contexto;

    public DAOLugar(Context contexto) {
        this.contexto = contexto;
    }


    public ArrayList<Lugar> getLugares() {
        ArrayList<Lugar> lugares = new ArrayList<Lugar>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Parseamos el documento obteniendolo de los recursos y lo almacenamos en un objeto tipo Document
            Document doc = builder.parse(this.contexto.getResources().openRawResource(R.raw.api_lugares));

            //Obtenemos el elemento raíz del documento, lugares
            Element raiz = doc.getDocumentElement();

            //Obtenemos todos los elementos llamados lugar, que cuelguen de la raíz.
            NodeList items = raiz.getElementsByTagName("lugar");

            //Recorremos todos los elementos obtenidos.
            for(int i = 0; i < items.getLength(); i++) {
                Node nodoLugar = items.item(i);
                Lugar lugar = new Lugar();

                // Recorremos todos los hijos que tenga el nodo lugar.
                for(int j = 0; j < nodoLugar.getChildNodes().getLength(); j++) {
                    Node nodoActual = nodoLugar.getChildNodes().item(j);

                    if(nodoActual.getNodeType() == Node.ELEMENT_NODE) {
                        String nombreNodo = nodoActual.getNodeName();
                        switch(nombreNodo) {
                            case "id":
                                lugar.setId(nodoActual.getChildNodes().item(0).getNodeValue());
                                break;
                            case "nombre":
                                lugar.setNombre(nodoActual.getChildNodes().item(0).getNodeValue());
                                break;
                            case "direccion":
                                lugar.setDireccion(nodoActual.getChildNodes().item(0).getNodeValue());
                                break;
                            case "latitud":
                                lugar.setLatitud(Double.parseDouble(nodoActual.getChildNodes().item(0).getNodeValue()));
                                break;
                            case "longitud":
                                lugar.setLongitud(Double.parseDouble(nodoActual.getChildNodes().item(0).getNodeValue()));
                                break;
                            case "imagen":
                                lugar.setRutaImagen(nodoActual.getChildNodes().item(0).getNodeValue());
                                break;
                            case "descripcion":
                                lugar.setDescripcion(nodoActual.getChildNodes().item(0).getNodeValue());
                                break;
                        }
                    }
                }
                lugares.add(lugar);
            }


        }
        catch(Exception e) {}

        return lugares;
    }
}
