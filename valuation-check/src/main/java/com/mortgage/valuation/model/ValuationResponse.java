package com.mortgage.valuation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for valuation processing containing structured property valuation data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValuationResponse {

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("propertyAddress")
    private PropertyAddress propertyAddress;

    @JsonProperty("valuationDetails")
    private ValuationDetails valuationDetails;

    @JsonProperty("propertyCharacteristics")
    private PropertyCharacteristics propertyCharacteristics;

    @JsonProperty("marketAnalysis")
    private MarketAnalysis marketAnalysis;

    @JsonProperty("riskAssessment")
    private RiskAssessment riskAssessment;

    @JsonProperty("recommendations")
    private List<String> recommendations;

    @JsonProperty("confidenceScore")
    private Double confidenceScore;

    public ValuationResponse() {}

    public ValuationResponse(String requestId) {
        this.requestId = requestId;
    }

    // Nested classes
    public static class PropertyAddress {
        @JsonProperty("line1")
        private String line1;

        @JsonProperty("line2")
        private String line2;

        @JsonProperty("city")
        private String city;

        @JsonProperty("postcode")
        private String postcode;

        @JsonProperty("county")
        private String county;

        // Getters and setters
        public String getLine1() { return line1; }
        public void setLine1(String line1) { this.line1 = line1; }
        public String getLine2() { return line2; }
        public void setLine2(String line2) { this.line2 = line2; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getPostcode() { return postcode; }
        public void setPostcode(String postcode) { this.postcode = postcode; }
        public String getCounty() { return county; }
        public void setCounty(String county) { this.county = county; }
    }

    public static class ValuationDetails {
        @JsonProperty("estimatedValue")
        private BigDecimal estimatedValue;

        @JsonProperty("valuationRange")
        private ValuationRange valuationRange;

        @JsonProperty("valuationMethod")
        private String valuationMethod;

        @JsonProperty("valuationDate")
        private String valuationDate;

        // Getters and setters
        public BigDecimal getEstimatedValue() { return estimatedValue; }
        public void setEstimatedValue(BigDecimal estimatedValue) { this.estimatedValue = estimatedValue; }
        public ValuationRange getValuationRange() { return valuationRange; }
        public void setValuationRange(ValuationRange valuationRange) { this.valuationRange = valuationRange; }
        public String getValuationMethod() { return valuationMethod; }
        public void setValuationMethod(String valuationMethod) { this.valuationMethod = valuationMethod; }
        public String getValuationDate() { return valuationDate; }
        public void setValuationDate(String valuationDate) { this.valuationDate = valuationDate; }
    }

    public static class ValuationRange {
        @JsonProperty("minValue")
        private BigDecimal minValue;

        @JsonProperty("maxValue")
        private BigDecimal maxValue;

        // Getters and setters
        public BigDecimal getMinValue() { return minValue; }
        public void setMinValue(BigDecimal minValue) { this.minValue = minValue; }
        public BigDecimal getMaxValue() { return maxValue; }
        public void setMaxValue(BigDecimal maxValue) { this.maxValue = maxValue; }
    }

    public static class PropertyCharacteristics {
        @JsonProperty("propertyType")
        private String propertyType;

        @JsonProperty("bedrooms")
        private Integer bedrooms;

        @JsonProperty("bathrooms")
        private Integer bathrooms;

        @JsonProperty("receptionRooms")
        private Integer receptionRooms;

        @JsonProperty("totalFloorArea")
        private Double totalFloorArea;

        @JsonProperty("plotSize")
        private Double plotSize;

        @JsonProperty("yearBuilt")
        private Integer yearBuilt;

        @JsonProperty("condition")
        private String condition;

        // Getters and setters
        public String getPropertyType() { return propertyType; }
        public void setPropertyType(String propertyType) { this.propertyType = propertyType; }
        public Integer getBedrooms() { return bedrooms; }
        public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }
        public Integer getBathrooms() { return bathrooms; }
        public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }
        public Integer getReceptionRooms() { return receptionRooms; }
        public void setReceptionRooms(Integer receptionRooms) { this.receptionRooms = receptionRooms; }
        public Double getTotalFloorArea() { return totalFloorArea; }
        public void setTotalFloorArea(Double totalFloorArea) { this.totalFloorArea = totalFloorArea; }
        public Double getPlotSize() { return plotSize; }
        public void setPlotSize(Double plotSize) { this.plotSize = plotSize; }
        public Integer getYearBuilt() { return yearBuilt; }
        public void setYearBuilt(Integer yearBuilt) { this.yearBuilt = yearBuilt; }
        public String getCondition() { return condition; }
        public void setCondition(String condition) { this.condition = condition; }
    }

    public static class MarketAnalysis {
        @JsonProperty("localMarketTrend")
        private String localMarketTrend;

        @JsonProperty("averagePricePerSqFt")
        private BigDecimal averagePricePerSqFt;

        @JsonProperty("comparableProperties")
        private List<ComparableProperty> comparableProperties;

        // Getters and setters
        public String getLocalMarketTrend() { return localMarketTrend; }
        public void setLocalMarketTrend(String localMarketTrend) { this.localMarketTrend = localMarketTrend; }
        public BigDecimal getAveragePricePerSqFt() { return averagePricePerSqFt; }
        public void setAveragePricePerSqFt(BigDecimal averagePricePerSqFt) { this.averagePricePerSqFt = averagePricePerSqFt; }
        public List<ComparableProperty> getComparableProperties() { return comparableProperties; }
        public void setComparableProperties(List<ComparableProperty> comparableProperties) { this.comparableProperties = comparableProperties; }
    }

    public static class ComparableProperty {
        @JsonProperty("address")
        private String address;

        @JsonProperty("salePrice")
        private BigDecimal salePrice;

        @JsonProperty("saleDate")
        private String saleDate;

        @JsonProperty("distance")
        private Double distance;

        // Getters and setters
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public BigDecimal getSalePrice() { return salePrice; }
        public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }
        public String getSaleDate() { return saleDate; }
        public void setSaleDate(String saleDate) { this.saleDate = saleDate; }
        public Double getDistance() { return distance; }
        public void setDistance(Double distance) { this.distance = distance; }
    }

    public static class RiskAssessment {
        @JsonProperty("overallRisk")
        private String overallRisk;

        @JsonProperty("riskFactors")
        private List<String> riskFactors;

        @JsonProperty("riskScore")
        private Integer riskScore;

        // Getters and setters
        public String getOverallRisk() { return overallRisk; }
        public void setOverallRisk(String overallRisk) { this.overallRisk = overallRisk; }
        public List<String> getRiskFactors() { return riskFactors; }
        public void setRiskFactors(List<String> riskFactors) { this.riskFactors = riskFactors; }
        public Integer getRiskScore() { return riskScore; }
        public void setRiskScore(Integer riskScore) { this.riskScore = riskScore; }
    }

    // Main class getters and setters
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    public PropertyAddress getPropertyAddress() { return propertyAddress; }
    public void setPropertyAddress(PropertyAddress propertyAddress) { this.propertyAddress = propertyAddress; }
    public ValuationDetails getValuationDetails() { return valuationDetails; }
    public void setValuationDetails(ValuationDetails valuationDetails) { this.valuationDetails = valuationDetails; }
    public PropertyCharacteristics getPropertyCharacteristics() { return propertyCharacteristics; }
    public void setPropertyCharacteristics(PropertyCharacteristics propertyCharacteristics) { this.propertyCharacteristics = propertyCharacteristics; }
    public MarketAnalysis getMarketAnalysis() { return marketAnalysis; }
    public void setMarketAnalysis(MarketAnalysis marketAnalysis) { this.marketAnalysis = marketAnalysis; }
    public RiskAssessment getRiskAssessment() { return riskAssessment; }
    public void setRiskAssessment(RiskAssessment riskAssessment) { this.riskAssessment = riskAssessment; }
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }
}
