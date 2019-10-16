package com.kafka.producer.dailystocktradedata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.kafka.producer.dailystocktradedata.model.DailyStockTradeData;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@SpringBootApplication
public class DailyStockTradeDataApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value(value = "${files.src.directory:.}")
    private String srcFilesDirectory;

    @Value(value = "${files.archive.directory:.}")
    private String archiveFilesDirectory;

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
        logger.info("No of threads given in command line::" + args[0]);
        Integer noOfThreads = Integer.valueOf(args[0]);
        if (noOfThreads <= 0) {
            logger.error("No of threads argumnt should be greater than 0");
            return;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(noOfThreads);
        if (!new File(srcFilesDirectory).exists()) {
            logger.error("Input source directory does not exists::" + srcFilesDirectory);
            return;
        }
        Stream<Path> files = Files.list(Paths.get(srcFilesDirectory)).filter(Files::isRegularFile);
        logger.info("Files available in input directory::" + srcFilesDirectory + " ||" + files.count());
        File[] filesTmp = new File(srcFilesDirectory).listFiles();

        for (int i = 0; i < filesTmp.length; i++) {
            File file = filesTmp[i];
            if (file.isFile()) {
                logger.info("File name::" + file.getName());
                executorService.execute(() -> {
                    CsvMapper csvMapper = new CsvMapper();
                    csvMapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);
                    csvMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    CsvSchema schema = CsvSchema.emptySchema().withHeader();
                    logger.info(schema.toString());
                    ObjectReader oReader = csvMapper.readerFor(DailyStockTradeData.class).with(schema);
                    try (Reader reader = new FileReader(file)) {
                        MappingIterator<DailyStockTradeData> dailyStockTradeDataMappingIterator = oReader.readValues(reader);
                        int count = 0;
                        while (dailyStockTradeDataMappingIterator.hasNext()) {
                            DailyStockTradeData dailyStockTradeData = dailyStockTradeDataMappingIterator.next();
                            kafkaTemplate.send(kafkaTopic, dailyStockTradeData);
                            count++;
                        }
                        logger.info("Total records in the file::" + file.getName() + "||" + count);
                        FileUtils.moveFileToDirectory(file, new File(archiveFilesDirectory), true);
                    } catch (Exception e) {
                        logger.error(e.toString());
                    }


                });
            }

        }

        executorService.shutdown();

    }

}
