package Test;


import Dao.DaoTools;


public class Test {

	public static void main(String[] args) {
		String []strings=DaoTools.getSomeSeed(10);
		for(int i=0;i<10;i++)
		{
		System.err.println(strings[i]);
		}
	}

}
