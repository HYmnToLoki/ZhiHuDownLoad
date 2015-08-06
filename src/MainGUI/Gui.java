package MainGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Test.TestGetPage;

public class Gui extends JFrame implements ActionListener{

	JButton Btn_start=new JButton("start");
	
	public Gui()
	{
		this.setSize(100,100);
		this.setLocation(100, 100);
		this.add(Btn_start);
		Btn_start.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==Btn_start)
		{
			TestGetPage testGetPage=new TestGetPage();
			String string="";
			string=testGetPage.getWebPage("http://www.zhihu.com/node/ProfileFolloweesListV2?method=next&params=%7B%22offset%22%3A20%2C%22order_by%22%3A%22created%22%2C%22hash_id%22%3A%22d00ade9168907740fcea35fa5fbc7ace%22%7D&_xsrf=2fd8c9059d757ce6d4770c7ea46983a1");
			System.out.println(string);
		}	
	}
	public static void main(String[] args) {
		Gui gui=new Gui();
		gui.setVisible(true);
	}

}
