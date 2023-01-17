package cars_center_managemet_b.CreateCsvBatch;

import cars_center_managemet_b.Entity.Cars;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<Cars> reader(){
        JdbcCursorItemReader<Cars> reader = new JdbcCursorItemReader<Cars>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT id,name,price,numberseats,datesale,sellingprice,buyername,username ,version " +
                "FROM cars ");
        reader.setRowMapper(new CarsRowMapper());

        return reader;
    }



    @Bean
    public CarsItemProcessor processor(){
        return new CarsItemProcessor();
    }

    @Bean
    public FlatFileItemWriter<Cars> writer(){
        FlatFileItemWriter<Cars> writer = new FlatFileItemWriter<Cars>();
        writer.setResource(new FileSystemResource("./src/main/resources/cars.csv"));
        writer.setLineAggregator(new DelimitedLineAggregator<Cars>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<Cars>() {{
                setNames(new String[]{"id","name","price","numberseats","datesale","sellingprice"
                        ,"buyername","username" ,"version"});

            }});
        }});

        return writer;
    }



    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Cars, Cars> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job exportEmployeeJob() {
        return jobBuilderFactory.get("exportEmployeeJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

}
