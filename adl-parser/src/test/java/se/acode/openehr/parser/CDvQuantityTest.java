/*
 * Copyright (C) 2005 Acode HB, Sweden.
 * All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You may obtain a copy of the License at
 * http://www.gnu.org/licenses/gpl.txt
 *
 */

package se.acode.openehr.parser;

import org.openehr.am.archetype.constraintmodel.CObject;
import org.openehr.am.archetype.Archetype;
import org.openehr.am.openehrprofile.datatypes.quantity.CDvQuantity;
import org.openehr.am.openehrprofile.datatypes.quantity.CDvQuantityItem;
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.support.basic.Interval;

import java.util.*;

/**
 * Test case tests parsing of domain type constraints extension.
 *
 * @author Rong Chen
 * @version 1.0
 */

public class CDvQuantityTest extends ParserTestBase {

    public CDvQuantityTest(String test) throws Exception {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {
        ADLParser parser = new ADLParser(loadFromClasspath(
                "adl-test-entry.c_dv_quantity.test.adl"));
        archetype = parser.parse();
    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception {
        archetype = null;
    }

    public void testCDvQuantity() throws Exception {
        CObject node = archetype.node(
                "/types[at0001]/items[at10005]/value");
        assertTrue("CDvQuantity expected", node instanceof CDvQuantity);
        CDvQuantity cdvquantity = (CDvQuantity) node;
        
        // verify property 
        CodePhrase property = cdvquantity.getProperty();
        assertNotNull("property is null", property);
        assertEquals("openehr", property.getTerminologyID().name());
        assertEquals("128", property.getCodeString());
        
        // verify item list
        List<CDvQuantityItem> list = cdvquantity.getList();
        assertEquals("unexpected size of list", 2, list.size());
        assertCDvQuantityItem(list.get(0), "yr", 
        		new Interval<Double>(0.0, 200.0), new Interval<Integer>(2, 2));
        assertCDvQuantityItem(list.get(1), "mth", 
        		new Interval<Double>(1.0, 36.0), new Interval<Integer>(2, 2));
    }
    
    private void assertCDvQuantityItem(CDvQuantityItem item, String units,
    		Interval<Double> magnitude, Interval<Integer> precision) {
    	assertEquals("unexpected units", units, item.getUnits());
    	assertEquals("unexpected magnitude interval", magnitude, 
    			item.getValue());
    	assertEquals("unexpected precision interval", precision, 
    			item.getPrecision());    	
    }

    private Archetype archetype;
}
