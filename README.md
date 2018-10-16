
# Anypoint Template: Salesforce to Workday Account-Prospect Migration	

<!-- Header (start) -->

<!-- Header (end) -->

# License Agreement
This template is subject to the conditions of the <a href="https://s3.amazonaws.com/templates-examples/AnypointTemplateLicense.pdf">MuleSoft License Agreement</a>. Review the terms of the license before downloading and using this template. You can use this template for free with the Mule Enterprise Edition, CloudHub, or as a trial in Anypoint Studio. 
# Use Case
<!-- Use Case (start) -->
As an admin I want to migrate Accounts from Salesforce to Workday as Prospects.

This template serves as a foundation for the process of migrating accounts from Salesforce, being able to specify the filtering criteria and desired behavior when an account already exists in Workday as Prospect.

As implemented, this template leverages the Mule batch module.
The batch job is divided into *Process* and *On Complete* stages.

Firstly the template queries all the existing Accounts at Salesforce that match the filtering criteria (in this case being a Prospect Account).
In the *Process* stage, the template queries the Workday for already existing prospects based on the Salesforce IDs retrieved before the batch processing.
The accounts get inserted or updated in the Workday system based on the results of these queries.
Lastly, the information about the migration results is sent to pre-configured e-mail recipient and output to the console as well.
<!-- Use Case (end) -->

# Considerations
<!-- Default Considerations (start) -->

<!-- Default Considerations (end) -->

<!-- Considerations (start) -->
There are certain pre-requisites that must be considered to run this template. All of them deal with the preparations in both source and destination systems, that must be made for the template to run smoothly. **Failing to do so could lead to unexpected behavior of the template.**
<!-- Considerations (end) -->



## Salesforce Considerations

Here's what you need to know about Salesforce to get this template to work:

- Where can I check that the field configuration for my Salesforce instance is the right one? See: <a href="https://help.salesforce.com/HTViewHelpDoc?id=checking_field_accessibility_for_a_particular_field.htm&language=en_US">Salesforce: Checking Field Accessibility for a Particular Field</a>.
- Can I modify the Field Access Settings? How? See: <a href="https://help.salesforce.com/HTViewHelpDoc?id=modifying_field_access_settings.htm&language=en_US">Salesforce: Modifying Field Access Settings</a>.

### As a Data Source

If the user who configured the template for the source system does not have at least *read only* permissions for the fields that are fetched, then an *InvalidFieldFault* API fault displays.

```
java.lang.RuntimeException: [InvalidFieldFault [ApiQueryFault 
[ApiFault  exceptionCode='INVALID_FIELD'
exceptionMessage='Account.Phone, Account.Rating, Account.RecordTypeId, 
Account.ShippingCity
^
ERROR at Row:1:Column:486
No such column 'RecordTypeId' on entity 'Account'. If you are attempting to 
use a custom field, be sure to append the '__c' after the custom field name. 
Reference your WSDL or the describe call for the appropriate names.'
]
row='1'
column='486'
]
]
```




## Workday Considerations


### As a Data Destination

There are no considerations with using Workday as a data destination.




## Workday Financials Considerations


### As a Data Destination

There are no considerations with using Workday Financials as a data destination.

# Run it!
Simple steps to get this template running.
<!-- Run it (start) -->
In any of the ways you would like to run this Template this is an example of the output you'll see after browse toting the HTTP endpoint:

<pre>
<h1>Batch Process initiated</h1>
<b>ID:</b>6eea3cc6-7c96-11e3-9a65-55f9f3ae584e<br/>
<b>Records to be processed: </b>3<br/>
<b>Started execution on: </b>Fri Mar 19 10:08:16 CET 2015
</pre>
<!-- Run it (end) -->

## Running On Premises
In this section we help you run this template on your computer.
<!-- Running on premise (start) -->

<!-- Running on premise (end) -->

### Where to Download Anypoint Studio and the Mule Runtime
If you are new to Mule, download this software:

+ [Download Anypoint Studio](https://www.mulesoft.com/platform/studio)
+ [Download Mule runtime](https://www.mulesoft.com/lp/dl/mule-esb-enterprise)

**Note:** Anypoint Studio requires JDK 8.
<!-- Where to download (start) -->

<!-- Where to download (end) -->

### Importing a Template into Studio
In Studio, click the Exchange X icon in the upper left of the taskbar, log in with your Anypoint Platform credentials, search for the template, and click Open.
<!-- Importing into Studio (start) -->

<!-- Importing into Studio (end) -->

### Running on Studio
After you import your template into Anypoint Studio, follow these steps to run it:

+ Locate the properties file `mule.dev.properties`, in src/main/resources.
+ Complete all the properties required as per the examples in the "Properties to Configure" section.
+ Right click the template project folder.
+ Hover your mouse over `Run as`.
+ Click `Mule Application (configure)`.
+ Inside the dialog, select Environment and set the variable `mule.env` to the value `dev`.
+ Click `Run`.
<!-- Running on Studio (start) -->

<!-- Running on Studio (end) -->

### Running on Mule Standalone
Update the properties in one of the property files, for example in mule.prod.properties, and run your app with a corresponding environment variable. In this example, use `mule.env=prod`. 


## Running on CloudHub
When creating your application in CloudHub, go to Runtime Manager > Manage Application > Properties to set the environment variables listed in "Properties to Configure" as well as the mule.env value.
<!-- Running on Cloudhub (start) -->
Once your app is all set and started, supposing you choose as domain name `sfdcaccountmigration` to trigger the use case you just need to browse to `http://sfdcaccountmigration.cloudhub.io/migrateaccounts` and report will be sent to the emails configured.
<!-- Running on Cloudhub (end) -->

### Deploying a Template in CloudHub
In Studio, right click your project name in Package Explorer and select Anypoint Platform > Deploy on CloudHub.
<!-- Deploying on Cloudhub (start) -->

<!-- Deploying on Cloudhub (end) -->

## Properties to Configure
To use this template, configure properties such as credentials, configurations, etc.) in the properties file or in CloudHub from Runtime Manager > Manage Application > Properties. The sections that follow list example values.
### Application Configuration
<!-- Application Configuration (start) -->
**Application Configuration**

+ http.port `9090` 
+ page.size `200`

**Salesforce Connector Configuration**

+ sfdc.username `bob.dylan@orga`
+ sfdc.password `DylanPassword123`
+ sfdc.securityToken `avsfwCUl7apQs56Xq2AKi3X`

**Workday Connector Configuration**

+ wdayf.username `wdayf_user`
+ wdayf.password `wdayf_password`
+ wdayf.tenant `wdayf_tenant`
+ wdayf.host `{your Workday domain}.workday.com`

+ wdayf.country `USA`
+ wdayf.state `USA-CA`
+ wdayf.postalCode `90001`
+ wdayf.city `San Francisco`
+ wdayf.street `Main Street 123`
+ wdayf.phone `123-4567`

**SMTP Services Configuration**

+ smtp.host `smtp.gmail.com`
+ smtp.port `587`
+ smtp.user `gmail_user`
+ smtp.password `gmail_password`

**EMail Details**

+ mail.from `batch.migrateaccounts.migration%40mulesoft.com`
+ mail.to `john.doe@mulesoft.com`
+ mail.subject `Batch Job Finished Report`
<!-- Application Configuration (end) -->

# API Calls
<!-- API Calls (start) -->
Salesforce imposes limits on the number of API Calls that can be made. However, we make API call to Salesforce only once during migration, so this is not something to worry about.
<!-- API Calls (end) -->

# Customize It!
This brief guide provides a high level understanding of how this template is built and how you can change it according to your needs. As Mule applications are based on XML files, this page describes the XML files used with this template. More files are available such as test classes and Mule application files, but to keep it simple, we focus on these XML files:

* config.xml
* businessLogic.xml
* endpoints.xml
* errorHandling.xml<!-- Customize it (start) -->

<!-- Customize it (end) -->

## config.xml
<!-- Default Config XML (start) -->
This file provides the configuration for connectors and configuration properties. Only change this file to make core changes to the connector processing logic. Otherwise, all parameters that can be modified should instead be in a properties file, which is the recommended place to make changes.<!-- Default Config XML (end) -->

<!-- Config XML (start) -->

<!-- Config XML (end) -->

## businessLogic.xml
<!-- Default Business Logic XML (start) -->
Functional aspect of the Template is implemented on this XML, directed by one flow responsible of excecuting the logic.
For the pourpose of this particular Template the *mainFlow* just excecutes a batch job which handles all the migration logic.
This flow has Exception handler that points to *errorHandling.xml* file.<!-- Default Business Logic XML (end) -->

<!-- Business Logic XML (start) -->

<!-- Business Logic XML (end) -->

## endpoints.xml
<!-- Default Endpoints XML (start) -->
This is the file where you will find the inbound side of your integration app.
This template has only a [HTTP Connector](http://www.mulesoft.org/documentation/display/current/HTTP+Connector) as the way to trigger the use case.

**HTTP Connector** - Start Report Generation

+ `${http.port}` is set as a property to be defined either on a property file or in CloudHub environment variables.
+ The path configured by default is `migrateaccounts` and you are free to change it for the one that you prefer.
+ The host name for all endpoints in your CloudHub configuration should be defined as `0.0.0.0`. CloudHub will then route requests from your application domain URL to the endpoint.<!-- Default Endpoints XML (end) -->

<!-- Endpoints XML (start) -->

<!-- Endpoints XML (end) -->

## errorHandling.xml
<!-- Default Error Handling XML (start) -->
This file handles how your integration reacts depending on the different exceptions. This file provides error handling that is referenced by the main flow in the business logic.<!-- Default Error Handling XML (end) -->

<!-- Error Handling XML (start) -->

<!-- Error Handling XML (end) -->

<!-- Extras (start) -->

<!-- Extras (end) -->
