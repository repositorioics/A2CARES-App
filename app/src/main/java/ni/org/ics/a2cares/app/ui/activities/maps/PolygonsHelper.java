package ni.org.ics.a2cares.app.ui.activities.maps;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel Salinas on 4/6/2021.
 */
public class PolygonsHelper {

    List<LatLng> getPolygonBoer(){
        List<LatLng> verticesBoer = new ArrayList<>();
        verticesBoer.add(new LatLng(12.15075318,	-86.27929099));
        verticesBoer.add(new LatLng(12.15050395,	-86.27642476));
        verticesBoer.add(new LatLng(12.15095112,	-86.27557763));
        verticesBoer.add(new LatLng(12.15091692,	-86.27415269));
        verticesBoer.add(new LatLng(12.14580532,	-86.27467363));
        verticesBoer.add(new LatLng(12.14581271,	-86.27478023));
        verticesBoer.add(new LatLng(12.14591238,	-86.27549517));
        verticesBoer.add(new LatLng(12.1457852,	-86.2757119));
        verticesBoer.add(new LatLng(12.14616209,	-86.27788968));
        verticesBoer.add(new LatLng(12.14641306,	-86.27934353));
        verticesBoer.add(new LatLng(12.14691926,	-86.28226515));
        verticesBoer.add(new LatLng(12.15044668,	-86.28189609));
        verticesBoer.add(new LatLng(12.15097513,	-86.28184079));
        verticesBoer.add(new LatLng(12.15075318,	-86.27929099));
        return verticesBoer;
    }
}
