package br.uece.metrocard.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"br.uece.metrocard.cucumber"},
        plugin = {"pretty"}
)
public class RunnerCucumberTest {
}
