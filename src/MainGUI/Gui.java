package MainGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Dao.DaoTools;
import Net.MainThread;
import Test.TestGetPage;

public class Gui extends JFrame implements ActionListener {

	JButton Btn_start = new JButton("start");

	public Gui() {
		this.setSize(100, 100);
		this.setLocation(100, 100);
		this.add(Btn_start);
		Btn_start.addActionListener(this);

	}

	public void startToDown() {
		//循环从usercatch表中读取待抓取的用户界面，以此为种子进行信息抓取
			try {
				//抓取用户的用户id
				String seed = "";
				//抓取的页面字符串
				String pageString = "";
				//从usercatch中获得一条数据
				seed = DaoTools.getOneUserCatch();
				if (seed == "") {
					System.out.println("抓取结束");
					return;
				}
				String UserFollowsUri = "http://www.zhihu.com/people/" + seed
						+ "/followees";
				pageString = MainThread.GetUserFollow(UserFollowsUri);
				//将的到的网址写入text文件，以便测试正则表达式
				MainThread.fileWriter(pageString);
				System.out.println(pageString);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Usercatch取出错误");
			}
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Btn_start) {
			startToDown();
		}
	}

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.setVisible(true);
	}

}
