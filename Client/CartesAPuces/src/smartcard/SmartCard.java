package smartcard;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

public class SmartCard {

	private Card card;
	private CardChannel channel;
	private CardTerminal terminal;
	
	public SmartCard(CardTerminal terminal) throws CardException {
		this.terminal = terminal;
	}

	public void connect() throws CardException{
		card = terminal.connect("T=0");
		channel = card.getBasicChannel();
	}
	/**
	 * Teste le code PIN
	 * 
	 * @param pos
	 * @param PINcode
	 * @return le résultat de l'exécution
	 * @throws CardException
	 */
	public ResponseAPDU testPin(byte pos, Word PINcode) throws CardException {
		byte[] bytes = PINcode.getBytes();
		byte[] cmd = new byte[] { (byte) 0x00, (byte) 0x20, (byte) 0x00, pos, (byte) 0x04, bytes[0], bytes[1], bytes[2],
				bytes[3] };

		CommandAPDU commandAPDU = new CommandAPDU(cmd);
		ResponseAPDU responseAPDU = channel.transmit(commandAPDU);

		return responseAPDU;
	}

	/**
	 * Lis les zone mémoire de la carte
	 * 
	 * @param pos
	 * @param len : nombre de cases à lire
	 * @return les réponses des requêtes
	 * @throws CardException
	 */
	public ArrayList<ResponseAPDU> readCard(byte pos, int len) throws CardException {
		ArrayList<ResponseAPDU> responses = new ArrayList<ResponseAPDU>();
		
		byte posEnd = (byte) (pos + len - 1);
		if (pos < (byte) 0x10 || (pos > (byte) 0x1F && pos < (byte) 0x28) || (pos > (byte) 0x37)) {
			return null;
		}
		if (posEnd < (byte) 0x10 || (posEnd > (byte) 0x1F && posEnd < (byte) 0x28) || (posEnd > (byte) 0x37)) {
			return null;
		}
		
		for(int count=0 ; count<len ; count++) {
			responses.add(readCard((byte) (pos+count)));
		}

		return responses;
	}

	/**
	 * Lis la zone mémoire de la carte
	 * 
	 * @param pos
	 * @return le tableau de byte
	 * @throws CardException
	 */
	public ResponseAPDU readCard(byte pos) throws CardException {
		if (pos < (byte) 0x10 || (pos > (byte) 0x1F && pos < (byte) 0x28) || (pos > (byte) 0x37)) {
			return null;
		}
		CommandAPDU commandAPDU = new CommandAPDU((byte) 0x80, (byte) 0xBE, (byte) 0x00, pos, (byte) 0x04);
		ResponseAPDU responseAPDU = channel.transmit(commandAPDU);

		return responseAPDU;
	}

	/**
	 * Ecris dans la zone mémoire de la carte
	 * 
	 * @param pos
	 * @param data
	 *            : byte array à écrire
	 * @return résultat de l'exécution
	 * @throws CardException
	 */
	public ArrayList<ResponseAPDU> writeToCard(byte pos, byte[] data) throws CardException, WordSizeException {
		Word[] words = Word.byteArrayToWordArray(data);
		return writeToCard(pos, words);
	}

	/**
	 * Ecris dans la zone mémoire de la carte
	 * 
	 * @param pos
	 * @param data
	 *            : Word array à écrire
	 * @return résultat de l'exécution
	 * @throws CardException
	 */
	public ArrayList<ResponseAPDU> writeToCard(byte pos, Word[] data) throws CardException {
		ArrayList<ResponseAPDU> responses = new ArrayList<ResponseAPDU>();
		byte posEnd = (byte) (pos + data.length - 1);
		if (pos < (byte) 0x10 || (pos > (byte) 0x1F && pos < (byte) 0x28) || (pos > (byte) 0x37)) {
			return null;
		}
		if (posEnd < (byte) 0x10 || (posEnd > (byte) 0x1F && posEnd < (byte) 0x28) || (posEnd > (byte) 0x37)) {
			return null;
		}

		for (int count = 0; count < data.length; count++) {
			byte[] cmdStart = new byte[] { (byte) 0x80, (byte) 0xDE, (byte) 0x00, pos, (byte) 0x04 };
			byte[] cmd = new byte[9];
			System.arraycopy(cmdStart, 0, cmd, 0, 5);
			System.arraycopy(data[count].getBytes(), 0, cmd, 5, 4);
			CommandAPDU commandAPDU = new CommandAPDU(cmd);
			responses.add(channel.transmit(commandAPDU));
			pos++;
		}

		return responses;
	}

	public void disconnect(boolean d) throws CardException {
		card.disconnect(d);
	}

	public static String toString(byte[] byteTab) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (byte b : byteTab) {
			sb.append(String.format("0x%02X ", b));
		}
		sb.append("]");
		return sb.toString();
	}
	
	public CardTerminal getTerminal(){
		return this.terminal;
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	public static void main(String[] args) throws CardException, WordSizeException {
		ResponseAPDU r;

		List<CardTerminal> terminauxDispos = TerminalFactory.getDefault().terminals().list();
		SmartCard sc = new SmartCard(terminauxDispos.get(0));

		Word pin0 = new Word((byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA);
		r = sc.testPin((byte) 0x07, pin0);
		System.out.println(String.format("%02X %02X", r.getSW1(), r.getSW2()));

		String password = "123456";

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());

			byte byteData[] = md.digest();
			System.out.println(toString(byteData));
			sc.writeToCard((byte) 0x10, byteData);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (WordSizeException e) {
			e.printStackTrace();
		}

		ArrayList<ResponseAPDU> responses = sc.readCard((byte) 0x10, 4);
		System.out.println(toString(responses.get(0).getData()));
		System.out.println(toString(responses.get(1).getData()));
		System.out.println(toString(responses.get(2).getData()));
		System.out.println(toString(responses.get(3).getData()));

		sc.disconnect(false);
	}
}