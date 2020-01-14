package de.greyshine.utils.myencryption;

import java.util.HashMap;
import java.util.Map;

public class EncrypterFactory {

	public static final String V1 = V1EncrypterImpl.NAME;
	public static final String V2 = V2EncrypterImpl.NAME;
	
	private static final Map<String,Encrypter> ES = new HashMap<>();
	
	static {
		ES.put( V1EncrypterImpl.NAME, new V1EncrypterImpl() );
		ES.put( V2EncrypterImpl.NAME, new V2EncrypterImpl() );
	}
	
	public static Encrypter get(String name) {
		
		final Encrypter e = ES.get( name );
		
		if ( e == null ) {
			throw new IllegalArgumentException("No encrypter found for name: "+ name);
		}
		
		return e;
	}
}
