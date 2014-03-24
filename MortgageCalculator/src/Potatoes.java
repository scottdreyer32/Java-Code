import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import java.text.*;




@SuppressWarnings("serial")
public class Potatoes extends JPanel{
	private JFormattedTextField principal, interest, length, payment;
	private JLabel princLabel, interLabel,lengthLabel,payLabel;
	
	
	public Potatoes(){
		JPanel labels = new JPanel(new GridLayout(0,1));
		JPanel fields = new JPanel(new GridLayout(0,1));
		
		princLabel = new JLabel("Loan Amount");
		interLabel = new JLabel("Interest Rate");
		lengthLabel = new JLabel("Loan Term");
		payLabel = new JLabel("Monthly Payment");
		
		principal = new JFormattedTextField(NumberFormat.getNumberInstance());
		principal.setColumns(10);
		
		interest = new JFormattedTextField(NumberFormat.getNumberInstance());
		interest.setColumns(10);
		//interest.addPropertyChangeListener("value", this);
		
		length = new JFormattedTextField(NumberFormat.getNumberInstance());
		length.setColumns(10);
		length.addActionListener(new TempListener());
		
		payment = new JFormattedTextField(NumberFormat.getCurrencyInstance());
		payment.setEditable(false);
		
		labels.add(princLabel);
		labels.add(interLabel);
		labels.add(lengthLabel);
		labels.add(payLabel);
		
		fields.add(principal);
		fields.add(interest);
		fields.add(length);
		fields.add(payment);
		
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));        
		add(labels, BorderLayout.CENTER);        
		add(fields, BorderLayout.LINE_END);
			
		
	}
	private class TempListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String LoanAmt = principal.getText();
			String intRate = interest.getText();
			String stlength = length.getText();
			
					
			double loanAmount, interestRate;
			loanAmount = Double.parseDouble(LoanAmt.replaceAll(",", ""));
			interestRate = Double.parseDouble(intRate);
			int termYears;
			termYears = Integer.parseInt(stlength);
			
			interestRate /=100.0;
			double monthlyRate = interestRate/12.0;
			int termMonths = termYears * 12;
			
			double monthlyPayment = 
			         (loanAmount*monthlyRate) / 
			            (1-Math.pow(1+monthlyRate, -termMonths));
			NumberFormat nf = NumberFormat.getCurrencyInstance();
									       
			payment.setText(nf.format(monthlyPayment));
		}

	}
	
	public static void main(String[] args) {        //Create and set up the window.        
		JFrame frame = new JFrame("Mortgage Calculator");        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         //Add contents to the window.        
		frame.add(new Potatoes());         //Display the window.        
		frame.pack();       
		frame.setVisible(true);    
		}
	}



