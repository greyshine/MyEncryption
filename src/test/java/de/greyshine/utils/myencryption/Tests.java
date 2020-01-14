package de.greyshine.utils.myencryption;

import javax.sound.midi.Soundbank;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class Tests {

	@Test
	@Ignore
	public void dev() {
		
		System.out.println( Byte.MIN_VALUE );
		System.out.println( Byte.MAX_VALUE );
		
	}
	
	@Test
	public void testV1() {
		
		final Encrypter v1e = EncrypterFactory.get( V1EncrypterImpl.NAME );
		
		final String sentence = "This is super nice! But é or °AÂzZ";
		final String pwd = "pWd!";
		
		
		String encrypted = v1e.encrypt( sentence , pwd );
		System.out.println( "ENC: "+ encrypted );
		
		String decrypted = v1e.decrypt( encrypted , pwd );
		System.out.println( "DEC: "+ decrypted );
		
		Assert.assertEquals( decrypted, sentence );
		
		encrypted = v1e.encrypt( sentence , null );
		System.out.println( encrypted );
		decrypted = v1e.decrypt( encrypted , null );
		Assert.assertEquals( decrypted, sentence );
	}
	
	@Test
	@Ignore
	public void testV2() {
		
		final Encrypter v2e = EncrypterFactory.get( V2EncrypterImpl.NAME );
		
		final String sentence = "This is super nice! But é or °AÂzZ";
		final String pwd = "pWd!";
		
		
		String encrypted = v2e.encrypt( sentence , pwd );
		
		System.out.println( "ENC: "+ encrypted );
		
		String decrypted = v2e.decrypt( encrypted , pwd );
		System.out.println( "DEC: "+ decrypted );
		
		Assert.assertEquals( decrypted, sentence );
		
		encrypted = v2e.encrypt( sentence , null );
		System.out.println( encrypted );
		decrypted = v2e.decrypt( encrypted , null );
		Assert.assertEquals( decrypted, sentence );
	}
	
}
