package net.minecraft.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;

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
public class Cartesian {
	public static <T> Iterable<T[]> cartesianProduct(Class<T> clazz, Iterable<? extends Iterable<? extends T>> sets) {
		return new Cartesian.Product(clazz, (Iterable[]) toArray(Iterable.class, sets));
	}

	public static <T> Iterable<List<T>> cartesianProduct(Iterable<? extends Iterable<? extends T>> sets) {
		/**+
		 * Convert an Iterable of Arrays (Object[]) to an Iterable of
		 * Lists
		 */
		return arraysAsLists(cartesianProduct(Object.class, sets));
	}

	private static <T> Iterable<List<T>> arraysAsLists(Iterable<Object[]> arrays) {
		return Iterables.transform(arrays, new Cartesian.GetList());
	}

	private static <T> T[] toArray(Class<? super T> clazz, Iterable<? extends T> it) {
		ArrayList arraylist = Lists.newArrayList();

		for (Object object : it) {
			arraylist.add(object);
		}

		return (T[]) ((Object[]) arraylist.toArray(createArray(clazz, arraylist.size())));
	}

	private static <T> T[] createArray(Class<? super T> parClass1, int parInt1) {
		return (T[]) ((Object[]) ((Object[]) Array.newInstance(parClass1, parInt1)));
	}

	static class GetList<T> implements Function<Object[], List<T>> {
		private GetList() {
		}

		public List<T> apply(Object[] aobject) {
			return (List<T>) Arrays.asList((Object[]) aobject);
		}
	}

	static class Product<T> implements Iterable<T[]> {
		private final Class<T> clazz;
		private final Iterable<? extends T>[] iterables;

		private Product(Class<T> clazz, Iterable<? extends T>[] iterables) {
			this.clazz = clazz;
			this.iterables = iterables;
		}

		public Iterator<T[]> iterator() {
			return (Iterator<T[]>) (this.iterables.length <= 0
					? Collections.singletonList((T[]) Cartesian.createArray(this.clazz, 0)).iterator()
					: new Cartesian.Product.ProductIterator(this.clazz, this.iterables));
		}

		static class ProductIterator<T> extends UnmodifiableIterator<T[]> {
			private int index;
			private final Iterable<? extends T>[] iterables;
			private final Iterator<? extends T>[] iterators;
			private final T[] results;

			private ProductIterator(Class<T> clazz, Iterable<? extends T>[] iterables) {
				this.index = -2;
				this.iterables = iterables;
				this.iterators = (Iterator[]) Cartesian.createArray(Iterator.class, this.iterables.length);

				for (int i = 0; i < this.iterables.length; ++i) {
					this.iterators[i] = iterables[i].iterator();
				}

				this.results = Cartesian.createArray(clazz, this.iterators.length);
			}

			private void endOfData() {
				this.index = -1;
				Arrays.fill(this.iterators, (Object) null);
				Arrays.fill(this.results, (Object) null);
			}

			public boolean hasNext() {
				if (this.index == -2) {
					this.index = 0;

					for (Iterator iterator1 : this.iterators) {
						if (!iterator1.hasNext()) {
							this.endOfData();
							break;
						}
					}

					return true;
				} else {
					if (this.index >= this.iterators.length) {
						for (this.index = this.iterators.length - 1; this.index >= 0; --this.index) {
							Iterator iterator = this.iterators[this.index];
							if (iterator.hasNext()) {
								break;
							}

							if (this.index == 0) {
								this.endOfData();
								break;
							}

							iterator = this.iterables[this.index].iterator();
							this.iterators[this.index] = iterator;
							if (!iterator.hasNext()) {
								this.endOfData();
								break;
							}
						}
					}

					return this.index >= 0;
				}
			}

			public T[] next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				} else {
					while (this.index < this.iterators.length) {
						this.results[this.index] = this.iterators[this.index].next();
						++this.index;
					}

					return (T[]) ((Object[]) this.results.clone());
				}
			}
		}
	}
}