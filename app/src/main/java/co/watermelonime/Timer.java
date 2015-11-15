package co.watermelonime;
import java.util.HashMap;

public class Timer {
	static HashMap<Integer, Long> map = new HashMap<>();

	public static String t(Integer id, String... message) {
		Long time = System.nanoTime();
		if (message.length == 0) {
			map.put(id, time);
			return null;
		} else {
			double elapsed = (time - map.get(id)) / 1000000.0;
			String s = String.format("Timer: %.3f (", elapsed);
			System.out.print(s);
			for (String ss : message)
				System.out.print(ss);
			System.out.println(")");
			return s;
		}
	}
}
