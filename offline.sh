#!/bin/sh
cd javascript
rm EaglercraftX_1.8_Offline_en_US.html
rm EaglercraftX_1.8_Offline_International.html
cd ../
chmod +x ./MakeOfflineDownload.sh
./MakeOfflineDownload.sh
cd javascript
mv EaglercraftX_1.8_Offline_en_US.html Resent_EaglercraftX_1.8_US.html
mv EaglercraftX_1.8_Offline_International.html Resent_EaglercraftX_1.8_International.html