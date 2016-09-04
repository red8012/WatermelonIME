package co.watermelonime.Common;
import java.util.HashMap;

import co.watermelonime.C;

public class Timer {
	static HashMap<Integer, Long> map = new HashMap<>();

	public static String t(Integer id, String... message) {
		if (!C.debug) return null;
		try {
			Long time = System.nanoTime();
			if (message.length == 0) {
				map.put(id, time);
				return null;
			} else {
				double elapsed = (time - map.get(id)) / 1000000.0;
				String s = String.format("Timer: %.3f ms (", elapsed);
				System.out.print(s);
				for (String ss : message)
					System.out.print(ss);
				System.out.println(")");
				return s;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
}
