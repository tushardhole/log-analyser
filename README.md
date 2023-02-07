# Prerequisite
    1. Java 8 or higher
    2. java command runs from command line

# Steps to build and run application
    1. open preferefed terminal app
    2. navigate to root of this repo i.e log-analyser
    3. run ./gradlew clean build (optional step, if there are no new local changes)
    4. navigate to release folder on cli and run below command
    5. java -jar app.jar test-logs.txt 2018-12-09

# Note
    1. Latest app is copied after every new build to release folder
    2. If you want analyse any other log file,
       make sure app.jar and log file is in same directory
