/**
 * 
 */
package com.ikanalm.plugins.jenkins.almclientpackage;


import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import com.ikanalm.plugins.jenkins.base.BaseAntStep;
import com.ikanalm.plugins.jenkins.utils.Utils;

import hudson.Extension;
import hudson.Util;
import hudson.tasks.Ant;
import hudson.tasks.Ant.AntInstallation;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;

/**
 * Class that defines the ALM ALM Client Create/Update Package Phase as a Jenkins pipeline step.
 * Example usage :
 * almclientPackage (antInstallation: "${antInstallation}",
 *      addPropertyFileLink: "false",
 *      allFilerevisions: "false",
 *      alm.baseUrl: "http://IKAN000:8080/alm",
 *      alm.package.archived: "false",
 *      alm.package.description: "Package ${packageName}",
 *      alm.package.name: "${packageName}",
 *      alm.package.oid: "0",
 *      alm.package.status: "0",
 *      alm.package.targetDate: "${packageDate}",
 *      alm.package.userid: "${packageUserid}",
 *      alm.project.name: "${projectName}",
 *      alm.projectStream.buildPrefix: "${buildPrefix}",
 *      alm.projectStream.buildSuffix: "${buildSuffix}",
 *      alm.rest.password: "",
 *      alm.rest.userid: "",
 *      alm.stopOnError: "true",
 *      builderName: "Client",
 *      dir.package: "${packageDir}",
 *      filerevisions: "${packageFile}",
 *      listPackageFilesAfter: "true",
 *      listPackageFilesBefore: "false",
 *      local.list: "${almSystem_location}/list"
 * )
 * 
 * Note that this Step CANNOT be used in a Jenkins Freestyle Project !
 *
 * @author IKAN
 *
 */
public class AlmclientPackageStep extends BaseAntStep {

	private static final String MAIN_SCRIPT = "almclientPackage.xml";
	private static final String FQ_PHASE_NAME = "com.ikanalm.plugins.jenkins.almclientpackage";

    private String antInstallation;
    private String addPropertyFileLink;
    private String allFilerevisions;
    private String almBaseUrl;
    private String almPackageArchived;
    private String almPackageDescription;
    private String almPackageName;
    private String almPackageOid;
    private String almPackageStatus;
    private String almPackageTargetDate;
    private String almPackageUserid;
    private String almProjectName;
    private String almProjectStreamBuildPrefix;
    private String almProjectStreamBuildSuffix;
    private String almRestPassword;
    private String almRestUserid;
    private String almStopOnError;
    private String builderName;
    private String dirPackage;
    private String filerevisions;
    private String listPackageFilesAfter;
    private String listPackageFilesBefore;
    private String localList;

    @DataBoundConstructor
	public AlmclientPackageStep(String antInstallation, String almBaseUrl, String almProjectName) {
	        this.antInstallation = antInstallation;
                this.almBaseUrl = almBaseUrl;
                this.almProjectName = almProjectName;

	}
        
	@Extension
	public static class DescriptorImpl extends BaseDescriptorImpl {
        /**
         * Display name of the Step in the Pipeline Syntax Snippet Generator
         */
		@Override 
		public String getDisplayName() {
            return "ALM Client Create/Update Package";
        }
		/**
		 * Name of the function that is used to call the Step in a Pipeline script 
		 */
		@Override
		public String getFunctionName() {
			return "almclientPackage";
		}
        /**
         * method that populates the configuration select named "antInstallation" with Ant installations
         * 
         * @return the antInstallation items
         */
        public ListBoxModel doFillAntInstallationItems() {
            ListBoxModel items = new ListBoxModel();
        	AntInstallation[] antInstallations = Jenkins.getInstance().getDescriptorByType(Ant.DescriptorImpl.class).getInstallations();
            
        	for (AntInstallation antInstallation : antInstallations) {
				items.add(antInstallation.getName());
			}
        	
            return items;
        }

        /**
         * Field Validation
         * @param value the antInstallation
         * @return the antInstallation description
         */
		public FormValidation doCheckAntInstallation(@QueryParameter String value) {
			if (Util.fixEmptyAndTrim(value) == null) {
				return FormValidation.warning("Choose Ant Installation");
			}
			return FormValidation.ok();
		}

		public FormValidation doCheckAlmBaseUrl(@QueryParameter String value) {
			if (Util.fixEmptyAndTrim(value) == null) {
				return FormValidation.warning("IKAN ALM URL Login cannot be empty");
			}
			return FormValidation.ok();
		}

		public FormValidation doCheckAlmPackageOid(@QueryParameter String value) {
			if (Util.fixEmptyAndTrim(value) == null) return FormValidation.ok();
			try {
				Integer.parseInt(value);
				return FormValidation.ok();
			} catch (NumberFormatException e) {
				return FormValidation.error("Package Oid to update. (0=create) not numeric");
			}
		}

		public FormValidation doCheckAlmProjectName(@QueryParameter String value) {
			if (Util.fixEmptyAndTrim(value) == null) {
				return FormValidation.warning("ALM Project Name where the Package is created or updated. cannot be empty");
			}
			return FormValidation.ok();
		}

	}

	/* (non-Javadoc)
	 * @see com.ikanalm.plugins.jenkins.base.BaseAntStep#getFQStepName()
	 */
	@Override
	public String getFQStepName() {
		return FQ_PHASE_NAME;
	}

	/* (non-Javadoc)
	 * @see com.ikanalm.plugins.jenkins.base.BaseAntStep#getMainScript()
	 */
	@Override
	public String getMainScript() {
		return MAIN_SCRIPT;
	}
	/**
	 * @return the antInstallation
	 */
	public String getAntInstallation() {
		return antInstallation;
	}

	/**
	 * @param antInstallation the antInstallation to set
	 */
	public void setAntInstallation(String antInstallation) {
		this.antInstallation = antInstallation;
	}

	/**
	 * @return the addPropertyFileLink
	 */
	public String getAddPropertyFileLink() {
		return addPropertyFileLink;
	}
	/**
	 * @param addPropertyFileLink the addPropertyFileLink to set
	 */
	@DataBoundSetter
	public void setAddPropertyFileLink(String addPropertyFileLink) {
		this.addPropertyFileLink = addPropertyFileLink;
	}

	/**
	 * @return the allFilerevisions
	 */
	public String getAllFilerevisions() {
		return allFilerevisions;
	}
	/**
	 * @param allFilerevisions the allFilerevisions to set
	 */
	@DataBoundSetter
	public void setAllFilerevisions(String allFilerevisions) {
		this.allFilerevisions = allFilerevisions;
	}

	/**
	 * @return the almBaseUrl
	 */
	public String getAlmBaseUrl() {
		return almBaseUrl;
	}
	/**
	 * @param almBaseUrl the almBaseUrl to set
	 */
	@DataBoundSetter
	public void setAlmBaseUrl(String almBaseUrl) {
		this.almBaseUrl = almBaseUrl;
	}

	/**
	 * @return the almPackageArchived
	 */
	public String getAlmPackageArchived() {
		return almPackageArchived;
	}
	/**
	 * @param almPackageArchived the almPackageArchived to set
	 */
	@DataBoundSetter
	public void setAlmPackageArchived(String almPackageArchived) {
		this.almPackageArchived = almPackageArchived;
	}

	/**
	 * @return the almPackageDescription
	 */
	public String getAlmPackageDescription() {
		return almPackageDescription;
	}
	/**
	 * @param almPackageDescription the almPackageDescription to set
	 */
	@DataBoundSetter
	public void setAlmPackageDescription(String almPackageDescription) {
		this.almPackageDescription = almPackageDescription;
	}

	/**
	 * @return the almPackageName
	 */
	public String getAlmPackageName() {
		return almPackageName;
	}
	/**
	 * @param almPackageName the almPackageName to set
	 */
	@DataBoundSetter
	public void setAlmPackageName(String almPackageName) {
		this.almPackageName = almPackageName;
	}

	/**
	 * @return the almPackageOid
	 */
	public String getAlmPackageOid() {
		return almPackageOid;
	}
	/**
	 * @param almPackageOid the almPackageOid to set
	 */
	@DataBoundSetter
	public void setAlmPackageOid(String almPackageOid) {
		this.almPackageOid = almPackageOid;
	}

	/**
	 * @return the almPackageStatus
	 */
	public String getAlmPackageStatus() {
		return almPackageStatus;
	}
	/**
	 * @param almPackageStatus the almPackageStatus to set
	 */
	@DataBoundSetter
	public void setAlmPackageStatus(String almPackageStatus) {
		this.almPackageStatus = almPackageStatus;
	}

	/**
	 * @return the almPackageTargetDate
	 */
	public String getAlmPackageTargetDate() {
		return almPackageTargetDate;
	}
	/**
	 * @param almPackageTargetDate the almPackageTargetDate to set
	 */
	@DataBoundSetter
	public void setAlmPackageTargetDate(String almPackageTargetDate) {
		this.almPackageTargetDate = almPackageTargetDate;
	}

	/**
	 * @return the almPackageUserid
	 */
	public String getAlmPackageUserid() {
		return almPackageUserid;
	}
	/**
	 * @param almPackageUserid the almPackageUserid to set
	 */
	@DataBoundSetter
	public void setAlmPackageUserid(String almPackageUserid) {
		this.almPackageUserid = almPackageUserid;
	}

	/**
	 * @return the almProjectName
	 */
	public String getAlmProjectName() {
		return almProjectName;
	}
	/**
	 * @param almProjectName the almProjectName to set
	 */
	@DataBoundSetter
	public void setAlmProjectName(String almProjectName) {
		this.almProjectName = almProjectName;
	}

	/**
	 * @return the almProjectStreamBuildPrefix
	 */
	public String getAlmProjectStreamBuildPrefix() {
		return almProjectStreamBuildPrefix;
	}
	/**
	 * @param almProjectStreamBuildPrefix the almProjectStreamBuildPrefix to set
	 */
	@DataBoundSetter
	public void setAlmProjectStreamBuildPrefix(String almProjectStreamBuildPrefix) {
		this.almProjectStreamBuildPrefix = almProjectStreamBuildPrefix;
	}

	/**
	 * @return the almProjectStreamBuildSuffix
	 */
	public String getAlmProjectStreamBuildSuffix() {
		return almProjectStreamBuildSuffix;
	}
	/**
	 * @param almProjectStreamBuildSuffix the almProjectStreamBuildSuffix to set
	 */
	@DataBoundSetter
	public void setAlmProjectStreamBuildSuffix(String almProjectStreamBuildSuffix) {
		this.almProjectStreamBuildSuffix = almProjectStreamBuildSuffix;
	}

	/**
	 * @return the almRestPassword
	 */
	public String getAlmRestPassword() {
		return almRestPassword;
	}
	/**
	 * @param almRestPassword the almRestPassword to set
	 */
	@DataBoundSetter
	public void setAlmRestPassword(String almRestPassword) {
		this.almRestPassword = almRestPassword;
	}

	/**
	 * @return the almRestUserid
	 */
	public String getAlmRestUserid() {
		return almRestUserid;
	}
	/**
	 * @param almRestUserid the almRestUserid to set
	 */
	@DataBoundSetter
	public void setAlmRestUserid(String almRestUserid) {
		this.almRestUserid = almRestUserid;
	}

	/**
	 * @return the almStopOnError
	 */
	public String getAlmStopOnError() {
		return almStopOnError;
	}
	/**
	 * @param almStopOnError the almStopOnError to set
	 */
	@DataBoundSetter
	public void setAlmStopOnError(String almStopOnError) {
		this.almStopOnError = almStopOnError;
	}

	/**
	 * @return the builderName
	 */
	public String getBuilderName() {
		return builderName;
	}
	/**
	 * @param builderName the builderName to set
	 */
	@DataBoundSetter
	public void setBuilderName(String builderName) {
		this.builderName = builderName;
	}

	/**
	 * @return the dirPackage
	 */
	public String getDirPackage() {
		return dirPackage;
	}
	/**
	 * @param dirPackage the dirPackage to set
	 */
	@DataBoundSetter
	public void setDirPackage(String dirPackage) {
		this.dirPackage = Utils.normalizePath(dirPackage);
	}

	/**
	 * @return the filerevisions
	 */
	public String getFilerevisions() {
		return filerevisions;
	}
	/**
	 * @param filerevisions the filerevisions to set
	 */
	@DataBoundSetter
	public void setFilerevisions(String filerevisions) {
		this.filerevisions = Utils.normalizePath(filerevisions);
	}

	/**
	 * @return the listPackageFilesAfter
	 */
	public String getListPackageFilesAfter() {
		return listPackageFilesAfter;
	}
	/**
	 * @param listPackageFilesAfter the listPackageFilesAfter to set
	 */
	@DataBoundSetter
	public void setListPackageFilesAfter(String listPackageFilesAfter) {
		this.listPackageFilesAfter = listPackageFilesAfter;
	}

	/**
	 * @return the listPackageFilesBefore
	 */
	public String getListPackageFilesBefore() {
		return listPackageFilesBefore;
	}
	/**
	 * @param listPackageFilesBefore the listPackageFilesBefore to set
	 */
	@DataBoundSetter
	public void setListPackageFilesBefore(String listPackageFilesBefore) {
		this.listPackageFilesBefore = listPackageFilesBefore;
	}

	/**
	 * @return the localList
	 */
	public String getLocalList() {
		return localList;
	}
	/**
	 * @param localList the localList to set
	 */
	@DataBoundSetter
	public void setLocalList(String localList) {
		this.localList = Utils.normalizePath(localList);
	}


}
