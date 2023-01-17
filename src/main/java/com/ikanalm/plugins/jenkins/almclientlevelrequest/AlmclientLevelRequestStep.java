/**
 * 
 */
package com.ikanalm.plugins.jenkins.almclientlevelrequest;


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
 * Class that defines the ALM ALM Client Level Request Create/Update/Control Phase as a Jenkins pipeline step.
 * Example usage :
 * almclientLevelRequest (antInstallation: "${antInstallation}",
 *      alm.baseUrl: "http://IKAN000:8080/alm",
 *      alm.buildNumber: "${buildNumber}",
 *      alm.deploysToExecute: "${deployEnvironments}",
 *      alm.description: "Build created by ${builderName} with Rest API",
 *      alm.levelRequest.levelName: "${levelName}",
 *      alm.levelRequest.oid: "",
 *      alm.package.name: "${packageName}",
 *      alm.project.name: "${projectName}",
 *      alm.projectStream.buildPrefix: "${buildPrefix}",
 *      alm.projectStream.buildSuffix: "${buildSuffix}",
 *      alm.redeliver: "${redeliver}",
 *      alm.rest.password: "",
 *      alm.rest.userid: "",
 *      alm.startDate: "",
 *      alm.status: "",
 *      alm.stopOnError: "true",
 *      alm.vcrTag: "",
 *      builderAction: "Create",
 *      builderName: "Client",
 *      levelRequest_access: "lroid",
 *      paramfile: "parameters.properties",
 *      status.maxwait_minutes: "30",
 *      status.success.mandatory: "true",
 *      status.waiting_seconds: "30"
 * )
 * 
 * Note that this Step CANNOT be used in a Jenkins Freestyle Project !
 *
 * @author IKAN
 *
 */
public class AlmclientLevelRequestStep extends BaseAntStep {

	private static final String MAIN_SCRIPT = "almclientLevelRequest.xml";
	private static final String FQ_PHASE_NAME = "com.ikanalm.plugins.jenkins.almclientlevelrequest";

    private String antInstallation;
    private String almBaseUrl;
    private String almBuildNumber;
    private String almDeploysToExecute;
    private String almDescription;
    private String almLevelRequestLevelName;
    private String almLevelRequestOid;
    private String almPackageName;
    private String almProjectName;
    private String almProjectStreamBuildPrefix;
    private String almProjectStreamBuildSuffix;
    private String almRedeliver;
    private String almRestPassword;
    private String almRestUserid;
    private String almStartDate;
    private String almStatus;
    private String almStopOnError;
    private String almVcrTag;
    private String builderAction;
    private String builderName;
    private String levelRequestAccess;
    private String paramfile;
    private String statusMaxwaitMinutes;
    private String statusSuccessMandatory;
    private String statusWaitingSeconds;

    @DataBoundConstructor
	public AlmclientLevelRequestStep(String antInstallation, String almBaseUrl, String almProjectName) {
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
            return "ALM Client Level Request Create/Update/Control";
        }
		/**
		 * Name of the function that is used to call the Step in a Pipeline script 
		 */
		@Override
		public String getFunctionName() {
			return "almclientLevelRequest";
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

		public FormValidation doCheckAlmBuildNumber(@QueryParameter String value) {
			if (Util.fixEmptyAndTrim(value) == null) return FormValidation.ok();
			try {
				Integer.parseInt(value);
				return FormValidation.ok();
			} catch (NumberFormatException e) {
				return FormValidation.error("Specific ALM buildNumber to use for a deploy Level not numeric");
			}
		}

		public FormValidation doCheckAlmLevelRequestOid(@QueryParameter String value) {
			if (Util.fixEmptyAndTrim(value) == null) return FormValidation.ok();
			try {
				Integer.parseInt(value);
				return FormValidation.ok();
			} catch (NumberFormatException e) {
				return FormValidation.error("LR Oid parameter used when levelRequest_access=lroid not numeric");
			}
		}

		public FormValidation doCheckAlmProjectName(@QueryParameter String value) {
			if (Util.fixEmptyAndTrim(value) == null) {
				return FormValidation.warning("ALM Project Name where the Level Request is created or updated. cannot be empty");
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
	 * @return the almBuildNumber
	 */
	public String getAlmBuildNumber() {
		return almBuildNumber;
	}
	/**
	 * @param almBuildNumber the almBuildNumber to set
	 */
	@DataBoundSetter
	public void setAlmBuildNumber(String almBuildNumber) {
		this.almBuildNumber = almBuildNumber;
	}

	/**
	 * @return the almDeploysToExecute
	 */
	public String getAlmDeploysToExecute() {
		return almDeploysToExecute;
	}
	/**
	 * @param almDeploysToExecute the almDeploysToExecute to set
	 */
	@DataBoundSetter
	public void setAlmDeploysToExecute(String almDeploysToExecute) {
		this.almDeploysToExecute = almDeploysToExecute;
	}

	/**
	 * @return the almDescription
	 */
	public String getAlmDescription() {
		return almDescription;
	}
	/**
	 * @param almDescription the almDescription to set
	 */
	@DataBoundSetter
	public void setAlmDescription(String almDescription) {
		this.almDescription = almDescription;
	}

	/**
	 * @return the almLevelRequestLevelName
	 */
	public String getAlmLevelRequestLevelName() {
		return almLevelRequestLevelName;
	}
	/**
	 * @param almLevelRequestLevelName the almLevelRequestLevelName to set
	 */
	@DataBoundSetter
	public void setAlmLevelRequestLevelName(String almLevelRequestLevelName) {
		this.almLevelRequestLevelName = almLevelRequestLevelName;
	}

	/**
	 * @return the almLevelRequestOid
	 */
	public String getAlmLevelRequestOid() {
		return almLevelRequestOid;
	}
	/**
	 * @param almLevelRequestOid the almLevelRequestOid to set
	 */
	@DataBoundSetter
	public void setAlmLevelRequestOid(String almLevelRequestOid) {
		this.almLevelRequestOid = almLevelRequestOid;
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
	 * @return the almRedeliver
	 */
	public String getAlmRedeliver() {
		return almRedeliver;
	}
	/**
	 * @param almRedeliver the almRedeliver to set
	 */
	@DataBoundSetter
	public void setAlmRedeliver(String almRedeliver) {
		this.almRedeliver = almRedeliver;
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
	 * @return the almStartDate
	 */
	public String getAlmStartDate() {
		return almStartDate;
	}
	/**
	 * @param almStartDate the almStartDate to set
	 */
	@DataBoundSetter
	public void setAlmStartDate(String almStartDate) {
		this.almStartDate = almStartDate;
	}

	/**
	 * @return the almStatus
	 */
	public String getAlmStatus() {
		return almStatus;
	}
	/**
	 * @param almStatus the almStatus to set
	 */
	@DataBoundSetter
	public void setAlmStatus(String almStatus) {
		this.almStatus = almStatus;
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
	 * @return the almVcrTag
	 */
	public String getAlmVcrTag() {
		return almVcrTag;
	}
	/**
	 * @param almVcrTag the almVcrTag to set
	 */
	@DataBoundSetter
	public void setAlmVcrTag(String almVcrTag) {
		this.almVcrTag = almVcrTag;
	}

	/**
	 * @return the builderAction
	 */
	public String getBuilderAction() {
		return builderAction;
	}
	/**
	 * @param builderAction the builderAction to set
	 */
	@DataBoundSetter
	public void setBuilderAction(String builderAction) {
		this.builderAction = builderAction;
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
	 * @return the levelRequestAccess
	 */
	public String getLevelRequestAccess() {
		return levelRequestAccess;
	}
	/**
	 * @param levelRequestAccess the levelRequestAccess to set
	 */
	@DataBoundSetter
	public void setLevelRequestAccess(String levelRequestAccess) {
		this.levelRequestAccess = levelRequestAccess;
	}

	/**
	 * @return the paramfile
	 */
	public String getParamfile() {
		return paramfile;
	}
	/**
	 * @param paramfile the paramfile to set
	 */
	@DataBoundSetter
	public void setParamfile(String paramfile) {
		this.paramfile = paramfile;
	}

	/**
	 * @return the statusMaxwaitMinutes
	 */
	public String getStatusMaxwaitMinutes() {
		return statusMaxwaitMinutes;
	}
	/**
	 * @param statusMaxwaitMinutes the statusMaxwaitMinutes to set
	 */
	@DataBoundSetter
	public void setStatusMaxwaitMinutes(String statusMaxwaitMinutes) {
		this.statusMaxwaitMinutes = statusMaxwaitMinutes;
	}

	/**
	 * @return the statusSuccessMandatory
	 */
	public String getStatusSuccessMandatory() {
		return statusSuccessMandatory;
	}
	/**
	 * @param statusSuccessMandatory the statusSuccessMandatory to set
	 */
	@DataBoundSetter
	public void setStatusSuccessMandatory(String statusSuccessMandatory) {
		this.statusSuccessMandatory = statusSuccessMandatory;
	}

	/**
	 * @return the statusWaitingSeconds
	 */
	public String getStatusWaitingSeconds() {
		return statusWaitingSeconds;
	}
	/**
	 * @param statusWaitingSeconds the statusWaitingSeconds to set
	 */
	@DataBoundSetter
	public void setStatusWaitingSeconds(String statusWaitingSeconds) {
		this.statusWaitingSeconds = statusWaitingSeconds;
	}


}
