package org.levi;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileChooser {

	public File chooseFile(int mode) {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(new File("/Users/nitinaggarwal"));
		jFileChooser.setFileSelectionMode(mode);
		int result = jFileChooser.showOpenDialog(new JFrame());

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jFileChooser.getSelectedFile();
			return selectedFile;
		}
		return null;
	}

}