package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.entity.Actor;
import org.w3c.dom.Element;

import java.util.List;

/**
 * Created by MrDeathJockey on 14/12/14.
 */
public class Command$If extends Command {

    private NestedCommandBlock nestedBlock;

    private Element ifElement;
    private String dataBeingChecked;
    private String checkOperation;
    private String dataBeingCompared;

    @Override
    public void make(CommandBlock block, Actor actor, Element e) {
        super.make(block, actor, e);
        nestedBlock = new NestedCommandBlock(block);
        List<Command> childCommands = ScriptParser.parse("IF", nestedBlock, actor, e);
        nestedBlock.setCommandList(childCommands);

        ifElement = e;
    }

    @Override
    public void exec(Actor actor, final CommandBlock block) {
        super.exec(actor, block);

        //Checking script data
        if(ifElement.hasAttribute("scriptDataParam1")) {
            dataBeingChecked = (String) Script.translate(ifElement.getAttribute("check"), ifElement.getAttribute("scriptDataParam1"));
        }
        else {
            dataBeingChecked = (String) Script.translate(ifElement.getAttribute("check"));
        }
        checkOperation = ifElement.getAttribute("operation");
        if(ifElement.hasAttribute("scriptDataParam2")) {
            dataBeingCompared = (String) Script.translate(ifElement.getAttribute("compare"), ifElement.getAttribute("scriptDataParam2"));
        }
        else {
            dataBeingCompared = (String) Script.translate(ifElement.getAttribute("compare"));
        }

        int checkVar, compareVar;

        switch(checkOperation) {
            //Int comparison
            case "==":
                checkVar = Integer.parseInt(dataBeingChecked);
                compareVar = Integer.parseInt(dataBeingCompared);

                if(checkVar == compareVar) {
                    nestedBlock.execute(actor);
                } else block.executeNext(actor);
                break;
            case ">=":
                checkVar = Integer.parseInt(dataBeingChecked);
                compareVar = Integer.parseInt(dataBeingCompared);

                if(checkVar >= compareVar) {
                    nestedBlock.execute(actor);
                } else block.executeNext(actor);
                break;
            case "<=":
                checkVar = Integer.parseInt(dataBeingChecked);
                compareVar = Integer.parseInt(dataBeingCompared);

                if(checkVar <= compareVar) {
                    nestedBlock.execute(actor);
                } else block.executeNext(actor);
                break;
            case "<":
                checkVar = Integer.parseInt(dataBeingChecked);
                compareVar = Integer.parseInt(dataBeingCompared);

                if(checkVar < compareVar) {
                    nestedBlock.execute(actor);
                } else block.executeNext(actor);
                break;
            case ">":
                checkVar = Integer.parseInt(dataBeingChecked);
                compareVar = Integer.parseInt(dataBeingCompared);

                if(checkVar > compareVar) {
                    nestedBlock.execute(actor);
                } else block.executeNext(actor);
                break;
            //String comparison
            case "equals":
                if(dataBeingCompared.equals(dataBeingChecked)) {

                    nestedBlock.execute(actor);
                } else block.executeNext(actor);
                break;
            default:
                break;
        }
    }

}
