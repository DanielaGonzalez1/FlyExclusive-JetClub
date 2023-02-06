package appium;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
                "html:target/cucumber-html-report.html",
                "json:target/cucumber-json-report.json"
        },
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features="src/test/resources/features",
        publish = true
)
public class RunCucumberTest
{

}

