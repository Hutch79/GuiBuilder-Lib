# GuiBuilder-Lib
A simple to use GuiBuilder to implement in your Plugin
---
This Library is still in Developement.  
Please note that it definetelly is NOT complete and will change!

---
[Link to Maven Repo](https://repo.hutch79.ch/#/releases/ch/hutch79/GuiBuilder)

## How to use
### Creating a Config file
```java
import ch.hutch79.guibuilder.GuiBuilder;

    @Override
    public void onEnable() {
        GuiBuilder guiBuilder = new GuiBuilder();
        guiBuilder.GuiBuilderInit(this);
        guiBuilder.createConfig("GUI.yml", true);
        guiBuilder.readConfig("GUI.yml");
    }
    
```
---
This line will create the config file and if set to true, copy default values on first creation.
```java
guiBuilder.createConfig("GUI.yml", true);
```
---
Here were actually going to read the file.  
This is also needed if you want to reload the data after an file edit.
```java
guiBuilder.readConfig("GUI.yml");
```
---
### Open a GUI
```java
GuiBuilder guiBuilder = new GuiBuilder();
guiBuilder.openGUI(player, "main");
```
With this methode you can open the "main" gui for a spesific Player.
