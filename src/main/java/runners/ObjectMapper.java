package runners;

import domain.*;
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
    private ArrayList<Belief> beliefList;
    private ArrayList<Attraction> attractionList;
    private ArrayList<Goal> goalList;
    private ArrayList<Outcome> outcomeList;


    public ObjectMapper() {
        parser = new RDFParser();
        areaList = new ArrayList<>();
        personaList = new ArrayList<>();
        resourceList = new ArrayList<>();
        beliefList = new ArrayList<>();
        attractionList = new ArrayList<>();
        goalList = new ArrayList<>();
        outcomeList = new ArrayList<>();
        mapContext();
        mapResource();
        mapBelief();
        mapAttraction();
        mapGoal();
        mapOutcome();
        System.out.println(getResourceList());

    }

    public static void main(String[] args) {
        ObjectMapper map = new ObjectMapper();
        map.mapContext();
        map.mapResource();
        map.mapBelief();
        map.mapAttraction();
        map.mapGoal();
        map.mapOutcome();
        System.out.println(map.getResourceList());
    }

    public void mapContext() {
        for (Node n : parser.getNodeList()) {
            if (n.hasContent()) {
                // check if the IE field says 'Context'
                if (parser.getContextTypeValue(n).contains("Area")) {
                    Area a = new Area();
                    a.setName(parser.getLabelStringValue(n));
                    a.setContextType("Area");
                    areaList.add(a);
                } else if (parser.getContextTypeValue(n).contains("Role")) {
                    Persona c = new Persona();
                    c.setName(parser.getLabelStringValue(n));
                    c.setContextType("Role");
                    Context cc = new Context();
                    cc.setContextType("Area");
                    cc.setName(parser.getContextStringValue(n));
                    c.setContext(cc);
                    personaList.add(c);
                }
            }
        }
    }

    public void mapResource() {
        for (Node n : parser.getNodeList()) {
            if (n.hasContent()) {
                // check if the IE field says 'Condition'
                if (parser.getIntentionalElementStringValue(n).contains("Condition")) {
                    //separately map every attribute to a new object, and do this for every object respectively
                    Resource r = new Resource();
                    r.setName(parser.getLabelStringValue(n));
                    // check the areaList to assign the correct Context to the Resource
                    for (Area a : areaList) {
                        if (a.getName().equals(parser.getContextStringValue(n))) {
                            r.setContext(a);
                        }
                    }
                    if (parser.getTransportStringValue(n) != null) {
                        r.setTransport(parser.getTransportStringValue(n));
                    } else r.setTransport("N/A");
                    r.setElementType("Resource");
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
                    Belief b = new Belief();
                    b.setName(parser.getLabelStringValue(n));
                    for (Persona p : personaList) {
                        if (p.getName().equals(parser.getContextStringValue(n))) {
                            b.setContext(p);
                        }
                    }
                    if (parser.getTransportStringValue(n) != null) {
                        b.setTransport(parser.getTransportStringValue(n));
                    } else b.setTransport("N/A");
                    b.setElementType("Belief");
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
                    a.setName(parser.getLabelStringValue(n));
                    for (Persona p : personaList) {
                        if (p.getName().equals(parser.getContextStringValue(n))) {
                            a.setContext(p);
                        }
                    }
                    if (parser.getTransportStringValue(n) != null) {
                        a.setTransport(parser.getTransportStringValue(n));
                    } else a.setTransport("N/A");
                    a.setElementType("Attraction");
                    attractionList.add(a);
                }
            }
        }
    }

    public void mapGoal() {
        for (Node n : parser.getNodeList()) {
            if (n.hasContent()) {
                if (parser.getIntentionalElementStringValue(n).contains("Goal")) {
                    Goal g = new Goal();
                    g.setName(parser.getLabelStringValue(n));
                    for (Persona p : personaList) {
                        if (p.getName().equals(parser.getContextStringValue(n))) {
                            g.setContext(p);
                        }
                    }
                    if (parser.getTransportStringValue(n) != null) {
                        g.setTransport(parser.getTransportStringValue(n));
                    } else g.setTransport("N/A");
                    g.setElementType("Goal");
                    goalList.add(g);
                }
            }
        }
    }

    public void mapOutcome() {
        for (Node n : parser.getNodeList()) {
            if (n.hasContent()) {
                if (parser.getIntentionalElementStringValue(n).contains("Outcome")) {
                    Outcome o = new Outcome();
                    o.setName(parser.getLabelStringValue(n));
                    for (Area a : areaList) {
                        if (a.getName().equals(parser.getContextStringValue(n))) {
                            o.setContext(a);
                        }
                    }
                    if (parser.getTransportStringValue(n) != null) {
                        o.setTransport(parser.getTransportStringValue(n));
                    } else o.setTransport("N/A");
                    o.setElementType("Outcome");
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

    public ArrayList<Belief> getBeliefList() {
        return beliefList;
    }

    public void setBeliefList(ArrayList<Belief> beliefList) {
        this.beliefList = beliefList;
    }

    public ArrayList<Attraction> getAttractionList() {
        return attractionList;
    }

    public void setAttractionList(ArrayList<Attraction> attractionList) {
        this.attractionList = attractionList;
    }

    public ArrayList<Goal> getGoalList() {
        return goalList;
    }

    public void setGoalList(ArrayList<Goal> goalList) {
        this.goalList = goalList;
    }

    public ArrayList<Outcome> getOutcomeList() {
        return outcomeList;
    }

    public void setOutcomeList(ArrayList<Outcome> outcomeList) {
        this.outcomeList = outcomeList;
    }

    public ArrayList<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(ArrayList<Resource> resourceList) {
        this.resourceList = resourceList;
    }
}
