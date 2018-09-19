import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import utility.StringFormatter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Marlon Kewaldar
 * Created on: 23-6-2018
 * Class representing a parser, responsible for retrieving the relevant information from the input XML.
 */

public class RDFParser {
    public static void main(String[] args) {
        RDFParser parser = new RDFParser();
        parser.printCurrentParsedFile(parser.parse(new File("input/twee_resources_ne.xml")));

    }

    /**
     * Reads the XML document
     *
     * @param file File to read
     * @return Document object ready to be reasoned with
     */
    private Document parse(File file) {
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return doc;
    }


    /**
     * Prints the formatted content of the file currently being handled
     * @param doc Document to be parsed
     */
    private void printCurrentParsedFile(Document doc) {
        List<Node> n = doc.selectNodes("/rdf:RDF/swivt:Subject/rdfs:label/..");

        System.out.println("Currently parsing: " + doc.getName());
        System.out.println();
        for (Node node : n) {
            //Label or name
            System.out.println("Name" + ": " + getLabelStringValue(node));

            //Context, whose string is changed to make it more readable
            System.out.println("Context" + ": " + getContextStringValue(node));

            //Transport type
            System.out.println("Transport" + ": " + getTransportStringValue(node));

            //Intentional element type
            System.out.println("Intentional Element Type" + ": " + getIntentionalElementStringValue(node));

            //Contributes to relationships
            for (String key : getContributesToRelationships(node).keySet()) {
                System.out.println("Contributes to: " + key + "(" + getContributesToRelationships(node).get(key) + ")");
            }
            System.out.println();
        }
            System.out.println("------------------------------------");
    }

    private String getLabelStringValue(Node node) {
        return StringFormatter.replaceSpaceWithLowercase(node.selectSingleNode("rdfs:label").getStringValue());
    }

    private String getContextStringValue(Node node) {
        return StringFormatter.retrieveFinalArtifactFromURI(node.selectSingleNode
                ("property:SCitHos_Context/@rdf:resource").getStringValue());
    }

    private String getTransportStringValue(Node node) {
        return node.selectSingleNode("property:Transport").getStringValue();
    }

    private String getIntentionalElementStringValue(Node node) {
        return node.selectSingleNode("property:Intentional_Element_type").getStringValue().trim();

    }

    /**
     * Initializes a HashMap with both the key and value of the contributes-to relationship
     *
     * @param node node which the HashMap will retrieve the information from
     * @return HashMap with the required information
     */
    private HashMap<String, String> getContributesToRelationships(Node node) {
        //Initialize two lists: one for the key (entity that is contributed to) and for the value (in what manner it is contributed to)
        List<Node> contNodeNameList = new ArrayList<>(node.selectNodes("child::*[contains(name(), \"Dummy\")]/attribute::rdf:resource"));
        List<Node> contNodeValueList = new ArrayList<>(node.selectNodes("child::*[contains(name(), \"property:Dummy_element_cont\")]"));
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < contNodeNameList.size(); ) {
            map.put(StringFormatter.retrieveFinalArtifactFromURI(contNodeNameList.get(i).getStringValue()), contNodeValueList.get(i).getText().trim());
            i++;
        }
        return map;
    }
}
