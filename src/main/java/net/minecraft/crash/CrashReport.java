package net.minecraft.crash;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.ArrayUtils;
import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.IOUtils;
import net.lax1dude.eaglercraft.v1_8.internal.EnumPlatformType;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
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
public class CrashReport {
	private static final Logger logger = LogManager.getLogger();
	private final String description;
	private final Throwable cause;
	/**+
	 * Category of crash
	 */
	private final CrashReportCategory theReportCategory = new CrashReportCategory(this, "System Details");
	/**+
	 * Holds the keys and values of all crash report sections.
	 */
	private final List<CrashReportCategory> crashReportSections = Lists.newArrayList();
	private boolean field_85059_f = true;
	private String[] stacktrace;

	public CrashReport(String descriptionIn, Throwable causeThrowable) {
		this.description = descriptionIn;
		this.cause = causeThrowable;
		this.stacktrace = EagRuntime.getStackTraceElements(causeThrowable);
		this.populateEnvironment();
	}

	/**+
	 * Populates this crash report with initial information about
	 * the running server and operating system / java environment
	 */
	private void populateEnvironment() {
		this.theReportCategory.addCrashSectionCallable("Minecraft Version", new Callable<String>() {
			public String call() {
				return "1.8.8";
			}
		});
		this.theReportCategory.addCrashSectionCallable("Operating System", new Callable<String>() {
			public String call() {
				return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version "
						+ System.getProperty("os.version");
			}
		});
		this.theReportCategory.addCrashSectionCallable("Java Version", new Callable<String>() {
			public String call() {
				return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
			}
		});
		this.theReportCategory.addCrashSectionCallable("Java VM Version", new Callable<String>() {
			public String call() {
				return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), "
						+ System.getProperty("java.vm.vendor");
			}
		});
		if (EagRuntime.getPlatformType() != EnumPlatformType.JAVASCRIPT) {
			this.theReportCategory.addCrashSectionCallable("Memory", new Callable<String>() {
				public String call() {
					long i = EagRuntime.maxMemory();
					long j = EagRuntime.totalMemory();
					long k = EagRuntime.freeMemory();
					long l = i / 1024L / 1024L;
					long i1 = j / 1024L / 1024L;
					long j1 = k / 1024L / 1024L;
					return k + " bytes (" + j1 + " MB) / " + j + " bytes (" + i1 + " MB) up to " + i + " bytes (" + l
							+ " MB)";
				}
			});
		}
	}

	/**+
	 * Returns the description of the Crash Report.
	 */
	public String getDescription() {
		return this.description;
	}

	/**+
	 * Returns the Throwable object that is the cause for the crash
	 * and Crash Report.
	 */
	public Throwable getCrashCause() {
		return this.cause;
	}

	/**+
	 * Gets the various sections of the crash report into the given
	 * StringBuilder
	 */
	public void getSectionsInStringBuilder(StringBuilder builder) {
		if ((this.stacktrace == null || this.stacktrace.length <= 0) && this.crashReportSections.size() > 0) {
			this.stacktrace = (String[]) ArrayUtils
					.subarray(((CrashReportCategory) this.crashReportSections.get(0)).getStackTrace(), 0, 1);
		}

		if (this.stacktrace != null && this.stacktrace.length > 0) {
			builder.append("-- Head --\n");
			builder.append("Stacktrace:\n");

			for (String stacktraceelement : this.stacktrace) {
				builder.append("\t").append("at ").append(stacktraceelement.toString());
				builder.append("\n");
			}

			builder.append("\n");
		}

		for (CrashReportCategory crashreportcategory : this.crashReportSections) {
			crashreportcategory.appendToStringBuilder(builder);
			builder.append("\n\n");
		}

		this.theReportCategory.appendToStringBuilder(builder);
	}

	/**+
	 * Gets the stack trace of the Throwable that caused this crash
	 * report, or if that fails, the cause .toString().
	 */
	public String getCauseStackTraceOrString() {
		StringBuilder stackTrace = new StringBuilder();

		if ((this.cause.getMessage() == null || this.cause.getMessage().length() == 0)
				&& ((this.cause instanceof NullPointerException) || (this.cause instanceof StackOverflowError)
						|| (this.cause instanceof OutOfMemoryError))) {
			stackTrace.append(this.cause.getClass().getName()).append(": ");
			stackTrace.append(this.description).append('\n');
		} else {
			stackTrace.append(this.cause.toString()).append('\n');
		}

		EagRuntime.getStackTrace(this.cause, (s) -> {
			stackTrace.append("\tat ").append(s).append('\n');
		});

		return stackTrace.toString();
	}

	/**+
	 * Gets the complete report with headers, stack trace, and
	 * different sections as a string.
	 */
	public String getCompleteReport() {
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("---- Minecraft Crash Report ----\n");
		stringbuilder.append("// ");
		stringbuilder.append(getWittyComment());
		stringbuilder.append("\n\n");
		stringbuilder.append("Time: ");
		stringbuilder.append((new SimpleDateFormat()).format(new Date()));
		stringbuilder.append("\n");
		stringbuilder.append("Description: ");
		stringbuilder.append(this.description);
		stringbuilder.append("\n\n");
		stringbuilder.append(this.getCauseStackTraceOrString());
		stringbuilder.append(
				"\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

		for (int i = 0; i < 87; ++i) {
			stringbuilder.append("-");
		}

		stringbuilder.append("\n\n");
		this.getSectionsInStringBuilder(stringbuilder);
		return stringbuilder.toString();
	}

	public CrashReportCategory getCategory() {
		return this.theReportCategory;
	}

	/**+
	 * Creates a CrashReportCategory
	 */
	public CrashReportCategory makeCategory(String name) {
		return this.makeCategoryDepth(name, 1);
	}

	/**+
	 * Creates a CrashReportCategory for the given stack trace depth
	 */
	public CrashReportCategory makeCategoryDepth(String categoryName, int stacktraceLength) {
		CrashReportCategory crashreportcategory = new CrashReportCategory(this, categoryName);
		if (this.field_85059_f) {
			int i = crashreportcategory.getPrunedStackTrace(stacktraceLength);
			String[] astacktraceelement = EagRuntime.getStackTraceElements(cause);
			String stacktraceelement = null;
			String stacktraceelement1 = null;
			int j = astacktraceelement.length - i;
			if (j < 0) {
				System.out.println(
						"Negative index in crash report handler (" + astacktraceelement.length + "/" + i + ")");
			}

			if (astacktraceelement != null && 0 <= j && j < astacktraceelement.length) {
				stacktraceelement = astacktraceelement[j];
				if (astacktraceelement.length + 1 - i < astacktraceelement.length) {
					stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - i];
				}
			}

			this.field_85059_f = crashreportcategory.firstTwoElementsOfStackTraceMatch(stacktraceelement,
					stacktraceelement1);
			if (i > 0 && !this.crashReportSections.isEmpty()) {
				CrashReportCategory crashreportcategory1 = (CrashReportCategory) this.crashReportSections
						.get(this.crashReportSections.size() - 1);
				crashreportcategory1.trimStackTraceEntriesFromBottom(i);
			} else if (astacktraceelement != null && astacktraceelement.length >= i && 0 <= j
					&& j < astacktraceelement.length) {
				this.stacktrace = new String[j];
				System.arraycopy(astacktraceelement, 0, this.stacktrace, 0, this.stacktrace.length);
			} else {
				this.field_85059_f = false;
			}
		}

		this.crashReportSections.add(crashreportcategory);
		return crashreportcategory;
	}

	/**+
	 * Gets a random witty comment for inclusion in this CrashReport
	 */
	private static String getWittyComment() {
		return "eagler";
	}

	/**+
	 * Creates a crash report for the exception
	 */
	public static CrashReport makeCrashReport(Throwable causeIn, String descriptionIn) {
		CrashReport crashreport;
		if (causeIn instanceof ReportedException) {
			crashreport = ((ReportedException) causeIn).getCrashReport();
		} else {
			crashreport = new CrashReport(descriptionIn, causeIn);
		}

		return crashreport;
	}
}