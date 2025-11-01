package com.mortgage.valuation.model.crm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollateralValuation {
    @JsonProperty("Id")
    private String id;
    @JsonProperty("ClientRef__c")
    private String clientRef;
    @JsonProperty("QuoteRequestId__c")
    private String quoteRequestId;
    @JsonProperty("LLC_BI__Collateral__c")
    private String collateralMgmt;
    @JsonProperty("ValuationId__c")
    private String valuationId;
    @JsonProperty("ValuationStatus__c")
    private String valuationStatus;
    @JsonProperty("Report_Issued_Version__c")
    private Integer reportIssuedVersion;
    @JsonProperty("SelectedQuoteId__c")
    private String selectedQuoteId;
    @JsonProperty("LLC_BI__Valuation_Date__c")
    private String valuationDate;
    @JsonProperty("IsValuationActive__c")
    private Boolean isValuationActive;
    @JsonProperty("ValuationReportId__c")
    private String valuationReportId;
    @JsonProperty("PassedQC__c")
    private Boolean passedQc;
    @JsonProperty("RecordTypeId")
    private String recordTypeId;
    @JsonProperty("JobNo__c")
    private String jobNo;
    @JsonProperty("AIP_Job_Number__c")
    private String aipJobNo;
    @JsonProperty("JobType__c")
    private String jobType;
    @JsonProperty("ValPathway__c")
    private String valPathway;
    @JsonProperty("EPCGrade__c")
    private String epcGrade;
    @JsonProperty("Flood_Score__c")
    private Integer floodScore;
    @JsonProperty("Flood2050__c")
    private String flood2050;
    @JsonProperty("AVMConfidence__c")
    private String avmConfidence;
    @JsonProperty("AVMValue__c")
    private String avmValue;
    @JsonProperty("Valuation_Fee__c")
    private BigDecimal valuationFee;
    @JsonProperty("Status__c")
    private String status;
    @JsonProperty("TransactionId__c")
    private String transactionId;
    @JsonProperty("InstructionRequestId__c")
    private String instructionRequestId;
    @JsonProperty("AllocationRequestId__c")
    private String allocationRequestId;
    @JsonProperty("Property_Address__c")
    private String propertyAddress;
    @JsonProperty("Market_Value__c")
    private BigInteger marketValue;
    @JsonProperty("Rental_Value__c")
    private BigInteger rentalValue;
    @JsonProperty("Date_of_Valuation__c")
    private String dateOfValuation;
    @JsonProperty("Date_of_Inspection__c")
    private String dateOfInspection;
    @JsonProperty("PropertyType__c")
    private String propertyType;
    @JsonProperty("PropertyStyle__c")
    private String propertyStyle;
    @JsonProperty("Tenure__c")
    private String tenure;
    @JsonProperty("Year_Built__c")
    private Integer yearBuilt;
    @JsonProperty("Year_of_most_recent_extension_conversion__c")
    private Integer lastExtensionOrConversionYear;
    @JsonProperty("No_of_Bedrooms__c")
    private Integer noOfBedrooms;
    @JsonProperty("No_of_Living_Rooms__c")
    private Integer noOfLivingRooms;
    @JsonProperty("No_of_Bathrooms__c")
    private Integer noOfBathrooms;
    @JsonProperty("No_of_Kitchens__c")
    private Integer noOfKitchens;
    @JsonProperty("No_of_Separate_Wc_s__c")
    private Integer noOfSeparateWcs;
    @JsonProperty("No_of_Other_Rooms__c")
    private Integer noOfOtherRooms;
    @JsonProperty("No_of_Garages__c")
    private Integer noOfGarages;
    @JsonProperty("No_of_Outbuildings__c")
    private Integer noOfOutbuildings;
    @JsonProperty("Has_a_Garden__c")
    private String garden;
    @JsonProperty("No_of_Parking_Spaces__c")
    private Integer noOfParkingSpaces;
    @JsonProperty("Has_fixed_central_heating__c")
    private String centralHeating;
    @JsonProperty("Central_heating_type__c")
    private String centralHeatingType;
    @JsonProperty("Condition_at_the_time_of_Valuation__c")
    private String propertyCondition;
    @JsonProperty("Gross_Floor_area__c")
    private Integer grossFloorArea;
    @JsonProperty("Is_property_suitable_security__c")
    private String isSuitableSecurity;
    @JsonProperty("Sustainable_demand_from_purchasers__c")
    private Boolean purchasersDemandSustainable;
    @JsonProperty("Sustainable_demand_from_tenants__c")
    private Boolean tenantsDemandSustainable;
    @JsonProperty("Property_appears_to_be_let_on_AST_s__c")
    private String letOnAst;
    @JsonProperty("Reinstatement_cost__c")
    private Integer reinstatementCost;
    @JsonProperty("Any_retention_recommended__c")
    private String retentionRecommended;
    @JsonProperty("Specialist_reports_required__c")
    private String specialistReportsRequired;
    @JsonProperty("Structural_Engineers__c")
    private String structuralEngineerFoundations;
    @JsonProperty("Damp_timber__c")
    private String dampAndTimber;
    @JsonProperty("Arboricultural__c")
    private Boolean arboriculturalReport;
    @JsonProperty("Cavity_Wall_Tie__c")
    private String structuralEngineerWalls;
    @JsonProperty("Mining__c")
    private Boolean miningReport;
    @JsonProperty("Electrical__c")
    private String electrical;
    @JsonProperty("EWS1__c")
    private String ews1;
    @JsonProperty("Other_specialist_report__c")
    private String otherSpecialistReport;
    @JsonProperty("Adjacent_to_or_over_commercial_use__c")
    private String commercialUseAdjacent;
    @JsonProperty("Evidence_of_occupancy_ties_restrictions__c")
    private String occupancyTiesOrRestrictions;
    @JsonProperty("Traditional_Construction__c")
    private String traditionalConstruction;
    @JsonProperty("If_no_what_construction__c")
    private String constructionType;
    @JsonProperty("Any_flying_FH_evident__c")
    private String flyingCreepingFreehold;
    @JsonProperty("No_of_Bedrooms_Less_Than_6_5_Sq_M__c")
    private Integer noOfBedroomsLessThan65Sqm;
    @JsonProperty("No_of_Bedrooms_6_51_8_Sq_M__c")
    private Integer noOfBedrooms6518Sqm;
    @JsonProperty("No_of_Bedrooms_8_01_10_Sq_M__c")
    private Integer noOfBedrooms80110Sqm;
    @JsonProperty("No_of_Bedrooms_10_Sq_M__c")
    private Integer noOfBedroomsGreaterThan10Sqm;
    @JsonProperty("No_of_Bedrooms_With_Ensuite__c")
    private Integer noOfBedroomsWithEnsuite;
    @JsonProperty("No_of_Bedrooms_With_Kitchenettes__c")
    private Integer noOfBedroomsWithKitchenettes;
    @JsonProperty("Size_of_communal_living_space__c")
    private Integer communalLivingSpaceSize;
    @JsonProperty("Size_of_shared_kitchens__c")
    private Integer sharedKitchensSize;
    @JsonProperty("Mains_water__c")
    private String mainsWater;
    @JsonProperty("Mains_Gas__c")
    private String mainsGas;
    @JsonProperty("Mains_drainage__c")
    private String mainsDrainage;
    @JsonProperty("Mains_Electricity__c")
    private String mainsElectricity;
    @JsonProperty("Solar_Panels__c")
    private String solarPanels;
    @JsonProperty("Other_utilities_information__c")
    private String otherElectricitySource;
    @JsonProperty("Access_is_private_and_made_Y_N__c")
    private String isAccessMadeUp;
    @JsonProperty("No_of_Epc_s_E_Or_Better__c")
    private Integer noOfEpcsEOrBetter;
    @JsonProperty("No_of_Epc_s_C_Or_Better__c")
    private Integer noOfEpcsCOrBetter;
    @JsonProperty("Additonal_information__c")
    private String additionalInformation;
    @JsonProperty("Valuer_Name__c")
    private String valuerName;
    @JsonProperty("Valuer_Firm__c")
    private String valuerFirm;
    @JsonProperty("Report_Due_Date__c")
    private String reportDueAt;
    @JsonProperty("claySubsidenceScore__c")
    private Integer claySubsidenceScore;
    @JsonProperty("Article4__c")
    private String article4;
    @JsonProperty("epcOrigin__c")
    private String epcOrigin;
    @JsonProperty("epcCertificateNo__c")
    private String epcCertificateNo;
    @JsonProperty("leaseholdTerm__c")
    private String leaseholdTerm;
    @JsonProperty("reinstatementAmount__c")
    private BigDecimal reinstatementAmount;
    @JsonProperty("claimsScore__c")
    private Integer claimsScore;
    @JsonProperty("Total_Subsidence_Score__c")
    private Integer totalSubsidenceScore;
    @JsonProperty("tpo__c")
    private String tpo;
    @JsonProperty("conservationArea__c")
    private String conservationArea;
    @JsonProperty("partiia__c")
    private String partiia;
    @JsonProperty("additionalLicencedArea__c")
    private String additionalLicencedArea;
    @JsonProperty("selectiveLicencedArea__c")
    private String selectiveLicencedArea;
    @JsonProperty("radonPot1km__c")
    private String radonPot1km;
    @JsonProperty("listedBuilding__c")
    private String listedBuilding;
    @JsonProperty("coalMiningRisk__c")
    private String coalMiningRisk;
    @JsonProperty("mundicRisk__c")
    private String mundicRisk;
    @JsonProperty("rmRentalAvm__c")
    private Double avmRentalValue;
    @JsonProperty("rmRentalConfidence__c")
    private String avmRentalConfidence;
    @JsonProperty("managementArea__c")
    private String managementArea;
    @JsonProperty("cheshireBrine__c")
    private String cheshireBrine;
    @JsonProperty("undefendedTotal__c")
    private Integer undefendedTotal;
    @JsonProperty("Milestone_Id__c")
    private String milestoneId;
    @JsonProperty("Survey_or_Branch_Id__c")
    private String surveyorBranchId;
    @JsonProperty("Transaction_Request_Milestone_Id__c")
    private String transactionMileId;
    @JsonProperty("Reason__c")
    private String reason;
    @JsonProperty("Appointment_Date__c")
    private String appointmentDate;
    @JsonProperty("Time_Window_Start__c")
    private String timeWindowStart;
    @JsonProperty("Time_Window_End__c")
    private String timeWindowEnd;
    @JsonProperty("Date_Predicted__c")
    private String datePredicted;
    @JsonProperty("Date_Achieved__c")
    private String dateAchieved;
    @JsonProperty("Transaction_Request_Type__c")
    private String transactionRequestType;
    @JsonProperty("Property_Name__c")
    private String propertyName;
    @JsonProperty("localAuthority__c")
    private String localAuthority;
    @JsonProperty("EPC_ValidUntil__c")
    private String epcValidUntil;
    @JsonProperty("Flood_Today_RAG__c")
    private String floodRag;
    @JsonProperty("Subsidence_RAG__c")
    private String subsRag;
    @JsonProperty("Mode__c")
    private String mode;
    @JsonProperty("Valuation_Report_Type__c")
    private String valuationReportType;
    @JsonProperty("Address_Match__c")
    private String doesAddressMatchInstruction;
    @JsonProperty("Actual_Address_If_Address_not_matched__c")
    private String reportAddress;
    @JsonProperty("Number_of_Houses__c")
    private Integer numberOfHouses;
    @JsonProperty("Number_of_Flats__c")
    private Integer numberOfFlats;
    @JsonProperty("Number_of_HMO_Houses__c")
    private Integer numberOfHmoHouses;
    @JsonProperty("Number_of_HMO_Flats__c")
    private Integer numberOfHmoFlats;
    @JsonProperty("IsThePropertyHMO__c")
    private String isHmo;
    @JsonProperty("Total_number_of_units__c")
    private Integer totalUnits;
    @JsonProperty("Unexpired_Term__c")
    private Integer unexpiredTerm;
    @JsonProperty("Converted_or_extended_in_past_10_years__c")
    private String convertedOrExtendedWithin10Years;
    @JsonProperty("is_New_Build__c")
    private String isNewBuild;
    @JsonProperty("If_new_build_has_build_reached_PC__c")
    private String hasBuildReachedPostCompletion;
    @JsonProperty("Is_reinspection_recomended__c")
    private String isReinspectionRecommended;
    @JsonProperty("Special_Risk_Of_Features__c")
    private String specialRisksOrFeatures;
    @JsonProperty("Is_plot_GT_3_acres__c")
    private String isPlotGreaterThan3Acres;
    @JsonProperty("Heating_Source_System__c")
    private String heatingSystem;
    @JsonProperty("Other_Heating_Source_Please_state__c")
    private String heatingSystemOtherDetails;
    @JsonProperty("RecommendedRetention__c")
    private BigInteger recommendedRetentionAmount;
    @JsonProperty("Roof__c")
    private String structuralEngineerRoof;
    @JsonProperty("Structural_Engineer_Outbuilding__c")
    private String structuralEngineerOutbuilding;
    @JsonProperty("Japanese_Knotweed__c")
    private String japaneseKnotweed;
    @JsonProperty("Other_Specialist_Report_Details__c")
    private String otherSpecialistReportDetails;
    @JsonProperty("Has_spray_foam_been_applied_to_the_roof__c")
    private String hasSprayFoamAppliedToRoof;
    @JsonProperty("Construction_Type__c")
    private String nonTraditionalConstructionType;
    @JsonProperty("Is_designated_defective_property_type__c")
    private String isDesignatedDefectivePropertyType;
    @JsonProperty("Does_it_appear_to_have_been_repaired__c")
    private String designatedDefectivePropertyRepaired;
    @JsonProperty("All_adjoining_properties_repaired__c")
    private String designatedDefectivePropertyAdjoiningRepaired;
    @JsonProperty("What_of_total_area_is_FFH__c")
    private String isFfhGreaterThan20Percent;
    @JsonProperty("Proportion_of_property_for_business_use__c")
    private String proportionOfPropertyUsedForBusiness;
    @JsonProperty("Is_access_from_public_land__c")
    private String isAccessFromPublicLand;
    @JsonProperty("Does_there_appear_to_be_shared_access__c")
    private String sharedAccess;
    @JsonProperty("Any_licensing_schemes_applicable__c")
    private String anyLicensingSchemes;
    @JsonProperty("Type_of_Licensing_scheme__c")
    private String licensingSchemeType;
    @JsonProperty("If_Other_license_please_state__c")
    private String licensingSchemeOtherDetails;
    @JsonProperty("Is_property_listed__c")
    private String isPropertyListed;
    @JsonProperty("Property_Band__c")
    private String listedPropertyBand;
    @JsonProperty("All_EPC_s_E_or_better__c")
    private String areAllEpcsBandEOrBetter;
    @JsonProperty("Property_suitable_security_reason__c")
    private String isSuitableSecurityReason;
    @JsonProperty("Property_Non_suitability_Other_reason__c")
    private String isSuitableSecurityReasonOtherDetails;
    @JsonProperty("All_flats_marketable_and_mortgageable__c")
    private String areAllUnitsIndividuallyMortgageable;
    @JsonProperty("Is_the_PropertyCurrently_let__c")
    private String isPropertyCurrentlyLet;
    @JsonProperty("Is_the_property_within_a_conservation_ar__c")
    private String withinConservationArea;
    @JsonProperty("Market_Value_on_completion_of_repairs_in__c")
    private BigInteger totalMarketValueAfterWorks;
    @JsonProperty("Market_Value_Single_Block_MUFB_Only__c")
    private BigInteger totalMarketValueSingleBlockInvestment;
    @JsonProperty("Property_Style__c")
    private String hmoPropertyStyle;
    @JsonProperty("Property_Type__c")
    private String hmoPropertyType;
    @JsonProperty("HMO_Number_Of_Bedrooms__c")
    private Integer hmoNumberOfBedrooms;
    @JsonProperty("Total_Floor_Area_Sq_m__c")
    private Integer totalFloorAreaSqm;
    @JsonProperty("article4Area__c")
    private String article4Area;
    @JsonProperty("Disposing_as_Individual_Units_within_12M__c")
    private String expectDisposalWithin12Months;
    @JsonProperty("Property_fulfil_the_def_of_S_257_HMO__c")
    private String fulfilsDefinitionOfS257Hmo;
    @JsonProperty("If_MUFB_are_any_flats_LT_30sqm__c")
    private String anyFlatsBelow30Sqm;
    @JsonProperty("If_MUFB_total_floors_in_the_block__c")
    private Integer totalFloorsInBlock;
    @JsonProperty("If_MUFB_total_flats_in_the_block__c")
    private Integer totalFlatsInBlock;
    @JsonProperty("is_single_property__c")
    private String isSingleProperty;
    @JsonProperty("is_multi_unit__c")
    private String isMultiUnit;
    @JsonProperty("Purchase_Or_Remo__c")
    private String transaction;
    @JsonProperty("Purchase_Price__c")
    private BigDecimal purchasePrice;
    @JsonProperty("Market_Rent__c")
    private BigDecimal marketRent;
    @JsonProperty("Report_Date__c")
    private String reportDate;
    @JsonProperty("If_newbuild_DIF_purchaser_incentive__c")
    private String purchaserIncentives;
    @JsonProperty("If_Flat_Floors_in_Block__c")
    private String numberOfFloorsInBuilding;
    @JsonProperty("If_flat_floor_on_which_located__c")
    private String flatFloorLocation;
    @JsonProperty("If_flat_total_flats_in_the_block__c")
    private String totalFlatsInBuilding;
    @JsonProperty("If_flat_served_by_lift__c")
    private String servedByLift;
    @JsonProperty("Year_of_conversion__c")
    private Integer yearOfConversion;
    @JsonProperty("If_Other_System_Please_State__c")
    private String otherHeatingSystemDetails;
    @JsonProperty("Floor_Area_Sq_m__c")
    private String floorAreaSqM;
    @JsonProperty("If_No_Other_Security_Specify_Reason__c")
    private String otherSecurityDetails;
    @JsonProperty("MMC_New_Build_Warranty_Seen_Acceptable__c")
    private String acceptableNewBuildWarranty;
    @JsonProperty("has_BOPAS_accreditation_been_seen__c")
    private String hasBopasAccreditationBeenSeen;
    @JsonProperty("EPC_Band__c")
    private String esgEpcBand;
    @JsonProperty("Has_the_proprty_had_works_since_EPC_date__c")
    private String worksSinceEPC;
    @JsonProperty("If_Yes_which_units__c")
    private String licensingSchemeDetails;
    @JsonProperty("Reviewer_Sentiment_1_5__c")
    private Integer reviewerSentiment;
    @JsonProperty("Reviewer__c")
    private String userId;
    @JsonProperty("MHP_Required__c")
    private Boolean mhpRequired;
    @JsonProperty("MHP_Utilised__c")
    private Boolean mhpUtilised;
    @JsonProperty("Escalated_to_physical_Redbook_val__c")
    private Boolean escalatedToPhysical;
    @JsonProperty("Bedroom_1_Market_Rent__c")
    private BigDecimal marketRentBedroom1;
    @JsonProperty("Bedroom_1_Notes__c")
    private String notesMarketRentBedroom1;
    @JsonProperty("Bedroom_2_Market_Rent__c")
    private BigDecimal marketRentBedroom2;
    @JsonProperty("Bedroom_2_Notes__c")
    private String notesMarketRentBedroom2;
    @JsonProperty("Bedroom_3_Market_Rent__c")
    private BigDecimal marketRentBedroom3;
    @JsonProperty("Bedroom_3_Notes__c")
    private String notesMarketRentBedroom3;
    @JsonProperty("Bedroom_4_Market_Rent__c")
    private BigDecimal marketRentBedroom4;
    @JsonProperty("Bedroom_4_Notes__c")
    private String notesMarketRentBedroom4;
    @JsonProperty("Bedroom_5_Market_Rent__c")
    private BigDecimal marketRentBedroom5;
    @JsonProperty("Bedroom_5_Notes__c")
    private String notesMarketRentBedroom5;
    @JsonProperty("Bedroom_6_Market_Rent__c")
    private BigDecimal marketRentBedroom6;
    @JsonProperty("Bedroom_6_Notes__c")
    private String notesMarketRentBedroom6;
    @JsonProperty("PropertyAgePriceRange__c")
    private String propertyAgeCondition;
    @JsonProperty("If_Partial_Let_How_Many_Let_Occupied__c")
    private String ifPartialHowManyRoomsAreOccupied;
    @JsonProperty("Was_the_property_tenanted__c")
    private String isTheLocationSubjectToAnyDominantMarketsForHmoTenants;
    @JsonProperty("If_key_industry_employer_please_state__c")
    private String ifKeyIndustryOrEmployerPleaseState;
    @JsonProperty("No_of_Bedrooms_6_51_10_2_SqM__c")
    private String noOfBedrooms65102SqM;
    @JsonProperty("Other__c")
    private String other;
    @JsonProperty("License_required_for_an_HMO_property__c")
    private String isALicenceRequiredToOperateThePropertyAsAHmo;
    @JsonProperty("What_type_of_license_is_required__c")
    private String ifYesDetailsWhatTypeOfLicenceIsRequired;
    @JsonProperty("Licence_displayed_ppty_avbl_inspection__c")
    private String wasTheHmoLicenceDisplayedAtThePropertyOrAvailableForInspection;
    @JsonProperty("If_Yes_occupied_in_line_with_Licence__c")
    private String ifYesDoesThePropertyAppearToBeOccupiedInAccordanceWithLicenceConditions;
    @JsonProperty("Market_Value_sum_of_parts__c")
    private BigDecimal valuationMarketValueAggregate;
    @JsonProperty("Flat_1_No_of_bedrooms__c")
    private Integer flat1NoOfBedrooms;
    @JsonProperty("Flat_1_No_of_living_rooms__c")
    private Integer flat1NoOfLivingRooms;
    @JsonProperty("Flat_1_No_of_bathrooms__c")
    private Integer flat1NoOfBathrooms;
    @JsonProperty("Flat_1_No_of_kitchens__c")
    private Integer flat1NoOfKitchens;
    @JsonProperty("Flat_1_No_of_separate_WC_s__c")
    private Integer flat1NoOfSeparateWcs;
    @JsonProperty("Flat_1_No_of_other_rooms__c")
    private Integer flat1NoOfOtherRooms;
    @JsonProperty("Flat_1_EPC_Band__c")
    private String flat1EpcBand;
    @JsonProperty("Flat_1_EPC_date_may_impact_rating__c")
    private String flat1HaveWorksBeenCompletedSinceEpcDateThatMayImpactExistingEpcBand;
    @JsonProperty("Flat_1_gross_floor_area__c")
    private Integer flat1FloorAreaSquareMetres;
    @JsonProperty("Flat_1_No_of_garages__c")
    private Integer flat1NoOfGarages;
    @JsonProperty("Flat_1_No_of_outbuildings__c")
    private Integer flat1NoOfOutbuildings;
    @JsonProperty("Flat_1_garden__c")
    private String flat1DoesItHaveAGarden;
    @JsonProperty("Flat_1_No_of_parking_spaces__c")
    private Integer flat1NoOfParkingSpaces;
    @JsonProperty("Flat_1_Market_Value__c")
    private BigDecimal flat1MarketValue;
    @JsonProperty("Flat_1_Rental_Value__c")
    private BigDecimal flat1MarketRent;
    @JsonProperty("Flat_2_No_of_bedrooms__c")
    private Integer flat2NoOfBedrooms;
    @JsonProperty("Flat_2_No_of_living_rooms__c")
    private Integer flat2NoOfLivingRooms;
    @JsonProperty("Flat_2_No_of_bathrooms__c")
    private Integer flat2NoOfBathrooms;
    @JsonProperty("Flat_2_No_of_kitchens__c")
    private Integer flat2NoOfKitchens;
    @JsonProperty("Flat_2_No_of_separate_WC_s__c")
    private Integer flat2NoOfSeparateWcs;
    @JsonProperty("Flat_2_No_of_other_rooms__c")
    private Integer flat2NoOfOtherRooms;
    @JsonProperty("Flat_2_EPC_Band__c")
    private String flat2EpcBand;
    @JsonProperty("Flat_2_EPC_date_may_impact_rating__c")
    private String flat2HaveWorksBeenCompletedSinceEpcDateThatMayImpactExistingEpcBand;
    @JsonProperty("Flat_2_gross_floor_area__c")
    private Integer flat2FloorAreaSquareMetres;
    @JsonProperty("Flat_2_No_of_garages__c")
    private Integer flat2NoOfGarages;
    @JsonProperty("Flat_2_No_of_outbuildings__c")
    private Integer flat2NoOfOutbuildings;
    @JsonProperty("Flat_2_garden__c")
    private String flat2DoesItHaveAGarden;
    @JsonProperty("Flat_2_No_of_parking_spaces__c")
    private Integer flat2NoOfParkingSpaces;
    @JsonProperty("Flat_2_Market_Value__c")
    private BigDecimal flat2MarketValue;
    @JsonProperty("Flat_2_Rental_Value__c")
    private BigDecimal flat2MarketRent;
    @JsonProperty("Flat_3_No_of_bedrooms__c")
    private Integer flat3NoOfBedrooms;
    @JsonProperty("Flat_3_No_of_living_rooms__c")
    private Integer flat3NoOfLivingRooms;
    @JsonProperty("Flat_3_No_of_bathrooms__c")
    private Integer flat3NoOfBathrooms;
    @JsonProperty("Flat_3_No_of_kitchens__c")
    private Integer flat3NoOfKitchens;
    @JsonProperty("Flat_3_No_of_separate_WC_s__c")
    private Integer flat3NoOfSeparateWcs;
    @JsonProperty("Flat_3_No_of_other_rooms__c")
    private Integer flat3NoOfOtherRooms;
    @JsonProperty("Flat_3_EPC_Band__c")
    private String flat3EpcBand;
    @JsonProperty("Flat_3_EPC_date_may_impact_rating__c")
    private String flat3HaveWorksBeenCompletedSinceEpcDateThatMayImpactExistingEpcBand;
    @JsonProperty("Flat_3_gross_floor_area__c")
    private Integer flat3FloorAreaSquareMetres;
    @JsonProperty("Flat_3_No_of_garages__c")
    private Integer flat3NoOfGarages;
    @JsonProperty("Flat_3_No_of_outbuildings__c")
    private Integer flat3NoOfOutbuildings;
    @JsonProperty("Flat_3_garden__c")
    private String flat3DoesItHaveAGarden;
    @JsonProperty("Flat_3_No_of_parking_spaces__c")
    private Integer flat3NoOfParkingSpaces;
    @JsonProperty("Flat_3_Market_Value__c")
    private BigDecimal flat3MarketValue;
    @JsonProperty("Flat_3_Rental_Value__c")
    private BigDecimal flat3MarketRent;
    @JsonProperty("Flat_4_No_of_bedrooms__c")
    private Integer flat4NoOfBedrooms;
    @JsonProperty("Flat_4_No_of_living_rooms__c")
    private Integer flat4NoOfLivingRooms;
    @JsonProperty("Flat_4_No_of_bathrooms__c")
    private Integer flat4NoOfBathrooms;
    @JsonProperty("Flat_4_No_of_kitchens__c")
    private Integer flat4NoOfKitchens;
    @JsonProperty("Flat_4_No_of_separate_WC_s__c")
    private Integer flat4NoOfSeparateWcs;
    @JsonProperty("Flat_4_No_of_other_rooms__c")
    private Integer flat4NoOfOtherRooms;
    @JsonProperty("Flat_4_EPC_Band__c")
    private String flat4EpcBand;
    @JsonProperty("Flat_4_EPC_date_may_impact_rating__c")
    private String flat4HaveWorksBeenCompletedSinceEpcDateThatMayImpactExistingEpcBand;
    @JsonProperty("Flat_4_gross_floor_area__c")
    private Integer flat4FloorAreaSquareMetres;
    @JsonProperty("Flat_4_No_of_garages__c")
    private Integer flat4NoOfGarages;
    @JsonProperty("Flat_4_No_of_outbuildings__c")
    private Integer flat4NoOfOutbuildings;
    @JsonProperty("Flat_4_garden__c")
    private String flat4DoesItHaveAGarden;
    @JsonProperty("Flat_4_No_of_parking_spaces__c")
    private Integer flat4NoOfParkingSpaces;
    @JsonProperty("Flat_4_Market_Value__c")
    private BigDecimal flat4MarketValue;
    @JsonProperty("Flat_4_Rental_Value__c")
    private BigDecimal flat4MarketRent;
    @JsonProperty("Flat_5_No_of_bedrooms__c")
    private Integer flat5NoOfBedrooms;
    @JsonProperty("Flat_5_No_of_living_rooms__c")
    private Integer flat5NoOfLivingRooms;
    @JsonProperty("Flat_5_No_of_bathrooms__c")
    private Integer flat5NoOfBathrooms;
    @JsonProperty("Flat_5_No_of_kitchens__c")
    private Integer flat5NoOfKitchens;
    @JsonProperty("Flat_5_No_of_separate_WC_s__c")
    private Integer flat5NoOfSeparateWcs;
    @JsonProperty("Flat_5_No_of_other_rooms__c")
    private Integer flat5NoOfOtherRooms;
    @JsonProperty("Flat_5_EPC_Band__c")
    private String flat5EpcBand;
    @JsonProperty("Flat_5_EPC_date_may_impact_rating__c")
    private String flat5HaveWorksBeenCompletedSinceEpcDateThatMayImpactExistingEpcBand;
    @JsonProperty("Flat_5_gross_floor_area__c")
    private Integer flat5FloorAreaSquareMetres;
    @JsonProperty("Flat_5_No_of_garages__c")
    private Integer flat5NoOfGarages;
    @JsonProperty("Flat_5_No_of_outbuildings__c")
    private Integer flat5NoOfOutbuildings;
    @JsonProperty("Flat_5_garden__c")
    private String flat5DoesItHaveAGarden;
    @JsonProperty("Flat_5_No_of_parking_spaces__c")
    private Integer flat5NoOfParkingSpaces;
    @JsonProperty("Flat_5_Market_Value__c")
    private BigDecimal flat5MarketValue;
    @JsonProperty("Flat_5_Rental_Value__c")
    private BigDecimal flat5MarketRent;
    @JsonProperty("Flat_6_No_of_bedrooms__c")
    private Integer flat6NoOfBedrooms;
    @JsonProperty("Flat_6_No_of_living_rooms__c")
    private Integer flat6NoOfLivingRooms;
    @JsonProperty("Flat_6_No_of_bathrooms__c")
    private Integer flat6NoOfBathrooms;
    @JsonProperty("Flat_6_No_of_kitchens__c")
    private Integer flat6NoOfKitchens;
    @JsonProperty("Flat_6_No_of_separate_WC_s__c")
    private Integer flat6NoOfSeparateWcs;
    @JsonProperty("Flat_6_No_of_other_rooms__c")
    private Integer flat6NoOfOtherRooms;
    @JsonProperty("Flat_6_EPC_Band__c")
    private String flat6EpcBand;
    @JsonProperty("Flat_6_EPC_date_may_impact_rating__c")
    private String flat6HaveWorksBeenCompletedSinceEpcDateThatMayImpactExistingEpcBand;
    @JsonProperty("Flat_6_gross_floor_area__c")
    private Integer flat6FloorAreaSquareMetres;
    @JsonProperty("Flat_6_No_of_garages__c")
    private Integer flat6NoOfGarages;
    @JsonProperty("Flat_6_No_of_outbuildings__c")
    private Integer flat6NoOfOutbuildings;
    @JsonProperty("Flat_6_garden__c")
    private String flat6DoesItHaveAGarden;
    @JsonProperty("Flat_6_No_of_parking_spaces__c")
    private Integer flat6NoOfParkingSpaces;
    @JsonProperty("Flat_6_Market_Value__c")
    private BigDecimal flat6MarketValue;
    @JsonProperty("Flat_6_Rental_Value__c")
    private BigDecimal flat6MarketRent;
    @JsonProperty("Case_reference_number__c")
    private String lenderReference;
    @JsonProperty("Construction_adheres_to_lending_policy__c")
    private String cwp;
    @JsonProperty("Date_of_Review__c")
    private String reviewDate;
    @JsonProperty("Declaration__c")
    private String declaration;
    @JsonProperty("Inspection_Type__c")
    private String inspectionType;
    @JsonProperty("Special_Reason_to_influence_cost__c")
    private String specialReasonToInfluenceCost;
    @JsonProperty("Inspection_Reason__c")
    private String inspectionReason;
    @JsonProperty("Retention_Required__c")
    private String retentionRequired;
    @JsonProperty("Mkt_Rent_when_remaining_works_complete__c")
    private BigDecimal marketRentWhenRemainingWorksAreCompleted;
    @JsonProperty("Lowest_EPC_Rating_in_Block__c")
    private String lowestEpcRatingInBlock;
    @JsonProperty("titleNumber__c")
    private String titleNumber;
    @JsonProperty("FloodResult__c")
    private String floodResult;
//    @JsonProperty("Classified_as_New_Build_by_the_Valuer__c") // Not present in sandbox
//    private String classifiedAsNewBuildByTheValuer;
//    @JsonProperty("RecordType")
//    private RecordType recordType;

}
