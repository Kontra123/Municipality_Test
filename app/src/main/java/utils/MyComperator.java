package utils;

import com.municipality.data.CountriesByRegion;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by AmirG on 10/28/2017.
 */
public class MyComperator  {

    public List<CountriesByRegion> doSortByArea(List<CountriesByRegion> list) {

        // will add countries without areas to the end of the list
        Collections.sort(list, new Comparator<CountriesByRegion>() {

            @Override
            public int compare(CountriesByRegion o1, CountriesByRegion o2) {
                if (o1.area == null) {
                    return 1;
                } else if (o2.area == null) {
                    return -1;
                }

                return Double.valueOf(o2.area).compareTo(o1.area);
            }
        });

        return list;
    }
}
