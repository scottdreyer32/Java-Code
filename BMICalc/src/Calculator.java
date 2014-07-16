import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


@SuppressWarnings("serial")
public class Calculator extends JPanel{
	@SuppressWarnings("rawtypes")
	private JComboBox heightFeet,heightInch, weight, heightMet, weightMet;
	private JLabel heightLabel, weightLabel,heightMetLabel,weightMetLabel,feetLabel,inchLabel,poundLabel,centimeterLabel,kiloLabel, bmiLabel;
	private JLabel bmiAnswerLabel,bmiAnswerLabelM,bmiLabelM;
	private JLabel message, messageMet, categoryEng,categoryMet;
	private ArrayList<Integer> feetCount,inchCount,cmCount,kgCount,lbCount;
	@SuppressWarnings("unused")
	private JTabbedPane all;
	private JButton calcMet,calcEng;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Calculator(){
//Instantiate Panels
		calcMet = new JButton("Calculate");
		calcMet.setSize(getPreferredSize());
		calcEng = new JButton("Calculate");
		calcEng.setSize(getPreferredSize());
		JPanel labels = new JPanel(new GridLayout(4,2));
		JPanel labelsTwo = new JPanel(new GridLayout(4,2));
		JPanel weightCombo = new JPanel(new FlowLayout());
		JPanel heightCombo = new JPanel(new FlowLayout());
		JPanel weightComboM = new JPanel(new FlowLayout());
		JPanel heightComboM = new JPanel(new FlowLayout());
		JPanel results = new JPanel(new BorderLayout());
		JPanel resultsMet = new JPanel(new BorderLayout());
		JTabbedPane all = new JTabbedPane();
		
//Set up Labels				
		heightLabel = new JLabel("Height in Inches");
		weightLabel = new JLabel("Weight in Pounds");
		heightMetLabel = new JLabel("Height in Meters");
		weightMetLabel = new JLabel("Weight in Kilograms");
		bmiLabel= new JLabel("Your BMI is:");
		bmiAnswerLabel = new JLabel("");
		bmiLabelM= new JLabel("Your BMI is:");
		bmiAnswerLabelM = new JLabel("");
		feetLabel = new JLabel("ft");
		inchLabel = new JLabel("in");
		poundLabel = new JLabel("lbs");
		centimeterLabel = new JLabel("cm");
		kiloLabel=  new JLabel("kg");
		message = new JLabel("Your category is:");
		categoryEng = new JLabel("");
		messageMet = new JLabel("Your category is:");
		categoryMet = new JLabel("");
		
//Set up comboboxes
		// heightFeet,heightInch, weight, heightMet, weightMet
		
		feetCount = new ArrayList<Integer>();
		for (int i =4;i<8;i++){
			feetCount.add(i);
		}
		heightFeet = new JComboBox(feetCount.toArray());
		
		inchCount = new ArrayList<Integer>();
		for (int i =0;i<12;i++){
			inchCount.add(i);
		}
		heightInch = new JComboBox(inchCount.toArray());
		
		lbCount = new ArrayList<Integer>();
		for (int i=60;i<340;i++){
			lbCount.add(i);
		}
		weight = new JComboBox(lbCount.toArray());
		
		cmCount = new ArrayList<Integer>();
		for (int i =120;i<250;i++){
			cmCount.add(i);
		}
		heightMet = new JComboBox(cmCount.toArray());
		
		kgCount = new ArrayList<Integer>();
		for (int i =22;i<156;i++){
			kgCount.add(i);
		}
		weightMet = new JComboBox(kgCount.toArray());
//Combine labels and boxes to add to panels
		heightCombo.add(heightFeet);
		heightCombo.add(feetLabel);
		heightCombo.add(heightInch);
		heightCombo.add(inchLabel);
		weightCombo.add(weight);
		weightCombo.add(poundLabel);
		
		heightComboM.add(heightMet);
		heightComboM.add(centimeterLabel);
		weightComboM.add(weightMet);
		weightComboM.add(kiloLabel);
		
//Add panels to grid layout
		labels.add(heightLabel);
		labels.add(heightCombo);
		labels.add(weightLabel);
		labels.add(weightCombo);
		labels.add(bmiLabel);
		labels.add(bmiAnswerLabel);
		labels.add(message);
		labels.add(categoryEng);
		
		labelsTwo.add(heightMetLabel);
		labelsTwo.add(heightComboM);
		labelsTwo.add(weightMetLabel);
		labelsTwo.add(weightComboM);
		labelsTwo.add(bmiLabelM);
		labelsTwo.add(bmiAnswerLabelM);
		labelsTwo.add(messageMet);
		labelsTwo.add(categoryMet);
		
//Action listeners		
		calcEng.addActionListener(new calcEngListener());
		calcMet.addActionListener(new calcMetListener());
		
//Set to one panel		
		results.add(labels,BorderLayout.NORTH);
		results.add(calcEng,BorderLayout.PAGE_END);
		
//Set to one Panel		
		resultsMet.add(labelsTwo,BorderLayout.CENTER);
		resultsMet.add(calcMet,BorderLayout.SOUTH);
//Add it all to frame		
		all.add(results, "English");
		all.add(resultsMet, "Metric");

		add(all);
		
	}
	private class calcMetListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			int centiMeters = (int)heightMet.getSelectedItem();
			int kilograms = (int)weightMet.getSelectedItem();	
			int meters = centiMeters / 100;
			int metSquar = meters * meters;
			int bmi = kilograms/metSquar;
			System.out.println(centiMeters +"");
			System.out.println(kilograms +"");
			System.out.println(bmi +"");
			if(bmi<=18){
				categoryMet.setText("Underweight");
				bmiAnswerLabelM.setText(bmi+"");
			}else if(bmi>18&&bmi<25){
				categoryMet.setText("Normal");
				bmiAnswerLabelM.setText(bmi+"");
			}else if(bmi>=25&&bmi<30){
				categoryMet.setText("Overweight");
				bmiAnswerLabelM.setText(bmi+"");
			}else{
				categoryMet.setText("Obese");
				bmiAnswerLabelM.setText(bmi+"");
			}
			}
			
		}

	
	private class calcEngListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			//BMI = ( Weight in Pounds / ( Height in inches x Height in inches ) ) x 703
			double pounds = (int)weight.getSelectedItem();
			double tallness = (int)heightFeet.getSelectedItem();
			tallness*=12;
			tallness+= (int)heightInch.getSelectedItem();
			tallness*= tallness;
			double conversion = pounds/tallness;
			System.out.println(conversion + "");
			int bmi = (int) (703 * conversion);
			
			System.out.println(""+bmi);
			System.out.println(""+tallness);
			System.out.println(""+pounds);

			if(bmi<=18){
				categoryEng.setText("Underweight");
				bmiAnswerLabel.setText(bmi+"");
			}else if(bmi>18&&bmi<25){
				categoryEng.setText("Normal");
				bmiAnswerLabel.setText(bmi+"");
			}else if(bmi>=25&&bmi<30){
				categoryEng.setText("Overweight");
				bmiAnswerLabel.setText(bmi+"");
			}else{
				categoryEng.setText("Obese");
				bmiAnswerLabel.setText(bmi+"");
			}
			
		}

	}
	
	public void addCalcMetButtonListener(ActionListener calcMetButtonListener) {
		calcMet.addActionListener(calcMetButtonListener);
	}
	public void addCalcEngButtonListener(ActionListener calcEngButtonListener) {
		calcEng.addActionListener(calcEngButtonListener);
	}
	
	public static void main(String[] args) {        //Create and set up the window.        
		JFrame frame = new JFrame("BMI Calculator");
		Dimension rect= new Dimension(350,250);
		frame.setPreferredSize(rect);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         //Add contents to the window.        
		frame.add(new Calculator());         //Display the window.        
		frame.pack();       
		frame.setVisible(true);  
		
		}
	
	}

