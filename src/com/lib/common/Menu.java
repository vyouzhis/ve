package com.lib.common;

import org.ppl.BaseClass.BaseModule;

public class Menu extends BaseModule {
	private String className = null;
	static Menu source;
	
	public Menu() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}
	
	public static Menu getInstance() {
		if (source == null) {
			source = new Menu();
		}

		return source;
	}

	@Override
	public void filter() {
		// TODO Auto-generated method stub
		
		super.View();
	}
	
}
