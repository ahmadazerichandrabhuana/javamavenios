import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

public class LoginPage extends CommonAction {
    @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Login\"]")
    List<WebElement> textHeaderLogin;
    @FindBy(xpath = "//XCUIElementTypeTextField[@name=\"Username input field\"]")
    List<WebElement> inputUsername;
    @FindBy(xpath = "//XCUIElementTypeSecureTextField[@name=\"Password input field\"]")
    List<WebElement> inputPassword;
    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Login button\"]")
    List<WebElement> buttonLogin;
    @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Provided credentials do not match any user in this service.\"]")
    List<WebElement> textErrorLogin;

    public LoginPage(IOSDriver iosDriver) {
        super(iosDriver);
        PageFactory.initElements(new AppiumFieldDecorator(iosDriver, Duration.ofSeconds(5)), this);
    }

    @Step("Input Username")
    public void inputUsernameField(String value){
        inputUsername.get(0).clear();
        inputUsername.get(0).sendKeys(value);
        hideKeyboard();
    }

    @Step("Input Password")
    public void inputPasswordField(String value){
        inputPassword.get(0).clear();
        inputPassword.get(0).sendKeys(value);
        hideKeyboard();
    }

    @Step("Click button Login")
    public void clickButtonLogin(){
        buttonLogin.get(0).click();
    }

    @Step("Verify Login Error Appear")
    public void verifyErrorAppear(String errorText){
        Assert.assertFalse(textErrorLogin.isEmpty());
        Assert.assertEquals(textErrorLogin.get(0).getText(), errorText);
    }
}
