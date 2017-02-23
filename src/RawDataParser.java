import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class RawDataParser {
	public static ArrayList<Subject> parse(File rawDataFile) throws FileNotFoundException {
		ArrayList<Subject> subjects = new ArrayList<>();
		Scanner s = new Scanner(rawDataFile);
		skipHeader(s);
		Subject currentSubject = null;
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String[] parsedItems = null;
			try{
				parsedItems = parseLine(line);
			} catch(ArrayIndexOutOfBoundsException e) {
				System.err.println("ERROR PARSING LINE " + line);
				continue;
			}
			String subjectName = parsedItems[0];
			String status = parsedItems[3];
			if(!status.equals("Stable")) {
				System.err.println("Non-stable line being skipped: " + line);
				continue;
			}
			if(currentSubject==null) { //if this is the start of the file, the currentSubject will be null
				currentSubject = new Subject(subjectName);
				subjects.add(currentSubject);
				
			} else if(!currentSubject.getName().equals(subjectName)) { //if we've finished one subject and started a new one
				currentSubject = new Subject(subjectName);
				subjects.add(currentSubject);
			}
			String timestampString = parsedItems[1];
			//the timestamps end with 's' for seconds, so we have to drop that
			double timestamp = Double.parseDouble(timestampString.substring(0,timestampString.length()-1));
			String weightString = parsedItems[2];
			//we multiply by ten to make the weights whole numbers to avoid floating point errors - the scale precision is 1/10th of a gram.
			double weight = Double.parseDouble(weightString)*10;
			currentSubject.addReading(timestamp, weight);
		}
		
		return subjects;
	}
	/*
	 * These files begin with several lines of header information describing the contents.  We skip through them,
	 * stopping once we see the line starting with "EXPERIMENT,SCALE,SUBJECT ID," as this indicates the start of data.
	 */
	private static void skipHeader(Scanner s) {
		while(!s.nextLine().startsWith("EXPERIMENT,SCALE,SUBJECT ID,")){}
	}
	/*
	 * We need to get four things from a line - the subject name, the timestamp, the scale weight, and the status
	 */
	private static String[] parseLine(String line) {
		String[] items = line.split(",");
		String[] result = new String[4];
		result[0] = items[2]; //subject name
		result[1] = items[14]; //timestamp
		result[2] = items[15]; //weight
		result[3] = items[16]; //status
		return result;
	}
}
