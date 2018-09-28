package yetanothertest;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SendMail implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
		try {
			String to=MailGui.display.getText();
			String subject=MailGui.display2.getText();
			String text=MailGui.display3.getText();
			int counter=Integer.parseInt(MailGui.display4.getText());
			for(int i=0;i<counter;i++){
			 MimeMessage email=MailTest.createEmail(to,"me",subject,text);
		    MailTest.sendMessage(MailTest.service,"me",email);
			}
		} catch (MessagingException | IOException | NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
