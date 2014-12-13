package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.entity.Actor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDeathJockey on 14/12/9.
 *
 * This will parse a script from a given script element in an XML file.
 */
public class ScriptParser {

    public static final Script parse(Actor actor, Element scriptElement) {
        if(actor == null || scriptElement == null) return null;
        if(!scriptElement.getTagName().equals("script")) return null;

        //Obtains the type of script
        Script.Type scriptType = Script.Type.valueOf(scriptElement.getAttribute("type"));
        Script script = new Script(scriptType, actor);

        List<Command> scriptCommands = new ArrayList<>();

        //Load all commands
        NodeList commandList = scriptElement.getChildNodes();
        for(int i = 0; i < commandList.getLength(); i++) {
            Node node = commandList.item(i);

            //Filter node for only top level elements
            if(node.getParentNode().getNodeName().equals("script")
                    && node.getParentNode().getNodeType() == Node.ELEMENT_NODE
                    && node.getNodeType() == Node.ELEMENT_NODE) {

                Element commandElement = (Element) node;

                //Creates command
                Command command = Command.getCommand(commandElement.getTagName());
                if(command != null) {
                    scriptCommands.add(command.make(script, actor, commandElement));
                }
            }
        }

        script.setCommandList(scriptCommands);

        return script;
    }

    public static List<Command> parse(String cmdName, CommandBlock block, Actor actor, Element cmdElement) {
        if(actor == null || cmdElement == null) return null;

        List<Command> blockCommands = new ArrayList<>();

        //Load all commands
        NodeList commandList = cmdElement.getChildNodes();
        for(int i = 0; i < commandList.getLength(); i++) {
            Node node = commandList.item(i);

            //Filter node for only top level elements
            if(node.getParentNode().getNodeName().equals(cmdName)
                    && node.getParentNode().getNodeType() == Node.ELEMENT_NODE
                    && node.getNodeType() == Node.ELEMENT_NODE) {

                Element commandElement = (Element) node;

                //Creates command
                Command cmd = Command.getCommand(commandElement.getTagName());
                cmd.setParentBlock(block);
                if(cmd != null) {
                    blockCommands.add(cmd.make(block, actor, commandElement));
                }
            }
        }
        return blockCommands;
    }
}
