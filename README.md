# Selenium Android Mobile


### Prerequisites

To use this framework it is necessary to have installed:

1. \[Java JDK\] ([Download](https://www.oracle.com/cl/java/technologies/javase/jdk11-archive-downloads.html)) since this is a Java project.
    * Once installed, make sure that the system variable `JAVA_HOME`  is configured and that it is inside the path.
        * Configure in Windows:
            * Open the file explorer and go to the Computer tab.
            * Click on Properties.
            * Click on Advanced System Settings.
            * Click on Advanced Options.
            * Click on Environment Variables.
            *  In the system variables, section check that the ` JAVA_HOME` variable is found.
                *   If it is not found click on New:
                *   In the variable name indicate `JAVA_HOME`
                *   In variable value indicate the address where the JDK was installed (for example C:\java\jdk)
                *   Click on accept
            *  Double-click on the path variable
            *  Check that among its lines you can find `%JAVA_HOME%\bin`
                * If it is not found click on New
                * Add the value `%JAVA_HOME%\bin`

            *  Click on Accept
            *  Open command console
            *  Type` java -version`
                *  It should return a value similar to the following :
               ```  
                openjdk version "11.0.16.1" 2022-08-12 LTS
                OpenJDK Runtime Environment Corretto-11.0.16.9.1 (build 11.0.16.1+9-LTS)
                OpenJDK 64-Bit Server VM Corretto-11.0.16.9.1 (build 11.0.16.1+9-LTS, mixed mode)
               ```
2. \[Maven\] ([Download](https://maven.apache.org/download.cgi)) since the framework used is Maven.
    *  Once installed, make sure that the system variable `MAVEN_HOME` is configured and that it is inside the path.
    *  Configure in Windows:
        * Open the file explorer and go to the Computer tab.
        * Click on Properties.
        * Click on Advanced System Settings.
        * Click on Advanced Options.
        * Click on Environment Variables.
        * In the system variables section check that the `MAVEN_HOME` variable is found.
            *   If it is not found click on New:
            *   In the variable name indicate `MAVEN_HOME`
            *   In variable value indicate the address where MAVEN was installed (e.g.: C:\apache-maven-3.6.3-bin).
            *   Click on accept.
        * Double-click on the path variable
        * Check that among its lines you can find ` %MAVEN_HOME%\bin`
            * If it is not found, click on New.
            * Add the value ` %MAVEN_HOME%%bin`
        * Click on Accept.
        * Open command console.
        * Type` mvn -version`
            *  Should return a value similar to the following :

    ```  
            Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
            Maven home: /opt/homebrew/Cellar/maven/3.8.6/libexec
            Java version: 11.0.16.1, vendor: Amazon.com Inc.
            Default locale: es_419, platform encoding: US-ASCII
            OS name: "mac os x", version: "12.2.1", arch: "x86_64", family: "mac"
     ```
3. \[IntelliJ Community\] ([Download](https://www.jetbrains.com/idea/download/#section=windows)) IDE 
4. \[Appium desktop\] ([Download](https://github.com/appium/appium-desktop/releases/tag/v1.22.3-4))
5. \[Appium inspector\] ([Download](https://github.com/appium/appium-inspector/releases))

### Simulator configuration

* Android
    * \[Android Studio\] ([Download](https://developer.android.com/studio)) must be installed to be able to emulate Android devices.
        * To create a simulator you must go to the Configure -> AVD Manager option.
        * Within the Manager select Create Virtual Device.
        * Select Phone in Category and a Device model.
        * Seleccionamos un sistema operativo.
        * Assign a name (optionally we can modify some parameters such as RAM and internal memory).
        * Once created, we run the created simulator

### Appium desktop configuration
* For this configuration we open appium and go to the "Edit Configurations" section.
* Having entered this section we have to configure the ANDROID_HOME environment variable with the path where the android studio SDK folder is located. You can find it quickly in the More actions > Sdk Manager section of android studio.
* Once we know this path, we copy and paste it into appium and click on "Saved and Restart".
* The server configuration is ready, this way we can hit "startServer" so we can start the server to be able to use appium inspector with the android studio emulator. The Host and Port values are left with the default ones which are those that are seen in the image.

### Appium inspector configuration
* In the Appium Server section we are going to configure the capabilities of the emulator that we are going to use and the configuration to be able to raise the server. To know more about these capabilities and their functionalities you can read the Appium documentation.

    * Remote Path: /wd/hub

    * Json Representation:
      {
      "app": "Route of the app",
      "platformName": "Android",
      "automationName": "UiAutomator2",
      "deviceName": "emulator-5554",
      "platformVersion": "12.0.0",
      "udid": "emulator-5554"
      }
    * "App": we set the path where the apk we are going to use is located.
    * platformName": set the type of device, in this case Android.
      automationName": set the driver to use, in this case "UiAutomator2" * "automationName": set the driver to use, in this case "UiAutomator2" * "deviceName": set the driver to use.
    * deviceName": set the device name, this can be obtained from the console as explained in the section "How to know the name of my device".
    * platformVersion": set the Android version.
    * Udid": set the same value of the device name.

* Once the configuration is done we click on "Start Session" and in this way we can use and find the locators to be able to perform the tests.


### Project installation

Here's what you have to do to download and install the JetClub project
To download it we must create a folder inside the C:/ directory.

* We open a terminal inside that folder and type the following commands

  ```
   git init
   git clone https://github.com/DanielaGonzalez1/FlyExclusive-Jetclub.git
   ```

* Once it has been downloaded we open the project with the IDE configured and we will see the project structure
  * In the project directory look for the file `pom.xml`.
  * Right-click on the file
  * Click on Update Project
  * Ready, all the dependencies should be installed.

Ready, we should now be able to run the example features found within the project.

### Project structure

```  
├── src  
│   ├── apps                                                  # Here we will deposit the binary with which the tests will be executed.  
│   ├── test                                                  #Test suites  
│   │   ├── java
│   │   │    └── [Package del proyecto]
│   │   │        ├── utils                                    # Folder with utilities.
│   │   │        ├── apis                                     # Here are the manager classes for calling the apis.
│   │   │        ├── appiumUtils                              # Folder with classes for managing the Appium session and its capabilities.
│   │   │        ├── database                                 # Here are the manager classes to make queries in the DB.
│   │   │        ├── pageObjects                              # Here all the pages of the app will be deposited.
│   │   │        │         └── page.java                      # This is the class with the locators and the methods to interact with the screen.
│   │   │        └── stepDefinitions                          # Here will be deposited all the pagesdefinition of the app.
│   │   │                  └── [Carpeta del pagedefinition]   # Each stepdefiniton must have a folder containing it.
│   │   │                     └── stepdefinition.java         # These classes contain the steps defined in feature.
│   │   ├── resources
│   │   │   ├── features                                      # All test features will be saved here.
│   │   │   │   └── example.feature 
│   │   │   └── properties                                    
│   │   │      ├── database-{ambiente}.properties             # Properties for connection to the DB.
│   │   │      ├── apis-{ambiente}.android.properties         # Properties used to call apis.
│   │   │      ├── browserstack-{ambiente}.android.properties # Properties to run our in browserstack
│   │   │      └── phone-{ambiente}.properties                # Properties for running our tests on physical or emulated devices
└── pom.xml                                                   # File containing the project dependencies
```

### Creating new scripts

To generate new scripts it is important to keep certain structure within the project

* Create Feature: It must have a descriptive name and it must be in the corresponding folder.
* Create java class of stepdefinition: An associated folder must be generated inside the stepdefinitions package.
    * For example
      ```
        │    ├── stepDefinitions
        │    │    └── login 
        │    │          └── loginStepDefinitions.java
      ```
* Create a java class of page
    * For example
        ```
        │    ├── pageObjects
        │    │    └── loginPage.java
      
        ```

### Execution

The following command is used to run the tests:

mvn clean test -Denvironment={environment} -Dplatform={browserstack/phone} -Dcucumber.filter.tags=@Login

Where:

* -Dplatform= indicates whether the tests are to be run on a physical/emulated device or on a cloud-hosted device (browserstack).
  -Denvironment= 'name of the environment where the tests will be run (QA, UAT, DEV)'.
* -Dcucumber.filter.tags="@login" for example @login to run tests referring to login
    * You can run more than one tag as follows:
        ```
        -Dcucumber.filter.tags="@login and @logout"

NOTE: You must be positioned in the console inside the folder where the project is located to execute the commands.

### Reports

Once the execution is finished you can see the report in the folder target/allure-results:

And to pull up the report you must run the following commands:

        ```
        allure serve target/allure-results
        ```
