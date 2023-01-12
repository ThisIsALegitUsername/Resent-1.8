package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.server.management.LowerStringMap;

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
public class ServersideAttributeMap extends BaseAttributeMap {
	private final Set<IAttributeInstance> attributeInstanceSet = Sets.newHashSet();
	protected final Map<String, IAttributeInstance> descriptionToAttributeInstanceMap = new LowerStringMap();

	public ModifiableAttributeInstance getAttributeInstance(IAttribute iattribute) {
		return (ModifiableAttributeInstance) super.getAttributeInstance(iattribute);
	}

	public ModifiableAttributeInstance getAttributeInstanceByName(String s) {
		IAttributeInstance iattributeinstance = super.getAttributeInstanceByName(s);
		if (iattributeinstance == null) {
			iattributeinstance = (IAttributeInstance) this.descriptionToAttributeInstanceMap.get(s);
		}

		return (ModifiableAttributeInstance) iattributeinstance;
	}

	/**+
	 * Registers an attribute with this AttributeMap, returns a
	 * modifiable AttributeInstance associated with this map
	 */
	public IAttributeInstance registerAttribute(IAttribute iattribute) {
		IAttributeInstance iattributeinstance = super.registerAttribute(iattribute);
		if (iattribute instanceof RangedAttribute && ((RangedAttribute) iattribute).getDescription() != null) {
			this.descriptionToAttributeInstanceMap.put(((RangedAttribute) iattribute).getDescription(),
					iattributeinstance);
		}

		return iattributeinstance;
	}

	protected IAttributeInstance func_180376_c(IAttribute iattribute) {
		return new ModifiableAttributeInstance(this, iattribute);
	}

	public void func_180794_a(IAttributeInstance iattributeinstance) {
		if (iattributeinstance.getAttribute().getShouldWatch()) {
			this.attributeInstanceSet.add(iattributeinstance);
		}

		for (IAttribute iattribute : this.field_180377_c.get(iattributeinstance.getAttribute())) {
			ModifiableAttributeInstance modifiableattributeinstance = this.getAttributeInstance(iattribute);
			if (modifiableattributeinstance != null) {
				modifiableattributeinstance.flagForUpdate();
			}
		}

	}

	public Set<IAttributeInstance> getAttributeInstanceSet() {
		return this.attributeInstanceSet;
	}

	public Collection<IAttributeInstance> getWatchedAttributes() {
		HashSet hashset = Sets.newHashSet();

		for (IAttributeInstance iattributeinstance : this.getAllAttributes()) {
			if (iattributeinstance.getAttribute().getShouldWatch()) {
				hashset.add(iattributeinstance);
			}
		}

		return hashset;
	}
}