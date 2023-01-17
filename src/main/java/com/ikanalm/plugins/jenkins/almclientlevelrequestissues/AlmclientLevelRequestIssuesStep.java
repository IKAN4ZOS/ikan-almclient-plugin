/**
 * 
 */
package com.ikanalm.plugins.jenkins.almclientlevelrequestissues;


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
 * Class that defines the ALM ALM Client Level Request Issues List Phase as a Jenkins pipeline step.
 * Example usage :
 * almclientLevelRequestIssues (antInstallation: "${antInstallation}",
 *      alm.baseUrl: "http://IKAN000:8080/alm",
 *      alm.rest.password: "",
 *      alm.rest.userid: "",
 *      alm.stopOnError: "true",
 *      builderName: "Client",
 *      excel.sheetName: "levelRequestIssues",
 *      issue.file: "${local.list}/levelRequestIssues.lst",
 *      levelName: "${levelName}",
 *      levelRequestIssues.fileType: "lst",
 *      levelRequestOid: "0",
 *      levelRequest_access: "lroid",
 *      local.list: "${almSystem_location}/list",
 *      packageName: "${packageName}",
 *      projectName: "${projectName}",
 *      projectStreamPrefix: "${buildPrefix}",
 *      projectStreamSuffix: "${buildSuffix}"
 * )
 * 
 * Note that this Step CANNOT be used in a Jenkins Freestyle Project !
 *
 * @author IKAN
 *
 */
public class AlmclientLevelRequestIssuesStep extends BaseAntStep {

	private static final String MAIN_SCRIPT = "almclientLevelRequestIssues.xml";
	private static final String FQ_PHASE_NAME = "com.ikanalm.plugins.jenkins.almclientlevelrequestissues";

    private String antInstallation;
    private String almBaseUrl;
    private String almRestPassword;
    private String almRestUserid;
    private String almStopOnError;
    private String builderName;
    private String excelSheetName;
    private String issueFile;
    private String levelName;
    private String levelRequestIssuesFileType;
    private String levelRequestOid;
    private String levelRequestAccess;
    private String localList;
    private String packageName;
    private String projectName;
    private String projectStreamPrefix;
    private String projectStreamSuffix;

    @DataBoundConstructor
	public AlmclientLevelRequestIssuesStep(String antInstallation, String almBaseUrl) {
	        this.antInstallation = antInstallation;
                this.almBaseUrl = almBaseUrl;

	}
        
	@Extension
	public static class DescriptorImpl extends BaseDescriptorImpl {
        /**
         * Display name of the Step in the Pipeline Syntax Snippet Generator
         */
		@Override 
		public String getDisplayName() {
            return "ALM Client Level Request Issues List";
        }
		/**
		 * Name of the function that is used to call the Step in a Pipeline script 
		 */
		@Override
		public String getFunctionName() {
			return "almclientLevelRequestIssues";
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

		public FormValidation doCheckLevelRequestOid(@QueryParameter String value) {
			if (Util.fixEmptyAndTrim(value) == null) return FormValidation.ok();
			try {
				Integer.parseInt(value);
				return FormValidation.ok();
			} catch (NumberFormatException e) {
				return FormValidation.error("level request Oid to select not numeric");
			}
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
	 * @return the excelSheetName
	 */
	public String getExcelSheetName() {
		return excelSheetName;
	}
	/**
	 * @param excelSheetName the excelSheetName to set
	 */
	@DataBoundSetter
	public void setExcelSheetName(String excelSheetName) {
		this.excelSheetName = excelSheetName;
	}

	/**
	 * @return the issueFile
	 */
	public String getIssueFile() {
		return issueFile;
	}
	/**
	 * @param issueFile the issueFile to set
	 */
	@DataBoundSetter
	public void setIssueFile(String issueFile) {
		this.issueFile = Utils.normalizePath(issueFile);
	}

	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}
	/**
	 * @param levelName the levelName to set
	 */
	@DataBoundSetter
	public void setLevelName(String levelName) {
		this.levelName = levelName;
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
	 * @return the levelRequestIssuesFileType
	 */
	public String getLevelRequestIssuesFileType() {
		return levelRequestIssuesFileType;
	}
	/**
	 * @param levelRequestIssuesFileType the levelRequestIssuesFileType to set
	 */
	@DataBoundSetter
	public void setLevelRequestIssuesFileType(String levelRequestIssuesFileType) {
		this.levelRequestIssuesFileType = levelRequestIssuesFileType;
	}

	/**
	 * @return the levelRequestOid
	 */
	public String getLevelRequestOid() {
		return levelRequestOid;
	}
	/**
	 * @param levelRequestOid the levelRequestOid to set
	 */
	@DataBoundSetter
	public void setLevelRequestOid(String levelRequestOid) {
		this.levelRequestOid = levelRequestOid;
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

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}
	/**
	 * @param packageName the packageName to set
	 */
	@DataBoundSetter
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	@DataBoundSetter
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the projectStreamPrefix
	 */
	public String getProjectStreamPrefix() {
		return projectStreamPrefix;
	}
	/**
	 * @param projectStreamPrefix the projectStreamPrefix to set
	 */
	@DataBoundSetter
	public void setProjectStreamPrefix(String projectStreamPrefix) {
		this.projectStreamPrefix = projectStreamPrefix;
	}

	/**
	 * @return the projectStreamSuffix
	 */
	public String getProjectStreamSuffix() {
		return projectStreamSuffix;
	}
	/**
	 * @param projectStreamSuffix the projectStreamSuffix to set
	 */
	@DataBoundSetter
	public void setProjectStreamSuffix(String projectStreamSuffix) {
		this.projectStreamSuffix = projectStreamSuffix;
	}


}
