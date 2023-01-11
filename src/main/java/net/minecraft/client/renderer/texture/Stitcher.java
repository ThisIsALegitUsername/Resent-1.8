package net.minecraft.client.renderer.texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import net.lax1dude.eaglercraft.v1_8.HString;
import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerTextureAtlasSprite;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.client.renderer.StitcherException;
import net.minecraft.util.MathHelper;

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
public class Stitcher {
	private final int mipmapLevelStitcher;
	private final Set<Stitcher.Holder> setStitchHolders = Sets.newHashSetWithExpectedSize(256);
	private final List<Stitcher.Slot> stitchSlots = Lists.newArrayListWithCapacity(256);
	private int currentWidth;
	private int currentHeight;
	private final int maxWidth;
	private final int maxHeight;
	private final boolean forcePowerOf2;
	private final int maxTileDimension;

	public Stitcher(int maxTextureWidth, int maxTextureHeight, boolean parFlag, int parInt1, int mipmapLevel) {
		this.mipmapLevelStitcher = mipmapLevel;
		this.maxWidth = maxTextureWidth;
		this.maxHeight = maxTextureHeight;
		this.forcePowerOf2 = parFlag;
		this.maxTileDimension = parInt1;
	}

	public int getCurrentWidth() {
		return this.currentWidth;
	}

	public int getCurrentHeight() {
		return this.currentHeight;
	}

	public void addSprite(EaglerTextureAtlasSprite parTextureAtlasSprite) {
		Stitcher.Holder stitcher$holder = new Stitcher.Holder(parTextureAtlasSprite, this.mipmapLevelStitcher);
		if (this.maxTileDimension > 0) {
			stitcher$holder.setNewDimension(this.maxTileDimension);
		}

		this.setStitchHolders.add(stitcher$holder);
	}

	public void doStitch() {
		Stitcher.Holder[] astitcher$holder = (Stitcher.Holder[]) this.setStitchHolders
				.toArray(new Stitcher.Holder[this.setStitchHolders.size()]);
		Arrays.sort(astitcher$holder);

		for (Stitcher.Holder stitcher$holder : astitcher$holder) {
			if (!this.allocateSlot(stitcher$holder)) {
				String s = HString.format("Unable to fit: %s - size: %dx%d - Maybe try a lowerresolution resourcepack?",
						new Object[] { stitcher$holder.getAtlasSprite().getIconName(),
								Integer.valueOf(stitcher$holder.getAtlasSprite().getIconWidth()),
								Integer.valueOf(stitcher$holder.getAtlasSprite().getIconHeight()) });
				throw new StitcherException(stitcher$holder, s);
			}
		}

		if (this.forcePowerOf2) {
			this.currentWidth = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
			this.currentHeight = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
		}

	}

	public List<EaglerTextureAtlasSprite> getStichSlots() {
		ArrayList arraylist = Lists.newArrayList();

		for (Stitcher.Slot stitcher$slot : this.stitchSlots) {
			stitcher$slot.getAllStitchSlots(arraylist);
		}

		ArrayList arraylist1 = Lists.newArrayList();

		for (Stitcher.Slot stitcher$slot1 : (List<Stitcher.Slot>) arraylist) {
			Stitcher.Holder stitcher$holder = stitcher$slot1.getStitchHolder();
			EaglerTextureAtlasSprite textureatlassprite = stitcher$holder.getAtlasSprite();
			textureatlassprite.initSprite(this.currentWidth, this.currentHeight, stitcher$slot1.getOriginX(),
					stitcher$slot1.getOriginY(), stitcher$holder.isRotated());
			arraylist1.add(textureatlassprite);
		}

		return arraylist1;
	}

	private static int getMipmapDimension(int parInt1, int parInt2) {
		return (parInt1 >> parInt2) + ((parInt1 & (1 << parInt2) - 1) == 0 ? 0 : 1) << parInt2;
	}

	/**+
	 * Attempts to find space for specified tile
	 */
	private boolean allocateSlot(Stitcher.Holder parHolder) {
		for (int i = 0; i < this.stitchSlots.size(); ++i) {
			if (((Stitcher.Slot) this.stitchSlots.get(i)).addSlot(parHolder)) {
				return true;
			}

			parHolder.rotate();
			if (((Stitcher.Slot) this.stitchSlots.get(i)).addSlot(parHolder)) {
				return true;
			}

			parHolder.rotate();
		}

		return this.expandAndAllocateSlot(parHolder);
	}

	/**+
	 * Expand stitched texture in order to make space for specified
	 * tile
	 */
	private boolean expandAndAllocateSlot(Stitcher.Holder parHolder) {
		int i = Math.min(parHolder.getWidth(), parHolder.getHeight());
		boolean flag = this.currentWidth == 0 && this.currentHeight == 0;
		boolean flag1;
		if (this.forcePowerOf2) {
			int j = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
			int k = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
			int l = MathHelper.roundUpToPowerOfTwo(this.currentWidth + i);
			int i1 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + i);
			boolean flag2 = l <= this.maxWidth;
			boolean flag3 = i1 <= this.maxHeight;
			if (!flag2 && !flag3) {
				return false;
			}

			boolean flag4 = j != l;
			boolean flag5 = k != i1;
			if (flag4 ^ flag5) {
				flag1 = !flag4;
			} else {
				flag1 = flag2 && j <= k;
			}
		} else {
			boolean flag6 = this.currentWidth + i <= this.maxWidth;
			boolean flag7 = this.currentHeight + i <= this.maxHeight;
			if (!flag6 && !flag7) {
				return false;
			}

			flag1 = flag6 && (flag || this.currentWidth <= this.currentHeight);
		}

		int j1 = Math.max(parHolder.getWidth(), parHolder.getHeight());
		if (MathHelper.roundUpToPowerOfTwo(
				(flag1 ? this.currentHeight : this.currentWidth) + j1) > (flag1 ? this.maxHeight : this.maxWidth)) {
			return false;
		} else {
			Stitcher.Slot stitcher$slot;
			if (flag1) {
				if (parHolder.getWidth() > parHolder.getHeight()) {
					parHolder.rotate();
				}

				if (this.currentHeight == 0) {
					this.currentHeight = parHolder.getHeight();
				}

				stitcher$slot = new Stitcher.Slot(this.currentWidth, 0, parHolder.getWidth(), this.currentHeight);
				this.currentWidth += parHolder.getWidth();
			} else {
				stitcher$slot = new Stitcher.Slot(0, this.currentHeight, this.currentWidth, parHolder.getHeight());
				this.currentHeight += parHolder.getHeight();
			}

			stitcher$slot.addSlot(parHolder);
			this.stitchSlots.add(stitcher$slot);
			return true;
		}
	}

	public static class Holder implements Comparable<Stitcher.Holder> {
		private final EaglerTextureAtlasSprite theTexture;
		private final int width;
		private final int height;
		private final int mipmapLevelHolder;
		private boolean rotated;
		private float scaleFactor = 1.0F;

		public Holder(EaglerTextureAtlasSprite parTextureAtlasSprite, int parInt1) {
			this.theTexture = parTextureAtlasSprite;
			this.width = parTextureAtlasSprite.getIconWidth();
			this.height = parTextureAtlasSprite.getIconHeight();
			this.mipmapLevelHolder = parInt1;
			this.rotated = Stitcher.getMipmapDimension(this.height, parInt1) > Stitcher.getMipmapDimension(this.width,
					parInt1);
		}

		public EaglerTextureAtlasSprite getAtlasSprite() {
			return this.theTexture;
		}

		public int getWidth() {
			return this.rotated
					? Stitcher.getMipmapDimension((int) ((float) this.height * this.scaleFactor),
							this.mipmapLevelHolder)
					: Stitcher.getMipmapDimension((int) ((float) this.width * this.scaleFactor),
							this.mipmapLevelHolder);
		}

		public int getHeight() {
			return this.rotated
					? Stitcher.getMipmapDimension((int) ((float) this.width * this.scaleFactor), this.mipmapLevelHolder)
					: Stitcher.getMipmapDimension((int) ((float) this.height * this.scaleFactor),
							this.mipmapLevelHolder);
		}

		public void rotate() {
			this.rotated = !this.rotated;
		}

		public boolean isRotated() {
			return this.rotated;
		}

		public void setNewDimension(int parInt1) {
			if (this.width > parInt1 && this.height > parInt1) {
				this.scaleFactor = (float) parInt1 / (float) Math.min(this.width, this.height);
			}
		}

		public String toString() {
			return "Holder{width=" + this.width + ", height=" + this.height + '}';
		}

		public int compareTo(Stitcher.Holder stitcher$holder) {
			int i;
			if (this.getHeight() == stitcher$holder.getHeight()) {
				if (this.getWidth() == stitcher$holder.getWidth()) {
					if (this.theTexture.getIconName() == null) {
						return stitcher$holder.theTexture.getIconName() == null ? 0 : -1;
					}

					return this.theTexture.getIconName().compareTo(stitcher$holder.theTexture.getIconName());
				}

				i = this.getWidth() < stitcher$holder.getWidth() ? 1 : -1;
			} else {
				i = this.getHeight() < stitcher$holder.getHeight() ? 1 : -1;
			}

			return i;
		}
	}

	public static class Slot {
		private final int originX;
		private final int originY;
		private final int width;
		private final int height;
		private List<Stitcher.Slot> subSlots;
		private Stitcher.Holder holder;

		public Slot(int parInt1, int parInt2, int widthIn, int heightIn) {
			this.originX = parInt1;
			this.originY = parInt2;
			this.width = widthIn;
			this.height = heightIn;
		}

		public Stitcher.Holder getStitchHolder() {
			return this.holder;
		}

		public int getOriginX() {
			return this.originX;
		}

		public int getOriginY() {
			return this.originY;
		}

		public boolean addSlot(Stitcher.Holder holderIn) {
			if (this.holder != null) {
				return false;
			} else {
				int i = holderIn.getWidth();
				int j = holderIn.getHeight();
				if (i <= this.width && j <= this.height) {
					if (i == this.width && j == this.height) {
						this.holder = holderIn;
						return true;
					} else {
						if (this.subSlots == null) {
							this.subSlots = Lists.newArrayListWithCapacity(1);
							this.subSlots.add(new Stitcher.Slot(this.originX, this.originY, i, j));
							int k = this.width - i;
							int l = this.height - j;
							if (l > 0 && k > 0) {
								int i1 = Math.max(this.height, k);
								int j1 = Math.max(this.width, l);
								if (i1 >= j1) {
									this.subSlots.add(new Stitcher.Slot(this.originX, this.originY + j, i, l));
									this.subSlots
											.add(new Stitcher.Slot(this.originX + i, this.originY, k, this.height));
								} else {
									this.subSlots.add(new Stitcher.Slot(this.originX + i, this.originY, k, j));
									this.subSlots.add(new Stitcher.Slot(this.originX, this.originY + j, this.width, l));
								}
							} else if (k == 0) {
								this.subSlots.add(new Stitcher.Slot(this.originX, this.originY + j, i, l));
							} else if (l == 0) {
								this.subSlots.add(new Stitcher.Slot(this.originX + i, this.originY, k, j));
							}
						}

						for (Stitcher.Slot stitcher$slot : this.subSlots) {
							if (stitcher$slot.addSlot(holderIn)) {
								return true;
							}
						}

						return false;
					}
				} else {
					return false;
				}
			}
		}

		public void getAllStitchSlots(List<Stitcher.Slot> parList) {
			if (this.holder != null) {
				parList.add(this);
			} else if (this.subSlots != null) {
				for (Stitcher.Slot stitcher$slot : this.subSlots) {
					stitcher$slot.getAllStitchSlots(parList);
				}
			}

		}

		public String toString() {
			return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height="
					+ this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + '}';
		}
	}
}