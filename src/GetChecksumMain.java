// Author: Benjamin Harris
// Date: July 15, 2020
// class to get a checksum from a file and write the string to a text file

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.security.MessageDigest;
import javax.swing.JFileChooser;

public class GetChecksumMain {

	public static void main(String[] args) {

		String dir = getDir();

		try {

			// Define file for which the checksum is created
			File file;
			JFileChooser fc = new JFileChooser(dir);
			fc.setAcceptAllFileFilterUsed(true);
			int result = fc.showDialog(null, "Load File");
			if (result == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();

				// Use MD5 algorithm
				MessageDigest md5Digest = MessageDigest.getInstance("MD5");

				// Get the checksum
				String checksum = getFileChecksum(md5Digest, file);

				// Put the checksum in a text file
				saveFile(checksum, dir);

			} // end if

		} catch (Exception e) {

		} // end try-catch

	} // end main()

	private static String getDir () {
		File f = null;
		String dirPath = null;
		try {
			f = new File(GetChecksumMain.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
			dirPath = f.toString();
		} catch (Exception e) {
		} // end try-catch
		return dirPath;
	} // end getDir()

	private static String getFileChecksum(MessageDigest digest, File file) throws IOException {

		// open file input stream for reading file content
		FileInputStream fis = new FileInputStream(file);

		// create byte array to hold input stream
		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		// read file data into byte array and update message digest
		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		} // end while

		// close input stream
		fis.close();

		// get the bytes from the 'digest' to create the hash string
		byte[] bytes = digest.digest();
		//Convert bytes (decimal format) to hexadecimal string
		StringBuilder sb = new StringBuilder();
		for(int i=0; i< bytes.length ;i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		} // end for

		//return complete hash
		return sb.toString();

	} // end getFileChecksum()

 	public static void saveFile (String st, String path) {
 
 		File file;

 		try {

			JFileChooser fc = new JFileChooser(path);
			fc.setAcceptAllFileFilterUsed(true);
			int result = fc.showDialog(null, "Save File");
			if ((result == JFileChooser.APPROVE_OPTION)) file = fc.getSelectedFile();
			else return;

			if (file.getParentFile().exists()) {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				out.println(st);
				out.close();
			} // end if
			
		} catch (Exception e) {
			
		} // end try-catch
 		
	} // end saveFile()

} // end class GetChecksumMain
