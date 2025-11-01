package com.mortgage.valuation.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mortgage.valuation.model.CompleteValuationDetails;
import com.mortgage.valuation.model.FileUploadRequest;
import com.mortgage.valuation.model.VRMetadata;
import com.mortgage.valuation.model.ValuationResponse;
import com.mortgage.valuation.model.crm.CollateralValuation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ValuationMapper {

    private static final String VALUATION_REPORT_ISSUED = "Valuation Report Issued";
    private final ObjectMapper mapper = new ObjectMapper();


    public static CollateralValuation buildCVRecord(FileUploadRequest request) {
        // TODO - add recordType ?
        var cvRecord = new CollateralValuation();
        cvRecord.setClientRef(request.getLoanApplicationId());
        cvRecord.setCollateralMgmt(request.getCollateralId());
        cvRecord.setStatus("Active");
        return cvRecord;
    }

    public CollateralValuation convertToCVRecord(ValuationResponse valuationResponse) throws IOException {
        var jsonData = mapper.writeValueAsString(valuationResponse);
        var vrMetaData = mapper.readValue(jsonData, VRMetadata.class);
        var completeValuationDetails = processVRMetaData(vrMetaData, CompleteValuationDetails.class);
        enrichMetadata(completeValuationDetails);
        return ShortFormValuationMapper.INSTANCE.mapToCVRecord(completeValuationDetails);
    }

    public <T> T processVRMetaData(VRMetadata vrMetaData, Class<T> classType) {
        Map<String, JsonNode> keyValue = vrMetaData.getAnswers().stream()
                .filter(answer -> answer.getValue() != null)
                .collect(Collectors.toMap(VRMetadata.PropertyDetails::getName, VRMetadata.PropertyDetails::getValue));
        return mapper.convertValue(keyValue, classType);
    }

    private void enrichMetadata(CompleteValuationDetails completeValuationDetails) {
        completeValuationDetails.setValuationStatus(VALUATION_REPORT_ISSUED);
        String address = Stream.of(
                        completeValuationDetails.getPropertyAddressHouseFlat(),
                        completeValuationDetails.getRoadNumber(),
                        completeValuationDetails.getRoadName(),
                        completeValuationDetails.getArea(),
                        completeValuationDetails.getTown(),
                        completeValuationDetails.getCounty(),
                        completeValuationDetails.getPostCode())
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining(", "));

        completeValuationDetails.setPropertyAddress(address);
        if (StringUtils.isEmpty(completeValuationDetails.getTitleTenureAndAccessTenure())) {
            completeValuationDetails.setTitleTenureAndAccessTenure(completeValuationDetails.getTenure());
        }
    }

}




