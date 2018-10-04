package runners;

import emont_casus.*;
import org.dom4j.Node;

import java.util.ArrayList;

/**
 * Author: Marlon Kewaldar
 * Created on: 24-9-2018
 * Class responsible for mapping the information from the runners.RDFParser into Java objects.
 */
public class ObjectMapper {
    private RDFParser parser;
    private ArrayList<Area> areaList;
    private ArrayList<Resource> resourceList;
    private ArrayList<Persona> personaList;
    private ArrayList<Intentional_Element> beliefList;
    private ArrayList<Attraction> attractionList;
    private ArrayList<Intentional_Element> goalList;
    private ArrayList<Intentional_Element> outcomeList;
    private Main main;



    public ObjectMapper() {
        parser = new RDFParser();
        areaList = new ArrayList<>();
        personaList = new ArrayList<>();
        resourceList = new ArrayList<>();
        beliefList = new ArrayList<>();
        attractionList = new ArrayList<>();
        goalList = new ArrayList<>();
        outcomeList = new ArrayList<>();
        mapEverything();
        createMockLocationData();
    }

    public static void main(String[] args) {
        ObjectMapper map = new ObjectMapper();
    }

    public void mapEverything() {
        mapContext();
        mapResource();
        mapBelief();
        mapAttraction();
        mapGoal();
        mapOutcome();
    }

    public void mapContext() {
        for (Node n : parser.getNodeList()) {
            if (n.hasContent()) {
                // check if the IE field says "Area"
                if (parser.getContextTypeValue(n).contains("Area")) {
                    Area a = new Area();
                    a.set_name(parser.getLabelStringValue(n));
                    areaList.add(a);
                }
                // otherwise, check if it contains "Role" (Persona)
                else if (parser.getContextTypeValue(n).contains("Role")) {
                    Persona c = new Persona();
                    c.set_name(parser.getLabelStringValue(n));
                    // ensure the Context of the Context is initialized correctly
                    Context cc = new Context();
                    cc.set_name(parser.getContextStringValue(n));
                    c.set_context(cc);
                    personaList.add(c);
                }
            }
        }
    }

    public void createMockLocationData() {
        getAreaList().get(0).set_latitude(51.499123);
        getAreaList().get(0).set_longitude(3.613360);
        getAreaList().get(1).set_latitude(51.455316);
        getAreaList().get(1).set_longitude(3.585747);
        getPersonaList().get(0).set_latitude(51.451770);
        getPersonaList().get(0).set_longitude(4.036266);
        getResourceList().get(0).set_latitude(51.519658);
        getResourceList().get(0).set_longitude(3.580812);

    }

    public void mapResource() {
        for (Node n : parser.getNodeList()) {
            if (n.hasContent()) {
                // check if the IE field says 'Condition'
                if (parser.getIntentionalElementStringValue(n).contains("Condition")) {
                    //separately map every attribute to a new object, and do this for every object respectively
                    Resource r = new Resource();
                    r.set_name(parser.getLabelStringValue(n));
                    // check the areaList to assign the correct Context to the Resource
                    for (Area a : areaList) {
                        if (a.getParameter("name").equals(parser.getContextStringValue(n))) {
                            r.set_context(a);
                        }
                    }
                    if (parser.getTransportStringValue(n) != null) {
                        Transport t = new Transport();
                        t.set_name(parser.getTransportStringValue(n));
                        r.set_transport(t);
                    }
                    r.set_IEType("Resource");
                    resourceList.add(r);
                }
            }
        }
    }


    public void mapBelief() {
        for (Node n : parser.getNodeList()) {
            if (n.hasContent()) {
                // check if the IE field says 'Condition'
                if (parser.getIntentionalElementStringValue(n).contains("Belief")) {
                    Intentional_Element b = new Intentional_Element();
                    b.set_name(parser.getLabelStringValue(n));
                    for (Persona p : personaList) {
                        if (p.getParameter("name").equals(parser.getContextStringValue(n))) {
                            b.set_context(p);
                        }
                    }
                    b.set_IEType("Belief");
                    beliefList.add(b);
                }
            }
        }
    }

    public void mapAttraction() {
        for (Node n : parser.getNodeList()) {
            if (n.hasContent()) {
                if (parser.getIntentionalElementStringValue(n).contains("Activity")) {
                    Attraction a = new Attraction();
                    a.set_name(parser.getLabelStringValue(n));
                    for (Persona p : personaList) {
                        if (p.getParameter("name").equals(parser.getContextStringValue(n))) {
                            a.set_context(p);
                        }
                    }
                    a.set_IEType("Attraction");
                    attractionList.add(a);
                }
            }
        }
    }

    public void mapGoal() {
        for (Node n : parser.getNodeList()) {
            if (n.hasContent()) {
                if (parser.getIntentionalElementStringValue(n).contains("Goal")) {
                    Intentional_Element g = new Intentional_Element();
                    g.set_name(parser.getLabelStringValue(n));
                    for (Persona p : personaList) {
                        if (p.getParameter("name").equals(parser.getContextStringValue(n))) {
                            g.set_context(p);
                        }
                    }
                    g.set_IEType("Goal");
                    goalList.add(g);
                }
            }
        }
    }

    public void mapOutcome() {
        for (Node n : parser.getNodeList()) {
            if (n.hasContent()) {
                if (parser.getIntentionalElementStringValue(n).contains("Outcome")) {
                    Intentional_Element o = new Intentional_Element();
                    o.set_name(parser.getLabelStringValue(n));
                    for (Area a : areaList) {
                        if (a.getParameter("name").equals(parser.getContextStringValue(n))) {
                            o.set_context(a);
                        }
                    }
                    o.set_IEType("Outcome");
                    outcomeList.add(o);
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

    public ArrayList<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(ArrayList<Area> areaList) {
        this.areaList = areaList;
    }

    public ArrayList<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(ArrayList<Persona> personaList) {
        this.personaList = personaList;
    }

    public ArrayList<Intentional_Element> getBeliefList() {
        return beliefList;
    }

    public void setBeliefList(ArrayList<Intentional_Element> beliefList) {
        this.beliefList = beliefList;
    }

    public ArrayList<Attraction> getAttractionList() {
        return attractionList;
    }

    public void setAttractionList(ArrayList<Attraction> attractionList) {
        this.attractionList = attractionList;
    }

    public ArrayList<Intentional_Element> getGoalList() {
        return goalList;
    }

    public void setGoalList(ArrayList<Intentional_Element> goalList) {
        this.goalList = goalList;
    }

    public ArrayList<Intentional_Element> getOutcomeList() {
        return outcomeList;
    }

    public void setOutcomeList(ArrayList<Intentional_Element> outcomeList) {
        this.outcomeList = outcomeList;
    }

    public ArrayList<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(ArrayList<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
