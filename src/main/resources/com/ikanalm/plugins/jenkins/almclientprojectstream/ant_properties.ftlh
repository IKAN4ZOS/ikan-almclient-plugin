

alm.baseUrl=${root.almBaseUrl}

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

<#-- almProjectStreamCreate field is optional : -->
<#if root.almProjectStreamCreate??>
almProjectStreamCreate=${root.almProjectStreamCreate}
</#if>

<#-- builderName field is optional : -->
<#if root.builderName??>
builderName=${root.builderName}
</#if>

projectStreamList=${root.projectStreamList}

<#-- projectStreamListWithStream field is optional : -->
<#if root.projectStreamListWithStream??>
projectStreamList.withStream=${root.projectStreamListWithStream}
</#if>

<#-- streamAcceptForcedBuild field is optional : -->
<#if root.streamAcceptForcedBuild??>
stream.acceptForcedBuild=${root.streamAcceptForcedBuild}
</#if>

stream.buildPrefix=${root.streamBuildPrefix}

<#-- streamBuildSuffix field is optional : -->
<#if root.streamBuildSuffix??>
stream.buildSuffix=${root.streamBuildSuffix}
</#if>

<#-- streamBuildTypeIndex field is optional : -->
<#if root.streamBuildTypeIndex??>
stream.buildType.index=${root.streamBuildTypeIndex}
</#if>

<#-- streamDescription field is optional : -->
<#if root.streamDescription??>
stream.description=${root.streamDescription}
</#if>

<#-- streamHighestBuildNumber field is optional : -->
<#if root.streamHighestBuildNumber??>
stream.highestBuildNumber=${root.streamHighestBuildNumber}
</#if>

stream.lifecycle=${root.streamLifecycle}

<#-- streamStatusIndex field is optional : -->
<#if root.streamStatusIndex??>
stream.status.index=${root.streamStatusIndex}
</#if>

<#-- streamTagBased field is optional : -->
<#if root.streamTagBased??>
stream.tagBased=${root.streamTagBased}
</#if>

<#-- streamTagTemplate field is optional : -->
<#if root.streamTagTemplate??>
stream.tagTemplate=${root.streamTagTemplate}
</#if>

<#-- streamVcrBranchName field is optional : -->
<#if root.streamVcrBranchName??>
stream.vcrBranchName=${root.streamVcrBranchName}
</#if>
