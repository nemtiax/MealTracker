import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class UIFrame extends JFrame {
	ArrayList<SubjectDrawer> drawers = new ArrayList<SubjectDrawer>();
	GraphViewer gv;
	ControlPanel cont;
	String experimentName = "";
	public UIFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		gv = new GraphViewer(drawers,800,800);
		this.add(gv,BorderLayout.CENTER);
		cont = new ControlPanel(gv);
		this.add(cont,BorderLayout.WEST);
		buildMenu();
		setTitle("No Experiment Loaded");
		pack();
		setVisible(true);
		
	}
	public void buildMenu() {
		JMenuBar jmb = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem load = new JMenuItem("Load...");
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadDialog();
			}
		
		});
		JMenuItem save = new JMenuItem("Export");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
			
		});
		file.add(load);
		file.add(save);
		jmb.add(file);
		this.setJMenuBar(jmb);
		
	}
	public void loadDialog() {
		JFileChooser fc = new JFileChooser(new File("."));
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println("LOADING " + file);
            loadFile(file);
		}
	}
	public void loadFile(File f) {
		drawers = new ArrayList<SubjectDrawer>();
		ArrayList<Subject> subjects;
		try {
			experimentName = f.getName().substring(0,f.getName().indexOf("_"));
			setTitle("Viewing " + experimentName);
			subjects = RawDataParser.parse(f);
			for(Subject s : subjects) {
				SubjectDrawer d = new SubjectDrawer(s,800,800);
				drawers.add(d);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gv.updateDrawers(drawers);
		cont.updateSubjectName();
	}
	public void save() {
		gv.drawAll();
		for(SubjectDrawer d : drawers) {
			Subject subj = d.getSubject();
			saveMeals(new File(experimentName + "_" + subj.getName() + "_meals.csv"),subj);
			saveImage(new File(experimentName + "_" + subj.getName() + "_chart.PNG"),d);
		}
	}
	public void saveImage(File f, SubjectDrawer d) {
		try {
			d.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveMeals(File f, Subject subj) {
		NumberFormat formatter = new DecimalFormat("#0.0");  
		try {
			FileWriter out = new FileWriter(f);

			double totalMealSize = 0;
			for(Meal m : subj.getMeals()) {
				totalMealSize+=m.getSize();
			}
			out.write("Total Meal Size," + formatter.format(totalMealSize/10) +"\n");
			out.write("Total Scale Decrease," + formatter.format((subj.getStartWeight() - subj.getEndWeight())/10) + "\n\n");
			out.write("Start Time,End Time,Size\n");
			for(Meal m : subj.getMeals()) {
				out.write(m.getStartTime() +"," + m.getEndTime() + "," + formatter.format(m.getSize()/10)+"\n");
			}
			out.flush();
			out.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
