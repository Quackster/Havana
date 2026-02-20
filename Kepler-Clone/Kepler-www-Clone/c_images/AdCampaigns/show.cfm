<cfapplication name="img" sessionManagement="true">

<!--- Get full path to images. --->
<cfset imageDirectory = expandPath(".")>

<!--- Get directory --->
<cfdirectory action="list" directory="#imageDirectory#" name="images" filter="*.png">

<!--- Do we have any images? --->
<cfif images.recordCount gt 0>

	<!--- store ID values --->
	<cfif not structKeyExists(session, "totalList") or session.totalList is "">
		<cfset session.totalList = valueList(images.name)>
	</cfif>

	<!--- pick a random number --->
	<cfset pickedIndex = randRange(1, listLen(session.totalList))>
	
	<!--- pick from list --->
	<cfset image = listGetAt(session.totalList, pickedIndex)>
	
	<!--- remove from total list --->
	<cfset session.totalList = listDeleteAt(session.totalList, pickedIndex)>	
	
	<!--- display it --->
	<cffile action="readbinary" file="#imageDirectory#\#image#" variable="pic"/>
	<cfcontent variable="#pic#">

</cfif>