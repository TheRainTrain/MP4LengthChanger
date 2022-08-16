# MP4 Length Changer
Have you ever wanted to change a length of a video to be unnaturally long at the touch of a fingertip?

Well now you can! Using MP4 Length Changer you can simply select a file, change the duration and/or timescale, save it somewhere and do whatever you want with it

Warning: some video players (like [GNOME's "Videos" app](https://wiki.gnome.org/Apps/Videos)) will recognize edited video files as corrupted. This doesn't mean that the file is corrupted, just that the application doesn't know how to play it. If you drag it over to Chrome, it will play just fine. If you need to use those video applications, you can revert the duration and timescale of the video the same way you changed it.

## Requirements
- Java 1.8 (8) or above

## Installation
### Windows, Mac, and others
Download the latest jar file in releases and run it using Java. For example:
```
java -jar mp4lengthchanger-1.0.0.jar
```
### Linux
An installation and uninstallation script is provided in the cli folder. Clone the repo and run them like any other script:
```
./install.sh
```
It is required that the current directory be the cli directory

You can then run the application like so
```
mp4lengthchanger
```

## Building
This project relies on Maven for building. Clone the repo and run the following:
```
mvn clean install
```
It is not specifically required that maven be used, although it is the recommended way to build this project.
