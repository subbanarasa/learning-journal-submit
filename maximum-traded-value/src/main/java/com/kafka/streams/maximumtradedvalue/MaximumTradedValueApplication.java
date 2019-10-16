package com.kafka.streams.maximumtradedvalue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.producer.dailystocktradedata.model.DailyStockTradeData;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Serialized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;

@SpringBootApplication
public class MaximumTradedValueApplication {
    private static final Logger logger = LoggerFactory.getLogger(MaximumTradedValueApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MaximumTradedValueApplication.class, args);
	}


    @EnableBinding(KafkaStreamsProcessor.class)
    public static class WordCountProcessorApplication {

        public static final String INPUT_TOPIC = "input";
        public static final String OUTPUT_TOPIC = "output";
        public static final int WINDOW_SIZE_MS = 30000;

        @StreamListener(Sink.INPUT)
        @SendTo(OUTPUT_TOPIC)
        public KStream<String, DailyStockTradeData> process(KStream<String, DailyStockTradeData> input) {
            ObjectMapper mapper = new ObjectMapper();
            Serde<DailyStockTradeData> stockDataSerde = new JsonSerde<>( DailyStockTradeData.class, mapper );
            input.foreach((s, dailyStockTradeData) -> logger.info("Input record::"+dailyStockTradeData));
            KStream<String, DailyStockTradeData> output = input
                     .groupBy((key, dailyStockTradeData) -> dailyStockTradeData.getTimestamp(),Serialized.with(null, stockDataSerde))
                     .count()
                     .toStream()
                    .map((key, dailyStockTradeData) -> new KeyValue<>(null,new DailyStockTradeData()));

            output.foreach((s, dailyStockTradeData) -> logger.info("Output record::"+dailyStockTradeData));

            return output;

        }
    }

}
