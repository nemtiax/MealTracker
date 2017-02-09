
public class SRP {
	private final double startTime;
	private final double endTime;
	private final double averageWeight;
	public SRP(double startTime, double endTime, double averageWeight) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.averageWeight = averageWeight;
	}
	public double getStartTime() {
		return startTime;
	}
	public double getEndTime() {
		return endTime;
	}
	public double getAverageWeight() {
		return averageWeight;
	}
	public String toString() {
		return "{ " + startTime + " to " + endTime + " at weight " + averageWeight + " }";
	}
}
