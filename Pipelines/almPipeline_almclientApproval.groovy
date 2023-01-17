def antInstallation = 'ANT-1.10.10'
def jdkInstallation = 'jdk-11.0.7'

pipeline {
	// ------------------------------
	// IKAN ALM Approval
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
        // almVcrTag= ''
        builderName = 'Jenkins'
        // approveLevelRequest = 'true'
        // approveReason = 'Ok'
        // buildPrefix = ''
        // buildSuffix = ''
        // levelName = 'TEST'
        // levelRequestOid = '0'
        // levelRequestAccess = 'level'
        // packageName = 'pack1'
        // projectName = 'thisProject'

        sourceProjectDir = "$almSystem_location/environments/build/$projectName"
    }
    stages {
        stage('Approval') {
            steps {
                script {
                    propertyFileALM = "${sourceProjectDir}/almclientApproval.properties"
                }
                dir("${sourceProjectDir}") { writeFile file:'almclientApproval.properties', text:'' }
                echo pwd()
       	        echo "JAVA_HOME=${JAVA_HOME}"
	        echo "Start almclientApproval()"
                withCredentials([usernamePassword(credentialsId: 'IKANALM-User', passwordVariable: 'almRestPassword', usernameVariable: 'almRestUserid')]) {
                    withEnv(["ANT_ARGS=-q -propertyfile ${propertyFileALM}"]) {
        		    withAnt(jdk: "${jdkInstallation}") {
                            almclientApproval (
                                antInstallation: "${antInstallation}",
                                almBaseUrl: '$almBaseUrl',
                                almLevelRequestLevelName: '$levelName',
                                almLevelRequestOid: '$levelRequestOid',
                                almPackageName: '$packageName', 
                                almProjectName: '$projectName',
                                almProjectStreamBuildPrefix: '$buildPrefix', 
                                almProjectStreamBuildSuffix: '$buildSuffix',
                                almRestPassword: '$almRestPassword',
                                almRestUserid: '$almRestUserid',
                                approveLevelRequest: '$approve',
                                approveReason: '$approveReason',
                                almStopOnError: 'true',
                                almVcrTag: '$almVcrTag',
                                builderName: '$builderName',
                                levelRequestAccess: '$levelRequestAccess'
                            )
                        }
                    }
                }
                echo "End almclientApproval()"
            }
        }
    }
}
