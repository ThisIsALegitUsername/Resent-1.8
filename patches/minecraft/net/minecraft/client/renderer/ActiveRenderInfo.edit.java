
# Eagler Context Redacted Diff
# Copyright (c) 2022 lax1dude. All rights reserved.

# Version: 1.0
# Author: lax1dude

> CHANGE  2 : 8  @  2 : 4

~ import net.lax1dude.eaglercraft.v1_8.EagRuntime;
~ import net.lax1dude.eaglercraft.v1_8.internal.buffer.FloatBuffer;
~ import net.lax1dude.eaglercraft.v1_8.internal.buffer.IntBuffer;
~ 
~ import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
~ import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;

> DELETE  11  @  7 : 9

> DELETE  17  @  15 : 17

> CHANGE  19 : 23  @  19 : 23

~ 	private static final int[] VIEWPORT = new int[4];
~ 	private static final float[] MODELVIEW = new float[16];
~ 	private static final float[] PROJECTION = new float[16];
~ 	private static final float[] OBJECTCOORDS = new float[3];

> CHANGE  33 : 38  @  33 : 38

~ 		EaglercraftGPU.glGetInteger(GL_VIEWPORT, VIEWPORT);
~ 		float f = (float) ((VIEWPORT[0] + VIEWPORT[2]) / 2);
~ 		float f1 = (float) ((VIEWPORT[1] + VIEWPORT[3]) / 2);
~ 		GlStateManager.gluUnProject(f, f1, 0.0F, MODELVIEW, PROJECTION, VIEWPORT, OBJECTCOORDS);
~ 		position = new Vec3((double) OBJECTCOORDS[0], (double) OBJECTCOORDS[1], (double) OBJECTCOORDS[2]);

> CHANGE  50 : 51  @  50 : 51

~ 		double d1 = parEntity.prevPosY + (parEntity.posY - parEntity.prevPosY) * parDouble1 + parEntity.getEyeHeight();

> EOF
