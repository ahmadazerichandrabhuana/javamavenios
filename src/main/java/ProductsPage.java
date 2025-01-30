import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

public class ProductsPage extends CommonAction {
    @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Products\"]")
    List<WebElement> textHeaderProducts;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Productss\"]")
    List<WebElement> textHeaderProductsIncorrectPath;

    public ProductsPage(IOSDriver iosDriver) {
        super(iosDriver);
        PageFactory.initElements(new AppiumFieldDecorator(iosDriver, Duration.ofSeconds(5)), this);
    }

    @Step("Verify Header \"Products\" Appear")
    public void verifyHeaderProductsAppear(){
        Assert.assertFalse(textHeaderProducts.isEmpty());
    }

    @Step("Verify Header \"Productssss\" Appear... but Failed")
    public void verifyHeaderProductsAppearFailed(){
        Assert.assertFalse(textHeaderProductsIncorrectPath.isEmpty());
    }
}
