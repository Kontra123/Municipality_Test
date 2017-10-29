package com.municipality;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.municipality.data.CountriesByRegion;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import utils.MyComperator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

/**
 * Tests that the parcelable interface is implemented correctly.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class ApplicationTest  {

    public static final String ITALY = "italy";
    public static final double ITALY_AREA = 3.0;

    public static final String SPAIN = "spain";
    public static final double SPAIN_AREA = 7.0;

    public static final String PALESTINE = "palestine";;
    public static final Double PALESTINE_AREA = null;

    public static final String FRANCE = "france";
    public static final double FRANCE_AREA = 5.0;

    private MyComperator myComperator;

    @Before
    public void initComperator() {
        myComperator = new MyComperator();
    }

    @Test
    public void testComperator() {

        List<CountriesByRegion> countriesByRegions = new ArrayList<CountriesByRegion>();

        CountriesByRegion countriesByRegion1 = new CountriesByRegion();
        countriesByRegion1.area = ITALY_AREA;
        countriesByRegion1.name = ITALY;
        countriesByRegions.add(countriesByRegion1);

        CountriesByRegion countriesByRegion2 = new CountriesByRegion();
        countriesByRegion2.area = SPAIN_AREA;
        countriesByRegion2.name = SPAIN;
        countriesByRegions.add(countriesByRegion2);

        CountriesByRegion countriesByRegion3 = new CountriesByRegion();
        countriesByRegion3.area = PALESTINE_AREA;
        countriesByRegion3.name = PALESTINE;
        countriesByRegions.add(countriesByRegion3);

        CountriesByRegion countriesByRegion4 = new CountriesByRegion();
        countriesByRegion4.area = FRANCE_AREA;
        countriesByRegion4.name = FRANCE;
        countriesByRegions.add(countriesByRegion4);

        countriesByRegions = myComperator.doSortByArea(countriesByRegions);

        assertEquals(countriesByRegions.get(0).area, SPAIN_AREA, 0.001);
        assertEquals(countriesByRegions.get(0).name, SPAIN);

        assertEquals(countriesByRegions.get(1).area, FRANCE_AREA, 0.001);
        assertEquals(countriesByRegions.get(1).name, FRANCE);

        assertEquals(countriesByRegions.get(2).area, ITALY_AREA, 0.001);
        assertEquals(countriesByRegions.get(2).name, ITALY);

        assertEquals(countriesByRegions.get(3).area, PALESTINE_AREA);
        assertEquals(countriesByRegions.get(3).name, PALESTINE);

    }



}