//Author: Rohan Parikh
//Credits to Eclipse for providing sample snippets

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet307 {

// Init function to load the BK Tree	
public static BKTree init(){
	FileInputStream fileIn = null;
	ObjectInputStream in = null;
	BKTree b = null;
	try {
		long startTime = System.nanoTime();
		fileIn = new FileInputStream("tree.ser");
		in = new ObjectInputStream(fileIn);
		b = (BKTree) in.readObject();
		in.close();
		fileIn.close();
		long endTime = System.nanoTime();
		System.out.println("Time to make the Data Structure : "+ (endTime-startTime)/1000000+"ms");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch(ClassNotFoundException e){
		e.printStackTrace();
	}
	return b;
}

// Main function to create window and load the javascript enabled HTML page
public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	shell.setBounds (10,10,1080,720);

	final Browser browser;
	try {
		browser = new Browser (shell, SWT.NONE);
	} catch (SWTError e) {
		System.out.println ("Could not instantiate Browser: " + e.getMessage ());
		display.dispose();
		return;
	}
	browser.setText (createHTML ());
	CustomFunction.setBKTree(init());
    final BrowserFunction function = new CustomFunction (browser, "theJavaFunction");
    
	browser.addProgressListener (new ProgressAdapter () {
		@Override
		public void completed (ProgressEvent event) {
			browser.addLocationListener (new LocationAdapter () {
				@Override
				public void changed (LocationEvent event) {
					browser.removeLocationListener (this);
					System.out.println ("left java function-aware page, so disposed CustomFunction");
					function.dispose ();
				}
			});
		}
	});

	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ())
			display.sleep ();
	}
	display.dispose ();
}

/**
 * @author Rohan
 *
 *//*
static class AnotherCustomFunction extends BrowserFunction {

	public BKTree b; 
	
	public AnotherCustomFunction(Browser browser, String name) {
		super(browser, name);
		// TODO Auto-generated constructor stub
	}
	
	public void setBKTree(BKTree t){
		b=t;
	}
	
	public Object function (Object[] arguments){
	
        ArrayList<String> s = b.search((String) arguments[0], 1);
		Object returnValue = s.toArray(); //Because javascript doesn't understand Arraylist
		return returnValue;
	}
	
}*/

// Custom java function that can be called from javascript code
static class CustomFunction extends BrowserFunction {
	
	static BKTree b;
	
	CustomFunction (Browser browser, String name) {
		super (browser, name);
	}
	
	static void setBKTree(BKTree t){
		b = t;
	}
	
	@Override
	public Object function (Object[] arguments){
		long startTime = System.nanoTime();
        ArrayList<String> s = b.search((String) arguments[0],1);
        long endTime = System.nanoTime();
        System.out.println("Time : "+ (endTime-startTime)/1000000+"ms");
		Object returnValue = s.toArray(); //Because javascript doesn't understand Arraylist
		return returnValue;
	}
	
	
}

// HTML to be embedded on the window
static String createHTML () {
	StringBuffer buffer = new StringBuffer ();
	buffer.append ("<html>\n");
	buffer.append ("<head>\n");
	buffer.append ("<script language=\"JavaScript\">\n");
	buffer.append ("function function1() {\n");
	buffer.append ("var s = document.getElementById(\"lol\").value;\n");
	buffer.append ("    var result;\n");
	buffer.append ("    try {\n");
	buffer.append ("        result = theJavaFunction(s)");
	buffer.append ("    } catch (e) {\n");
	buffer.append ("        alert('a java error occurred: ' + e.message);\n");
	buffer.append ("        return;\n");
	buffer.append ("    }\n");
	buffer.append ("    for (var i = 0; i < result.length; i++) {\n");
	buffer.append ("        document.write('<br>'+result[i]);\n");
	buffer.append ("    }\n");
	buffer.append ("}\n");
	buffer.append ("</script>\n");
	buffer.append ("</head>\n");
	buffer.append ("<body>\n");
	buffer.append ("<input type=\"text\" id=\"lol\">\n");
	buffer.append ("<input id=button type=\"button\" value=\"Click here!\" onclick=\"function1();\">\n");
	buffer.append ("</body>\n");
	buffer.append ("</html>\n");
	return buffer.toString ();
}

}


