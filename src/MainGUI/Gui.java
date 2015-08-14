package MainGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Been.User;
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
		//ѭ����usercatch���ж�ȡ��ץȡ���û����棬�Դ�Ϊ���ӽ�����Ϣץȡ
			try {
				//ץȡ�û����û�id
				String seed = "";
				//ץȡ��ҳ���ַ���
				String pageString = "";
				//��usercatch�л��һ������
				seed = DaoTools.getOneUserCatch();
				if (seed == "") {
					System.out.println("ץȡ����");
					return;
				}
//				if(DaoTools.CheckInfo(seed))
//				{
//					continue;
//				}
				String UserFollowsUri = "http://www.zhihu.com/people/" + seed
						+ "/followees";
				pageString = MainThread.GetUserFollow(UserFollowsUri);
				User user=MainThread.GetUserInfo(pageString, seed);
				System.out.println(user.toString());
				if(DaoTools.InsertUserInfo(user))
				{
					System.out.println(seed+"insert info success...");
				}
				MainThread.InsertUserCache(pageString);
				String UserFollowersUri = "http://www.zhihu.com/people/" + seed
						+ "/followers";
				String pageString2="";
				pageString2=MainThread.GetUserFollow(UserFollowersUri);
				MainThread.InsertUserCache(pageString2);
				MainThread.GeTFolloweesList(user.getFollowees(), user.getFollowers(), user.getHash_id(), user.getId());
				//���ĵ�����ַд��text�ļ����Ա����������ʽ
				//MainThread.fileWriter(pageString);
				//System.out.println(pageString);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
