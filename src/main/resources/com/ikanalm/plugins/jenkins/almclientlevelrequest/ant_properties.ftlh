

alm.baseUrl=${root.almBaseUrl}

<#-- almBuildNumber field is optional : -->
<#if root.almBuildNumber??>
alm.buildNumber=${root.almBuildNumber}
</#if>

<#-- almDeploysToExecute field is optional : -->
<#if root.almDeploysToExecute??>
alm.deploysToExecute=${root.almDeploysToExecute}
</#if>

<#-- almDescription field is optional : -->
<#if root.almDescription??>
alm.description=${root.almDescription}
</#if>

<#-- almLevelRequestLevelName field is optional : -->
<#if root.almLevelRequestLevelName??>
alm.levelRequest.levelName=${root.almLevelRequestLevelName}
</#if>

<#-- almLevelRequestOid field is optional : -->
<#if root.almLevelRequestOid??>
alm.levelRequest.oid=${root.almLevelRequestOid}
</#if>

<#-- almPackageName field is optional : -->
<#if root.almPackageName??>
alm.package.name=${root.almPackageName}
</#if>

alm.project.name=${root.almProjectName}

<#-- almProjectStreamBuildPrefix field is optional : -->
<#if root.almProjectStreamBuildPrefix??>
alm.projectStream.buildPrefix=${root.almProjectStreamBuildPrefix}
</#if>

<#-- almProjectStreamBuildSuffix field is optional : -->
<#if root.almProjectStreamBuildSuffix??>
alm.projectStream.buildSuffix=${root.almProjectStreamBuildSuffix}
</#if>

<#-- almRedeliver field is optional : -->
<#if root.almRedeliver??>
alm.redeliver=${root.almRedeliver}
</#if>

<#-- almRestPassword field is optional : -->
<#if root.almRestPassword??>
alm.rest.password=${root.almRestPassword}
</#if>

<#-- almRestUserid field is optional : -->
<#if root.almRestUserid??>
alm.rest.userid=${root.almRestUserid}
</#if>

<#-- almStartDate field is optional : -->
<#if root.almStartDate??>
alm.startDate=${root.almStartDate}
</#if>

<#-- almStatus field is optional : -->
<#if root.almStatus??>
alm.status=${root.almStatus}
</#if>

<#-- almStopOnError field is optional : -->
<#if root.almStopOnError??>
alm.stopOnError=${root.almStopOnError}
</#if>

<#-- almVcrTag field is optional : -->
<#if root.almVcrTag??>
alm.vcrTag=${root.almVcrTag}
</#if>

<#-- builderAction field is optional : -->
<#if root.builderAction??>
builderAction=${root.builderAction}
</#if>

<#-- builderName field is optional : -->
<#if root.builderName??>
builderName=${root.builderName}
</#if>

<#-- levelRequestAccess field is optional : -->
<#if root.levelRequestAccess??>
levelRequest_access=${root.levelRequestAccess}
</#if>

<#-- paramfile field is optional : -->
<#if root.paramfile??>
paramfile=${root.paramfile}
</#if>

<#-- statusMaxwaitMinutes field is optional : -->
<#if root.statusMaxwaitMinutes??>
status.maxwait_minutes=${root.statusMaxwaitMinutes}
</#if>

<#-- statusSuccessMandatory field is optional : -->
<#if root.statusSuccessMandatory??>
status.success.mandatory=${root.statusSuccessMandatory}
</#if>

<#-- statusWaitingSeconds field is optional : -->
<#if root.statusWaitingSeconds??>
status.waiting_seconds=${root.statusWaitingSeconds}
</#if>
