import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class GraphViewer extends JPanel {
	
	int subjectListIndex = 0;
	ArrayList<SubjectDrawer> drawers;
	
	double maxDev = 2;
	double minLength = 600;
	double minMeal = 4;
	
	public GraphViewer(ArrayList<SubjectDrawer> drawers, int xSize, int ySize) {
		this.drawers = drawers;
		setPreferredSize(new Dimension(xSize,ySize));
	}
	public void updateDrawers(ArrayList<SubjectDrawer> drawers) {
		this.drawers = drawers;
		this.subjectListIndex = 0;
		drawers.get(subjectListIndex).getSubject().buildSRPs(maxDev, minLength);
		drawers.get(subjectListIndex).getSubject().buildMeals(minMeal);
		drawers.get(subjectListIndex).draw();
		repaint();
	}
	public void drawAll() {
		for(SubjectDrawer d : drawers) {
			if(!d.isDrawn()) {
				d.getSubject().buildSRPs(maxDev, minLength);
				d.getSubject().buildMeals(minMeal);
				d.draw();
			}
		}
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(drawers.size()==0) return;
		g.drawImage(drawers.get(subjectListIndex).getImage(), 0, 0, null);
	}
	public int advanceSubject() {
		if(drawers.size()==0) return 0;
		subjectListIndex = (subjectListIndex+1)%drawers.size();
		if(!drawers.get(subjectListIndex).isDrawn()) {
			drawers.get(subjectListIndex).getSubject().buildSRPs(maxDev, minLength);
			drawers.get(subjectListIndex).getSubject().buildMeals(minMeal);
			drawers.get(subjectListIndex).draw();
		}
		repaint();
		return subjectListIndex;
	}
	public int devanceSubject() {
		if(drawers.size()==0) return 0;
		subjectListIndex = (subjectListIndex-1+drawers.size())%drawers.size();
		if(!drawers.get(subjectListIndex).isDrawn()) {
			drawers.get(subjectListIndex).getSubject().buildSRPs(maxDev, minLength);
			drawers.get(subjectListIndex).getSubject().buildMeals(minMeal);
			drawers.get(subjectListIndex).draw();
		}
		repaint();
		return subjectListIndex;
	}
	public String getCurrentName() {
		if(drawers.size()==0) return "NONE";
		return drawers.get(subjectListIndex).getSubject().getName();
	}
	public double getMaxDev() {
		return maxDev;
	}
	public double getMinLength() {
		return minLength;
	}
	public double getMinMeal() {
		return minMeal;
	}
	public void setMinLength(double minLength) {
		this.minLength = minLength;
		for(SubjectDrawer d : drawers) {
			d.setDrawn(false);
		}
		if(drawers.size()==0) return;
		drawers.get(subjectListIndex).getSubject().buildSRPs(maxDev, minLength);
		drawers.get(subjectListIndex).getSubject().buildMeals(minMeal);
		drawers.get(subjectListIndex).draw();
		repaint();
	}
	public void setMinMeal(double minMeal) {
		this.minMeal = minMeal;
		for(SubjectDrawer d : drawers) {
			d.setDrawn(false);
		}
		if(drawers.size()==0) return;
		drawers.get(subjectListIndex).getSubject().buildSRPs(maxDev, minLength);
		drawers.get(subjectListIndex).getSubject().buildMeals(minMeal);
		drawers.get(subjectListIndex).draw();
		repaint();
	}
	public void setMaxDev(double maxDev) {
		this.maxDev = maxDev;
		for(SubjectDrawer d : drawers) {
			d.setDrawn(false);
		}
		if(drawers.size()==0) return;
		drawers.get(subjectListIndex).getSubject().buildSRPs(maxDev, minLength);
		drawers.get(subjectListIndex).getSubject().buildMeals(minMeal);
		drawers.get(subjectListIndex).draw();
		repaint();
	}
}
