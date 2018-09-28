
package yetanothertest;




import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MailGui extends Frame {
	public static TextField display;{
	    display = new TextField("to",50);
	    display.setEditable(true); 
	    display.setBounds(65, 38, 290, 32);}
	public static TextField display2;{
	    display2 = new TextField("subject",50);
	    display2.setEditable(true); 
	    display2.setBounds(65, 76, 290, 32);}
	public static TextArea display3;{
	    display3 = new TextArea("text");
	    display3.setEditable(true); 
	    display3.setBounds(65, 114, 290, 96);}
	public static TextField display4;{
	    display4 = new TextField("spam counter",150);
	    display4.setEditable(true); 
	    display4.setBounds(130, 220, 200, 32);}
	public MailGui(){
		super("Mail Spammer App");
		
		this.setLayout(null);
		this.setFont(new Font("Courier New",Font.BOLD,12));
		this.setBackground(Color.blue);
		this.setSize(400,300);
		this.setLocation(250,250);
		this.setVisible(true);
		this.toFront();
		this.setResizable(true);
		this.add(display);
		this.add(display2);
		this.add(display3);
		this.add(display4);
		this.addWindowListener(new CloseWindowAndExit());
		Button button=new Button("Send");
		button.setBounds(65,220,60,35);
		button.addActionListener(MailTest.send);
		this.add(button);
	}
}
class CloseWindowAndExit extends WindowAdapter{
	public void windowClosing(WindowEvent closeWindowAndExit){
		System.exit(0);
	}
}