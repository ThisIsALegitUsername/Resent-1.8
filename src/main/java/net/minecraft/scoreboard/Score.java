package net.minecraft.scoreboard;

import java.util.Comparator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

/**+
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files are (c) 2022-2023 LAX1DUDE. All Rights Reserved.
 * 
 * WITH THE EXCEPTION OF PATCH FILES, MINIFIED JAVASCRIPT, AND ALL FILES
 * NORMALLY FOUND IN AN UNMODIFIED MINECRAFT RESOURCE PACK, YOU ARE NOT ALLOWED
 * TO SHARE, DISTRIBUTE, OR REPURPOSE ANY FILE USED BY OR PRODUCED BY THE
 * SOFTWARE IN THIS REPOSITORY WITHOUT PRIOR PERMISSION FROM THE PROJECT AUTHOR.
 * 
 * NOT FOR COMMERCIAL OR MALICIOUS USE
 * 
 * (please read the 'LICENSE' file this repo's root directory for more info) 
 * 
 */
public class Score {
	/**+
	 * Used for sorting score by points
	 */
	public static final Comparator<Score> scoreComparator = new Comparator<Score>() {
		public int compare(Score score, Score score1) {
			return score.getScorePoints() > score1.getScorePoints() ? 1
					: (score.getScorePoints() < score1.getScorePoints() ? -1
							: score1.getPlayerName().compareToIgnoreCase(score.getPlayerName()));
		}
	};
	private final Scoreboard theScoreboard;
	private final ScoreObjective theScoreObjective;
	private final String scorePlayerName;
	private int scorePoints;
	private boolean locked;
	private boolean field_178818_g;

	public Score(Scoreboard theScoreboardIn, ScoreObjective theScoreObjectiveIn, String scorePlayerNameIn) {
		this.theScoreboard = theScoreboardIn;
		this.theScoreObjective = theScoreObjectiveIn;
		this.scorePlayerName = scorePlayerNameIn;
		this.field_178818_g = true;
	}

	public void increseScore(int amount) {
		if (this.theScoreObjective.getCriteria().isReadOnly()) {
			throw new IllegalStateException("Cannot modify read-only score");
		} else {
			this.setScorePoints(this.getScorePoints() + amount);
		}
	}

	public void decreaseScore(int amount) {
		if (this.theScoreObjective.getCriteria().isReadOnly()) {
			throw new IllegalStateException("Cannot modify read-only score");
		} else {
			this.setScorePoints(this.getScorePoints() - amount);
		}
	}

	public void func_96648_a() {
		if (this.theScoreObjective.getCriteria().isReadOnly()) {
			throw new IllegalStateException("Cannot modify read-only score");
		} else {
			this.increseScore(1);
		}
	}

	public int getScorePoints() {
		return this.scorePoints;
	}

	public void setScorePoints(int points) {
		int i = this.scorePoints;
		this.scorePoints = points;
		if (i != points || this.field_178818_g) {
			this.field_178818_g = false;
			this.getScoreScoreboard().func_96536_a(this);
		}

	}

	public ScoreObjective getObjective() {
		return this.theScoreObjective;
	}

	/**+
	 * Returns the name of the player this score belongs to
	 */
	public String getPlayerName() {
		return this.scorePlayerName;
	}

	public Scoreboard getScoreScoreboard() {
		return this.theScoreboard;
	}

	public boolean isLocked() {
		return this.locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void func_96651_a(List<EntityPlayer> parList) {
		this.setScorePoints(this.theScoreObjective.getCriteria().func_96635_a(parList));
	}
}