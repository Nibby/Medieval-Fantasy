package hidden.indev0r.core.gui.component.interfaces;

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
