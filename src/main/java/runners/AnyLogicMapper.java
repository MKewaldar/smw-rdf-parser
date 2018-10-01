package runners;

import com.anylogic.engine.Point;
import emont_casus.Area;
import emont_casus.Attraction;
import emont_casus.Main;

/**
 * Author: Marlon
 * Created on: 1-10-2018
 */
public class AnyLogicMapper {
    private ObjectMapper map;

    public AnyLogicMapper() {
        map = new ObjectMapper();
    }

    public static void main(String[] args) {

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
            main.add_attractionList("NA", "NA", "NA", a.getName(), a.getLatitude(),
                    a.getLongitude(), a.IEType, a.context);
        }
        for (Attraction a : main.attractionList) {
            Point p = new Point();
            p.setLatLon(a.latitude, a.longitude);
            a.setLocation(p);
        }
        return attractionList;
    }

}
