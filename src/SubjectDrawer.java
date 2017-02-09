import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SubjectDrawer {
	
	Subject subject;
	int xSize,ySize;
	BufferedImage image;
	Graphics2D g;
	boolean isDrawn = false;
	public SubjectDrawer(Subject subject, int xSize, int ySize) {
		image = new BufferedImage(xSize,ySize,BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D)(image.getGraphics());
		
		this.subject = subject;
		this.xSize = xSize;
		this.ySize = ySize;
	}
	public Subject getSubject() {
		return subject;
	}
	public void save(File f) throws IOException {
		ImageIO.write(image, "PNG", f);
	}
	public boolean isDrawn() {
		return isDrawn;
	}
	public void setDrawn(boolean d) {
		isDrawn = d;
	}
	public BufferedImage getImage() {
		return image;
	}
	
	public void draw() {
		drawBackground();
		drawSRPZones();
		drawMealZones();
		drawGrid();
		drawReadings();
		drawSRPLines();
		isDrawn = true;
	}
	public void drawGrid() {
		g.setColor(Color.LIGHT_GRAY);
		for(int i = (int)subject.getMinimalWeight()-5;i<=(int)subject.getMaximalWeight()+5;i+=10) {
			int y = yCoord(i);
			g.drawLine(0, y, xSize, y);
		}
	}
	public void drawReadings() {
		g.setColor(new Color(0,0,0,32));
		for(Reading r : subject.getReadings()) {
			double time = r.getTime();
			double weight = r.getWeight();
			int x = xCoord(time);
			int y = yCoord(weight);
			g.fillOval(x-2, y-2, 5, 5);
		}
	}
	public void drawSRPZones() {
		
		for(SRP srp : subject.getSRPs()) {
			double startTime = srp.getStartTime();
			double endTime = srp.getEndTime();
			int startX = xCoord(startTime);
			int width = xCoord(endTime) - xCoord(startTime);
			g.setColor(new Color(0,0,255,32));
			g.fillRect(startX, 0, width, ySize);
		}
	}
	public void drawMealZones() {
		for(Meal m : subject.getMeals()) {
			double startTime = m.getStartTime();
			double endTime = m.getEndTime();
			
			int startX = xCoord(startTime);
			int width = xCoord(endTime) - xCoord(startTime);
			
			g.setColor(new Color(255,0,0,32));
			g.fillRect(startX,0,width,ySize);
		}
	}
	public void drawSRPLines() {
		for(SRP srp : subject.getSRPs()) {
			double startTime = srp.getStartTime();
			double endTime = srp.getEndTime();
			double avgWeight = srp.getAverageWeight();
			int startX = xCoord(startTime);
			int endX = xCoord(endTime);
			int y = yCoord(avgWeight);
			g.setColor(Color.BLUE);
			g.drawLine(startX, y, endX, y);
		}
	}
	
	public void drawBackground() {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, xSize, ySize);
	}
	
	public int yCoord(double value) {
		return ySize - (int)( (value - (subject.getMinimalWeight()-5))/((subject.getMaximalWeight()+5)-(subject.getMinimalWeight()-5)) * ySize);
	}
	public int xCoord(double value) {
		return (int)(value/82800.0 * xSize);
	}
	
	
}
