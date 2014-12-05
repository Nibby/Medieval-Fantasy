package hidden.indev0r.core.gui;

public class GHUD {


	private GElement hotbar;
	private GElement stats;

	public GHUD(){
		hotbar = new GElement$Hotbar();
		stats = new GElement$Stats();
	}


	public void render(){
		hotbar.render();
		stats.render();
	}

	public void tick(){


	}

}
