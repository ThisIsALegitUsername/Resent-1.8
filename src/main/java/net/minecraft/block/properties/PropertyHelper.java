package net.minecraft.block.properties;

import com.google.common.base.Objects;

public abstract class PropertyHelper<T extends Comparable<T>> implements IProperty<T> {
	private final Class<T> valueClass;
	private final String name;

	protected PropertyHelper(String name, Class<T> valueClass) {
		this.valueClass = valueClass;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	/**+
	 * The class of the values of this property
	 */
	public Class<T> getValueClass() {
		return this.valueClass;
	}

	public String toString() {
		return Objects.toStringHelper(this).add("name", this.name).add("clazz", this.valueClass)
				.add("values", this.getAllowedValues()).toString();
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			PropertyHelper propertyhelper = (PropertyHelper) object;
			return this.valueClass.equals(propertyhelper.valueClass) && this.name.equals(propertyhelper.name);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return 31 * this.valueClass.hashCode() + this.name.hashCode();
	}
}