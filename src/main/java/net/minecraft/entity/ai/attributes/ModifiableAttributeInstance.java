package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

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
public class ModifiableAttributeInstance implements IAttributeInstance {
	private final BaseAttributeMap attributeMap;
	private final IAttribute genericAttribute;
	private final Map<Integer, Set<AttributeModifier>> mapByOperation = Maps.newHashMap();
	private final Map<String, Set<AttributeModifier>> mapByName = Maps.newHashMap();
	private final Map<EaglercraftUUID, AttributeModifier> mapByUUID = Maps.newHashMap();
	private double baseValue;
	private boolean needsUpdate = true;
	private double cachedValue;

	public ModifiableAttributeInstance(BaseAttributeMap attributeMapIn, IAttribute genericAttributeIn) {
		this.attributeMap = attributeMapIn;
		this.genericAttribute = genericAttributeIn;
		this.baseValue = genericAttributeIn.getDefaultValue();

		for (int i = 0; i < 3; ++i) {
			this.mapByOperation.put(Integer.valueOf(i), Sets.newHashSet());
		}

	}

	/**+
	 * Get the Attribute this is an instance of
	 */
	public IAttribute getAttribute() {
		return this.genericAttribute;
	}

	public double getBaseValue() {
		return this.baseValue;
	}

	public void setBaseValue(double d0) {
		if (d0 != this.getBaseValue()) {
			this.baseValue = d0;
			this.flagForUpdate();
		}
	}

	public Collection<AttributeModifier> getModifiersByOperation(int i) {
		return (Collection) this.mapByOperation.get(Integer.valueOf(i));
	}

	public Collection<AttributeModifier> func_111122_c() {
		HashSet hashset = Sets.newHashSet();

		for (int i = 0; i < 3; ++i) {
			hashset.addAll(this.getModifiersByOperation(i));
		}

		return hashset;
	}

	/**+
	 * Returns attribute modifier, if any, by the given UUID
	 */
	public AttributeModifier getModifier(EaglercraftUUID uuid) {
		return (AttributeModifier) this.mapByUUID.get(uuid);
	}

	public boolean hasModifier(AttributeModifier attributemodifier) {
		return this.mapByUUID.get(attributemodifier.getID()) != null;
	}

	public void applyModifier(AttributeModifier attributemodifier) {
		if (this.getModifier(attributemodifier.getID()) != null) {
			throw new IllegalArgumentException("Modifier is already applied on this attribute!");
		} else {
			Set<AttributeModifier> object = (Set) this.mapByName.get(attributemodifier.getName());
			if (object == null) {
				object = Sets.newHashSet();
				this.mapByName.put(attributemodifier.getName(), object);
			}

			((Set) this.mapByOperation.get(Integer.valueOf(attributemodifier.getOperation()))).add(attributemodifier);
			((Set) object).add(attributemodifier);
			this.mapByUUID.put(attributemodifier.getID(), attributemodifier);
			this.flagForUpdate();
		}
	}

	protected void flagForUpdate() {
		this.needsUpdate = true;
		this.attributeMap.func_180794_a(this);
	}

	public void removeModifier(AttributeModifier attributemodifier) {
		for (int i = 0; i < 3; ++i) {
			Set set = (Set) this.mapByOperation.get(Integer.valueOf(i));
			set.remove(attributemodifier);
		}

		Set set1 = (Set) this.mapByName.get(attributemodifier.getName());
		if (set1 != null) {
			set1.remove(attributemodifier);
			if (set1.isEmpty()) {
				this.mapByName.remove(attributemodifier.getName());
			}
		}

		this.mapByUUID.remove(attributemodifier.getID());
		this.flagForUpdate();
	}

	public void removeAllModifiers() {
		Collection collection = this.func_111122_c();
		if (collection != null) {
			for (AttributeModifier attributemodifier : (List<AttributeModifier>) Lists.newArrayList(collection)) {
				this.removeModifier(attributemodifier);
			}

		}
	}

	public double getAttributeValue() {
		if (this.needsUpdate) {
			this.cachedValue = this.computeValue();
			this.needsUpdate = false;
		}

		return this.cachedValue;
	}

	private double computeValue() {
		double d0 = this.getBaseValue();

		for (AttributeModifier attributemodifier : this.func_180375_b(0)) {
			d0 += attributemodifier.getAmount();
		}

		double d1 = d0;

		for (AttributeModifier attributemodifier1 : this.func_180375_b(1)) {
			d1 += d0 * attributemodifier1.getAmount();
		}

		for (AttributeModifier attributemodifier2 : this.func_180375_b(2)) {
			d1 *= 1.0D + attributemodifier2.getAmount();
		}

		return this.genericAttribute.clampValue(d1);
	}

	private Collection<AttributeModifier> func_180375_b(int parInt1) {
		HashSet hashset = Sets.newHashSet(this.getModifiersByOperation(parInt1));

		for (IAttribute iattribute = this.genericAttribute.func_180372_d(); iattribute != null; iattribute = iattribute
				.func_180372_d()) {
			IAttributeInstance iattributeinstance = this.attributeMap.getAttributeInstance(iattribute);
			if (iattributeinstance != null) {
				hashset.addAll(iattributeinstance.getModifiersByOperation(parInt1));
			}
		}

		return hashset;
	}
}