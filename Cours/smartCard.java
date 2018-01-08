package smartcard;

import java.util.List;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
/**
 *
 * @author Guillaume
 */
public class smartCard {
    private static CardTerminal terminal;
    private static Card carte;
    private static int i;
    private static String texte=new String();
    
    static public List<CardTerminal> getTerminals() throws CardException {
        return TerminalFactory.getDefault().terminals().list();

    }
    
    static public String toString(byte[] byteTab){
        String texte="";
        String hexNombre;
        for(i=0;i<byteTab.length;i++){
                hexNombre="";
                hexNombre=Integer.toHexString(byteTab[i]);
                if(hexNombre.length()==1){
                    texte+=" 0"+hexNombre;
                }
                else{
                    texte+=" "+hexNombre;
                }
        }
        return texte;
    }
    
    public static void main(String[] args) throws CardException {
        List<CardTerminal> terminauxDispos = smartCard.getTerminals();
        //Premier terminal dispo
        terminal = terminauxDispos.get(0);
        System.out.println(terminal.toString());
        //Connexion à la carte
        carte = terminal.connect("T=0");
        //ATR (answer To Reset)
        System.out.println(toString(carte.getATR().getBytes()));
        
        CardChannel channel = carte.getBasicChannel();
        CommandAPDU commande = new CommandAPDU(0x80,0xBE,0x10,0x00,0x04);
        ResponseAPDU r = channel.transmit(commande);
        System.out.println("reponse : " + (byte) r.getData()[1]);
        System.out.println(texte);
        carte.disconnect(false);
        
        

    }
}
