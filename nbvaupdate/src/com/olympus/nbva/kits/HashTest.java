package com.olympus.nbva.kits;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashTest {
	public static void main(String[] args) {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(100, "sachin");
		hm.put(101, "sehwag");
		hm.put(102, "gambir");
		hm.put(101, "sehwag2");
		hm.put(100, "sachin2");
		Set set = hm.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry m = (Map.Entry) it.next();
			System.out.println(m.getKey() + ":" + m.getValue());
		}
	}
}
