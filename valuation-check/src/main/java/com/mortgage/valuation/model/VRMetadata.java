package com.mortgage.valuation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO matching the requested simplified form JSON model used by downstream systems.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VRMetadata {

    @JsonProperty("createdUtc")
    private String createdUtc;

    @JsonProperty("lastUpdatedUtc")
    private String lastUpdatedUtc;

    @JsonProperty("formCode")
    private String formCode;

    @JsonProperty("status")
    private String status;

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("answersFormVersions")
    private List<String> answersFormVersions;

    @JsonDeserialize(contentUsing = ItemDeserializer.class)
    @JsonProperty("answers")
    private List<PropertyDetails> answers;


    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    public static class PropertyDetails {
        private String name;
        private JsonNode value;
    }

    public static class ItemDeserializer extends StdDeserializer<PropertyDetails> {

        public ItemDeserializer() {
            this(null);
        }

        public ItemDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public PropertyDetails deserialize(JsonParser jp, DeserializationContext context)
                throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            var propertyName = convertText(node.path("questionId").asText());
            var valueNode = extractNode(node);
            return new PropertyDetails(propertyName, valueNode);
        }
    }

    public static JsonNode extractNode(JsonNode node) {
        var value = node.get("value");
        if (value == null || value.asText() == null || "null".equals(value.asText())) {
            return null;
        }
        return switch (node.path("answerType").asText()) {
            case "decimal" -> new DecimalNode(new BigDecimal(value.asText()));
            case "options" -> new TextNode(value.get(0).asText());
            case "number" -> new IntNode(value.asInt());
            case "date" -> formatDate(value.asText());
            case "text" -> new TextNode(value.asText());
            default -> new TextNode(value.asText());
        };
    }

    public static String convertText(String input) {
        return input.contains("_") ? StringUtils.substringAfter(input, "_").replace("_", "") : input;
    }

    private static JsonNode formatDate(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate localDate = LocalDate.parse(date, inputFormatter);
            return new TextNode(localDate.format(outputFormatter));
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
