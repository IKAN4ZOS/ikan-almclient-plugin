

alm.baseUrl=${root.almBaseUrl}

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

<#-- almRestPassword field is optional : -->
<#if root.almRestPassword??>
alm.rest.password=${root.almRestPassword}
</#if>

<#-- almRestUserid field is optional : -->
<#if root.almRestUserid??>
alm.rest.userid=${root.almRestUserid}
</#if>

<#-- almStopOnError field is optional : -->
<#if root.almStopOnError??>
alm.stopOnError=${root.almStopOnError}
</#if>

<#-- approveLevelRequest field is optional : -->
<#if root.approveLevelRequest??>
approveLevelRequest=${root.approveLevelRequest}
</#if>

<#-- approveReason field is optional : -->
<#if root.approveReason??>
approveReason=${root.approveReason}
</#if>

<#-- builderName field is optional : -->
<#if root.builderName??>
builderName=${root.builderName}
</#if>

<#-- levelRequestAccess field is optional : -->
<#if root.levelRequestAccess??>
levelRequest_access=${root.levelRequestAccess}
</#if>
