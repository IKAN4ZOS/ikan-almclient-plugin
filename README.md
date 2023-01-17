# IKAN ALM Client Rest API plugin

[![Jenkins Plugin](https://img.shields.io/jenkins/plugin/v/ikan-almclient-downloader.svg)](https://plugins.jenkins.io/ikan-almclient-downloader) [![GitHub release](https://img.shields.io/github/v/release/jenkinsci/ikan-almclient-downloader.svg?label=release)](https://github.com/jenkinsci/ikan-almclient-downloader-plugin/releases) [![Jenkins Plugin Installs](https://img.shields.io/jenkins/plugin/i/ikan-almclient-downloader.svg?color=blue)](https://plugins.jenkins.io/ikan-almclient-downloader)

## Overview

The ALM Client RestAPI plugin allows Builder’s Users to execute IKAN ALM Rest API main functionalities, such as Create Level Request or Project Stream or Package on IKAN ALM Projects and Approve or Reject a Level Request and list Level Request Issues. Users can manage IKAN ALM Build and Deploy processes and other tasks with their Builder Software. 

## Prerequisites

The following are required to use this plugin:

* Builder Software (Jenkins/CloudBees/Hudson)
* [IKAN ALM Software](https://www.ikanalm.com "IKAN ALM") 
* IKAN ALM Base Plugin
* IKAN ALM Client RestAPI Plugin

## Agreement and changes

* [Plugins License](https://www.ikanalm.com/PluginPlusLicense "Plugins License")
* [Changes Log](./CHANGELOG.md)

## Installing in a Builder Instance

First method from IKAN site:

- Ask the last JENKINS-solution_ALMCLIENT archive to [IKAN site](https://www.ikan.be "IKAN") using our *Contact us* menu.
- Unzip the received JENKINS-solution_ALMCLIENT-x.y.z.zip (tar.gz) archive in the Builder server.
- Install the ALM Base Plugin (almbase-jenkins-plugin-x.y.z-SNAPSHOT.hpi)  
and ALM Client RestAPI Plugin (ikan-almclient-plugin-x.y.z-SNAPSHOT.hpi) according to the Builder instructions for installing plugins. Go to Manage Plugins menu  
and Advanced settings for selecting each hpi file in the Deploy plugin part of the page.

Second method from ‘Available plugins’:

- Install the ALM Base Plugin using the ‘Available plugins’ in your Builder Software, firstly.
- Install the ALM Client RestAPI Plugin using the ‘Available plugins’ in your Builder Software, next.
- Ask the last Pipeline models to [IKAN site](https://www.ikan.be "IKAN") using our *Contact us* menu or by our ALM Support <support@ikanalm.com>. 

## Configuring Administration

### Configuration System

Set the variables as:

- Environment variables like:

---
	Name	almBaseUrl
	Value	http://ikan000:8080/alm

---
	Name	almSystem_location
	Value	C:\Jenkins\almPluginBase
---

The **almSystem_location** parameter must have the location where the JENKINS-solution_ALMCLIENT archive has been unzipped with the *almPluginsBase* folder. If you don’t use this archive, you create this folder somewhere in your Server. Next you create a *Pipelines* folder for putting provided pipeline models.

### Manage Plugins

- Activate if not: 	
    - ‘Build Pipeline Plugin’
	- ‘Build Timeout’
	- ‘Pipeline Utility Steps’	(if not exist, download it, and add it for your Jenkins version)
	- ‘Workspace Cleanup Plugin’
	- ‘Artifactory Plugin’	(Optional)

- Install ALM Plugins with the “Advanced” sheet (first way) or “Available” sheet (second way):
	- ALM Base Plugin ‘ikan-almbase-plugin’
	- ALM Client RestAPI Plugin ‘ikan-almclient-plugin’

### Global Tool Configuration

Add if not: 	‘JDK’ Installation

	Name		jdk-11.0.7
	JAVA-HOME	C:\Java\jdk-11.0.7

Add if not: 	‘ANT’ Installation

	Name		ANT-1.10.10
	Install automatically
	Install from Apache
	Version		1.10.10

## Executing IKAN ALM Client Rest API activities

Create a ‘Pipeline’ project and use the corresponding Pipeline script model provided in the archive for working.

	- almPipeline_almclientApproval.groovy
	- almPipeline_almclientLevelRequest.groovy
	- almPipeline_almclientLevelRequestIssues.groovy
	- almPipeline_almclientPackage.groovy
	- almPipeline_almclientProjectStream.groovy

These pipelines are prepared for managing all parameters used by the task to run.
First, you set correct values of:

```
	def antInstallation = 'ANT-1.10.10'
	def jdkInstallation = 'jdk-11.0.7'
```

Next, each parameter is defined with or without ‘//’ (groovy comment).  
When a parameter is commented, it must be defined in the Parameters of the Project with valid structure.  
(Choice, Password, String, etc.). Other parameters are fixed in the pipeline for the steps.

## Using Pipeline Syntax to Generate Pipeline Script

1. Do one of the following:
	* When working with an existing Pipeline job, click the *Pipeline Syntax* link in the left panel. The *Snippet Generator* appears.
	* When configuring a Pipeline job, click the *Pipeline Syntax* link at the bottom of the Pipeline configuration section. The *Snippet Generator* appears.
2. From the *Sample Step* list, select one *almclientXxx* task.
3. From the *Parameters* list, select or provide the correct value for this task.
4. Click *Generate Pipeline Script* button. The Groovy script to invoke the almclientXxx script appears.  
The script can be added to the Pipeline section when configuring a Pipeline job.  
A sample script is shown below:

![Generate Pipeline Script](./images/GeneratePipelineScript.PNG)

5.  Copy the script text and put it into your Job pipeline part. Next see the Pipeline Requipement paragraph for completing the Job pipeline lines of the step with this example.

## Pipeline Requirement

To use a generated pipeline, your almBaseURL, almRestUserid and almRestPassword must be configured properly to run it with other useful parameters. Define them in the configure system screen or in the Project parameters.
Next the task must be surround with these lines:

```
            steps {
                script {
                    propertyFileALM = "${sourceProjectDir}/almclientLevelRequest.properties"
                }
                dir("${sourceProjectDir}") { writeFile file:'almclientLevelRequest.properties', text:'' }
                echo pwd()
       	        echo "JAVA_HOME=${JAVA_HOME}"
	        echo "Start almclientLevelRequest()"
                withEnv(["ANT_ARGS=-q -propertyfile ${propertyFileALM}"]) {
    		    withAnt(jdk: "${jdkInstallation}") {
			almclientXxx (
			    ...
            )
		}
	}

```

### Pipeline LevelRequest sample

```
def antInstallation = 'ANT-1.10.10'
def jdkInstallation = 'jdk-11.0.7'

pipeline {
	// ------------------------------
	// IKAN ALM LevelRequest
	// ------------------------------
	// specify agents/nodes
    agent any
	// define scripting tools
	tools {
		ant "${antInstallation}"
		jdk "${jdkInstallation}"
	}

    environment {
        // almBaseUrl: 'http://IKAN000:8080/alm'
        // almSystem_location
        // almRestPassword= ''
        // almRestUserid= ''
        // almStartDate= ''
        // almStatus= ''
        // almVcrTag= ''
        // builderAction = 'Create'
        builderName = 'Jenkins'
        // buildNumber = '1'
        // buildPrefix = ''
        // buildSuffix = ''
        // deployEnvironments = ''
        // levelName = 'TEST'
        // levelRequestOid = ''
        // packageName = ''
        // paramfile= 'parameters.properties'
        // projectName = 'thisProject'
        // redeliver = 'false'

        sourceProjectDir = "$almSystem_location/environments/build/$projectName"
    }
    stages {
        stage('Create LevelRequest') {
            steps {
                script {
                    propertyFileALM = "${sourceProjectDir}/almclientLevelRequest.properties"
                }
                dir("${sourceProjectDir}") { writeFile file:'almclientLevelRequest.properties', text:'' }
                echo pwd()
       	        echo "JAVA_HOME=${JAVA_HOME}"
	        echo "Start almclientLevelRequest()"
                withEnv(["ANT_ARGS=-q -propertyfile ${propertyFileALM}"]) {
    		    withAnt(jdk: "${jdkInstallation}") {
                        almclientLevelRequest (
                            antInstallation: "${antInstallation}",
                            almBaseUrl: '$almBaseUrl',
                            almBuildNumber: '$buildNumber',
                            almDeploysToExecute: '$deployEnvironments',
                            almDescription: 'Build created by $builderName with Rest API',
                            almLevelRequestLevelName: '$levelName', 
                            almLevelRequestOid: '$levelRequestOid', 
                            almPackageName: '$packageName', 
                            almProjectName: '$projectName', 
                            almProjectStreamBuildPrefix: '$buildPrefix', 
                            almProjectStreamBuildSuffix: '$buildSuffix',
                            almRedeliver: '$redeliver',
                            almRestPassword: '$almRestPassword',
                            almRestUserid: '$almRestUserid',
                            almStartDate: '$almStartDate',
                            almStatus: '$almStatus',
                            almStopOnError: 'true',
                            almVcrTag: '$almVcrTag',
                            builderAction: '$builderAction',
                            builderName: '$builderName',
                            levelRequestAccess: 'lroid',
                            paramfile: '$paramfile',
                            statusMaxwaitMinutes: '30',
                            statusSuccessMandatory: 'true',
                            statusWaitingSeconds: '30'
                        )
                    }
                }
                echo "End almclientLevelRequest()"
            }
        }
    }
}

```

# Product support

IKAN helps with customers with its documentation, the Documentation support web site, and telephone customer support.

## Support Web Site

You can access online information for IKAN ALM product via our Frontline support site at IKAN ALM REST API v1 [IKAN ALM Documentation](https://docs.ikanalm.com/rest-api/5.9/RESTAPI_Overview.html "IKAN ALM Documentation").  
IKAN ALM Documentation provides access to critical information about your IKAN ALM product. You can, read or download documentation, access product fixes, or e-mail your questions or comments. The documentation is free of registration.

## Contacting Customer Support

At IKAN, we make our products and documentation like the industry. Feedback from our customers helps us maintain our quality. If you need support services, please obtain the following information before calling IKAN’s support. This information is displayed in the *Contact us* dialog box:

* Your name, your e-mail, the subject will contain the name, release number of your product.
* the message will contain if necessary:
    - Installation information including installed options, whether the product uses local or network databases, whether it is installed in the default directories, whether it is a standalone or network installation, and whether it is a client or agent or server installation.
    - Environment information, such as the operating system and release on which the product is installed, memory, hardware and network specification, and the names and releases of other applications that were running when the problem occurred.
    - The location of the problem within the running application and the user actions taken before the problem occurred.
    - The exact application, licensing, or operating system error messages, if any.

You can contact IKAN in one of the following ways:

### Phone
* Belgium: +32 15 44 50 40.
### Web
You can report issues via ALM Support: <support@ikanalm.com>.

**Note:** Please report all high-priority issues by phone.

### Corporate Web Site

To access IKAN's site on the Web, go to <https://www.ikan.be>. The IKAN site provides a variety of product and support information.
