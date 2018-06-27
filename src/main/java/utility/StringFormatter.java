package utility;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Author: Marlon
 * Created on: 27-6-2018
 * Class representing a utility.StringFormatter, responsible for manipulating the strings parsed from the RDF parser so
 * AnyLogic can read them properly.
 */

public final class StringFormatter {

    /**
     * Replaces all space characters with lowercases, because AnyLogic fields don't accept spaces.
     *
     * @param stringToBeFormatted Input string
     * @return Output string
     */
    public static String replaceSpaceWithLowercase(String stringToBeFormatted) {
        return stringToBeFormatted.replaceAll(" ", "_");
    }

    /**
     * Returns the final artifact from an URI as String.
     *
     * @param stringToBeFormatted Input string
     * @return Output string
     */
    public static String retrieveFinalArtifactFromURI(String stringToBeFormatted) {
        try {
            URI uri = new URI(stringToBeFormatted);
            //Get the last element after the final '/' token.
            return uri.toString().substring(uri.toString().lastIndexOf('/') + 1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
