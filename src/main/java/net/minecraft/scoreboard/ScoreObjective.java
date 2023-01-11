package net.minecraft.scoreboard;

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
public class ScoreObjective {
	private final Scoreboard theScoreboard;
	private final String name;
	private final IScoreObjectiveCriteria objectiveCriteria;
	private IScoreObjectiveCriteria.EnumRenderType renderType;
	private String displayName;

	public ScoreObjective(Scoreboard theScoreboardIn, String nameIn, IScoreObjectiveCriteria objectiveCriteriaIn) {
		this.theScoreboard = theScoreboardIn;
		this.name = nameIn;
		this.objectiveCriteria = objectiveCriteriaIn;
		this.displayName = nameIn;
		this.renderType = objectiveCriteriaIn.getRenderType();
	}

	public Scoreboard getScoreboard() {
		return this.theScoreboard;
	}

	public String getName() {
		return this.name;
	}

	public IScoreObjectiveCriteria getCriteria() {
		return this.objectiveCriteria;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String nameIn) {
		this.displayName = nameIn;
		this.theScoreboard.func_96532_b(this);
	}

	public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
		return this.renderType;
	}

	public void setRenderType(IScoreObjectiveCriteria.EnumRenderType type) {
		this.renderType = type;
		this.theScoreboard.func_96532_b(this);
	}
}