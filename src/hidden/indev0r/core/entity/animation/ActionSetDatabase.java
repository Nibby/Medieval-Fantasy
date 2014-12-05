package hidden.indev0r.core.entity.animation;

import hidden.indev0r.core.reference.References;
import hidden.indev0r.core.texture.Textures;
import hidden.indev0r.core.util.CipherEngine;
import hidden.indev0r.core.util.XMLParser;
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

public  class ActionSetDatabase {

    //Keeping a record of all registered sets with unique IDs
    //In case if we want XML genereated NPC's to inherit some of these sets
    private static final Map<Integer, ActionSet> setMap = new HashMap<>();

    /**
     * Loads 'asdb.dat', which is the XML equivalence for all action set
     * definitions
     */
    public void loadActionSets() throws Exception {
        Path dbPath = References.ACTION_SET_DATABASE_PATH;
        Cipher cipher = CipherEngine.getCipher(Cipher.DECRYPT_MODE, References.CIPHER_KEY_2);

        DataInputStream input = new DataInputStream(new CipherInputStream(Files.newInputStream(dbPath), cipher));
        byte[] bytes = new byte[input.readInt()];
        input.readFully(bytes);

        String data = new String(bytes, Charset.forName("UTF-8"));
        XMLParser parser = new XMLParser(data);

        Document doc = parser.getDocument();
        Element root = (Element) doc.getElementsByTagName("actionSetDB").item(0);

        NodeList actionSetList = root.getElementsByTagName("actionSet");
        for(int i = 0; i < actionSetList.getLength(); i++) {
            Element eSet = (Element) actionSetList.item(i);

            //Parse attribute information for current action set
            int setID = Integer.parseInt(eSet.getAttribute("id"));
            String setResource = "spritesheet:" + eSet.getAttribute("resource");

            //Load spritesheet resource from Textures
            SpriteSheet resource = null;
            try {
                resource = (SpriteSheet) Textures.get(setResource);

                if(resource == null) {
                    JOptionPane.showMessageDialog(null, "Unable to locate resource:\n'" + setResource +"'", "ActionSetDatabase - set " + setID, JOptionPane.ERROR_MESSAGE);
                    continue;
                }
            }  catch(Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Unable to locate resource:\n'" + setResource +"'", "ActionSetDatabase - set " + setID, JOptionPane.ERROR_MESSAGE);
                continue;
            }

            //Create set object from existing information
            ActionSet set = new ActionSet(setID);
            registerActionSet(set);

            //Map all the actions and frames within this set
            NodeList actionList = eSet.getElementsByTagName("action");
            for(int j = 0; j < actionList.getLength(); j++) {
                Element eAction = (Element) actionList.item(j);
                String actionTypeString = eAction.getAttribute("type");
                ActionType actionType = ActionType.valueOf(actionTypeString);

                if(actionType == null) {
                    JOptionPane.showMessageDialog(null, "Unable to find action type: " + actionTypeString +"' for id '" + setID + "'", "ActionSetDatabase - set " + setID, JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                int actionXShift, actionYShift;

                try {
                    actionXShift = (eAction.hasAttribute("shiftX") ? Integer.parseInt(eAction.getAttribute("shiftX")) : 0);
                    actionYShift = (eAction.hasAttribute("shiftY") ? Integer.parseInt(eAction.getAttribute("shiftY")) : 0);
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(null, "Unable to load action xShift or yShift attribute for id '" + setID + "'", "ActionSetDatabase - set " + setID, JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                //Create and register newly created action from given information
                Action action = new Action(actionType, actionXShift, actionYShift);
                set.add(action);

                //Read all the frames in the given action
                NodeList frameList = eAction.getElementsByTagName("frame");
                for(int k = 0; k < frameList.getLength(); k++) {
                    Element eFrame = (Element) frameList.item(k);

                    //Read sprite information, in co-ord pairs (e.g. 2,3)
                    String frameData = eFrame.getAttribute("sprite");
                    String[] frameXY = frameData.split(",");
                    int frameX, frameY, frameTime;
                    int frameXShift, frameYShift;
                    try {
                        frameX = Integer.parseInt(frameXY[0]);
                        frameY = Integer.parseInt(frameXY[1]);
                        frameTime = Integer.parseInt(eFrame.getAttribute("time"));
                        frameXShift = (eFrame.hasAttribute("shiftX") ? Integer.parseInt(eFrame.getAttribute("shiftX")) : 0);
                        frameYShift = (eFrame.hasAttribute("shiftY") ? Integer.parseInt(eFrame.getAttribute("shiftY")) : 0);
                    } catch(Exception e) {
                        JOptionPane.showMessageDialog(null, "Error occurred while loading frame data!\n" + e, "ActionSetDatabase - set " + setID, JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    //Create and add frame to set, from given information
                    try {
                        action.addFrame(resource.getSprite(frameX, frameY), frameTime, frameXShift, frameYShift);
                    } catch(Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error occurred upon creating frame for action '" + actionTypeString + "'!", "ActionSetDatabase - set " + setID, JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                }
            }
        }

    }

    private void registerActionSet(ActionSet set) {
        int id = set.getID();
        if(setMap.get(id) != null) {
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

    public static ActionSetDatabase getDatabase() { return database; }

    private static final ActionSetDatabase database = new ActionSetDatabase();

    private ActionSetDatabase() {}
}
