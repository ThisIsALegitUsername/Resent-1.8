@echo off
title MakeOfflineDownload
java -cp "desktopRuntime/MakeOfflineDownload.jar;desktopRuntime/CompileEPK.jar" net.lax1dude.eaglercraft.v1_8.buildtools.workspace.MakeOfflineDownload "javascript/OfflineDownloadTemplate.txt" "javascript/classes.js;javascript/fix-webm-duration.js" "javascript/assets.epk" "javascript/Resent_3.7_patch__1.8_US.html" "javascript/Resent_3.7_patch__1.8_International.html" "javascript/lang"
pause