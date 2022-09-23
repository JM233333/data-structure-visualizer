English | [简体中文](./README.cn.md)

# Data Structure Visualizer

![license_gpl](https://img.shields.io/badge/License-GPL-green)

*Authored by JM233333*

*Version 0.1*

## Introduction

### Overview

**Data Structure Visualizer** is a PC desktop application that provides visualization and interaction of algorithms and data structures, and supports programmers (even newbies) to simply extend it.

*This application provides a **step-by-step animation demonstration** and a matching **code tracking** , which facilitate users to intuitively understand the various operations performed on the data structure.*

*This application can be used as an auxiliary tool for personal learning, as well as a teaching tool (e.g. demonstrating on the screen in class)*

*For convenience of description, we will abbreviate the data structure (or algorithm) visualized by the application as **VDS** below.*

### Features

With this visualization tool, you can:

- Intuitively see the graphical representation of the logical structure of VDS, as well as the animation of the various operations performed on the VDS (i.e. the data structure maintenance or algorithm execution);

- See the code trace synchronized with the animation, as well as the synchronized classic console output;

- Use single-step animation mode to deeply understand the operation process of VDS.

- Add other user-defined VDS by yourself and let the tool visually display them.

This tool provides good support for user extensions. Programmers don't need to read and understand the source code of the application. They just need to consult the developer manual provided by us. The only condition is that the programmers should has at least a basic understanding of Java syntax, and they need to follow some preset rules while extending.

However, the extension is limited. At present, the tool only supports VDSs based on one or more of the following logical structures:

- Linear List (including Sequential List and Linked List);

- Binary Tree.

Obviously supporting only the above is insufficient, so I plan to add support for the following logical structures in future versions:

- Two-dimensional nested Linear List (including Sequential List and Linked List, cross-nesting supported);

- K-ary Tree;

- Graph (simple structure only).

## Environment Configuration

Windows and Linux are supported (availability guaranteed on Windows 10 and Ubuntu 18/20/22).

JDK 8+ and the JavaFX module are required.

- If using JDK 10+, you may need to manually add JavaFX modules to your JDK.

- We recommend Linux users **DO NOT** install Java through the package manager (e.g. `sudo apt install openjdk-8-jdk`) as the JavaFX module may not be built in. The JDK 8 downloaded from the Oracle official website has a JavaFX module built in, which requires no additional configuration.

We already provide an Ant build script. It is recommended to use Apache Ant 1.10.x.

## Installation

You can build the project using Ant or any IDE (e.g. Ecllipse, IDEA, ...).

The easiest way is to build directly with the Ant build script we provided, but you must install Apache Ant 1.10.x, and set the environment variable `JAVA_HOME` to the root directory of JDK. After completing the configuration, go to the root directory of the source code folder, open the CLI, and run the following command:

```
ant all
```

If the build is successful, you will see the prompt `BUILD SUCCESSFUL` in the command line, and you can find the following in `out/artifacts/` :

```
custom/
lib/
data-structure-visualizer.jar
```

Run the `.jar` file directly to launch the application.

```bash
java -jar /path/to/data-structure-visualizer.jar
```

In addition, because the `.iml` configuration file used by IDEA is retained in the project source code, it is very convenient to use IDEA to build the project, just one-click compile and run.

*Notice:*

- *JDK 8 or higher version is required.*

- *If you build the project with Ant, then 1.10.x is required.*

## Overview of the Core GUI

Start the visualization (please refer to the tutorial document for how-to-startup) , you will see the following GUI:

![design/sample-gui.jpg](design/sample-gui.jpg)

The **Menu** at the top of the GUI is used to switch the VDS or return to the main menu.

- *Note: The current version only supports returning to the main menu. The other feature is planned to be implemented in a future version.*

The **Controller** below the GUI is the main UI for users to interact with the application, which includes 4 sub-modules:

- **Method Triggers** are used to control the VDS. You can use the method trigger to let the data structure perform certain specified operations, such as letting the stack execute `push 2`.

- **Animation Controller** includes all controls for setting animation related parameters, such as playing and pausing animation, adjusting animation playback rate, and setting whether to play the animation single-step or not.

- **Output Box** is used to receive the output stream from the VDS during the execution of the operations, and decide which content to output according to the detail-level of the output set by the user.

- **Batch Processor** Supports execution of complex operation flows. You can enter a sequence of operations in the text box, or read a preset sequence of operations from the specified file and batch process the operations.

The **Monitor** in the center of the GUI is responsible for viusalizing the VDS. All visual elements used to display the VDS, and their animation effects, are displayed by the monitor. Actually, the monitor is similar to an artboard.

The **Code Tracker** on the right side of the GUI is responsible for displaying the actual implementation code of the VDS, and is responsible for code tracking while performing operations.

For more detailed usage, please refer to the tutorial document in the pre-compiled product package or source code directory.

## Defects of the Current Version

### Planned Features

；

### Known BUGs

Until 2020.9.21, I have not found any bug in the latest released version (v0.1).

## More

；

## Todo List

Things planned to do in the near future:

- Refactors the implementation of MethodTrigger.

- Update : Supports global visual pointers for VDS.

## Update History

| Date       | Updates |
| :---       | :--- |
| 2020.9.20  | Fix BUG : Mistake of build functionality of batch-processing. |
|            | Fix BUG : Mistake of reconstructing process of VisualizedBST. |
|            | Fix BUG : Mistake of step-by-step mode after batch-building. |
|            | Fix BUG : Mistake in {traverseXxxOrder()} of VisualizedBST. |
|            | Reorders the update history in README. |
| 2020.9.19  | Update : Optimizes code-tracking. |
|            | Update : Implements method-invocation-stack for VDS. |
| 2020.9.18  | Update : Implements {traverseXxxOrder()} of VisualizedBST. |
|            | Fix BUG : Mistake in method {erase(value)} of VisualizedBST. |
| 2020.9.17  | Update : Supports fast-build for batch-processing. |
| 2020.9.16  | Update : Implements the main part of OutputBox. |
|            | Fix BUG : Lack of out-of-bounds-check in several methods of VisualizedList. |
|            | Fix BUG : Few UI layout mistakes. |
| 2020.9.15  | Writes tutorial for user (Chinese). |
|            | Refactors the design documents. |
| 2020.9.10  | Update : Implements illegal input detection for batch-processing. |
| 2020.9.7   | Refactors the source code. |
|            | Refactors the information for javadoc. |
| 2020.9.5   | Update : Implements methods {erase(value)} of VisualizedBST. (too complex!) |
| 2020.8.25  | Update : Adds support for single-step animation of VisualizedBST. |
|            | Fix BUG : BUG triggered while running batch-processing more than once. |
| 2020.8.24  | Update : Implements methods {get(index)} and {find(value)} of VisualizedList. |
|            | Update : Implements methods of VisualizedBST. |
| 2020.3.29  | Update : Adds support for single-step animation of VisualizedList. |
|            | Fix BUG : Mistake in method {pushFront(value)} of VisualizedList. |
|            | Fix BUG : Sometimes wrong current method name in code-tracking. |
| 2020.3.28  | Fix BUG : Animation mistake of VisualPointer. |
|            | Adds the todo list into the README. |
| 2020.3.27  | Fix BUG : Localization of VisualBST. |
|            | Fix BUG : Animation mistake of batch-processing. |
| 2020.3.23  | Finishes writing README. |
| 2020.3.17  | Adds support for compilation with Ant. |
| 2020.3.5   | Most basic features are implemented. |
| 2019.12.1  | Starts developing this project. |
|            | Early update records are omitted for brevity. |