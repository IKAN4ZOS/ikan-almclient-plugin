/**
 * 
 */
package com.ikanalm.plugins.jenkins.almclientapproval;


import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import com.ikanalm.plugins.base.steps.BaseAntStep;

import hudson.Extension;
import hudson.Util;
import hudson.tasks.Ant;
import hudson.tasks.Ant.AntInstallation;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;

/**
 * Class that defines the ALM ALM Client Level Request Approval Phase as a Jenkins pipeline step.
 * Example usage :
 * almclientApproval (antInstallation: "${antInstallation}",
 *      alm.baseUrl: "http://IKAN000:8080/alm",
 *      alm.levelRequest.levelName: "${levelName}",
 *      alm.levelRequest.oid: "",
 *      alm.package.name: "${packageName}",
 *      alm.project.name: "${projectName}",
 *      alm.projectStream.buildPrefix: "${buildPrefix}",
 *      alm.projectStream.buildSuffix: "${buildSuffix}",
 *      alm.rest.password: "",
 *      alm.rest.userid: "",
 *      alm.stopOnError: "true",
 *      approveLevelRequest: "true",
 *      approveReason: "",
 *      builderName: "Client",
 *      levelRequest_access: "lroid"
 * )
 * 
 * Note that this Step CANNOT be used in a Jenkins Freestyle Project !
 *
 * @author IKAN
 *
 */
public class AlmclientApprovalStep extends BaseAntStep {

	private static final String MAIN_SCRIPT = "almclientApproval.xml";
	private static final String FQ_PHASE_NAME = "com.ikanalm.plugins.jenkins.almclientapproval";

    private String antInstallation;
    private String almBaseUrl;
    private String almLevelRequestLevelName;
    private String almLevelRequestOid;
    private String almPackageName;
    private String almProjectName;
    private String almProjectStreamBuildPrefix;
    private String almProjectStreamBuildSuffix;
    private String almRestPassword;
    private String almRestUserid;
    private String almStopOnError;
    private String approveLevelRequest;
    private String approveReason;
    private String builderName;
    private String levelRequestAccess;

    @DataBoundConstructor
	public AlmclientApprovalStep(String antInstallation, String almBaseUrl, String almProjectName) {
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
            return "ALM Client Level Request Approval";
        }
		/**
		 * Name of the function that is used to call the Step in a Pipeline script 
		 */
		@Override
		public String getFunctionName() {
			return "almclientApproval";
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
	 * @return the approveLevelRequest
	 */
	public String getApproveLevelRequest() {
		return approveLevelRequest;
	}
	/**
	 * @param approveLevelRequest the approveLevelRequest to set
	 */
	@DataBoundSetter
	public void setApproveLevelRequest(String approveLevelRequest) {
		this.approveLevelRequest = approveLevelRequest;
	}

	/**
	 * @return the approveReason
	 */
	public String getApproveReason() {
		return approveReason;
	}
	/**
	 * @param approveReason the approveReason to set
	 */
	@DataBoundSetter
	public void setApproveReason(String approveReason) {
		this.approveReason = approveReason;
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


}
