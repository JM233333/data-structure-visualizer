English | [简体中文](./README.cn.md)

# DataStructureVisualizer

---

## Introduction

#### Overview

**DataStructureVisualizer** is a PC desktop application that provides visualization and interaction of algorithms and data structures, and supports programmers (even newbies) to simply extend it.

*For convenience of description, we will abbreviate the data structure (or algorithm) visualized by the application as **VDS**.*

#### Features

With this visualization tool, you can:

- See the visual elements of the logical structure of VDS;

- See an animation of the algorithm execution process and data structure maintenance process;

- See code traces of performed VDS operations synchronized with the animation;

- See the output of various operations of the VDS.

And you can also interact with the VDS, for example:

- Let the VDS perform the operation specified by the user;

- Set animation parameters, such as adjusting the animation playback rate and setting whether to play in a single step;

- Control the detail-level of the output, such as requiring all intermediate DEBUG information to be output or only the final result.

What's more, the programmers can add user-defined VDS by themselves.

Importantly, They don't need to read and understand the source code of the application. They just need to consult the developer manual provided by us. The only condition is that the programmers should has at least a basic understanding of Java syntax, and they need to follow some preset rules while extending.

However, the extension is limited. At present, the tool only supports VDSs based on one or more of the following logical structures:

- Linear List (including Sequential List and Linked List);

- Binary Tree.

Obviously supporting only the above is insufficient, so we plan to add support for the following logical structures in future versions:

- Two-dimensional nested Linear List (including Sequential List and Linked List, cross-nesting supported);

- K-ary Tree;

- Graph (simple structure only).

---

## Quick Start

---

## Development Environment

- JDK 1.8 update 60

- JavaFX in JDK 1.8

- IntelliJ IDEA 2019.2.4 (Community Edition) (not necessary)

- Markdown, PlantUML (not necessary)

## Change Log (after 2020.3)

| Date | Updates |
| :--- | :--- |
| 2020.3.5 | All basic features are implemented. |
| 2020.3.17 | Adds support for compilation with Ant. |