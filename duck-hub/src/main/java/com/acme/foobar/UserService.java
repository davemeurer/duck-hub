package com.acme.foobar;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 public void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {
		      doPost(request, response);
		  }

		  public void doPost(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {

		    HttpSession session = request.getSession(true);

		    Boolean loggedin=(Boolean)session.getAttribute("loggedin");
		    AcmeConfig conf=(AcmeConfig)session.getAttribute("config");
			AuthorsConfig aconf= new AuthorsConfig();
		    if((loggedin==null) || (!loggedin.booleanValue()) || (conf==null)) response.sendRedirect("Config");

		    else {
		      response.setContentType("text/html");
		      response.addHeader("Cache-Control","no-cache");
		      String courseslist = "";
		      Hashtable<?, ?> authorHash = aconf.AuthorHash;
		      for (Enumeration keys = authorHash.keys();keys.hasMoreElements();) {
		          String key = (String)keys.nextElement();
		          AcmeAuthor acmeAuthor=aconf.GetAuthor(key);
		          Vector authorCL = acmeAuthor.getCourseList();
		          for (int i=0;i<authorCL.size();i++) {
					courseslist = courseslist + authorCL.elementAt(i) + "|";
				  }
		      }
		  response.getWriter().write(" <html>\n"+
		  " <head>\n"+
		  " \n"+
		  " <link rel=\"stylesheet\" href=\"aha.css\">\n"+
		  " <script language=\"javascript\"> <!--\n" +
		  "   function checkpasswd() { \n"+
		  "     if(document.authorform.password.value==document.authorform.password2.value) { \n"+
		  "       listField = document.authorform.courselist; \n"+
		  "       var len = listField.length; \n"+
		  "       var totalvalue=\"\";\n"+
		  "       for (var i=0; i<len; i++) { \n"+
		  "         if (listField.options[i].value!=\"\") \n"+
		  "           totalvalue=totalvalue+listField.options[i].value+\"|\"; \n"+
		  "       }\n"+
		  "       document.authorform.courses.value=totalvalue;\n"+
		  "       document.authorform.submit() \n"+
		  "     } else { \n"+
		  "       alert(\"Passwords are inequal\") \n" +
		  "     } \n"+
		  "   } \n"+
		  "   function addToList() { \n"+
		  "     listField = document.authorform.courselist; \n"+
		  "     newValue = document.authorform.coursename.value; \n"+
		  "     coursesValue = document.authorform.courseslist.value; \n"+
		  "     fromJava = coursesValue.split(\"|\");\n"+
		  "     if (newValue==\"\") { \n"+
		  "       alert(\"You cannot add blank values!\"); \n"+
		  "     } else if (newValue.indexOf('|')>-1) { \n"+
		  "       alert(\"Course name should not contain '|' sign!\"); \n"+
		  "     } else if (IsDigit(newValue.charAt(0))) { \n"+
		  "       alert(\"Course name should start with a character!\"); \n"+
		  "     } else {\n"+
		  "       var exists=0; \n"+
		  "       var len = listField.length; \n"+
		  "       for (var i=0; i<len; i++) { \n"+
		  "         if (listField.options[i].value==newValue && listField.options[i].text==newValue) { \n"+
		  "           alert(\"You have already assigned this course for this author!\"); \n"+
		  "		      exists=1; \n"+
		  "           break; \n"+
		  "         } \n"+
		  "       } \n"+
		  "       if (exists==0) {\n"+
		  "         for (var i=0; i<fromJava.length; i++) { \n"+
		  "           if (fromJava[i]==newValue) { \n"+
		  "             alert(\"One of the authors already has a course with this name! Please, enter another name!\"); \n"+
		  "		        exists=1; \n"+
		  "   	       break; \n"+
		  "           } \n"+
		  "         } \n"+
		  "       } \n"+
		  "       if (exists==0) { \n"+
		  "         len = listField.length++; // Increase the size of list and return the size \n"+
		  "         listField.options[len].value = newValue; \n"+
		  "         listField.options[len].text = newValue; \n"+
		  "         listField.selectedIndex = len; // Highlight the one just entered (shows the user that it was entered) \n"+
		  "       } // Ends the check to see if the value entered on the form is empty \n"+
		  "     } \n"+
		  "   } \n"+
		  "   function removeFromList() {\n"+
		  "     listField = document.authorform.courselist; \n"+
		  "     if ( listField.length == -1) { // If the list is empty \n"+
		  "       alert(\"There are no values which can be removed!\"); \n"+
		  "     } else { \n"+
		  "       var selected = listField.selectedIndex; \n"+
		  "       if (selected == -1 || listField.value==\"\") { \n"+
		  "         alert(\"You must select an entry to be removed!\"); \n"+
		  "       } else {  // Build arrays with the text and values to remain \n"+
		  "         var replaceTextArray = new Array(listField.length-1); \n"+
		  "         var replaceValueArray = new Array(listField.length-1); \n"+
		  "         for (var i = 0; i < listField.length; i++) { \n"+
		  "			  // Put everything except the selected one into the array \n"+
		  "			  if ( i < selected) { replaceTextArray[i] = listField.options[i].text; } \n"+
		  "			  if ( i > selected ) { replaceTextArray[i-1] = listField.options[i].text; } \n"+
		  "			  if ( i < selected) { replaceValueArray[i] = listField.options[i].value; } \n"+
		  "			  if ( i > selected ) { replaceValueArray[i-1] = listField.options[i].value; } \n"+
		  "			} \n"+
		  "			listField.length = replaceTextArray.length; // Shorten the input list \n"+
		  "			for (i = 0; i < replaceTextArray.length; i++) { // Put the array back into the list \n"+
		  "			  listField.options[i].value = replaceValueArray[i]; \n"+
		  "			  listField.options[i].text = replaceTextArray[i]; \n"+
		  "			} \n"+
		  "       } // Ends the check to make sure something was selected\n"+
		  "	    } // Ends the check for there being none in the list \n"+
		  "	  } \n"+
		  "   function IsDigit(txtChar){\n"+
		  "     var digits = \"0123456789\";\n"+
		  "     if (digits.indexOf(txtChar)<0)\n"+
		  "			return false;\n"+
		  "     return true;\n"+
		  "   }\n"+
		  " // -->\n" +
		  " </script>\n" +
		  " </head>\n"+
		  " \n"+
		  " <body>\n"+
		  " <table>\n"+
		  " <tr>\n"+
		  " <td valign=\"top\">\n"+
		  " \n"+
		  " <!-- start menu -->\n"+
		  " <h2>aha</h2>\n"+
		  " <b>The AHA configurator</b>\n"+
		  " <br><br>\n"+
		  " <a href=\"ConfDB\">Configure DataBase</a><br>\n"+
		  " <a href=\"Managers\" title=\"new managers and settings\">Manager Configuration</a><br>\n"+
		  " <a href=\"Authors\" title=\"authors and settings\">Authors</a><br>\n"+
		  " <a href=\"ConvertCL\" title=\"convert concept list\">Convert concept list from internal format to XML file</a><br>\n"+
		  " <a href=\"ConvertXML\" title=\"convert XML file\">Convert concept list from XML file to internal format</a><br>\n"+
		  " <a href=\"Logout\" title=\"Exit\">Logout</a><br>\n"+
		  " <!-- end menu -->\n"+
		  " </td>\n"+
		  " <td>\n"+
		  " <!-- start content -->\n"+
		  " \n");
		    }
		  }
	

}
