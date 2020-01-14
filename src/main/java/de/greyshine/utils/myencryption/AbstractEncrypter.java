package de.greyshine.utils.myencryption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public abstract class AbstractEncrypter implements Encrypter {
	
	public static final Random RANDOM = new Random(); 
	
	public final String name;
	public final int alphabetSize;
	public final int resultSize;
	
	public AbstractEncrypter(String name) {
		this( name, 16, 2 );
	}
	
	public AbstractEncrypter(String name, int alphabetSize, int resultSize) {
		this.name = name;
		this.alphabetSize = alphabetSize;
		this.resultSize = resultSize;
	}

	@Override
	public String encrypt(String text, String password) {
		
		password = normalizePassword(password);
		if ( text == null || password == null ) { return text; }
		
		final StringBuffer sb = new StringBuffer();
		final OutputStream os = new OutputStream() {
			
			@Override
			public void write(int b) throws IOException {
				
				String v = Integer.toString(b + 128, alphabetSize);
				while( v.length() < resultSize ) {
					v = "0"+v;
				}
				
				sb.append( RANDOM.nextBoolean() ? v.toLowerCase() : v.toUpperCase() );
			}
		};
		
		try {
			
			encrypt( new ByteArrayInputStream(text.getBytes()), os, password);
		
		} catch (IOException e) {
			throw new IllegalStateException( e );
		}
		
		return sb.toString();
	}

	@Override
	public String decrypt(String text, String password) {
		
		password = normalizePassword(password);
		if ( text == null || password == null ) { return text; }
		
		text = text.toLowerCase();
		
		if ( text.length() % resultSize != 0 ) { throw new IllegalArgumentException("illegal amount of characters"); }
		if ( !text.matches( "[0-9a-z]+" ) ) { throw new IllegalArgumentException("illegal characters contained"); }
		
		final String txt = text; 
		
		final InputStream is = new InputStream() {
			
			final int txtSize = txt.length();
			int idx = 0;
			
			@Override
			public int available() throws IOException {
				return idx < txtSize ? resultSize : 0;
			}

			@Override
			public int read() throws IOException {
				
				if ( available() < 1 ) { return -1; }
				
				final String t = txt.substring( idx, idx+resultSize );
				idx+=resultSize;
				
				int v = Integer.parseInt( t, alphabetSize );
				return v-128;
			}
		};
		
		
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			
			decrypt( is, baos, password );
		
		} catch (IOException e) {
			throw new IllegalStateException( e );
		}
		
		return new String( baos.toByteArray() );
	}
	
	protected void stream(InputStream in, OutputStream out) throws IOException {
		for( int s = in.available(); s > 0; s = in.available() ) {
			while( s-- > 0 ) {
				out.write( in.read() );
			}
		}
		out.flush();
	}
	
	public final String normalizePassword(String password) {
		return password == null || password.trim().isEmpty() ? null : password.trim();
	}
	
	private static int[] sToChars(String string) {
		
		if ( string == null ) { return new int[0]; }
		
		final char[] chars = string.toCharArray();
		final int[] ints = new int[chars.length];
		for(int i=0, l=chars.length; i<l; i++) {
			ints[i] = chars[i];
		}
		return ints;
	}
	
	class EndlessProvider {
		
		final int[] is;
		int idx = 0;
		
		public EndlessProvider( int[] is ) {
			this.is = is;
		}
		
		public EndlessProvider( String string ) {
			this( sToChars(string) );
		}

		int next() {
			if ( is.length < 1 ) { return -1; }
			idx = idx >= is.length ? 0 : idx;
			return is[ idx++ ];
		}
	}
}
