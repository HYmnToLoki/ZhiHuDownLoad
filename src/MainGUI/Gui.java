package MainGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;





import Been.User;
import Dao.DaoTools;
import Net.MainThread;

public class Gui extends JFrame implements ActionListener {
	
	JButton Btn_start = new JButton("start");
	public static int numbered=0;
	public Gui() {
		this.setSize(100, 100);
		this.setLocation(100, 100);
		this.add(Btn_start);
		Btn_start.addActionListener(this);

	}


		

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Btn_start) {
			Hello h1=new Hello();
			new Thread(h1,"get").start();
			new Thread(h1,"take1").start();
			new Thread(h1,"take2").start();
			new Thread(h1,"take3").start();	
		}
	}

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.setVisible(true);
	}
	
	class Hello implements Runnable
	{
		private  Queue<String> hs=null;
		public Hello()
		{
			hs=new LinkedList<String>(); 
		}
		@Override
		public void run() {
			if(Thread.currentThread().getName().equals("get"))
			{
				while(true)
				{
					if(hs.size()==10)
					{
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
				System.out.println(Thread.currentThread().getName()+"线程=====================执行"+"-----------"+hs.size());
				tools(Thread.currentThread().getName());
					}
				}
			}else
			{
				while(true)
				{
					System.out.println(Thread.currentThread().getName()+"线程=====================执行"+"-----------"+hs.size());
					String seed=tools(Thread.currentThread().getName());
					if(seed!=null)
					{
					startToDown(seed);
					}
				}
			}
			
		}
		public synchronized String tools(String name)
		{
			if(name.equals("get"))
			{
				if(hs.size()<10)
				{
					String []seedlis=DaoTools.getSomeSeed(10-hs.size());
					for(int i=0;i<seedlis.length;i++)
					{
						hs.add(seedlis[i]);
						System.out.println("添加队列成功");
					}
					return "";
				}
			}else 
			{
				
				return hs.poll();
			}
			return "";
		}
		public void startToDown(String seed) {
			//循环从usercatch表中读取待抓取的用户界面，以此为种子进行信息抓取
				try {

					//抓取的页面字符串
					String pageString = "";
					//从usercatch中获得一条数据
//					seed = hs.poll();
//					if (seed == "") {
//						System.out.println("抓取结束");
//						continue;
//					}
//					if(DaoTools.CheckInfo(seed))
//					{
//						DaoTools.deleteUserCache(seed);
//						continue;
//					}
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
					Gui.numbered++;
					System.out.println(seed+"down load success..."+Gui.numbered);
					//将的到的网址写入text文件，以便测试正则表达式
					//MainThread.fileWriter(pageString);
					//System.out.println(pageString);
					DaoTools.deleteUserCache(seed);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		
	}

}
