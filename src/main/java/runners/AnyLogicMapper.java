package runners;

import com.anylogic.engine.Agent;
import com.anylogic.engine.Point;
import emont_casus.*;

import java.util.ArrayList;

/**
 * Author: Marlon Kewaldar
 * Created on: 1-10-2018
 */
public class AnyLogicMapper {
    private ObjectMapper map;

    public AnyLogicMapper() {
        map = new ObjectMapper();
    }

    public static void main(String[] args) {
        new AnyLogicMapper();
    }

    public Main._areaList_Population initAreaList(Main._areaList_Population areaList, Main main) {
        for (Area a : map.getAreaList()) {
            main.add_areaList(a.name, a.context, a.latitude, a.longitude);
        }
        for (Area a : main.areaList) {
            Point p = new Point();
            p.setLatLon(a.latitude, a.longitude);
            a.setLocation(p);
        }
        return areaList;
    }

    public Main._attractionList_Population initAttractionList(Main._attractionList_Population attractionList, Main main) {
        for (Attraction a : map.getAttractionList()) {
            main.add_attractionList(map.getBlank(), map.getBlank(), map.getBlank(), a.name, a.latitude,
                    a.longitude, a.IEType, a.context);
        }
        for (Attraction a : main.attractionList) {
            Point p = new Point();
            p.setLatLon(a.latitude, a.longitude);
            a.setLocation(p);
        }
        return attractionList;
    }

    public Main._personaList_Population initPersonaList(Main._personaList_Population personaList, Main main) {
        for (Persona p : map.getPersonaList()) {
            main.add_personaList(p.name, p.context, p.latitude, p.longitude);
        }
        for (Persona p : main.personaList) {
            Point po = new Point();
            po.setLatLon(p.latitude, p.longitude);
            p.setLocation(po);
        }
        return personaList;
    }

    public Main._resourceList_Population initResourceList(Main._resourceList_Population resourceList, Main main) {
        for (Resource r : map.getResourceList()) {
            main.add_resourceList(map.getBlank(), map.getBlank(), map.getBlank() , r.name
                    , r.latitude, r.longitude,
                    r.IEType, r.context, r.transport);
        }
        for (Resource r : main.resourceList) {
            Point p = new Point();
            p.setLatLon(r.latitude, r.longitude);
            r.setLocation(p);
        }

        return resourceList;
    }

    public Main._intentionalElementList_Population initIEList(Main._intentionalElementList_Population IEList, Main main) {
        ArrayList<Intentional_Element> ieCollection = new ArrayList<>();
        ieCollection.addAll(map.getBeliefList());
        ieCollection.addAll(map.getGoalList());
        ieCollection.addAll(map.getOutcomeList());

        for (Intentional_Element i : ieCollection) {
            main.add_intentionalElementList(map.getBlank(),  map.getBlank(), map.getBlank() , i.name,
                    i.latitude, i.longitude, i.IEType, i.context);
        }
        for (Intentional_Element i : main.intentionalElementList) {
            Point p = new Point();
            p.setLatLon(i.latitude, i.longitude);
            i.setLocation(p);
        }
        return IEList;
    }

    public ObjectMapper getMap() {
        return map;
    }

    public void setMap(ObjectMapper map) {
        this.map = map;
    }

}
