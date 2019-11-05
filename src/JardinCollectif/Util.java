package JardinCollectif;

public class Util {
	public static Boolean newCycle = false;

	public static void print(Object o) {
		if (newCycle) {
			System.out.println();
			newCycle = false;
		}
		System.out.println(o.toString());
	}
}
