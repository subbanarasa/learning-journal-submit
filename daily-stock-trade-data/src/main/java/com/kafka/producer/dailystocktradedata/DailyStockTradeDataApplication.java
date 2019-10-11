package com.kafka.producer.dailystocktradedata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.kafka.producer.dailystocktradedata.model.DailyStockTradeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@SpringBootApplication
public class DailyStockTradeDataApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value(value = "${files.directory:.}")
    private String filesDirectory;

    @Value(value = "${kafka.topic}")
    private String kafkaTopic;

    @Autowired
    private KafkaTemplate<String, DailyStockTradeData> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DailyStockTradeDataApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        if (args.length <= 0) {
            logger.info("No of threads command line argument is missing");
            return;
        }
        logger.info("No of threads given in command line::"+args[0]);
        Integer noOfThreads = Integer.valueOf(args[0]);

        ExecutorService executorService = Executors.newFixedThreadPool(noOfThreads);

        Stream<Path> files = Files.list(Paths.get(filesDirectory)).filter(Files::isRegularFile);
        logger.info("Files available in input directory::"+filesDirectory+" ||"+files);
        files.forEach( path ->  {
            logger.info("File name::"+path.getFileName());
            executorService.execute(() ->{
                CsvMapper csvMapper = new CsvMapper();
                csvMapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);
                csvMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                CsvSchema schema = CsvSchema.emptySchema().withHeader();
                logger.info(schema.toString());
                ObjectReader oReader = csvMapper.readerFor(DailyStockTradeData.class).with(schema);
                try (Reader reader = new FileReader(path.toFile())) {
                    MappingIterator<DailyStockTradeData> dailyStockTradeDataMappingIterator = oReader.readValues(reader);
                    int count = 0;
                    while (dailyStockTradeDataMappingIterator.hasNext()) {
                        DailyStockTradeData dailyStockTradeData = dailyStockTradeDataMappingIterator.next();
                        kafkaTemplate.send(kafkaTopic,dailyStockTradeData);
                        count++;
                    }
                    logger.info("Total records for the file::"+path.getFileName()+"||"+count);
                }catch (Exception e){
                    logger.error(e.toString());
                }

            });
        });

        executorService.shutdown();

    }


}
