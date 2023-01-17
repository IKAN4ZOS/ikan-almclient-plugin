def antInstallation = 'ANT-1.10.10'
def jdkInstallation = 'jdk-11.0.7'

pipeline {
	// ------------------------------
	// IKAN ALM Package
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
        addPropertyFileLink = 'false'
        allFilerevisions = 'false'
        builderName = 'Jenkins'
        packageArchived = 'false'
        // packageDescription = 'Package 1'
        // buildPrefix = ''
        // buildSuffix = ''
        // filerevisions = '${almSystem_location}/package_${packageName}_filerevisions.xml'
        listFilesAfter = 'true'
        listFilesBefore = 'false'
        localList = "${almSystem_location}/list/${packageName}"
        // packageDate = 'yyyy-mm-dd'
        // packageDir = "${almSystem_location}/packages/${packageName}"
        // packageName = 'pack1'
        packageStatus = '0'
        // packageOid = '0'
        packageUserid = "${almRestUserid}"
        // projectName = 'thisProject'

        sourceProjectDir = "$almSystem_location/environments/build/$projectName/$packageName"
    }
    stages {
        stage('Package') {
            steps {
                script {
                    propertyFileALM = "${sourceProjectDir}/almclientPackage.properties"
                }
                dir("${sourceProjectDir}") { writeFile file:'almclientPackage.properties', text:'' }
                echo pwd()
       	        echo "JAVA_HOME=${JAVA_HOME}"
	        echo "Start almclientPackage()"
                withCredentials([usernamePassword(credentialsId: 'IKANALM-User', passwordVariable: 'almRestPassword', usernameVariable: 'almRestUserid')]) {
                    withEnv(["ANT_ARGS=-q -propertyfile ${propertyFileALM}"]) {
        		    withAnt(jdk: "${jdkInstallation}") {
                            almclientPackage (
                                antInstallation: "${antInstallation}",
                                addPropertyFileLink: '$addPropertyFileLink',
                                allFilerevisions: '$allFilerevisions',
                                almBaseUrl: '$almBaseUrl',
                                almPackageArchived: '$packageArchived',
                                almPackageDescription: '$packageDescription',
                                almPackageName: '$packageName',
                                almPackageOid: '$packageOid',
                                almPackageStatus: '$packageStatus',
                                almPackageTargetDate: '$packageDate',
                                almPackageUserid: '$packageUserid',
                                almProjectName: '$projectName',
                                almProjectStreamBuildPrefix: '$buildPrefix',
                                almProjectStreamBuildSuffix: '$buildSuffix',
                                almRestPassword: '$almRestPassword',
                                almRestUserid: '$almRestUserid',
                                almStopOnError: 'true',
                                builderName: '$builderName',
                                dirPackage: '$packageDir',
                                localList: '$localList',
                                filerevisions: '$filerevisions',
                                listPackageFilesAfter: '$listFilesAfter',
                                listPackageFilesBefore: '$listFilesBefore'
                            )
                        }
                    }
                }
                echo "End almclientPackage()"
            }
        }
    }
}
