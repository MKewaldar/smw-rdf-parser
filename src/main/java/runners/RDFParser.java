package runners;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import utility.StringFormatter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Author: Marlon Kewaldar
 * Created on: 23-6-2018
 * Class representing a parser, responsible for retrieving the relevant information from the input XML.
 */

public class RDFParser {

    private Document docToBeParsed;
    private List<Node> nodeList;


    public RDFParser() {
        initGUI();
    }

    public static void main(String[] args) {
        RDFParser parser = new RDFParser();
        parser.initGUI();

    }

    public void initGUI() {
        // initialize GUI
        File workingDirectory = new File(System.getProperty("user.dir"));
        JFileChooser jfc = new JFileChooser(workingDirectory);
        jfc.setDialogTitle("Select an RDF file");
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
        jfc.addChoosableFileFilter(filter);

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            docToBeParsed = parse(selectedFile);
            nodeList = parse(selectedFile).selectNodes("/rdf:RDF/swivt:Subject/rdfs:label/..");
            printCurrentParsedFile(parse(selectedFile));
        }
    }

    /**
     * Reads the XML document
     *
     * @param file File to read
     * @return Document object ready to be reasoned with
     */
    public Document parse(File file) {
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
     *
     * @param doc Document to be parsed
     */
    public void printCurrentParsedFile(Document doc) {
        System.out.println("Currently parsing: " + getDocToBeParsed().getName());
        System.out.println();
        for (Node node : getNodeList()) {
            if (node.hasContent()) {
                //Label or name
                printLabelStringValue(node);
                //Context, whose string is changed to make it more readable
                printContextStringValue(node);
                printContextTypeValue(node);
                //Transport type
                printTransportStringValue(node);
                //Intentional element type
                printIntentionalElementStringValue(node);
                //Contributes to relationships
                //    printContributesToRelationships(node);
                //Depends on relationships
                //printDependsOnRelationships(node);
                System.out.println();
            }
            System.out.println("------------------------------------");
        }
    }

    public String getLabelStringValue(Node node) {
        if (node.selectSingleNode("rdfs:label") != null)
            return StringFormatter.replaceSpaceWithLowercase(node.selectSingleNode("rdfs:label").getStringValue());
        else return null;
    }

    public void printLabelStringValue(Node node) {
        if (node.selectSingleNode("rdfs:label") != null)
            System.out.println("Name: " + getLabelStringValue(node));
    }

    public String getContextStringValue(Node node) {
        if (node.selectSingleNode("property:SCitHos_Context/@rdf:resource") != null) {
            return StringFormatter.retrieveFinalArtifactFromURI(node.selectSingleNode
                    ("property:SCitHos_Context/@rdf:resource").getStringValue());
        } else return null;
    }

    public void printContextStringValue(Node node) {
        if (node.selectSingleNode("property:SCitHos_Context/@rdf:resource") != null) {
            System.out.println("Context: " + getContextStringValue(node));
        }
    }

    public String getContextTypeValue(Node node) {
        if (node.selectSingleNode("property:SCitHos_Context_type") != null) {
            return node.selectSingleNode("property:SCitHos_Context_type").getStringValue();
        } else return " ";
    }

    public void printContextTypeValue(Node node) {
        if (node.selectSingleNode("property:SCitHos_Context_type") != null) {
            System.out.println("Context Type: " + getContextTypeValue(node));
        }
    }

    public String getTransportStringValue(Node node) {
        if (node.selectSingleNode("property:Transport") != null) {
            return node.selectSingleNode("property:Transport").getStringValue();
        } else return null;
    }

    public void printTransportStringValue(Node node) {
        if (node.selectSingleNode("property:Transport") != null) {
            System.out.println("Transport: " + getTransportStringValue(node));
        }
    }

    public String getIntentionalElementStringValue(Node node) {
        if (node.selectSingleNode("property:Intentional_Element_type") != null) {
            return node.selectSingleNode("property:Intentional_Element_type").getStringValue().trim();
        } else return " ";
    }

    public void printIntentionalElementStringValue(Node node) {
        if (node.selectSingleNode("property:Intentional_Element_type") != null) {
            System.out.println("Intentional Element Type: "
                    + getIntentionalElementStringValue(node));
        }
    }

    /**
     * Initializes a HashMap with both the key and value of the contributes-to relationship
     *
     * @param node node which the HashMap will retrieve the information from
     * @return HashMap with the required information
     */
    private HashMap<String, String> getContributesToRelationships(Node node) {
        if (node.selectSingleNode("child::*[contains(name(), \"Dummy\")]/attribute::rdf:resource") != null &&
                node.selectSingleNode("child::*[contains(name(), \"property:Dummy_element_cont\")]") != null) {
            //Initialize two lists: one for the key (entity that is contributed to) and for the value (in what manner it is contributed to)
            List<Node> contNodeNameList = new ArrayList<>(node.selectNodes("child::*[contains(name(), \"Dummy\")]"));
            List<Node> contNodeValueList = new ArrayList<>(node.selectNodes("child::*[contains(name(), \"property:Dummy_element_cont\")]"));
            HashMap<String, String> map = new HashMap<>();
            for (int i = 0; i < contNodeNameList.size(); ) {
                map.put(StringFormatter.retrieveFinalArtifactFromURI(contNodeNameList.get(i).getStringValue()), contNodeValueList.get(i).getText().trim());
                i++;
            }
            return map;
        } else return null;
    }

    private void printContributesToRelationships(Node node) {
        if (node.selectSingleNode("child::*[contains(name(), \"Dummy\")]/attribute::rdf:resource") != null &&
                node.selectSingleNode("child::*[contains(name(), \"property:Dummy_element_cont\")]") != null) {
            for (String key : Objects.requireNonNull(getContributesToRelationships(node)).keySet()) {
                System.out.println("Contributes to: " + key + "(" + Objects.requireNonNull(getContributesToRelationships(node)).get(key) + ")");
            }
        }
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public Document getDocToBeParsed() {
        return docToBeParsed;
    }

    public void setDocToBeParsed(Document docToBeParsed) {
        this.docToBeParsed = docToBeParsed;
    }

    private String getDependsOnRelationships(Node node) {
        if (node.selectSingleNode("/rdf:RDF/swivt:Subject/property:Element_link/@rdf:resource") != null) {
            return (node.selectSingleNode("/rdf:RDF/swivt:Subject/property:Element_link/@rdf:resource")).getStringValue();
        } else return null;
    }

//    private void printDependsOnRelationships(Node node) {
//        if (node.selectSingleNode("/rdf:RDF/swivt:Subject/property:Element_link/@rdf:resource") != null) {
//            for (Node n : node.selectNodes("/rdf:RDF/swivt:Subject/property:Element_link/@rdf:resource")) {
//                System.out.println("Depends on: " +
//                        node.selectSingleNode("/rdf:RDF/swivt:Subject/property:Element_link/@rdf:resource").getStringValue());
//            }
//        }
//    }
}