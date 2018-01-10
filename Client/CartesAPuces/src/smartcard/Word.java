package smartcard;

public class Word {
	private byte[] bytes;
	
	public Word() {
		bytes = new byte[4];
	}
	
	public Word(byte[] bytes) {
		if(bytes.length == 4) {
			this.bytes = bytes;	
		}
	}
	
	public Word(byte b1, byte b2, byte b3, byte b4) {
		this.bytes = new byte[]{b1, b2, b3, b4};
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	public static Word[] byteArrayToWordArray(byte[] bytes) throws WordSizeException {
		if(bytes.length % 4 != 0) {
			throw new WordSizeException("bytes n'est pas de taille %4");
		}
		
		Word[] words = new Word[bytes.length/4];
		for(int i=0 ; i<words.length ; i++) {
			words[i] = new Word(bytes[4*i], bytes[4*i+1], bytes[4*i+2], bytes[4*i+3]);
		}
		
		return words;
	}
}
