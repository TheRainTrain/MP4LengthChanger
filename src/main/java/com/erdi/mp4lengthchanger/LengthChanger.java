package com.erdi.mp4lengthchanger;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import com.erdi.mp4lengthchanger.awt.IntField;
import com.erdi.mp4lengthchanger.mp4.MP4;

public class LengthChanger implements Runnable {
	private MP4 mp4;
	private JFrame frame;
	private File file;

	public void run() {
		frame = new JFrame("MP4 Length Changer");
		
		frame.setSize(310, 120);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		frame.setResizable(false);
		
		IntField durationField = new IntField();
		IntField timescaleField = new IntField();

		durationField.setTemplateText("Duration");
		timescaleField.setTemplateText("Timescale");

		JTextArea lengthField = new JTextArea();
		lengthField.setBackground(null);
		
		new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(mp4 == null) {
					lengthField.setText("Length: N/A");
				} else {
					mp4.setDuration(durationField.getValue());
					mp4.setTimescale(timescaleField.getValue());
					
					lengthField.setText("Length: " + mp4.getLength());
				}
			}
		}).start();
		
		JButton saveButton = new JButton("Save To");
		saveButton.setEnabled(false);
		
		JButton selectFileButton = new JButton("Select MP4");
		
		selectFileButton.addActionListener(event -> {
			JFileChooser chooser = new JFileChooser();
			
			chooser.setFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "MP4 Files (*.mp4)";
				}
				
				@Override
				public boolean accept(File file) {
					return file.isDirectory() || file.getName().toLowerCase().endsWith(".mp4");
				}
			});
			
			chooser.showOpenDialog(null);
			
			file = chooser.getSelectedFile();
			if(file != null) {
				JDialog dialog = new JDialog((Frame)null, "Loading MP4");
				
				dialog.add(new JLabel("Loading MP4..."));
				dialog.setUndecorated(true);
				dialog.pack();
				dialog.setLocationRelativeTo(frame);
				
				new Thread(() -> {
					dialog.setVisible(true);
					try {
						selectFileButton.setEnabled(false);
						mp4 = MP4.readMP4(file);
						saveButton.setEnabled(true);

						durationField.setText(Integer.toString(mp4.getRawDuration()));
						timescaleField.setText(Integer.toString(mp4.getTimescale()));
					} catch(Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error occurred! Stack printed.");
					}

					dialog.setVisible(false);
					selectFileButton.setEnabled(true);
					dialog.dispose();
				}).start();
			}
		});
		
		saveButton.addActionListener(event -> {
			JFileChooser chooser = new JFileChooser();
			
			chooser.setFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "MP4 Files (*.mp4)";
				}
				
				@Override
				public boolean accept(File file) {
					return file.isDirectory() || file.getName().toLowerCase().endsWith(".mp4");
				}
			});
			
			chooser.showSaveDialog(null);
			
			File chosen = chooser.getSelectedFile();
			
			if(chosen != null) {
				JDialog dialog = new JDialog((Frame)null, "Saving MP4");
				
				dialog.add(new JLabel("Saving MP4..."));
				dialog.setUndecorated(true);
				dialog.pack();
				dialog.setLocationRelativeTo(frame);
				
				new Thread(() -> {
					dialog.setVisible(true);
					frame.setEnabled(false);
					
					try {
						mp4.saveToFile(chosen);
					} catch(Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error occurred! Stack printed.");
					}
					
					frame.setEnabled(true);
					dialog.setVisible(false);
					dialog.dispose();
				}).start();
			}
		});

		frame.add(durationField);
		frame.add(timescaleField);
		frame.add(selectFileButton, FlowLayout.CENTER);
		frame.add(saveButton);
		frame.add(lengthField);

		frame.setVisible(true);
	}
}
