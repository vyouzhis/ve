package com.lib.thread;

import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseRapidThread;

public class testThread extends BaseRapidThread {

	@Override
	public void Run() {
		// TODO Auto-generated method stub
		String sql = "select * from web_user limit 1";
		Map<String, Object> res = FetchOne(sql);
		echo(res);
		
		long millis = (long)randomWithRange(1, 10)*1000;
		
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isRun() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Stop() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void mailbox(Object o) {
		// TODO Auto-generated method stub
		
		List<String> s = (List<String>) o;
		for(String k:s){
			echo(k);
		}
	}
	
	private int randomWithRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}

	@Override
	public String title() {
		// TODO Auto-generated method stub
		return null;
	}

}