package org.upgrad.upstac.testrequests;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.Contracts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.upgrad.upstac.exception.AppException;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.testrequests.lab.*;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.models.Gender;

import javax.persistence.ManyToOne;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
class LabRequestControllerTest {

    @InjectMocks
    TestRequestController testRequestController;

    @Autowired
    LabRequestController labRequestController;

    @Autowired
    TestRequestQueryService testRequestQueryService;

    @Mock
    UserLoggedInService userLoggedInService;

    @Mock
    LabResultService labResultService;

    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_valid_test_request_id_should_update_the_request_status(){

        //TestRequest testRequest = getTestRequestByStatus(RequestStatus.INITIATED);
        //Implement this method

        TestRequest testRequest = getLabResult();
        User user= createUser();
        Mockito.when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        Mockito.when(labResultService.assignForLabTest(testRequest ,user)).thenReturn(testRequest);

        //Create another object of the TestRequest method and explicitly assign this object for Lab Test using assignForLabTest() method
        // from labRequestController class. Pass the request id of testRequest object.

        TestRequest result = labRequestController.assignForLabTest(testRequest.requestId);

        assertNotNull(result);
        assertEquals(result.requestId,testRequest.requestId);
        //Use assertThat() methods to perform the following two comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'INITIATED'
        // make use of assertNotNull() method to make sure that the lab result of second object is not null
        // use getLabResult() method to get the lab result


    }

    public TestRequest getTestRequestByStatus(RequestStatus status) {
        return testRequestQueryService.findBy(status).stream().findFirst().get();
    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_valid_test_request_id_should_throw_exception(){

        Long InvalidRequestId= -34L;

        //Implement this method


        // Create an object of ResponseStatusException . Use assertThrows() method and pass assignForLabTest() method
        // of labRequestController with InvalidRequestId as Id


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"
        User user= createUser();
        TestRequest testRequest = getTestRequestByStatus(RequestStatus.INITIATED);

        Mockito.when(userLoggedInService.getLoggedInUser()).thenReturn(user);
       Mockito.when(labResultService.assignForLabTest(testRequest ,user)).thenThrow(new AppException("Invalid data"));

        //Act
        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            labRequestController.assignForLabTest(InvalidRequestId);
        });

        //Assert
        assertThat(result.getMessage(),containsString("Invalid ID"));
    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_valid_test_request_id_should_update_the_request_status_and_update_test_request_details(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);

        //Implement this method
        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        //Create another object of the TestRequest method and explicitly update the status of this object
        // to be 'LAB_TEST_IN_PROGRESS'. Make use of updateLabTest() method from labRequestController class (Pass the previously created two objects as parameters)

        //Use assertThat() methods to perform the following three comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'LAB_TEST_COMPLETED'
        // 3. the results of both the objects created should be same. Make use of getLabResult() method to get the results.



    }


    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_test_request_id_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        Long InvalidRequestId= -34L;
        User user= createUser();
        CreateLabResult createLabResult = getCreateLabResult();
        Mockito.when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        Mockito.when(labResultService.updateLabTest(testRequest ,createLabResult)).thenThrow(new AppException("Invalid data"));

        //Act
        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            labRequestController.updateLabTest(InvalidRequestId, createLabResult);
        });


        //Assert
        assertThat(result.getMessage(),containsString("Invalid ID"));

        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with a negative long value as Id and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_empty_status_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);

        //Implement this method
        CreateLabResult createLabResult = getCreateLabResult();
        createLabResult.setResult(null);

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter
        // Set the result of the above created object to null.

        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            labRequestController.updateLabTest(testRequest.requestId, createLabResult);
        });
        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with request Id of the testRequest object and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method

        assertThat(result.getMessage(),containsString("ConstraintViolationException"));
        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "ConstraintViolationException"

    }

    public CreateLabResult getCreateLabResult() {
        CreateLabResult createLabResult = new CreateLabResult();

        createLabResult.setBloodPressure("33");
        createLabResult.setHeartBeat("44");
        createLabResult.setOxygenLevel("33");
        createLabResult.setComments("Positive");

        return createLabResult;

    }

    public TestRequest getLabResult(){
        TestRequest createTestRequest = new TestRequest();
        createTestRequest.setAddress("some Addres");
        createTestRequest.setAge(98);
        createTestRequest.setEmail("someone" + "123456789" + "@somedomain.com");
        createTestRequest.setGender(Gender.MALE);
        createTestRequest.setName("someuser");
        createTestRequest.setPhoneNumber("123456789");
        createTestRequest.setPinCode(716768);
        return createTestRequest;
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName("someuser");
        return user;
    }

}