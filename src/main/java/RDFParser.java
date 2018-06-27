import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import utility.StringFormatter;

import java.io.File;

/**
 * Author: Marlon
 * Created on: 23-6-2018
 * Class representing a parser, responsible for retrieving the relevant information from the input XML.
 */

public class RDFParser {
    public static void main(String[] args) {
        RDFParser parser = new RDFParser();
        parser.printCurrentParsedFile(parser.parse(new File("input/centraal_station_middelburg.xml")));
        parser.printCurrentParsedFile(parser.parse(new File("input/Autoweg Mburg-Vliss.xml")));
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
     * Prints the formatted content of the file currently being handled
     * @param doc Document to be parsed
     */
    private void printCurrentParsedFile(Document doc) {
        //Label or name
        System.out.println(doc.selectSingleNode("/rdf:RDF/swivt:Subject/rdfs:label").getName() + ": " +
                StringFormatter.replaceSpaceWithLowercase(doc.selectSingleNode("/rdf:RDF/swivt:Subject/rdfs:label").getStringValue()));

        //Context, whose string is changed to make it more readable
        System.out.println(doc.selectSingleNode("/rdf:RDF/swivt:Subject/property:Context").getName() + ": " +
                StringFormatter.retrieveFinalArtifactFromURI(doc.selectSingleNode("/rdf:RDF/swivt:Subject/property:Context/@rdf:resource").getStringValue()));


        //Transport type
        System.out.println(doc.selectSingleNode("/rdf:RDF/swivt:Subject/property:Transport").getName() + ": " +
                doc.selectSingleNode("/rdf:RDF/swivt:Subject/property:Transport").getStringValue());

        //Intentional element type
        System.out.println(doc.selectSingleNode("/rdf:RDF/swivt:Subject/property:Intentional_Element_type").getName() + ": " +
                doc.selectSingleNode("/rdf:RDF/swivt:Subject/property:Intentional_Element_type").getStringValue());

        System.out.println("------------------------------------");
    }
}
