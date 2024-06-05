import dao.*;
import dto.ComponentDTO;
import dto.MedicationDTO;
import dto.MedicationMapper;
import model.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        final MedicationDTO medicationDTO = new MedicationDTO();
        final ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName("name");
        componentDTO.setDescription("description");

        final MedicationBuilder expectedResult = new MedicationBuilder.Builder()
                .setDescription("description")
                .setName("name")
                .build();

        // Run the test
        final MedicationBuilder result = MedicationMapper.toEntity(medicationDTO);

        System.out.println(result);
        System.out.println(expectedResult);
    }
}
