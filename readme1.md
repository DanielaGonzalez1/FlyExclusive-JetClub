Prerequisites
To use this framework it is necessary to have installed:

[Java JDK] (Download) since this is a Java project.

Once installed, make sure that the system variable JAVA_HOME is configured and that it is inside the path.
Configure in Windows:
Open the file explorer and go to the Computer tab.

Click on Properties

Click on Advanced System Settings

Click on Advanced Options

Click on Environment Variables

In the system variables, section check that the JAVA_HOME variable is found.

If it is not found click on New:
In the variable name indicate JAVA_HOME
In variable value indicate the address where the JDK was installed (for example C:\java\jdk)
Click on accept
Double-click on the path variable

Check that among its lines you can find %JAVA_HOME%\bin

If it is not found click on New
Add the value %JAVA_HOME% "bin
Click on Accept

Open command console

Type java -version

It should return a value similar to the following :
openjdk version "11.0.16.1" 2022-08-12 LTS
OpenJDK Runtime Environment Corretto-11.0.16.9.1 (build 11.0.16.1+9-LTS)
OpenJDK 64-Bit Server VM Corretto-11.0.16.9.1 (build 11.0.16.1+9-LTS, mixed mode)