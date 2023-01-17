def antInstallation = 'ANT-1.10.10'
def jdkInstallation = 'jdk-11.0.7'

pipeline {
	// ------------------------------
	// IKAN ALM LevelRequest Issues
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
        builderName = 'Jenkins'
        // buildPrefix = ''
        // buildSuffix = ''
        // levelName = 'TEST'
        // levelRequestOid = '0'
        // packageName = ''
        // projectName = 'thisProject'
        localList = "$almSystem_location/environments/build/$projectName/list"
        issuesFileType = 'lst'
        issueFile= 'levelRequestIssues'
        excelSheetName = "levelRequestIssues"

        sourceProjectDir = "$almSystem_location/environments/build/$projectName"
    }
    stages {
        stage('List LevelRequest Issues') {
            steps {
                script {
                    propertyFileALM = "${sourceProjectDir}/almclientLevelRequestIssues.properties"
                }
                dir("${sourceProjectDir}") { writeFile file:'almclientLevelRequestIssues.properties', text:'' }
                echo pwd()
       	        echo "JAVA_HOME=${JAVA_HOME}"
	        echo "Start almclientLevelRequestIssues()"
                withCredentials([usernamePassword(credentialsId: 'IKANALM-User', passwordVariable: 'almRestPassword', usernameVariable: 'almRestUserid')]) {
                    withEnv(["ANT_ARGS=-q -propertyfile ${propertyFileALM}"]) {
        		    withAnt(jdk: "${jdkInstallation}") {
                            almclientLevelRequestIssues (
                                antInstallation: "${antInstallation}",
                                almBaseUrl: '$almBaseUrl',
                                almRestPassword: '$almRestPassword',
                                almRestUserid: '$almRestUserid',
                                almStopOnError: 'true',
                                builderName: '$builderName',
                                excelSheetName: '$excelSheetName',
                                issueFile: "${localList}/${issuefile}.${issuesFileType}",
                                levelName: '$levelName',
                                levelRequestAccess: 'level',
                                levelRequestIssuesFileType: '$issuesFileType',
                                levelRequestOid: '$levelRequestOid',
                                localList : '$localList',
                                packageName: '$packageName',
                                projectName: '$projectName',
                                projectStreamPrefix: '$buildPrefix',
                                projectStreamSuffix: '$buildSuffix'
                            )
                        }
                    }
                }
                echo "End almclientLevelRequestIssues()"
            }
        }
    }
}
