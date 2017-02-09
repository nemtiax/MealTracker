import java.util.ArrayList;


public class Subject {
	String name;
	ArrayList<Reading> scaleReadings;
	ArrayList<SRP> srps;
	ArrayList<Meal> meals;
	double minimalWeight = Double.MAX_VALUE;
	double maximalWeight = -Double.MAX_VALUE;
	double startWeight = 0;
	double endWeight = 0;
	public Subject(String name) {
		this.name = name;
		scaleReadings = new ArrayList<>();
	}
	public double getMinimalWeight() {
		return minimalWeight;
	}
	public double getMaximalWeight() {
		return maximalWeight;
	}
	public ArrayList<Reading> getReadings() {
		return scaleReadings;
	}
	public ArrayList<SRP> getSRPs() {
		return srps;
	}
	public ArrayList<Meal> getMeals() {
		return meals;
	}
	public double getStartWeight() {
		return startWeight;
	}
	public double getEndWeight() {
		return endWeight;
	}
	public void addReading(double timestamp, double weight) {
		if(scaleReadings.size()==0) {
			startWeight = weight;
		}
		endWeight = weight;
		
		if(weight < minimalWeight) {
			minimalWeight = weight;
		}
		if(weight > maximalWeight) {
			maximalWeight = weight;
		}
		
		scaleReadings.add(new Reading(timestamp,weight));
		
	}
	/*
	 * Compute the SRPs (stable reading periods) given a maxDeviation and a minLength
	 * 
	 * maxDeviation indicates the maximum amount a reading can differ from the baseline and still be considered stable
	 * For example, with a maxDeviation of 2 (2/10ths of a gram), the sequence 71,72,73,70,72 is a potential SRP because the values are never more then 2 away from the starting value
	 * 
	 * minLength indicates the minimum length of time to be considered a valid SRP.
	 * 
	 */
	public void buildSRPs(double maxDeviation, double minLength) {
		srps = new ArrayList<>();
		if(scaleReadings.size()==0) return;
		Reading previousReading = scaleReadings.get(0);
		double startWeight = previousReading.getWeight();
		double sumOfWeights = 0;
		double totalTime = 0;
		double startTime = previousReading.getTime();
		for(int i = 1;i<scaleReadings.size();i++) {
			Reading nextReading = scaleReadings.get(i);
			sumOfWeights += previousReading.getWeight() * (nextReading.getTime() - previousReading.getTime());
			totalTime += (nextReading.getTime() - previousReading.getTime());
			
			if(Math.abs(startWeight - nextReading.getWeight()) > maxDeviation) {
				if(nextReading.getTime() - startTime >= minLength) {
					SRP srp = new SRP(startTime,nextReading.getTime(),sumOfWeights/totalTime);
					srps.add(srp);
				}
				sumOfWeights = 0;
				totalTime = 0;
				startTime = nextReading.getTime();
				startWeight = nextReading.getWeight();
			}
			
			previousReading = nextReading;
		}
		sumOfWeights += previousReading.getWeight() * (82800 - previousReading.getTime());
		totalTime += (82800 - previousReading.getTime());
		if(82800 - startTime >= minLength) {
			SRP srp = new SRP(startTime,82800,sumOfWeights/totalTime);
			srps.add(srp);
		}
	}
	
	public void buildMeals(double minSize) {
		meals = new ArrayList<>();
		if(srps.size()==0) return;
		SRP previousSRP = srps.get(0);
		for(int i = 1;i<srps.size();i++) {
			SRP nextSRP = srps.get(i);
			double mealSize = previousSRP.getAverageWeight() - nextSRP.getAverageWeight();
			if(mealSize >= minSize) {
				Meal m = new Meal(previousSRP.getEndTime(),nextSRP.getStartTime(),mealSize);
				meals.add(m);
			}
			previousSRP = nextSRP;
		}
	}
	public String getName() {
		return name;
	}
	public String toString() {
		return name;
	}
}
