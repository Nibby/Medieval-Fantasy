package hidden.indev0r.game.entity.animation;

import hidden.indev0r.game.reference.References;
import hidden.indev0r.game.texture.ResourceManager;
import hidden.indev0r.game.util.CipherEngine;
import hidden.indev0r.game.util.XMLParser;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.swing.*;
import java.io.DataInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ActionSetDatabase {

	//Keeping a record of all registered sets with unique IDs
	//In case if we want XML generated NPCs to inherit some of these sets
	private static final Map<Integer, ActionSet> setMap = new HashMap<>();

	/**
	 * Loads 'asdb.dat', which is the XML equivalence for all action setStat definitions
	 */
	public void loadActionSets() throws Exception {
		Path dbPath = References.ACTION_SET_DATABASE_PATH;
		Cipher cipher = CipherEngine.getCipher(Cipher.DECRYPT_MODE, References.CIPHER_KEY_2);

		DataInputStream input = new DataInputStream(new CipherInputStream(Files.newInputStream(dbPath), cipher));
		byte[] bytes = new byte[input.readInt()];
		input.readFully(bytes);
		input.close();
		
		String data = new String(bytes, Charset.forName("UTF-8"));
		XMLParser parser = new XMLParser(data);

		Document doc = parser.getDocument();
		Element root = (Element) doc.getElementsByTagName("actionSetDB").item(0);

		NodeList actionSetList = root.getElementsByTagName("actionSet");
		for (int i = 0; i < actionSetList.getLength(); i++) {
			Element eSet = (Element) actionSetList.item(i);

			//Parse attribute information for current action setStat
			int setID = Integer.parseInt(eSet.getAttribute("id"));
			String setResource = "spritesheet:" + eSet.getAttribute("resource");

			//Load spritesheet resource from Textures
			SpriteSheet resource = null;
			try {
				resource = (SpriteSheet) ResourceManager.get(setResource);

				if (resource == null) {
					JOptionPane.showMessageDialog(null, "Unable to locate resource:\n'" + setResource + "' (null resource)", "ActionSetDatabase - setStat " + setID, JOptionPane.ERROR_MESSAGE);
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Unable to locate resource:\n'" + setResource + "' (error)", "ActionSetDatabase - setStat " + setID, JOptionPane.ERROR_MESSAGE);
				continue;
			}

			//Create setStat object from existing information
			ActionSet set = new ActionSet(setID);
			registerActionSet(set);

			//Map all the actions and frames within this setStat
			NodeList actionList = eSet.getElementsByTagName("action");
			for (int j = 0; j < actionList.getLength(); j++) {
				Element eAction = (Element) actionList.item(j);
				String actionTypeString = eAction.getAttribute("type");
				ActionType actionType = ActionType.valueOf(actionTypeString);

				if (actionType == null) {
					JOptionPane.showMessageDialog(null, "Unable to find action type: " + actionTypeString + "' for id '" + setID + "'", "ActionSetDatabase - setStat " + setID, JOptionPane.ERROR_MESSAGE);
					continue;
				}

				int actionXShift, actionYShift;

				try {
					actionXShift = (eAction.hasAttribute("shiftX") ? Integer.parseInt(eAction.getAttribute("shiftX")) : 0);
					actionYShift = (eAction.hasAttribute("shiftY") ? Integer.parseInt(eAction.getAttribute("shiftY")) : 0);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Unable to load action xShift or yShift attribute for id '" + setID + "'", "ActionSetDatabase - setStat " + setID, JOptionPane.ERROR_MESSAGE);
					continue;
				}

				//Create and register newly created action from given information
				Action action = new Action(actionType, actionXShift, actionYShift);
				set.add(action);

				//Read all the frames in the given action
				NodeList frameList = eAction.getElementsByTagName("frame");
				for (int k = 0; k < frameList.getLength(); k++) {
					Element eFrame = (Element) frameList.item(k);

					Image frameSprite = null;
					int frameTime;
					int frameXShift, frameYShift;


					//If done via. sprites
					if (eFrame.hasAttribute("sprite")) {
						//Read sprite information, in co-ord pairs (e.g. 2,3)
						try {
							int frameX = -1, frameY = -1;

							String frameData = eFrame.getAttribute("sprite");
							String[] frameXY = frameData.split(",");
							frameX = Integer.parseInt(frameXY[0]);
							frameY = Integer.parseInt(frameXY[1]);
							frameSprite = resource.getSprite(frameX, frameY);

						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error occurred while loading frame data (sprite)!\n" + e, "ActionSetDatabase - setStat " + setID, JOptionPane.ERROR_MESSAGE);
							continue;
						}
					}

					//If done via subimage
					else if (eFrame.hasAttribute("subImage")) {
						try {
							int startX, startY, width, height;


							String frameData = eFrame.getAttribute("subImage");
							String[] frameSub = frameData.split(",");
							startX = Integer.parseInt(frameSub[0]);
							startY = Integer.parseInt(frameSub[1]);
							width = Integer.parseInt(frameSub[2]);
							height = Integer.parseInt(frameSub[3]);
							frameSprite = resource.getSubImage(startX, startY, width, height);

						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error occurred while loading frame data (subImage)!\n" + e, "ActionSetDatabase - setStat " + setID, JOptionPane.ERROR_MESSAGE);
							continue;
						}
					}

					//Load data
					try {

						frameTime = Integer.parseInt(eFrame.getAttribute("time"));
						frameXShift = (eFrame.hasAttribute("shiftX") ? Integer.parseInt(eFrame.getAttribute("shiftX")) : 0);
						frameYShift = (eFrame.hasAttribute("shiftY") ? Integer.parseInt(eFrame.getAttribute("shiftY")) : 0);

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Error occurred while loading frame data!\n" + e, "ActionSetDatabase - setStat " + setID, JOptionPane.ERROR_MESSAGE);
						continue;
					}

					//Create and add frame to setStat, from given information
					try {
						action.addFrame(frameSprite, frameTime, frameXShift, frameYShift);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error occurred upon creating frame for action '" + actionTypeString + "'!", "ActionSetDatabase - setStat " + setID, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}

	}

	private void registerActionSet(ActionSet set) {
		int id = set.getID();
		if (setMap.get(id) != null) {
			JOptionPane.showMessageDialog(null,
					"EntityActionSet '" + id + "' already registered!",
					"Internal Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		setMap.put(id, set);
	}

	public static ActionSet get(int id) {
		return setMap.get(id);
	}

	public static ActionSetDatabase getDatabase() {
		return database;
	}

	private static final ActionSetDatabase database = new ActionSetDatabase();

	private ActionSetDatabase() {
	}
}
