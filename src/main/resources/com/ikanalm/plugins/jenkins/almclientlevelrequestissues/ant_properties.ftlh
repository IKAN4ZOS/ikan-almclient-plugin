

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

<#-- builderName field is optional : -->
<#if root.builderName??>
builderName=${root.builderName}
</#if>

<#-- excelSheetName field is optional : -->
<#if root.excelSheetName??>
excel.sheetName=${root.excelSheetName}
</#if>

<#-- issueFile field is optional : -->
<#if root.issueFile??>
issue.file=${root.issueFile}
</#if>

<#-- levelName field is optional : -->
<#if root.levelName??>
levelName=${root.levelName}
</#if>

<#-- levelRequestIssuesFileType field is optional : -->
<#if root.levelRequestIssuesFileType??>
levelRequestIssues.fileType=${root.levelRequestIssuesFileType}
</#if>

<#-- levelRequestOid field is optional : -->
<#if root.levelRequestOid??>
levelRequestOid=${root.levelRequestOid}
</#if>

<#-- levelRequestAccess field is optional : -->
<#if root.levelRequestAccess??>
levelRequest_access=${root.levelRequestAccess}
</#if>

<#-- localList field is optional : -->
<#if root.localList??>
local.list=${root.localList}
</#if>

<#-- packageName field is optional : -->
<#if root.packageName??>
packageName=${root.packageName}
</#if>

<#-- projectName field is optional : -->
<#if root.projectName??>
projectName=${root.projectName}
</#if>

<#-- projectStreamPrefix field is optional : -->
<#if root.projectStreamPrefix??>
projectStreamPrefix=${root.projectStreamPrefix}
</#if>

<#-- projectStreamSuffix field is optional : -->
<#if root.projectStreamSuffix??>
projectStreamSuffix=${root.projectStreamSuffix}
</#if>
