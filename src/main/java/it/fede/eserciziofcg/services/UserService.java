package it.fede.eserciziofcg.services;

import it.fede.eserciziofcg.models.entities.UserEntity;
import it.fede.eserciziofcg.repositories.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class UserService {

    public static final String COL_FIRST_NAME = "FirstName";
    public static final String COL_LAST_NAME = "LastName";
    public static final String COL_EMAIL = "Email";
    public static final String COL_ADDRESS = "Address";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Check and parse the csv then insert into db
     * @param file input file from Controller
     */
    public void bulkInsertUsers(MultipartFile file) {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {

            CSVParser parser = CSVParser.parse(reader, CSVFormat.EXCEL.withFirstRecordAsHeader());

            // Read and check headers
            List<String> headers = parser.getHeaderNames();

            if (!headers.contains(COL_FIRST_NAME) ||
                    !headers.contains(COL_LAST_NAME) ||
                    !headers.contains(COL_EMAIL) ||
                    !headers.contains(COL_ADDRESS)) {
                throw new RuntimeException("Una o più colonne mancanti");

            }
            List<CSVRecord> records = parser.getRecords();


            for (CSVRecord record : records) {
                UserEntity user = UserEntity.builder().firstname(record.get(COL_FIRST_NAME))
                        .lastname(record.get(COL_LAST_NAME))
                        .email(record.get(COL_EMAIL))
                        .address(record.get(COL_ADDRESS)).build();

                userRepository.save(user);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
