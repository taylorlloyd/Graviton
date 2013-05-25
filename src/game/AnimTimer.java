package game;

public class AnimTimer {
	public static int countSince(long start, double ms) {
		int timeelapsed = (int)(System.currentTimeMillis() - start);
		return (int)(timeelapsed/ms);
	}
	public static long startTmr() {
		return System.currentTimeMillis();
	}
}
