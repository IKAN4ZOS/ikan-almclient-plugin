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
                withCredentials([usernamePassword(credentialsId: 'IKANALM-User', passwordVariable: 'almRestPassword', usernameVariable: 'almRestUserid')]) {
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
                }
                echo "End almclientLevelRequest()"
            }
        }
    }
}
