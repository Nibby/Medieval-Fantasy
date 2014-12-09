package hidden.indev0r.game.gui.component.interfaces;

public interface GStatsSupplier {
	int getHealth();
	int getHealthMax();
	int getMana();
	int getManaMax();
	int getExperience();
	int getExperienceMax();

    org.newdawn.slick.Image getPreviewImage();

    int getLevel();
}
