package org.levi;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

//import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Driver {

	private static JTextArea textArea;
	private JFrame frame;
	private JTextField textField;
	private File f;
	private File t;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Driver window = new Driver();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Driver() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("StormRunner Log Search !!");
		frame.setBounds(100, 100, 795, 800);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblName = new JLabel("Search String");
		lblName.setBounds(65, 31, 92, 14);
		frame.getContentPane().add(lblName);

		textField = new JTextField();
		textField.setBounds(150, 28, 570, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel folderLabel = new JLabel("Source: ");
		folderLabel.setBounds(65, 65, 92, 14);
		frame.getContentPane().add(folderLabel);

		final JLabel folderName = new JLabel("No File/Folder Selected !!");
		folderName.setBounds(150, 65, 500, 14);
		frame.getContentPane().add(folderName);

		JButton fileButton = new JButton("Select");
		fileButton.setBounds(150, 90, 110, 23);
		frame.getContentPane().add(fileButton);

		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileChooser fC = new FileChooser();
				f = fC.chooseFile(JFileChooser.FILES_AND_DIRECTORIES);
				folderName.setText(f.toString());
			}
		});

		JLabel tFolderLabel = new JLabel("Target: ");
		tFolderLabel.setBounds(65, 135, 92, 14);
		frame.getContentPane().add(tFolderLabel);

		final JLabel tFolderName = new JLabel("No Folder Selected !!");
		tFolderName.setBounds(150, 135, 500, 14);
		frame.getContentPane().add(tFolderName);

		JButton tFileButton = new JButton("Select a folder");
		tFileButton.setBounds(150, 160, 150, 23);
		frame.getContentPane().add(tFileButton);

		tFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileChooser fC = new FileChooser();
				t = fC.chooseFile(JFileChooser.DIRECTORIES_ONLY);
				tFolderName.setText(t.toString());
			}
		});

		JButton btnSubmit = new JButton("Start Scan");
		// btnSubmit.setBackground(Color.BLUE);
		// btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(65, 200, 650, 40);
		frame.getContentPane().add(btnSubmit);

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				redirectSystemStreams();
				if (textField.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Enter search key !!");
				else if (f == null)
					JOptionPane.showMessageDialog(null, "Select a file/folder !!");
				else
					try {
						if (f.toString().endsWith(".tar.gz"))
							Tar.decompress(f.toString(), f.getParentFile());
						int count = new FileList(t.toString() + "\\Output.log").listFiles(f.getParentFile(),
								textField.getText());
						System.out.println("\n Total Number of hits" + count);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});

		final JLabel note = new JLabel("Run-Time Logs");
		note.setBounds(10, 250, 500, 14);
		frame.getContentPane().add(note);

		textArea = new JTextArea();
		JScrollPane sp = new JScrollPane(textArea);
		sp.setBounds(10, 270, 770, 500);
		frame.getContentPane().add(sp);

	}

	private static void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textArea.append(text);
			}
		});
	}

	static private void redirectSystemStreams() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}
}