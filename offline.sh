#!/bin/sh
cd javascript
rm EaglercraftX_1.8_Offline_en_US.html
rm EaglercraftX_1.8_Offline_International.html
rm Resent_6.0_patch__1.8_US.html
rm Resent_6.0_patch_1.8_International.html
cd ../
./MakeOfflineDownload.sh
cd javascript
mv EaglercraftX_1.8_Offline_en_US.html Resent_6.0_patch__1.8_US.html
mv EaglercraftX_1.8_Offline_International.html Resent_6.0_patch_1.8_International.html