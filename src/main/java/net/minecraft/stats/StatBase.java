package net.minecraft.stats;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import net.minecraft.event.HoverEvent;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IJsonSerializable;

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
public class StatBase {
	public final String statId;
	private final IChatComponent statName;
	public boolean isIndependent;
	private final IStatType type;
	private final IScoreObjectiveCriteria field_150957_c;
	private Class<? extends IJsonSerializable> field_150956_d;
	private static NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.US);
	public static IStatType simpleStatType = new IStatType() {
		public String format(int parInt1) {
			return StatBase.numberFormat.format((long) parInt1);
		}
	};
	private static DecimalFormat decimalFormat = new DecimalFormat("########0.00");
	public static IStatType timeStatType = new IStatType() {
		public String format(int i) {
			double d0 = (double) i / 20.0D;
			double d1 = d0 / 60.0D;
			double d2 = d1 / 60.0D;
			double d3 = d2 / 24.0D;
			double d4 = d3 / 365.0D;
			return d4 > 0.5D ? StatBase.decimalFormat.format(d4) + " y"
					: (d3 > 0.5D ? StatBase.decimalFormat.format(d3) + " d"
							: (d2 > 0.5D ? StatBase.decimalFormat.format(d2) + " h"
									: (d1 > 0.5D ? StatBase.decimalFormat.format(d1) + " m" : d0 + " s")));
		}
	};
	public static IStatType distanceStatType = new IStatType() {
		public String format(int i) {
			double d0 = (double) i / 100.0D;
			double d1 = d0 / 1000.0D;
			return d1 > 0.5D ? StatBase.decimalFormat.format(d1) + " km"
					: (d0 > 0.5D ? StatBase.decimalFormat.format(d0) + " m" : i + " cm");
		}
	};
	public static IStatType field_111202_k = new IStatType() {
		public String format(int i) {
			return StatBase.decimalFormat.format((double) i * 0.1D);
		}
	};

	public StatBase(String statIdIn, IChatComponent statNameIn, IStatType typeIn) {
		this.statId = statIdIn;
		this.statName = statNameIn;
		this.type = typeIn;
		this.field_150957_c = new ObjectiveStat(this);
		IScoreObjectiveCriteria.INSTANCES.put(this.field_150957_c.getName(), this.field_150957_c);
	}

	public StatBase(String statIdIn, IChatComponent statNameIn) {
		this(statIdIn, statNameIn, simpleStatType);
	}

	/**+
	 * Initializes the current stat as independent (i.e., lacking
	 * prerequisites for being updated) and returns the current
	 * instance.
	 */
	public StatBase initIndependentStat() {
		this.isIndependent = true;
		return this;
	}

	/**+
	 * Register the stat into StatList.
	 */
	public StatBase registerStat() {
		if (StatList.oneShotStats.containsKey(this.statId)) {
			throw new RuntimeException(
					"Duplicate stat id: \"" + ((StatBase) StatList.oneShotStats.get(this.statId)).statName + "\" and \""
							+ this.statName + "\" at id " + this.statId);
		} else {
			StatList.allStats.add(this);
			StatList.oneShotStats.put(this.statId, this);
			return this;
		}
	}

	/**+
	 * Returns whether or not the StatBase-derived class is a
	 * statistic (running counter) or an achievement (one-shot).
	 */
	public boolean isAchievement() {
		return false;
	}

	public String format(int parInt1) {
		return this.type.format(parInt1);
	}

	public IChatComponent getStatName() {
		IChatComponent ichatcomponent = this.statName.createCopy();
		ichatcomponent.getChatStyle().setColor(EnumChatFormatting.GRAY);
		ichatcomponent.getChatStyle().setChatHoverEvent(
				new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new ChatComponentText(this.statId)));
		return ichatcomponent;
	}

	public IChatComponent func_150955_j() {
		IChatComponent ichatcomponent = this.getStatName();
		IChatComponent ichatcomponent1 = (new ChatComponentText("[")).appendSibling(ichatcomponent).appendText("]");
		ichatcomponent1.setChatStyle(ichatcomponent.getChatStyle());
		return ichatcomponent1;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			StatBase statbase = (StatBase) object;
			return this.statId.equals(statbase.statId);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return this.statId.hashCode();
	}

	public String toString() {
		return "Stat{id=" + this.statId + ", nameId=" + this.statName + ", awardLocallyOnly=" + this.isIndependent
				+ ", formatter=" + this.type + ", objectiveCriteria=" + this.field_150957_c + '}';
	}

	public IScoreObjectiveCriteria func_150952_k() {
		return this.field_150957_c;
	}

	public Class<? extends IJsonSerializable> func_150954_l() {
		return this.field_150956_d;
	}

	public StatBase func_150953_b(Class<? extends IJsonSerializable> oclass) {
		this.field_150956_d = oclass;
		return this;
	}
}