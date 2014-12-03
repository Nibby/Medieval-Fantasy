package hidden.indev0r.core.character;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

public class CharacterNode {

	private CharacterNode            parentNode;
	private ArrayList<CharacterNode> childNodes;

	private boolean isParent;
	private Image   nodePart;

	public CharacterNode(String location) {
		this(null, location);
		isParent = true;
	}

	public CharacterNode(CharacterNode parentNode, String location) {

		try {
			nodePart = new Image(location);
			nodePart.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		if(parentNode == null){
			this.childNodes = new ArrayList<CharacterNode>(0);
		} else {
			this.childNodes = null;
			this.parentNode = parentNode;
			parentNode.childNodes.add(this);
		}
	}

	public void draw(Graphics g, int x, int y) {
		if (!isParent) return;
		g.pushTransform();
		g.scale(5, 5);
		g.drawImage(nodePart, x, y);
		for(CharacterNode c : childNodes)g.drawImage(c.nodePart, x, y);
		g.popTransform();
	}


}
