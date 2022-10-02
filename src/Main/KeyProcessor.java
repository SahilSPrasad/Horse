/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);

	// Static Method(s)
	public static void processKey(char key){
		//System.out.println("key: " + key);
		if(key == ' ')	{
			//System.out.println("called");
			Main.isWpressed = false;
			Main.isDpressed = false;
			Main.isApressed = false;
			Main.isSpressed = false;
			return;
		}

		// Debounce routine below...
		if (key == last)
			if (!sw.isTimeUp()) return;
		last = key;
		sw.resetWatch();

		/* TODO: You can modify values below here! */
		switch(key) {
			case '%':								// ESC key
				System.exit(0);
				break;

			case '$':
				Main.isSpacePressed = !Main.isSpacePressed;
				break;

			case 'w':
				//isHeldDown = true;
				Main.isWpressed = true;
				Main.isApressed = false;
				Main.isSpressed = false;
				Main.isDpressed = false;
				Main.faceForward = true;
				break;

			case 'd':
				//isHeldDown = true;
				//System.out.println("dpressed");
				Main.isDpressed = true;
				Main.isApressed = false;
				Main.isWpressed = false;
				Main.isSpressed = false;
				Main.faceForward = false;
				Main.isSpacePressed = false;
				break;

			case 'a':
				//isHeldDown = true;
				Main.isApressed = true;
				Main.isDpressed = false;
				Main.isWpressed = false;
				Main.isSpressed = false;
				Main.faceForward = false;
				Main.isSpacePressed = false;
				break;

			case 's':
				//isHeldDown = true;
				Main.isSpressed = true;
				Main.isApressed = false;
				Main.isWpressed = false;
				Main.isDpressed = false;
				Main.faceForward = false;
				Main.isSpacePressed = false;
				break;

			case 'm':
				// For mouse coordinates
				Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
				break;
		}
	}
}