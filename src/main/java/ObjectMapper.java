import domain.Context;
import domain.Resource;
import org.dom4j.Node;

import java.io.File;
import java.util.ArrayList;

/**
 * Author: Marlon
 * Created on: 24-9-2018
 */
public class ObjectMapper {
    private RDFParser parser = new RDFParser();
    private File fileToBeParsed;
    private ArrayList<Resource> resourceList = new ArrayList<>();

    public ObjectMapper() {
        fileToBeParsed = new File("input/all.xml");
    }

    public static void main(String[] args) {
        RDFParser parser = new RDFParser();
        File file = new File("input/all.xml");
        parser.printCurrentParsedFile(parser.parse(file));
        ObjectMapper map = new ObjectMapper();
        map.mapResource();
    }

    public void mapResource() {
        for (Node n : parser.getNodeList()) {
            if (n.hasContent()) {
                if (parser.getIntentionalElementStringValue(n).contains("Condition")) {
                    Resource r = new Resource();
                    r.setName(parser.getLabelStringValue(n));
                    Context c = new Context();
                    c.setName(parser.getContextStringValue(n));
                    c.setContextType("Context");
                    r.setContext(c);
                    if (parser.getTransportStringValue(n) != null) {
                        r.setTransport(parser.getTransportStringValue(n));
                    }
                    r.setIEType("Resource");
                    resourceList.add(r);
                }
            }
        }
    }
}
