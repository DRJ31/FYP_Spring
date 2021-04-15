# Syllabus Management System

<a href="https://github.com/DRJ31/FYP_Spring"><img src="https://img.shields.io/github/license/DRJ31/FYP_Spring" alt="MIT License"></a>
<a href="https://github.com/DRJ31/FYP_Spring/releases"><img src="https://img.shields.io/github/v/release/DRJ31/FYP_Spring" alt="Latest Release"></a>
<a href="https://syllabus.drjchn.com"><img src="https://img.shields.io/uptimerobot/ratio/7/m787675536-52dbeb8ba252240a5bebc38f" alt="Up Ratio"></a>
<a href="https://syllabus.drjchn.com"><img src="https://img.shields.io/uptimerobot/status/m787675536-52dbeb8ba252240a5bebc38f" alt="Site Status"></a>

A simple system to manage course syllabus from different schools. Due to incomplete design, the project has a low completion which contains only the basic functions like register, login, create, edit and delete syllabus etc.

## Installation
### 1. Open the Project
First, open the project with **IntelliJ IDEA**, then the IDE will automatically install packages according to `pom.xml`.

### 2. Configuration
When the IDE pop up a tip that ask you to configure a Web Framework, just configure it with default option.

Next, go to `File->Project Structure->Artifacts` and add a `Web Application: Exploded` from module.

Last but not least, go to **run configuration** and add a `Tomcat Local Server` with the module in Artifacts.

### 3. Build
Go to `File->Project Structure->Artifacts` and add a `Web Application: Archive` from module, and click **Include in project build**.