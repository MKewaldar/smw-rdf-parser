import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

public class RDFParser {
    public static void main(String[] args) {
        RDFParser RDFParser = new RDFParser();
        RDFParser.testMethod(RDFParser.parse(new File("centraal_station_middelburg.xml")));
    }

    /**
     * Reads the XML document
     *
     * @param file File to read
     * @return Document object ready to be reasoned with
     */
    private Document parse(File file) {
        SAXReader reader = new SAXReader();
        org.dom4j.Document doc = null;
        try {
            doc = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return doc;
    }
        

    /**
     * TODO: Remove
     *
     * @param doc Document to be parsed
     */
    private void testMethod(Document doc) {
        Element root = doc.getRootElement();
        System.out.println("Root element: " + root.getQualifiedName());
        System.out.println();

        System.out.println(doc.selectSingleNode("/rdf:RDF/swivt:Subject/rdfs:label").getName() + ": " +
                doc.selectSingleNode("/rdf:RDF/swivt:Subject/rdfs:label").getStringValue());

        String str = doc.selectSingleNode("/rdf:RDF/swivt:Subject/property:Context/@rdf:resource").getStringValue();

        str = str.substring(str.lastIndexOf('/') + 1);

        System.out.println(doc.selectSingleNode("/rdf:RDF/swivt:Subject/property:Context").getName() + ": " + str);

        System.out.println(doc.selectSingleNode("/rdf:RDF/swivt:Subject/property:Transport").getName() + ": " +
                doc.selectSingleNode("/rdf:RDF/swivt:Subject/property:Transport").getStringValue());

        System.out.println(doc.selectSingleNode("/rdf:RDF/swivt:Subject/property:Intentional_Element_type").getName() + ": " +
                doc.selectSingleNode("/rdf:RDF/swivt:Subject/property:Intentional_Element_type").getStringValue());

    }
}
