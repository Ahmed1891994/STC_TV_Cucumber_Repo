package stepDefinitions;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.StcTvPlanPage;

public class StcTvPlanStepDefinition {

	StcTvPlanPage stctvplanpage;
	
	@Given("User Chooses {string}")
	public void User_Chooses_Item_by_index(String country) {
		stctvplanpage = new StcTvPlanPage();
		stctvplanpage.OpenCountryList();
		stctvplanpage.ChooseCountryFlow(country);
	}

	@Then("Verify prices for each plan for {string}")
	public void VerifyPricesForEachPlan(String country) {
		Assert.assertEquals(stctvplanpage.getActualPrices(country), stctvplanpage.getExpectedPrices(country));
	}

	@Then("Verify Plan Type for {string}")
	public void VerifyPlanType(String country) {
		Assert.assertEquals(stctvplanpage.getActualPlans(country), stctvplanpage.getExpectedPlans(country));
	}

	@Then("Verify Currency for each plan for {string}")
	public void VerifyCurrencyForPlan(String country) {
        Assert.assertEquals(stctvplanpage.getActualCurrency(country),stctvplanpage.getExpectedCurrency(country));
	}
}
