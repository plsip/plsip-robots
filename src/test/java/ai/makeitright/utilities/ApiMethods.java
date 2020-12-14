package ai.makeitright.utilities;

import static io.restassured.RestAssured.given;

public class ApiMethods {

    public static void createNewJob(String workflowID, String codeVersion, String processID, String apiTokenPassword, String apiTokenServer) {
        String job = "{\n" +
                "  \"author\": \"Automation API\",\n" +
                "  \"workflowID\": \"" + workflowID + "\",\n" +
                "  \"jobsConfiguration\": [\n" +
                "    {\n" +
                "      \"codeVersion\": \"" + codeVersion + "\",\n" +
                "      \"processID\": \"" + processID + "\",\n" +
                "      \"gitReferenceType\": \"branch\",\n" +
                "      \"gitReferenceName\": \"master\",\n" +
                "      \"inputParameters\":{\n" +
                "        \"channel\":\"#pf-cloud-automation-tests\",\n" +
                "        \"slackFlag\":\"false\",\n" +
                "        \"url\":\"https://www.google.pl/\",\n" +
                "        \"expectedTitle\":\"Google\"\n" +
                "        }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        given()
                .header("Token", apiTokenPassword)
                .body(job)
                .contentType("application/json")
                .when().post(apiTokenServer).then().log().body().statusCode(200);

    }
}
