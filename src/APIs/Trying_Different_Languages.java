package APIs;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import APIs.SynthesiserV2;

/**
 * This is where all begins .
 *
 * @author GOXR3PLUS
 *
 */
public class Trying_Different_Languages {

    //Create a Synthesizer instance
    SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");

    /**
     * Constructor
     */
    public Trying_Different_Languages(String in) {
        try {
            speak(in);
        } catch (IOException e) {

        }
    }

    /**
     * Calls the MaryTTS to say the given text
     *
     * @param text
     */
    public void speak(String text) throws IOException{

        //Create a new Thread because JLayer is running on the current Thread and will make the application to lag
        Thread thread = new Thread(() -> {
            try {

                //Create a JLayer instance
                AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(text));
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            } catch (IOException e) {

            }


        });

        //We don't want the application to terminate before this Thread terminates
        thread.setDaemon(false);

        //Start the Thread
        thread.start();

    }
    /*
    tutorial
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String voi = sc.nextLine();
        new Trying_Different_Languages(voi);
    }
     */


}
