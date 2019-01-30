package main;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

import my.function.BaiduAI;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

public class test1  extends JFrame {
	
	//设置APPID/AK/SK
    public static final String APP_ID = "9913483";
    public static final String API_KEY = "Sxm4a1NDtWNsQC2e7YmwxrcD";
    public static final String SECRET_KEY = "wCFyYSQvtvPiEflykQSwlDQECmgwLsTG";
	
		  //declare PrintStream and JTextArea
		    private static PrintStream ps = null;
		    private JTextArea textPane = new JTextArea();  //constructor
		    public test1() 
		    {

		    setSize( 310, 180 );

		      getContentPane().add(textPane);

		      //this is the trick: overload the println(String)
		      //method of the PrintStream
		      //and redirect anything sent to this to the text box
		    ps = new PrintStream(System.out) {
		      public void println(String x) {
		        textPane.append(x + "\n");
		      }
		    };
		    }

		    public PrintStream getPs() {
		      return ps;
		    }

		  public static void main(String args[]) throws IOException, JSONException {
			  
			  
			    
			    String imgStr="data:image/png;base64,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";
		       

			    System.out.println(BaiduAI.get_image(imgStr));
			  
		  }
		

}
