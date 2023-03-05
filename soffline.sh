#!/bin/sh
cd javascript
mv assets.epk as.epk
cd badwifi
rm EaglercraftX_1.8_Offline_en_US.html
rm EaglercraftX_1.8_Offline_International.html
rm Resent_3.6_patch__1.8_US.html
rm Resent_3.6_patch_1.8_International.html
cp assets.epk ../assets.epk
cd ../../
./MakeOfflineDownload.sh
cd javascript/badwifi
mv ../EaglercraftX_1.8_Offline_en_US.html ./Resent_3.6_patch__1.8_US.html
mv ../EaglercraftX_1.8_Offline_International.html ./Resent_3.6_patch_1.8_International.html
cd ../
rm assets.epk
mv as.epk assets.epk