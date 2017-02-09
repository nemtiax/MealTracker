import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;


public class ControlPanel extends JPanel{
	

	DeviationControl devControl;
	SRPControl srpControl;
	MealControl mealControl;
	SubjectControl subjectControl;
	GraphViewer viewer;

	
	public ControlPanel(GraphViewer viewer) {
		this.viewer = viewer;
		setPreferredSize(new Dimension(200,800));
		setLayout(new GridLayout(32,1));
		add(new JLabel("MAX DEV",SwingConstants.CENTER));
		devControl = new DeviationControl(this);
		add(devControl);
		add(new JLabel("MIN STABLE LENGTH",SwingConstants.CENTER));
		srpControl = new SRPControl(this);
		add(srpControl);
		add(new JLabel("MIN MEAL SIZE",SwingConstants.CENTER));
		mealControl = new MealControl(this);
		add(mealControl);
		add(new JLabel("SUBJECT",SwingConstants.CENTER));
		subjectControl = new SubjectControl(this);
		add(subjectControl);
	}
	public void updateSubjectName() {
		String name = viewer.getCurrentName();
		subjectControl.setName(name);
	}
	public void incrementMaxDev() {
		double newMaxDev = viewer.getMaxDev() + 1;
		devControl.setDev(newMaxDev);
		viewer.setMaxDev(newMaxDev);
	}
	public void decrementMaxDev() {
		double newMaxDev = viewer.getMaxDev() - 1;
		if(newMaxDev < 0) newMaxDev = 0;
		devControl.setDev(newMaxDev);
		viewer.setMaxDev(newMaxDev);
	}
	public void incrementSRPLength() {
		double newMinLength = viewer.getMinLength() + 10;
		srpControl.setSRPLength(newMinLength);
		viewer.setMinLength(newMinLength);
	}
	public void decrementSRPLength() {
		double newMinLength = viewer.getMinLength() - 10;
		if(newMinLength<0) newMinLength=0;
		srpControl.setSRPLength(newMinLength);
		viewer.setMinLength(newMinLength);
	}
	public void incrementMinMeal() {
		double newMinMeal = viewer.getMinMeal() + 1;
		mealControl.setMinMeal(newMinMeal);
		viewer.setMinMeal(newMinMeal);
	}
	public void decrementMinMeal() {
		double newMinMeal = viewer.getMinMeal() - 1;
		if(newMinMeal<0) newMinMeal = 0;
		mealControl.setMinMeal(newMinMeal);
		viewer.setMinMeal(newMinMeal);
	}
	public void advanceViewer() {
		viewer.advanceSubject();
		String name = viewer.getCurrentName();
		subjectControl.setName(name);
	}
	public void devanceViewer() {
		viewer.devanceSubject();
		String name = viewer.getCurrentName();
		subjectControl.setName(name);
	}
}
class SubjectControl extends JPanel {
	JLabel subjectName;
	public SubjectControl(final ControlPanel parent) {
		setLayout(new BorderLayout());
		subjectName = new JLabel("",SwingConstants.CENTER);
		setName(parent.viewer.getCurrentName());
		JButton left = new BasicArrowButton(BasicArrowButton.WEST);
		JButton right = new BasicArrowButton(BasicArrowButton.EAST);
		add(left,BorderLayout.WEST);
		add(right,BorderLayout.EAST);
		add(subjectName,BorderLayout.CENTER);
		ActionListener increase = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.advanceViewer();
			}
			
		};
		right.addActionListener(increase);
		ActionListener decrease = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.devanceViewer();
			}
			
		};
		left.addActionListener(decrease);
	}
	public void setName(String name) {
		subjectName.setText(name);
	}
}
class MealControl extends JPanel {
	JTextField minMeal;
	NumberFormat formatter = new DecimalFormat("#0.0");   
	public MealControl(final ControlPanel parent) {
		setPreferredSize(new Dimension(200,50));
		setLayout(new BorderLayout());
		minMeal = new JTextField();
		setMinMeal(parent.viewer.minMeal);
		minMeal.setEditable(false);
		add(minMeal,BorderLayout.CENTER);
		ActionListener increase = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.incrementMinMeal();
			}
			
		};
		ActionListener decrease = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.decrementMinMeal();
			}
			
		};
		UpDownButtons updown = new UpDownButtons(increase,decrease);
		add(updown,BorderLayout.EAST);
	}
	public void setMinMeal(double minMealSize) {
		minMeal.setText(formatter.format(minMealSize/10) + " grams");
	}
}
class SRPControl extends JPanel {
	JTextField srpLength;
	NumberFormat formatter = new DecimalFormat("#0.0");   
	public SRPControl(final ControlPanel parent) {
		setPreferredSize(new Dimension(200,50));
		setLayout(new BorderLayout());
		srpLength = new JTextField();
		setSRPLength(parent.viewer.minLength);
		srpLength.setEditable(false);
		add(srpLength,BorderLayout.CENTER);
		ActionListener increase = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.incrementSRPLength();
			}
			
		};
		ActionListener decrease = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.decrementSRPLength();
			}
			
		};
		UpDownButtons updown = new UpDownButtons(increase,decrease);
		add(updown,BorderLayout.EAST);
	}
	public void setSRPLength(double minSRPLength) {
		srpLength.setText(formatter.format(minSRPLength) + " seconds");
	}
}
class DeviationControl extends JPanel {
	JTextField deviation;
	NumberFormat formatter = new DecimalFormat("#0.0");   
	public DeviationControl(final ControlPanel parent) {
		setPreferredSize(new Dimension(200,50));
		setLayout(new BorderLayout());
		deviation = new JTextField();
		setDev(parent.viewer.maxDev);
		deviation.setEditable(false);
		add(deviation,BorderLayout.CENTER);
		ActionListener increaseDev = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.incrementMaxDev();
			}
			
		};
		ActionListener decreaseDev = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.decrementMaxDev();
			}
			
		};
		UpDownButtons updown = new UpDownButtons(increaseDev,decreaseDev);
		add(updown,BorderLayout.EAST);
	}
	public void setDev(double maxDev) {
		deviation.setText(formatter.format(maxDev/10) + " grams");
	}
}
class UpDownButtons extends JPanel {
	static BufferedImage upArrow,downArrow;
	static {
		upArrow = new BufferedImage(50,25,BufferedImage.TYPE_INT_RGB);
		Graphics g = upArrow.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0,0,50,25);
		g.setColor(Color.BLACK);
		g.fillPolygon(new int[]{10,25,40}, new int[]{20,5,20},3);
		
		downArrow = new BufferedImage(50,25,BufferedImage.TYPE_INT_RGB);
		g = downArrow.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0,0,50,25);
		g.setColor(Color.BLACK);
		g.fillPolygon(new int[]{10,25,40}, new int[]{5,20,5},3);
	}
	public UpDownButtons(ActionListener up, ActionListener down) {
		setLayout(new GridLayout(2,1));
		JButton upButton = new BasicArrowButton(BasicArrowButton.NORTH);
		JButton downButton = new BasicArrowButton(BasicArrowButton.SOUTH);
		upButton.addActionListener(up);
		downButton.addActionListener(down);
		add(upButton);
		add(downButton);
	}
}
