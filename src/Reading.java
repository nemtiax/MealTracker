
public class Reading {
	private final double timestamp;
	private final double weight;
	public Reading(double timestamp, double weight) {
		this.timestamp = timestamp;
		this.weight = weight;
	}
	public String toString() {
		return "[ " + timestamp + ": " + weight + " ]";
	}
	public double getWeight() {
		return weight;
	}
	public double getTime() {
		return timestamp;
	}
}
