UPDATE INFO (11.11.2019), now you can download from google play store
https://play.google.com/store/apps/details?id=com.euassfdevelopmentproject.q2

The link of demo_video for customers
https://youtu.be/weIBwggLFHY

The link of demo_long_version for class
https://youtu.be/K2HxoQOrcJs

The link of buid instraction.
https://docs.google.com/document/d/1_t0MVjGjsYUDdzUNjAKc5-L9PVjaNoBSnnLmP_5_IgE/edit?usp=sharing

The Dropbox link of APK file.
https://www.dropbox.com/s/n658j0e7doxgpap/app-release.apk?dl=0

Build Instruction

To import Android Studio Project from GitHub link. Please follow the steps below.

Open the Android Studio(if you have not installed Android Studio yet, follow this link→https://developer.android.com/studio/install). If you have been opening your own project, please close all, that would be easy.
Click the “Get from Version Control” 



Put the https://github.com/Kazukafka/Quvism link into the URL box.




Then push the button of CLONE and Click Yes.



Please go to the “Gradle Script” → “build.gradle”


Please make sure that your compileSdkVersion & targetSdkVersion are 29 NOT 30 otherwise, it has a plugin issue.
In the next step, open the “Open AVD Manager” and choose “Pixel 3a XL API 30”. Then made it.

Referance


flip animation https://stackoverflow.com/questions/46111262/card-flip-animation-in-android


to study the system flip animation https://github.com/wajahatkarim3/EasyFlipView
