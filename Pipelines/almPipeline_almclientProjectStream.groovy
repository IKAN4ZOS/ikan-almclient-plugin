def antInstallation = 'ANT-1.10.10'
def jdkInstallation = 'jdk-11.0.7'

pipeline {
	// ------------------------------
	// IKAN ALM ProjectStream
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
        acceptForcedBuild = 'true'
        builderName = 'Jenkins'
        // buildPrefix = ''
        // buildPrefixSuffixRef = ''
        // buildSuffix = ''
        // buildTypeIndex: 0=Full; 1=Partial; 2=Production-based; 3=Tag-based
        // buildTypeIndex= '0'
        highestBuildNumber = '1'
        lifecycle= 'BRANCH'
        // projectName = 'thisProject'
        projectName2 = ''
        // statusIndex: 0=Under construction; 1=Planning; 2=Development; 3=Testing; 4=Stable; 5=General available; 6=Frozen; 7=Closed
        // statusIndex= '0'
        tagBased= 'false'

        sourceProjectDir = "$almSystem_location/environments/build/$projectName"
    }
    stages {
        stage('Create ProjectStream') {
            steps {
                script {
                    propertyFileALM = "${sourceProjectDir}/almclientProjectStream.properties"
                }
                dir("${sourceProjectDir}") { writeFile file:'almclientProjectStream.properties', text:'' }
                echo pwd()
       	        echo "JAVA_HOME=${JAVA_HOME}"
	        echo "Start almclientProjectStream()"
                withCredentials([usernamePassword(credentialsId: 'IKANALM-User', passwordVariable: 'almRestPassword', usernameVariable: 'almRestUserid')]) {
                    withEnv(["ANT_ARGS=-q -propertyfile ${propertyFileALM}"]) {
        		    withAnt(jdk: "${jdkInstallation}") {
        		        almclientProjectStream (
                                antInstallation: "${antInstallation}",
                                almBaseUrl: '$almBaseUrl',
                                almProjectStreamCreate: 'true', 
                                almRestPassword: '$almRestPassword',
                                almRestUserid: '$almRestUserid',
                                almStopOnError: 'true',
                                projectStreamList: '$projectName $projectName2',
                                projectStreamListWithStream: '$buildPrefixSuffixRef',
                                streamAcceptForcedBuild: '$acceptForcedBuild',
                                streamBuildPrefix: '$buildPrefix',
                                streamBuildSuffix: '$buildSuffix', 
                                streamBuildTypeIndex: '$buildTypeIndex',
                                streamDescription: '$projectName $buildPrefix - $buildSuffix',
                                streamHighestBuildNumber: '$highestBuildNumber',
                                streamLifecycle: '$lifecycle', 
                                streamStatusIndex: '$statusIndex',
                                streamTagBased: '$tagBased',
                                streamTagTemplate: '$streamType_$prefix_$suffix[_$packageName]_b$buildNumber',
                                streamVcrBranchName: '$buildPrefix-$buildSuffix'
                            )
                        }
                    }
                }
                echo "End almclientProjectStream()"
            }
        }
    }
}