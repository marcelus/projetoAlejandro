/**
 * Created by Marcelus on 05/04/2017.
 */
import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

public class ListAvailablePorts {

    public void list() {
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
//        ((CommPortIdentifier)ports.nextElement()).

        while(ports.hasMoreElements())
            System.out.println(((CommPortIdentifier)ports.nextElement()).getName());
    }

    public static void main(String[] args) {
        new ListAvailablePorts().list();
    }
}  