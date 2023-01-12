package net.minecraft.util;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

public abstract class ChatComponentStyle implements IChatComponent {
	/**+
	 * The later siblings of this component. If this component turns
	 * the text bold, that will apply to all the siblings until a
	 * later sibling turns the text something else.
	 */
	protected List<IChatComponent> siblings = Lists.newArrayList();
	private ChatStyle style;

	/**+
	 * Appends the given component to the end of this one.
	 */
	public IChatComponent appendSibling(IChatComponent component) {
		component.getChatStyle().setParentStyle(this.getChatStyle());
		this.siblings.add(component);
		return this;
	}

	/**+
	 * Gets the sibling components of this one.
	 */
	public List<IChatComponent> getSiblings() {
		return this.siblings;
	}

	/**+
	 * Appends the given text to the end of this component.
	 */
	public IChatComponent appendText(String text) {
		return this.appendSibling(new ChatComponentText(text));
	}

	public IChatComponent setChatStyle(ChatStyle style) {
		this.style = style;

		for (IChatComponent ichatcomponent : this.siblings) {
			ichatcomponent.getChatStyle().setParentStyle(this.getChatStyle());
		}

		return this;
	}

	public ChatStyle getChatStyle() {
		if (this.style == null) {
			this.style = new ChatStyle();

			for (IChatComponent ichatcomponent : this.siblings) {
				ichatcomponent.getChatStyle().setParentStyle(this.style);
			}
		}

		return this.style;
	}

	public Iterator<IChatComponent> iterator() {
		return Iterators.concat(Iterators.forArray(new ChatComponentStyle[] { this }),
				createDeepCopyIterator(this.siblings));
	}

	/**+
	 * Get the text of this component, <em>and all child
	 * components</em>, with all special formatting codes removed.
	 */
	public final String getUnformattedText() {
		StringBuilder stringbuilder = new StringBuilder();

		for (IChatComponent ichatcomponent : this) {
			stringbuilder.append(ichatcomponent.getUnformattedTextForChat());
		}

		return stringbuilder.toString();
	}

	/**+
	 * Gets the text of this component, with formatting codes added
	 * for rendering.
	 */
	public final String getFormattedText() {
		StringBuilder stringbuilder = new StringBuilder();

		for (IChatComponent ichatcomponent : this) {
			stringbuilder.append(ichatcomponent.getChatStyle().getFormattingCode());
			stringbuilder.append(ichatcomponent.getUnformattedTextForChat());
			stringbuilder.append(EnumChatFormatting.RESET);
		}

		return stringbuilder.toString();
	}

	/**+
	 * Creates an iterator that iterates over the given components,
	 * returning deep copies of each component in turn so that the
	 * properties of the returned objects will remain externally
	 * consistent after being returned.
	 */
	public static Iterator<IChatComponent> createDeepCopyIterator(Iterable<IChatComponent> components) {
		Iterator iterator = Iterators.concat(
				Iterators.transform(components.iterator(), new Function<IChatComponent, Iterator<IChatComponent>>() {
					public Iterator<IChatComponent> apply(IChatComponent ichatcomponent) {
						return ichatcomponent.iterator();
					}
				}));
		iterator = Iterators.transform(iterator, new Function<IChatComponent, IChatComponent>() {
			public IChatComponent apply(IChatComponent ichatcomponent) {
				IChatComponent ichatcomponent1 = ichatcomponent.createCopy();
				ichatcomponent1.setChatStyle(ichatcomponent1.getChatStyle().createDeepCopy());
				return ichatcomponent1;
			}
		});
		return iterator;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof ChatComponentStyle)) {
			return false;
		} else {
			ChatComponentStyle chatcomponentstyle = (ChatComponentStyle) object;
			return this.siblings.equals(chatcomponentstyle.siblings)
					&& this.getChatStyle().equals(chatcomponentstyle.getChatStyle());
		}
	}

	public int hashCode() {
		return 31 * this.style.hashCode() + this.siblings.hashCode();
	}

	public String toString() {
		return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
	}
}