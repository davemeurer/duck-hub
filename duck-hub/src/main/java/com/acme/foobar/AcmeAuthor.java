package com.acme.foobar;

import java.util.Vector;

public class AcmeAuthor {
	
	private Vector courseList=new Vector();
	
	   public void setCourseList(Vector v) {
		     courseList=v;
		   }

		   public Vector getCourseList() {
		     return courseList;
		   }

}
