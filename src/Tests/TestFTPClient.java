package Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import FTPZybo.FTPClient;

/**
 * 
 * This test assumes the user has access to a server with the parameters given in FTP signature,
 * and furthermore, a file with the given name.
 *
 */
public class TestFTPClient {
	FTPClient ftp = new FTPClient("localhost", "user", "qwerty");
	String testFileName = "testting.txt";
	
	@Test
	public void testList() {
		boolean condition;
		ArrayList<String> als = new ArrayList<String>();
		als = ftp.list();
		condition = als.isEmpty();
		assertFalse(condition);
	}
	
	@Test
	public void testRetrTrue() {
		boolean condition;
		condition = ftp.retr(testFileName);
		assertTrue(condition);
	}
	
	@Test
	public void testRetrFalse() {
		boolean condition;
		condition = ftp.retr("wrongName.txt");
		assertFalse(condition);
	}
	
	@Test
	public void testRetrFileCorrectly() {
		boolean condition;
		ftp.retr(testFileName);
		File f = new File(testFileName);
		condition = f.exists();
		assertTrue(condition);
	}
	
	
}
