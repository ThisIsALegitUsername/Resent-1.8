package net.minecraft.util;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Iterators;
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
public class ClassInheritanceMultiMap<T> extends AbstractSet<T> {
	private static final Set<Class<?>> field_181158_a = Sets.newHashSet();
	private final Map<Class<?>, List<T>> map = Maps.newHashMap();
	private final Set<Class<?>> knownKeys = Sets.newIdentityHashSet();
	private final Class<T> baseClass;
	private final List<T> field_181745_e = Lists.newArrayList();

	public ClassInheritanceMultiMap(Class<T> baseClassIn) {
		this.baseClass = baseClassIn;
		this.knownKeys.add(baseClassIn);
		this.map.put(baseClassIn, this.field_181745_e);

		for (Class oclass : field_181158_a) {
			this.createLookup(oclass);
		}

	}

	protected void createLookup(Class<?> clazz) {
		field_181158_a.add(clazz);

		for (Object object : this.field_181745_e) {
			if (clazz.isAssignableFrom(object.getClass())) {
				this.func_181743_a((T) object, clazz);
			}
		}

		this.knownKeys.add(clazz);
	}

	protected Class<?> func_181157_b(Class<?> parClass1) {
		if (this.baseClass.isAssignableFrom(parClass1)) {
			if (!this.knownKeys.contains(parClass1)) {
				this.createLookup(parClass1);
			}

			return parClass1;
		} else {
			throw new IllegalArgumentException("Don\'t know how to search for " + parClass1);
		}
	}

	public boolean add(T parObject) {
		for (Class oclass : this.knownKeys) {
			if (oclass.isAssignableFrom(parObject.getClass())) {
				this.func_181743_a(parObject, oclass);
			}
		}

		return true;
	}

	private void func_181743_a(T parObject, Class<?> parClass1) {
		List list = (List) this.map.get(parClass1);
		if (list == null) {
			this.map.put(parClass1, (List<T>) Lists.newArrayList(new Object[] { parObject }));
		} else {
			list.add(parObject);
		}

	}

	public boolean remove(Object parObject) {
		Object object = parObject;
		boolean flag = false;

		for (Class oclass : this.knownKeys) {
			if (oclass.isAssignableFrom(object.getClass())) {
				List list = (List) this.map.get(oclass);
				if (list != null && list.remove(object)) {
					flag = true;
				}
			}
		}

		return flag;
	}

	public boolean contains(Object parObject) {
		return Iterators.contains(this.getByClass(parObject.getClass()).iterator(), parObject);
	}

	public <S> Iterable<S> getByClass(final Class<S> clazz) {
		return new Iterable<S>() {
			public Iterator<S> iterator() {
				List list = (List) ClassInheritanceMultiMap.this.map
						.get(ClassInheritanceMultiMap.this.func_181157_b(clazz));
				if (list == null) {
					return Iterators.emptyIterator();
				} else {
					Iterator iterator = list.iterator();
					return Iterators.filter(iterator, clazz);
				}
			}
		};
	}

	public Iterator<T> iterator() {
		return this.field_181745_e.isEmpty() ? Iterators.emptyIterator()
				: Iterators.unmodifiableIterator(this.field_181745_e.iterator());
	}

	public int size() {
		return this.field_181745_e.size();
	}
}