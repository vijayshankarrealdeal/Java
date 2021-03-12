//// Converter.java
//
//// To use this code, add the following Maven dependency to your project:
////
////
////     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
////     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
////
//// Import this package:
////
////     import io.quicktype.Converter;
////
//// Then you can deserialize a JSON string with
////
////     VideoSeries[] data = Converter.fromJsonString(jsonString);
//
//package io.quicktype;
//
//import java.io.IOException;
//import com.fasterxml.jackson.databind.*;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import java.util.*;
//import java.time.LocalDate;
//import java.time.OffsetDateTime;
//import java.time.OffsetTime;
//import java.time.ZoneOffset;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeFormatterBuilder;
//import java.time.temporal.ChronoField;
//
//public class Converter {
//    // Date-time helpers
//
//    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
//            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
//            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
//            .appendOptional(DateTimeFormatter.ISO_INSTANT)
//            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
//            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
//            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//            .toFormatter()
//            .withZone(ZoneOffset.UTC);
//
//    public static OffsetDateTime parseDateTimeString(String str) {
//        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
//    }
//
//    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
//            .appendOptional(DateTimeFormatter.ISO_TIME)
//            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
//            .parseDefaulting(ChronoField.YEAR, 2020)
//            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
//            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
//            .toFormatter()
//            .withZone(ZoneOffset.UTC);
//
//    public static OffsetTime parseTimeString(String str) {
//        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
//    }
//    // Serialize/deserialize helpers
//
//    public static VideoSeries[] fromJsonString(String json) throws IOException {
//        return getObjectReader().readValue(json);
//    }
//
//    public static String toJsonString(VideoSeries[] obj) throws JsonProcessingException {
//        return getObjectWriter().writeValueAsString(obj);
//    }
//
//    private static ObjectReader reader;
//    private static ObjectWriter writer;
//
//    private static void instantiateMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.findAndRegisterModules();
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
//            @Override
//            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
//                String value = jsonParser.getText();
//                return Converter.parseDateTimeString(value);
//            }
//        });
//        mapper.registerModule(module);
//        reader = mapper.readerFor(VideoSeries[].class);
//        writer = mapper.writerFor(VideoSeries[].class);
//    }
//
//    private static ObjectReader getObjectReader() {
//        if (reader == null) instantiateMapper();
//        return reader;
//    }
//
//    private static ObjectWriter getObjectWriter() {
//        if (writer == null) instantiateMapper();
//        return writer;
//    }
//}
//
//// VideoSeries.java
//
//package io.quicktype;
//
//        import com.fasterxml.jackson.annotation.*;
//
//public class VideoSeries {
//    private long[] viewsOnVideo;
//    private String discription;
//    private String sourceLink;
//    private String name;
//    private String source;
//    private String webpage;
//    private String title;
//    private String cloudStorageLink;
//    private String socialHandle;
//
//    @JsonProperty("viewsOnVideo")
//    public long[] getViewsOnVideo() { return viewsOnVideo; }
//    @JsonProperty("viewsOnVideo")
//    public void setViewsOnVideo(long[] value) { this.viewsOnVideo = value; }
//
//    @JsonProperty("discription")
//    public String getDiscription() { return discription; }
//    @JsonProperty("discription")
//    public void setDiscription(String value) { this.discription = value; }
//
//    @JsonProperty("sourceLink")
//    public String getSourceLink() { return sourceLink; }
//    @JsonProperty("sourceLink")
//    public void setSourceLink(String value) { this.sourceLink = value; }
//
//    @JsonProperty("name")
//    public String getName() { return name; }
//    @JsonProperty("name")
//    public void setName(String value) { this.name = value; }
//
//    @JsonProperty("source")
//    public String getSource() { return source; }
//    @JsonProperty("source")
//    public void setSource(String value) { this.source = value; }
//
//    @JsonProperty("webpage")
//    public String getWebpage() { return webpage; }
//    @JsonProperty("webpage")
//    public void setWebpage(String value) { this.webpage = value; }
//
//    @JsonProperty("title")
//    public String getTitle() { return title; }
//    @JsonProperty("title")
//    public void setTitle(String value) { this.title = value; }
//
//    @JsonProperty("cloudStorageLink")
//    public String getCloudStorageLink() { return cloudStorageLink; }
//    @JsonProperty("cloudStorageLink")
//    public void setCloudStorageLink(String value) { this.cloudStorageLink = value; }
//
//    @JsonProperty("socialHandle")
//    public String getSocialHandle() { return socialHandle; }
//    @JsonProperty("socialHandle")
//    public void setSocialHandle(String value) { this.socialHandle = value; }
//}
