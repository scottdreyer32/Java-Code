package all;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;

@SuppressWarnings({ "serial", "unused" })
public class GUI extends JFrame {
	@SuppressWarnings("rawtypes")
	private JComboBox hours, minutes, seconds;
	JButton startButton, stopButton, contButton;
	ArrayList<Integer> boxHours, minSec;
	private JLabel hourLabel, minuteLabel, secondsLabel, time;
	CountDownTimer myTimer;
	JFrame frame, frame2;
	JPanel fields, functions, everyThing, timeKeeper;
	Timer timer;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GUI() {
		startButton = new JButton("Start");
		stopButton = new JButton("Stop");
		contButton = new JButton("Continue");
		boxHours = new ArrayList<Integer>();
		for (int i = 0; i < 100; ++i) {
			boxHours.add(i);
		}
		minSec = new ArrayList<Integer>();
		for (int i = 0; i <= 59; ++i) {
			minSec.add(i);
		}

		time = new JLabel(" ");
		hours = new JComboBox(boxHours.toArray());
		minutes = new JComboBox(minSec.toArray());
		seconds = new JComboBox(minSec.toArray());
		timeKeeper = new JPanel(new FlowLayout());
		functions = new JPanel(new FlowLayout());
		Dimension dim2 = new Dimension(400, 50);
		timeKeeper.setPreferredSize(dim2);
		timeKeeper.setBackground(Color.DARK_GRAY);
		Dimension dim = new Dimension(400, 80);

		fields = new JPanel(new FlowLayout());
		everyThing = new JPanel(new BorderLayout());
		hourLabel = new JLabel("hrs");
		minuteLabel = new JLabel("min");
		secondsLabel = new JLabel("sec");
		hourLabel.setForeground(Color.white);
		minuteLabel.setForeground(Color.white);
		secondsLabel.setForeground(Color.white);

		hours.setFont(new Font("Arial Black", Font.PLAIN, 15));
		minutes.setFont(new Font("Arial Black", Font.PLAIN, 15));
		seconds.setFont(new Font("Arial Black", Font.PLAIN, 15));
		startButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		contButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		stopButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		timeKeeper.setPreferredSize(dim);
		time.setFont(new Font("OCR-A II", Font.BOLD, 50));
		time.setForeground(Color.WHITE);
		functions.setBackground(Color.DARK_GRAY);
		fields.setPreferredSize(dim2);
		fields.setBackground(Color.DARK_GRAY);
		fields.add(hours);
		fields.add(hourLabel);
		fields.add(minutes);
		fields.add(minuteLabel);
		fields.add(seconds);
		fields.add(secondsLabel);
		fields.add(startButton);
		functions.add(stopButton);
		functions.add(contButton);
		timeKeeper.add(time);
		everyThing.add(timeKeeper, BorderLayout.NORTH);
		everyThing.add(fields, BorderLayout.CENTER);
		everyThing.add(functions, BorderLayout.SOUTH);

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(everyThing);
		frame.pack();
		frame.setVisible(true);
		addStartButtonListener(new TempListener());
		addStopButtonListener(new StopListener());
		addContinueButtonListener(new ContinueListener());
		contButton.setEnabled(false);
		stopButton.setEnabled(false);

	}

	public void addStartButtonListener(ActionListener startButtonListener) {
		startButton.addActionListener(startButtonListener);
	}

	public void addStopButtonListener(ActionListener stopButtonListener) {
		stopButton.addActionListener(stopButtonListener);
	}

	public void addContinueButtonListener(ActionListener contButtonListener) {
		contButton.addActionListener(contButtonListener);
	}

	private class TempListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			contButton.setEnabled(true);
			int numHours = (int) hours.getSelectedItem();
			int numMinutes = (int) minutes.getSelectedItem();
			int numSeconds = (int) seconds.getSelectedItem();

			myTimer = new CountDownTimer(numHours, numMinutes, numSeconds);
			time.setText(myTimer.toString());
			int delay = 1000; // milliseconds=1sec

			ActionListener taskPerformer = new ActionListener() {

				public void actionPerformed(ActionEvent evt) {

					myTimer.dec();
					time.setText(myTimer.toString());
					if (myTimer.isFinished() == true) {
						timer.stop();
						startButton.setEnabled(true);
					}
				}
			};
			timer = new Timer(delay, taskPerformer);
			timer.start();

		}

	}

	private class StopListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (timer.isRunning()) {
				timer.stop();
				startButton.setEnabled(true);
				contButton.setEnabled(true);
			}

		}
	}

	private class ContinueListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (myTimer.isFinished() == false) {
				timer.start();
				startButton.setEnabled(false);
			}
		}
	}

	private void redraw() {

	}

	public static boolean isNumeric(String str) {
		try {
			int d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		new GUI();
	}
}
