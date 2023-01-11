package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.lax1dude.eaglercraft.v1_8.EaglerZLIB;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;

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
public class CompressedStreamTools {
	/**+
	 * Load the gzipped compound from the inputstream.
	 */
	public static NBTTagCompound readCompressed(InputStream is) throws IOException {
		DataInputStream datainputstream = new DataInputStream(
				new BufferedInputStream(EaglerZLIB.newGZIPInputStream(is)));

		NBTTagCompound nbttagcompound;
		try {
			nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
		} finally {
			datainputstream.close();
		}

		return nbttagcompound;
	}

	/**+
	 * Write the compound, gzipped, to the outputstream.
	 */
	public static void writeCompressed(NBTTagCompound outputStream, OutputStream parOutputStream) throws IOException {
		DataOutputStream dataoutputstream = new DataOutputStream(
				new BufferedOutputStream(EaglerZLIB.newGZIPOutputStream(parOutputStream)));

		try {
			write(outputStream, (DataOutput) dataoutputstream);
		} finally {
			dataoutputstream.close();
		}

	}

	/**+
	 * Reads the given DataInput, constructs, and returns an
	 * NBTTagCompound with the data from the DataInput
	 */
	public static NBTTagCompound read(DataInputStream inputStream) throws IOException {
		/**+
		 * Reads the given DataInput, constructs, and returns an
		 * NBTTagCompound with the data from the DataInput
		 */
		return read(inputStream, NBTSizeTracker.INFINITE);
	}

	/**+
	 * Reads the given DataInput, constructs, and returns an
	 * NBTTagCompound with the data from the DataInput
	 */
	public static NBTTagCompound read(DataInput parDataInput, NBTSizeTracker parNBTSizeTracker) throws IOException {
		NBTBase nbtbase = func_152455_a(parDataInput, 0, parNBTSizeTracker);
		if (nbtbase instanceof NBTTagCompound) {
			return (NBTTagCompound) nbtbase;
		} else {
			throw new IOException("Root tag must be a named compound tag");
		}
	}

	public static void write(NBTTagCompound parNBTTagCompound, DataOutput parDataOutput) throws IOException {
		writeTag(parNBTTagCompound, parDataOutput);
	}

	private static void writeTag(NBTBase parNBTBase, DataOutput parDataOutput) throws IOException {
		parDataOutput.writeByte(parNBTBase.getId());
		if (parNBTBase.getId() != 0) {
			parDataOutput.writeUTF("");
			parNBTBase.write(parDataOutput);
		}
	}

	private static NBTBase func_152455_a(DataInput parDataInput, int parInt1, NBTSizeTracker parNBTSizeTracker)
			throws IOException {
		byte b0 = parDataInput.readByte();
		if (b0 == 0) {
			return new NBTTagEnd();
		} else {
			parDataInput.readUTF();
			NBTBase nbtbase = NBTBase.createNewByType(b0);

			try {
				nbtbase.read(parDataInput, parInt1, parNBTSizeTracker);
				return nbtbase;
			} catch (IOException ioexception) {
				CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
				crashreportcategory.addCrashSection("Tag name", "[UNNAMED TAG]");
				crashreportcategory.addCrashSection("Tag type", Byte.valueOf(b0));
				throw new ReportedException(crashreport);
			}
		}
	}
}