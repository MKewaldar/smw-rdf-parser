import domain.Context;
import domain.Resource;
import org.dom4j.Node;

import java.util.ArrayList;

/**
 * Author: Marlon Kewaldar
 * Created on: 24-9-2018
 * Class responsible for mapping the information from the RDFParser into Java objects.
 */
public class ObjectMapper {
    private RDFParser parser;
    private ArrayList<Resource> resourceList;

    public ObjectMapper() {
        parser = new RDFParser();
        resourceList = new ArrayList<>();
    }

    public static void main(String[] args) {
        ObjectMapper map = new ObjectMapper();
        map.mapResource();
        System.out.println(map.getResourceList());
    }

    public void mapResource() {
        for (Node n : parser.getNodeList()) {
            if (n.hasContent()) {
                // check if the IE field says 'Condition'
                if (parser.getIntentionalElementStringValue(n).contains("Condition")) {
                    //separately map every attribute to a new object, and do this for every object respectively
                    Resource r = new Resource();
                    r.setName(parser.getLabelStringValue(n));
                    Context c = new Context();
                    c.setName(parser.getContextStringValue(n));
                    c.setContextType("Context");
                    r.setContext(c);
                    if (parser.getTransportStringValue(n) != null) {
                        r.setTransport(parser.getTransportStringValue(n));
                    } else r.setTransport("N/A");
                    r.setElementType("Resource");
                    resourceList.add(r);
                }
            }
        }
    }

    public RDFParser getParser() {
        return parser;
    }

    public void setParser(RDFParser parser) {
        this.parser = parser;
    }


    public ArrayList<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(ArrayList<Resource> resourceList) {
        this.resourceList = resourceList;
    }
}
