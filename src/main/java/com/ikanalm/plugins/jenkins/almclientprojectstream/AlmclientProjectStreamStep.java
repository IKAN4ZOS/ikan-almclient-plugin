/**
 * 
 */
package com.ikanalm.plugins.jenkins.almclientprojectstream;


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
 * Class that defines the ALM ALM Client Project Stream(s) Create Phase as a Jenkins pipeline step.
 * Example usage :
 * almclientProjectStream (antInstallation: "${antInstallation}",
 *      alm.baseUrl: "http://IKAN000:8080/alm",
 *      alm.rest.password: "",
 *      alm.rest.userid: "",
 *      alm.stopOnError: "true",
 *      almProjectStreamCreate: "true",
 *      builderName: "Client",
 *      projectStreamList: "${projectName}",
 *      projectStreamList.withStream: "${buildPrefixRef} ${buildSuffixRef}",
 *      stream.acceptForcedBuild: "true",
 *      stream.buildPrefix: "${buildPrefix}",
 *      stream.buildSuffix: "${buildSuffix}",
 *      stream.buildType.index: "0",
 *      stream.description: "${projectName} ${buildPrefix} - ${buildSuffix}",
 *      stream.highestBuildNumber: "0",
 *      stream.lifecycle: "BRANCH",
 *      stream.status.index: "0",
 *      stream.tagBased: "false",
 *      stream.tagTemplate: "${streamType}_${prefix}_${suffix}[_${packageName}]_b${buildNumber}",
 *      stream.vcrBranchName: "${buildPrefix}-$buildSuffix}"
 * )
 * 
 * Note that this Step CANNOT be used in a Jenkins Freestyle Project !
 *
 * @author IKAN
 *
 */
public class AlmclientProjectStreamStep extends BaseAntStep {

	private static final String MAIN_SCRIPT = "almclientProjectStream.xml";
	private static final String FQ_PHASE_NAME = "com.ikanalm.plugins.jenkins.almclientprojectstream";

    private String antInstallation;
    private String almBaseUrl;
    private String almRestPassword;
    private String almRestUserid;
    private String almStopOnError;
    private String almProjectStreamCreate;
    private String builderName;
    private String projectStreamList;
    private String projectStreamListWithStream;
    private String streamAcceptForcedBuild;
    private String streamBuildPrefix;
    private String streamBuildSuffix;
    private String streamBuildTypeIndex;
    private String streamDescription;
    private String streamHighestBuildNumber;
    private String streamLifecycle;
    private String streamStatusIndex;
    private String streamTagBased;
    private String streamTagTemplate;
    private String streamVcrBranchName;

    @DataBoundConstructor
	public AlmclientProjectStreamStep(String antInstallation, String almBaseUrl, String projectStreamList, String streamBuildPrefix, String streamLifecycle) {
	        this.antInstallation = antInstallation;
                this.almBaseUrl = almBaseUrl;
                this.projectStreamList = projectStreamList;
                this.streamBuildPrefix = streamBuildPrefix;
                this.streamLifecycle = streamLifecycle;

	}
        
	@Extension
	public static class DescriptorImpl extends BaseDescriptorImpl {
        /**
         * Display name of the Step in the Pipeline Syntax Snippet Generator
         */
		@Override 
		public String getDisplayName() {
            return "ALM Client Project Stream(s) Create";
        }
		/**
		 * Name of the function that is used to call the Step in a Pipeline script 
		 */
		@Override
		public String getFunctionName() {
			return "almclientProjectStream";
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

		public FormValidation doCheckProjectStreamList(@QueryParameter String value) {
			if (Util.fixEmptyAndTrim(value) == null) {
				return FormValidation.warning("ALM Project Stream Reference(s) to select for creating stream.buildPrefix and stream.buildSuffixrni.e. [Project] [buildPrefix] [buildSuffix];.. (case sensitive) cannot be empty");
			}
			return FormValidation.ok();
		}

		public FormValidation doCheckStreamBuildPrefix(@QueryParameter String value) {
			if (Util.fixEmptyAndTrim(value) == null) {
				return FormValidation.warning("projectStream Prefix to create. cannot be empty");
			}
			return FormValidation.ok();
		}

		public FormValidation doCheckStreamHighestBuildNumber(@QueryParameter String value) {
			if (Util.fixEmptyAndTrim(value) == null) return FormValidation.ok();
			try {
				Integer.parseInt(value);
				return FormValidation.ok();
			} catch (NumberFormatException e) {
				return FormValidation.error("Project Stream Highest Build Number for tagging. not numeric");
			}
		}

		public FormValidation doCheckStreamLifecycle(@QueryParameter String value) {
			if (Util.fixEmptyAndTrim(value) == null) {
				return FormValidation.warning("projectStream Lifecycle (must exist) to use for linking the IKAN ALM project Stream. cannot be empty");
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
	 * @return the almProjectStreamCreate
	 */
	public String getAlmProjectStreamCreate() {
		return almProjectStreamCreate;
	}
	/**
	 * @param almProjectStreamCreate the almProjectStreamCreate to set
	 */
	@DataBoundSetter
	public void setAlmProjectStreamCreate(String almProjectStreamCreate) {
		this.almProjectStreamCreate = almProjectStreamCreate;
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
	 * @return the projectStreamList
	 */
	public String getProjectStreamList() {
		return projectStreamList;
	}
	/**
	 * @param projectStreamList the projectStreamList to set
	 */
	@DataBoundSetter
	public void setProjectStreamList(String projectStreamList) {
		this.projectStreamList = projectStreamList;
	}

	/**
	 * @return the projectStreamListWithStream
	 */
	public String getProjectStreamListWithStream() {
		return projectStreamListWithStream;
	}
	/**
	 * @param projectStreamListWithStream the projectStreamListWithStream to set
	 */
	@DataBoundSetter
	public void setProjectStreamListWithStream(String projectStreamListWithStream) {
		this.projectStreamListWithStream = projectStreamListWithStream;
	}

	/**
	 * @return the streamAcceptForcedBuild
	 */
	public String getStreamAcceptForcedBuild() {
		return streamAcceptForcedBuild;
	}
	/**
	 * @param streamAcceptForcedBuild the streamAcceptForcedBuild to set
	 */
	@DataBoundSetter
	public void setStreamAcceptForcedBuild(String streamAcceptForcedBuild) {
		this.streamAcceptForcedBuild = streamAcceptForcedBuild;
	}

	/**
	 * @return the streamBuildPrefix
	 */
	public String getStreamBuildPrefix() {
		return streamBuildPrefix;
	}
	/**
	 * @param streamBuildPrefix the streamBuildPrefix to set
	 */
	@DataBoundSetter
	public void setStreamBuildPrefix(String streamBuildPrefix) {
		this.streamBuildPrefix = streamBuildPrefix;
	}

	/**
	 * @return the streamBuildSuffix
	 */
	public String getStreamBuildSuffix() {
		return streamBuildSuffix;
	}
	/**
	 * @param streamBuildSuffix the streamBuildSuffix to set
	 */
	@DataBoundSetter
	public void setStreamBuildSuffix(String streamBuildSuffix) {
		this.streamBuildSuffix = streamBuildSuffix;
	}

	/**
	 * @return the streamBuildTypeIndex
	 */
	public String getStreamBuildTypeIndex() {
		return streamBuildTypeIndex;
	}
	/**
	 * @param streamBuildTypeIndex the streamBuildTypeIndex to set
	 */
	@DataBoundSetter
	public void setStreamBuildTypeIndex(String streamBuildTypeIndex) {
		this.streamBuildTypeIndex = streamBuildTypeIndex;
	}

	/**
	 * @return the streamDescription
	 */
	public String getStreamDescription() {
		return streamDescription;
	}
	/**
	 * @param streamDescription the streamDescription to set
	 */
	@DataBoundSetter
	public void setStreamDescription(String streamDescription) {
		this.streamDescription = streamDescription;
	}

	/**
	 * @return the streamHighestBuildNumber
	 */
	public String getStreamHighestBuildNumber() {
		return streamHighestBuildNumber;
	}
	/**
	 * @param streamHighestBuildNumber the streamHighestBuildNumber to set
	 */
	@DataBoundSetter
	public void setStreamHighestBuildNumber(String streamHighestBuildNumber) {
		this.streamHighestBuildNumber = streamHighestBuildNumber;
	}

	/**
	 * @return the streamLifecycle
	 */
	public String getStreamLifecycle() {
		return streamLifecycle;
	}
	/**
	 * @param streamLifecycle the streamLifecycle to set
	 */
	@DataBoundSetter
	public void setStreamLifecycle(String streamLifecycle) {
		this.streamLifecycle = streamLifecycle;
	}

	/**
	 * @return the streamStatusIndex
	 */
	public String getStreamStatusIndex() {
		return streamStatusIndex;
	}
	/**
	 * @param streamStatusIndex the streamStatusIndex to set
	 */
	@DataBoundSetter
	public void setStreamStatusIndex(String streamStatusIndex) {
		this.streamStatusIndex = streamStatusIndex;
	}

	/**
	 * @return the streamTagBased
	 */
	public String getStreamTagBased() {
		return streamTagBased;
	}
	/**
	 * @param streamTagBased the streamTagBased to set
	 */
	@DataBoundSetter
	public void setStreamTagBased(String streamTagBased) {
		this.streamTagBased = streamTagBased;
	}

	/**
	 * @return the streamTagTemplate
	 */
	public String getStreamTagTemplate() {
		return streamTagTemplate;
	}
	/**
	 * @param streamTagTemplate the streamTagTemplate to set
	 */
	@DataBoundSetter
	public void setStreamTagTemplate(String streamTagTemplate) {
		this.streamTagTemplate = streamTagTemplate;
	}

	/**
	 * @return the streamVcrBranchName
	 */
	public String getStreamVcrBranchName() {
		return streamVcrBranchName;
	}
	/**
	 * @param streamVcrBranchName the streamVcrBranchName to set
	 */
	@DataBoundSetter
	public void setStreamVcrBranchName(String streamVcrBranchName) {
		this.streamVcrBranchName = streamVcrBranchName;
	}


}
