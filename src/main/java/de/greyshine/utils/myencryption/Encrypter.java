package de.greyshine.utils.myencryption;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Encrypter {

	String encrypt( String text, String password );
	String decrypt( String text, String password );
	
	void decrypt( InputStream in, OutputStream out, String password ) throws IOException;
	void encrypt( InputStream in, OutputStream out, String password ) throws IOException;
	
}
