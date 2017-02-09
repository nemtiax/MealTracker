
public class Meal {
	private final double startTime;
	private final double endTime;
	private final double size;
	public Meal(double startTime, double endTime, double size) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.size = size;
	}
	public String toString() {
		return "{ " + startTime + " to " + endTime + " with size " + size + " }";
	}
	public double getStartTime() {
		return startTime;
	}
	public double getEndTime() {
		return endTime;
	}
	public double getSize() {
		return size;
	}
}
