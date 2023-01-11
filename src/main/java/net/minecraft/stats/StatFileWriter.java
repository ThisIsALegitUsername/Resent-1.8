package net.minecraft.stats;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;

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
public class StatFileWriter {
	protected final Map<StatBase, TupleIntJsonSerializable> statsData = Maps.newHashMap();

	/**+
	 * Returns true if the achievement has been unlocked.
	 */
	public boolean hasAchievementUnlocked(Achievement achievementIn) {
		return this.readStat(achievementIn) > 0;
	}

	/**+
	 * Returns true if the parent has been unlocked, or there is no
	 * parent
	 */
	public boolean canUnlockAchievement(Achievement achievementIn) {
		return achievementIn.parentAchievement == null || this.hasAchievementUnlocked(achievementIn.parentAchievement);
	}

	public int func_150874_c(Achievement parAchievement) {
		if (this.hasAchievementUnlocked(parAchievement)) {
			return 0;
		} else {
			int i = 0;

			for (Achievement achievement = parAchievement.parentAchievement; achievement != null
					&& !this.hasAchievementUnlocked(achievement); ++i) {
				achievement = achievement.parentAchievement;
			}

			return i;
		}
	}

	public void increaseStat(EntityPlayer player, StatBase stat, int amount) {
		if (!stat.isAchievement() || this.canUnlockAchievement((Achievement) stat)) {
			this.unlockAchievement(player, stat, this.readStat(stat) + amount);
		}
	}

	/**+
	 * Triggers the logging of an achievement and attempts to
	 * announce to server
	 */
	public void unlockAchievement(EntityPlayer var1, StatBase statbase, int i) {
		TupleIntJsonSerializable tupleintjsonserializable = (TupleIntJsonSerializable) this.statsData.get(statbase);
		if (tupleintjsonserializable == null) {
			tupleintjsonserializable = new TupleIntJsonSerializable();
			this.statsData.put(statbase, tupleintjsonserializable);
		}

		tupleintjsonserializable.setIntegerValue(i);
	}

	/**+
	 * Reads the given stat and returns its value as an int.
	 */
	public int readStat(StatBase stat) {
		TupleIntJsonSerializable tupleintjsonserializable = (TupleIntJsonSerializable) this.statsData.get(stat);
		return tupleintjsonserializable == null ? 0 : tupleintjsonserializable.getIntegerValue();
	}

	public <T extends IJsonSerializable> T func_150870_b(StatBase parStatBase) {
		TupleIntJsonSerializable tupleintjsonserializable = (TupleIntJsonSerializable) this.statsData.get(parStatBase);
		return (T) (tupleintjsonserializable != null ? tupleintjsonserializable.getJsonSerializableValue() : null);
	}

	public <T extends IJsonSerializable> T func_150872_a(StatBase parStatBase, T parIJsonSerializable) {
		TupleIntJsonSerializable tupleintjsonserializable = (TupleIntJsonSerializable) this.statsData.get(parStatBase);
		if (tupleintjsonserializable == null) {
			tupleintjsonserializable = new TupleIntJsonSerializable();
			this.statsData.put(parStatBase, tupleintjsonserializable);
		}

		tupleintjsonserializable.setJsonSerializableValue(parIJsonSerializable);
		return (T) parIJsonSerializable;
	}
}