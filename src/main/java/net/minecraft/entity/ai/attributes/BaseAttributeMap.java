package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import net.minecraft.server.management.LowerStringMap;

public abstract class BaseAttributeMap {
	protected final Map<IAttribute, IAttributeInstance> attributes = Maps.newHashMap();
	protected final Map<String, IAttributeInstance> attributesByName = new LowerStringMap();
	protected final Multimap<IAttribute, IAttribute> field_180377_c = HashMultimap.create();

	public IAttributeInstance getAttributeInstance(IAttribute attribute) {
		return (IAttributeInstance) this.attributes.get(attribute);
	}

	public IAttributeInstance getAttributeInstanceByName(String attributeName) {
		return (IAttributeInstance) this.attributesByName.get(attributeName);
	}

	/**+
	 * Registers an attribute with this AttributeMap, returns a
	 * modifiable AttributeInstance associated with this map
	 */
	public IAttributeInstance registerAttribute(IAttribute attribute) {
		if (this.attributesByName.containsKey(attribute.getAttributeUnlocalizedName())) {
			throw new IllegalArgumentException("Attribute is already registered!");
		} else {
			IAttributeInstance iattributeinstance = this.func_180376_c(attribute);
			this.attributesByName.put(attribute.getAttributeUnlocalizedName(), iattributeinstance);
			this.attributes.put(attribute, iattributeinstance);

			for (IAttribute iattribute = attribute.func_180372_d(); iattribute != null; iattribute = iattribute
					.func_180372_d()) {
				this.field_180377_c.put(iattribute, attribute);
			}

			return iattributeinstance;
		}
	}

	protected abstract IAttributeInstance func_180376_c(IAttribute var1);

	public Collection<IAttributeInstance> getAllAttributes() {
		return this.attributesByName.values();
	}

	public void func_180794_a(IAttributeInstance parIAttributeInstance) {
	}

	public void removeAttributeModifiers(Multimap<String, AttributeModifier> parMultimap) {
		for (Entry entry : parMultimap.entries()) {
			IAttributeInstance iattributeinstance = this.getAttributeInstanceByName((String) entry.getKey());
			if (iattributeinstance != null) {
				iattributeinstance.removeModifier((AttributeModifier) entry.getValue());
			}
		}

	}

	public void applyAttributeModifiers(Multimap<String, AttributeModifier> parMultimap) {
		for (Entry entry : parMultimap.entries()) {
			IAttributeInstance iattributeinstance = this.getAttributeInstanceByName((String) entry.getKey());
			if (iattributeinstance != null) {
				iattributeinstance.removeModifier((AttributeModifier) entry.getValue());
				iattributeinstance.applyModifier((AttributeModifier) entry.getValue());
			}
		}

	}
}