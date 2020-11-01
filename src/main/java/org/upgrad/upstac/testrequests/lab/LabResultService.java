package org.upgrad.upstac.testrequests.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.upgrad.upstac.testrequests.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.users.User;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Validated
public class LabResultService {


    @Autowired
    private LabResultRepository labResultRepository;


    private static Logger logger = LoggerFactory.getLogger(LabResultService.class);



    private LabResult createLabResult(User tester, TestRequest testRequest) {

       // Create lab result with the tester and test request details
       // Return save lab result details

        LabResult labresult = new LabResult();
        labresult.setTester(tester);
        labresult.setRequest(testRequest);
        return saveLabResult(labresult);
        //Implement this method to create the lab result module service
        // create object of LabResult class and use the setter methods to set tester and testRequest details
        // make use of saveLabResult() method to return the LabResult object
    }

    @Transactional
    LabResult saveLabResult(LabResult labResult) {
        return labResultRepository.save(labResult);
    }



    public LabResult assignForLabTest(TestRequest testRequest, User tester) {

        return createLabResult(tester, testRequest);


    }


    public LabResult updateLabTest(TestRequest testRequest, CreateLabResult createLabResult) {

        // Create lab result with the lab result details
        // Return save lab result details
        LabResult labresult = labResultRepository.findByRequest(testRequest).get();
        labresult.setComments(createLabResult.getComments());
        labresult.setBloodPressure(createLabResult.getBloodPressure());
        labresult.setHeartBeat(createLabResult.getHeartBeat());
        labresult.setOxygenLevel(createLabResult.getOxygenLevel());
        labresult.setTemperature(createLabResult.getTemperature());
        labresult.setResult(createLabResult.getResult());
        labresult.setUpdatedOn(LocalDate.now());

        return saveLabResult(labresult);

        //Implement this method to update the lab test
        // create an object of LabResult and make use of setters to set Blood Pressure, Comments,
        // HeartBeat, OxygenLevel, Temperature, Result and UpdatedOn values
        // make use of the saveLabResult() method to return the object of LabResult



    }


}
