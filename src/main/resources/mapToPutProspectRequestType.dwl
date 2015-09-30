%dw 1.0
%output application/java
%function countryLookup(sfdcCountry) {
	(country: 'USA') when sfdcCountry == 'USA' //add new line like this for different countries
} unless sfdcCountry is :null otherwise country: null

%function stateLookup(sfdcState) {
	(state: 'USA-CA') when sfdcState == 'CA' //add new line like this for different states
} unless sfdcState is :null otherwise state: null
---
{
    "prospectData": {
        "contactData": {
            "addressData": [{
                "addressFormatType": null,
                "addressID": null,
                "addressLineData": [{
                    "descriptor": null,
                    "type": "ADDRESS_LINE_1",
                    "value": payload.BillingStreet default p('wdayf.street')
                }],
                "addressReference": null,
                "countryCityReference": null,
                "countryReference": {
                    "descriptor": null,
                    "ID": [{
                        "type": "ISO_3166-1_Alpha-3_Code",
                        "value": countryLookup(payload.BillingCountry).country default p('wdayf.country')
                    }]
                },
                "countryRegionReference": {
                    "descriptor": null,
                    "ID": [{
                        "type": "Country_Region_ID",
                        "value": stateLookup(payload.BillingState).state default p('wdayf.state')
                    }]
                },
                "defaultedBusinessSiteAddress": null,
                "delete": null,
                "doNotReplaceAll": null,
                "effectiveDate": null,
                "formattedAddress": null,
                "lastModified": null,
                "municipality": payload.BillingCity default p('wdayf.city'),
                "municipalityLocal": null,
                "postalCode": payload.BillingPostalCode default p('wdayf.postalCode'),
                "submunicipalityData": [],
                "subregionData": [],
                "usageData": [{
                    "comments": null,
                    "public": false,
                    "typeData": [
                        {
                            "primary": true,
                            "typeReference": {
                                "descriptor": null,
                                "ID": [
                                    {
                                        "type": "Communication_Usage_Type_ID",
                                        "value": "HOME"
                                    }
                                ]
                            }
                        }
                    ],
                    "useForReference": [],
                    "useForTenantedReference": []
                }]
            }],
            "emailAddressData" 	: [],
            "instantMessengerData": [],
            "phoneData": [{
                "areaCode": null,
                "countryISOCode": null,
                "formattedPhone": null,
                "internationalPhoneCode": null,
                "phoneDeviceTypeReference": {
                    "descriptor": null,
                    "ID": [{
                        "type": "Phone_Device_Type_ID",
                        "value": "1063.5"
                    }]
                },
                "phoneExtension": null,
                "phoneNumber": payload.Phone default p('wdayf.phone'),
                "usageData": [{
                    "comments": null,
                    "public": true,
                    "typeData": [{
                        "primary": true,
                        "typeReference": {
                            "descriptor": null,
                            "ID": [{
                                "type": "Communication_Usage_Type_ID",
                                "value": "HOME"
                            }]
                        }
                        }],
                    "useForReference": [],
                    "useForTenantedReference": []
                }]
            }],
            "webAddressData": [{
            	"webAddress": payload.Website when (payload.Website matches /http(s)?:\/\/.*/) 
            	            otherwise "http://" ++ payload.Website,
            	"usageData": [{
                    "public": true,
                    "typeData": [{
                        "primary": true,
                        "typeReference": {
                            "ID": [{
                                "type": "Communication_Usage_Type_ID",
                                "value": "HOME"
                            }]
                        }
                    }]
                }]
            }] unless payload.Website == null otherwise []
        },
        "customerID": null,
        "prospectID": payload.Id,
        "prospectName": payload.Name,
        "prospectSourceReference": {
            "descriptor": null,
            "ID": [{
                    "type": "External_Source_Code",
                    "value": "SFDC"
                }]
        }
    },
    "prospectReference": {
        "descriptor": null,
        "ID": [{
                "type": "Prospect_Reference_ID",
                "value": payload.wdayfReferenceId
            }]
    } unless payload.wdayfReferenceId == null otherwise null,
    "version": null
} as :object {class: "com.workday.revenue.PutProspectRequestType"}