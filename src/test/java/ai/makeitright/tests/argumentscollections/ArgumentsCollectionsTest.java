package ai.makeitright.tests.argumentscollections;

import ai.makeitright.utilities.DriverConfig;
import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ArgumentsCollectionsTest extends DriverConfig {

    //from configuration:
    private String pfUrl;
    private String pfArgumentsCollectionsPagePartUrl;

    //for reporting:
    private String repositoryAddress;

    @Before
    public void before() {
        pfUrl = System.getProperty("inputParameters.pfUrl");
        pfArgumentsCollectionsPagePartUrl = System.getProperty("inputParameters.pfArgumentsCollectionsPagePartUrl");
    }

    @Test
    public void createArgumentsCollection() {

        Faker faker = new Faker();
        String nameOfArgumentsCollection = faker.funnyName().name();
        String nameOfArgument = faker.funnyName().name();
        String devaultArgumentValue = faker.funnyName().name();


        driver.manage().window().maximize();
        final String argumentsCollectionsPageUrl = pfUrl + pfArgumentsCollectionsPagePartUrl;
        driver.get(argumentsCollectionsPageUrl);

        final ArgumentsCollectionsPage argumentsCollectionsPage =
                new ArgumentsCollectionsPage(driver, argumentsCollectionsPageUrl);
        final ArgumentsCollectionsPage.CreateArgumentsCollectionModalWindow createArgumentsCollectionModalWindow =
                argumentsCollectionsPage.clickCreateArgumentsCollectionButton();
        ArgumentsCollectionPage argumentsCollectionPage = createArgumentsCollectionModalWindow
                .writeIntoCollectionNameInput(nameOfArgumentsCollection)
                .clickSaveButton();
        final ArgumentsCollectionPage.AddArgumentModalWindow addArgumentModalWindow =
                argumentsCollectionPage.clickAddArgumentButton();
        argumentsCollectionPage = addArgumentModalWindow
                .writeIntoArgumentNameInput(nameOfArgument)
                .writeIntoDefaultValueinput(devaultArgumentValue)
                .clickSaveButton();

//        Assertions


    }

    @After
    public void prepareJson() {
        final JSONObject obj = new JSONObject();
        obj.put("repositoryaddress", repositoryAddress);
        System.setProperty("output", obj.toString());
        driver.close();
    }

}
