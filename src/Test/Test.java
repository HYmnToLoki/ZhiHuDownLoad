package Test;

import java.io.UnsupportedEncodingException;

import Net.MainThread;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainThread mThread=new MainThread();
		try {
			mThread.GeTFolloweesList(103, 78, "00841a8ef77cffe40660c5fb6937cc38","zhang-kiki-30");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
