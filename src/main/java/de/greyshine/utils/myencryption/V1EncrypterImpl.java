package de.greyshine.utils.myencryption;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class V1EncrypterImpl extends AbstractEncrypter {
	
	public static final String NAME = "v1"; 
	
	private final static int ALPHABET_SIZE = 26;
	private final static int MAX_PREFILLED_ZERO = 4;
	
	private final int[] PRIMES = { 13, 3, 7, 17, 1 }; 
	
	public V1EncrypterImpl() {
		super(NAME, ALPHABET_SIZE, MAX_PREFILLED_ZERO);
	}
	
	@Override
	public void encrypt( InputStream in, OutputStream out, String password ) throws IOException {
		
		if ( in == null || out == null ) { return; }
		password = normalizePassword( password );
		
		if ( password == null ) {
			stream( in, out );
			return;
		}
		
		final EndlessProvider pwdChars = new EndlessProvider( password );
		final EndlessProvider primes = new EndlessProvider( PRIMES );
		int count = 0;
		
		while( in.available() > 0 ) {
			
			final int npc = npc( ++count, pwdChars, primes );
			out.write( in.read() ^ npc );
		}
		
		out.flush();
	}
	
	@Override
	public void decrypt( InputStream in, OutputStream out, String password ) throws IOException {
		
		if ( in == null || out == null ) { return; }
		password = normalizePassword( password );
		
		if ( password == null ) {
			stream( in, out );
			return;
		}
		
		final EndlessProvider pwdChars = new EndlessProvider( password );
		final EndlessProvider primes = new EndlessProvider( PRIMES );
		int count = 0;
		
		while( in.available() > 0 ) {
			
			final int npc = npc( ++count, pwdChars, primes );
			out.write( in.read() ^ npc );
		}
		
		out.flush();
	}
	
	private static int npc(int count, EndlessProvider cp, EndlessProvider pp) {
		
		final int p = pp.next();
		if ( count - 1 > p ) { count = 1; }
		return cp.next() * count * p;
	}
}
