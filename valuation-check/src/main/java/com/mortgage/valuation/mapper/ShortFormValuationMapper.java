package com.mortgage.valuation.mapper;

import com.mortgage.valuation.model.CompleteValuationDetails;
import com.mortgage.valuation.model.crm.CollateralValuation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import java.math.BigDecimal;


@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShortFormValuationMapper {

    ShortFormValuationMapper INSTANCE = Mappers.getMapper(ShortFormValuationMapper.class);


    @Mapping(target = "quoteRequestId", source = "transactionRequestId")
    @Mapping(target = "transactionId", source = "transactionRequestId")
    @Mapping(target = "transactionMileId", source = "transactionRequestMilestoneId")
    @Mapping(target = "letOnAst", source = "doesThePropertyAppearToBeLetOnAsts")
    @Mapping(target = "purchaserIncentives", source = "details",
            qualifiedByName = "mapPurchaserIncentives")
    @Mapping(target = "propertyStyle", source = "securityProperyTypeAndStyle")
    @Mapping(target = "garden",  source = "isThereAGarden")
    @Mapping(target = "constructionType", source = "stateNonTraditionalConstructionType")
    @Mapping(target = "lastExtensionOrConversionYear", source = "yearOfMostRecentExtension")
    @Mapping(target = "tenure", source = "titleTenureAndAccessTenure")
    @Mapping(target = "totalMarketValueAfterWorks", source = "marketValAfterWorksIssuesResolved")
    @Mapping(target = "unexpiredTerm", source = "ifLeaseholdWhatIsTheUnexpiredLeaseTerm")
    @Mapping(target = "hasBuildReachedPostCompletion", source = "newBuildHasTheBuildReachedPracticalCompletion")
    @Mapping(target = "isReinspectionRecommended", source = "isReinspectionRequired")
    @Mapping(target = "isPlotGreaterThan3Acres", source = "isPlotGreaterThan3Acres")
    @Mapping(target = "isPropertyCurrentlyLet", source = "tenancyIsThePropertyCurrentlyLet")
    @Mapping(target = "hasSprayFoamAppliedToRoof",  source = "constructionHasSprayFoamBeenAppliedToTheRoof")
    @Mapping(target = "isDesignatedDefectivePropertyType", source = "isThePropertyADesignatedDefectivePropertyType")
    @Mapping(target = "designatedDefectivePropertyRepaired", source = "ifYesDoesItAppearToHaveBeenRepaired")
    @Mapping(target = "designatedDefectivePropertyAdjoiningRepaired",
            source = "details", qualifiedByName = "mapAdjoiningProperties")
    @Mapping(target = "isFfhGreaterThan20Percent", source = "whatPercentageOfTotalAreaIsFfh")
    @Mapping(target = "proportionOfPropertyUsedForBusiness", source = "proportionOfPropertyBeingUsedForBusinessPurposes")
    @Mapping(target = "anyLicensingSchemes", source = "anyApplicableLicensingSchemeInPlace")
    @Mapping(target = "licensingSchemeOtherDetails",  source = "ifOtherDetailsLicenseScheme")
    @Mapping(target = "listedPropertyBand", source = "ifYesWhichBand")
    @Mapping(target = "valuerFirm", source = "firmName")
    @Mapping(target = "sharedAccess", source = "doesThereAppearToBeSharedAccess")
    @Mapping(target = "doesAddressMatchInstruction", source = "addressMatch")
    @Mapping(target = "commercialUseAdjacent", source = "adjacentToOrOverCommercialUse")
    @Mapping(target = "flyingCreepingFreehold", source = "anyFlyingCreepingFhEvident")
    @Mapping(target = "worksSinceEPC", source = "hasThePropertyHadWorksSinceEpcDateThatMayImpactExistingRating")
    @Mapping(target = "centralHeating", source = "isThereAFixedCentralHeatingSystem")
    @Mapping(target = "traditionalConstruction", source = "details",
            qualifiedByName = "mapTraditionalConstruction")
    @Mapping(target = "withinConservationArea", source = "isThePropertyWithinAConservationArea")
    @Mapping(target = "numberOfFloorsInBuilding", source = "numberOfFloorsInTheBuilding")
    @Mapping(target = "purchasePrice", source = "purchasePriceEstimatedValuePpEv")
    @Mapping(target = "structuralEngineerOutbuilding", source = "structuralEngineerOutbuildingOrBoundaryStructure")
    @Mapping(target = "mainsWater", source = "utilitiesMainsWater")
    @Mapping(target = "reportAddress", source = "actualAddressIfAddressNotAMatch")
    @Mapping(target = "otherSpecialistReportDetails", source = "ifOtherDetailsSpecialistReport")
    @Mapping(target = "servedByLift", source = "isThePropertyServedByALift")
    @Mapping(target = "article4Area", source = "isThePropertyWithinAnArticle4Area")
    @Mapping(target = "noOfBedroomsLessThan65Sqm", source = "noOfBedroomsLessThan65SqM")
    @Mapping(target = "noOfBedroomsGreaterThan10Sqm", source = "numberOfBedroomsGreaterThan102SqM")
    @Mapping(target = "flatFloorLocation", source = "onWhichFloorIsTheFlatLocated")
    @Mapping(target = "communalLivingSpaceSize", source = "sizeOfCommunalLivingSpaceAggregate")
    @Mapping(target = "licensingSchemeType", source = "statutoryConsiderationsLicensingSchemesInPlace")
    @Mapping(target = "sharedKitchensSize", source = "sizeOfSharedKitchensAggregate")
    @Mapping(target = "areAllUnitsIndividuallyMortgageable", source = "areAllFlatsMarketableAndMortgageable")
    @Mapping(target = "occupancyTiesOrRestrictions", source = "anyEvidenceOfOccupancyTiesOrRestrictionsGuidanceNoteIfRestrictionThen0")
    @Mapping(target = "specialReasonToInfluenceCost", source = "anySpecialRisksLikelyToInfluenceInsuranceAcceptanceOrCost")
    @Mapping(target = "inspectionReason", source =
            "reInspectionDetailsProvidedByLenderPleaseNoteTheReasonForTheReInspectionBeingRequestedDevelopmentWorksHaveBeenCompleted")
    @Mapping(target = "retentionRequired", source = "remainingRetentionRecommended")
    @Mapping(target = "marketRentWhenRemainingWorksAreCompleted", source = "marketRentWhenRemainingWorksAreCompleted")
    @Mapping(target = "totalFlatsInBuilding", source = "howManyFlatsAreInTheEntireBuilding")
    @Mapping(target = "otherHeatingSystemDetails", source = "ifOtherDetailsHeatingSystem")
    @Mapping(target = "otherSpecialistReport", source = "securityOther", qualifiedByName = "truncateString")
    @Mapping(target = "propertyAgeCondition", source = "propertyCondition")
    @Mapping(target = "valuerName", source = "valuersDeclarationValuerName")
    @Mapping(target = "otherSecurityDetails", source = "details", qualifiedByName = "mapOtherSecurityDetails")
    @Mapping(target = "noOfBedrooms", source = "details", qualifiedByName = "mapNoOfBedrooms")
    @Mapping(target = "structuralEngineerRoof", source = "details", qualifiedByName = "mapStructuralEngineerRoof")
    @Mapping(target = "specialistReportsRequired", source = "details", qualifiedByName = "mapSpecialistReportsRequired")
    @Mapping(target = "marketRent", source = "details", qualifiedByName = "mapMarketRent")
    @Mapping(target = "acceptableNewBuildWarranty", source = "isThereAnAcceptableNewBuildWarranty")
    @Mapping(target = "hasBopasAccreditationBeenSeen", source = "ifMmcHasBopasAccreditationBeenSeen")
    @Mapping(target = "isSuitableSecurity", source = "details", qualifiedByName = "mapIsSuitableSecurity")
    @Mapping(target = "isSuitableSecurityReason", source = "details", qualifiedByName = "mapIsSuitableSecurityReason")
    @Mapping(target = "dateOfInspection", source = "details", qualifiedByName = "mapDateOfInspection")
    @Mapping(target = "dateOfValuation", source = "details", qualifiedByName = "mapDateOfInspection")
//    @Mapping(target = "classifiedAsNewBuildByTheValuer", source = "isThePropertyANewBuild")
    CollateralValuation mapToCVRecord(CompleteValuationDetails details);

    @Named("mapAdjoiningProperties")
    default String mapAdjoiningProperties(CompleteValuationDetails details) {
        String ifYesDoAttachedNeighbouringPropertiesAlsoAppearToHaveBeenRepaired =
                details.getIfYesDoAttachedNeighbouringPropertiesAlsoAppearToHaveBeenRepaired();
        String doAttachedNeighbouringPropertiesAlsoAppearToHaveBeenRepaired =
              details.getDoAttachedNeighbouringPropertiesAlsoAppearToHaveBeenRepaired();

        return StringUtils.isNotBlank(ifYesDoAttachedNeighbouringPropertiesAlsoAppearToHaveBeenRepaired) ?
                ifYesDoAttachedNeighbouringPropertiesAlsoAppearToHaveBeenRepaired :
                doAttachedNeighbouringPropertiesAlsoAppearToHaveBeenRepaired;
    }

    @Named("mapTraditionalConstruction")
    default String mapTraditionalConstruction(CompleteValuationDetails details) {
        String isThePropertyBuiltOfTraditionalConstruction = details.getIsThePropertyBuiltOfTraditionalConstruction();
        String constructionIsThePropertyBuiltOfTraditionalConstruction =
                details.getConstructionIsThePropertyBuiltOfTraditionalConstruction();

        return StringUtils.isNotBlank(isThePropertyBuiltOfTraditionalConstruction) ?
                isThePropertyBuiltOfTraditionalConstruction : constructionIsThePropertyBuiltOfTraditionalConstruction;
    }

    @Named("mapPurchaserIncentives")
    default String mapPurchaserIncentives(CompleteValuationDetails details) {
        String areAnyPurchaserIncentivesNotedOnTheDif = details.getAreAnyPurchaserIncentivesNotedOnTheDif();
        String newbuildAreAnyPurchaserIncentivesNotedOnTheDif = details.getNewbuildAreAnyPurchaserIncentivesNotedOnTheDif();

        return StringUtils.isNotBlank(areAnyPurchaserIncentivesNotedOnTheDif) ?
                areAnyPurchaserIncentivesNotedOnTheDif : newbuildAreAnyPurchaserIncentivesNotedOnTheDif;
    }

    @Named("mapNoOfBedrooms")
    default Integer mapNoOfBedrooms(CompleteValuationDetails details) {
        Integer noOfBedrooms = details.getNoOfBedrooms();
        Integer accommodationNumberOfNoOfBedrooms = details.getAccommodationNumberOfNoOfBedrooms();

        return NumberUtils.isCreatable(String.valueOf(noOfBedrooms)) ?
                noOfBedrooms : accommodationNumberOfNoOfBedrooms;
    }

    @Named("mapStructuralEngineerRoof")
    default String mapStructuralEngineerRoof(CompleteValuationDetails details) {
        return StringUtils.defaultIfBlank(details.getStructuralEngineerRoof(),
                details.getSpecialistReportsStructuralEngineerRoof());
    }

    @Named("mapSpecialistReportsRequired")
    default String mapSpecialistReportsRequired(CompleteValuationDetails details) {
        return StringUtils.defaultIfBlank(details.getSpecialistReportsRequired(),
                details.getSpecialistReportsSpecialistReportsRequired());
    }

    @Named("mapMarketRent")
    default BigDecimal mapMarketRent(CompleteValuationDetails details) {
        BigDecimal marketRent = details.getMarketRent();
        BigDecimal marketRentAggregate = details.getMarketRentAggregate();

        return NumberUtils.isCreatable(String.valueOf(marketRent)) ?
                marketRent : marketRentAggregate;
    }

    @Named("mapIsSuitableSecurity")
    default String mapIsSuitableSecurity(CompleteValuationDetails details) {
        return StringUtils.defaultIfBlank(details.getCertificationIsThePropertySuitableSecurity(),
                details.getRecommendationsPropertyProvidesAcceptableSecurity());
    }

    @Named("mapOtherSecurityDetails")
    default String mapOtherSecurityDetails(CompleteValuationDetails details) {
        return StringUtils.defaultIfBlank(details.getIfOtherDetailsSuitableSecurity(),
                details.getIfOtherDetailsAcceptableSecurity());
    }

    @Named("mapIsSuitableSecurityReason")
    default String mapIsSuitableSecurityReason(CompleteValuationDetails details) {
        String ifPropertyIsNotSuitableSecurityPleaseIdentifyWhy = details.getIfPropertyIsNotSuitableSecurityPleaseIdentifyWhy();
        String ifNoDetails = details.getIfNoDetails();
        String ifYesDetailsDevelopmentWorksCompletionIsConfirmed = details.getIfYesDetailsDevelopmentWorksCompletionIsConfirmed();

        return StringUtils.defaultIfBlank(
                ifPropertyIsNotSuitableSecurityPleaseIdentifyWhy,
                StringUtils.defaultIfBlank(ifYesDetailsDevelopmentWorksCompletionIsConfirmed, ifNoDetails));
    }

    @Named("mapDateOfInspection")
    default String mapDateOfInspection(CompleteValuationDetails details) {
        return StringUtils.defaultIfBlank(details.getInspectionDate(), details.getAppointmentDate());
    }

    @Named("truncateString")
    default String truncateString(String value) {
        return StringUtils.substring((value), 0, 251);
    }
}

