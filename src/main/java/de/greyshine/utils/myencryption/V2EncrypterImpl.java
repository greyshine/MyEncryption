package de.greyshine.utils.myencryption;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class V2EncrypterImpl extends AbstractEncrypter {
	
	public static final String NAME = "v2";
	
	public V2EncrypterImpl() {
		super(NAME);
	}

	@Override
	public void decrypt(InputStream in, OutputStream out, String password) throws IOException {
		throw new IllegalStateException("not yet implemented"); 
	}

	@Override
	public void encrypt(InputStream in, OutputStream out, String password) throws IOException {
		throw new IllegalStateException("not yet implemented");
	} 
}
