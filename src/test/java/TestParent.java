import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

public class TestParent {
    private static final Logger log = LoggerFactory.getLogger(TestParent.class);
    static Yaml yaml;
    static InputStream inputStream;
    static Map<String, Object> yamlData;
    static IOSDriver iosDriver;
    static AppiumDriverLocalService service;
    static String service_url;
    static DesiredCapabilities iosCapabilities;

    static CommonAction commonAction;
    static LoginPage loginPage;
    static MenuPage menuPage;
    static ProductsPage productsPage;
    // add your own more pages below here

    public void loadYaml() {
        yaml = new Yaml();
        try {
            inputStream = new FileInputStream("config.yaml");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        yamlData = yaml.load(inputStream);
    }

    public void loadAllPages(IOSDriver iosDriver){
        commonAction = new CommonAction(iosDriver);
        loginPage = new LoginPage(iosDriver);
        menuPage = new MenuPage(iosDriver);
        productsPage = new ProductsPage(iosDriver);
        // when you add new pages, instantiate them as well below here
    }

    @BeforeSuite
    public void startDataPreparation() throws IOException {
        FileUtils.deleteDirectory(new File("allure-results"));
        loadYaml();
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
        service_url = service.getUrl().toString();
        iosCapabilities = new DesiredCapabilities();
        iosCapabilities.setCapability("appium:automationName", "XCUITest");
        iosCapabilities.setCapability("appium:udid", yamlData.get("udid").toString());
        iosCapabilities.setCapability("appium:bundleId", yamlData.get("bundleId").toString());
        iosCapabilities.setCapability("appium:autoGrantPermissions","true");
    }

    @BeforeMethod
    protected void startTestCase() {
        try {
            iosDriver = new IOSDriver(new URL(service_url), iosCapabilities);
            loadAllPages(iosDriver);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterMethod
    public void closeTestCase(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot scrShot = iosDriver;
            File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
            Allure.addAttachment("Screenshot", Files.newInputStream(SrcFile.toPath()));
        }
        iosDriver.quit();
    }

    @AfterSuite
    public void addAllureInformation() {
        service.stop();
        Properties allure = new Properties();
        allure.setProperty("Platform", "iOS (" + iosDriver.getCapabilities().getCapability("platformVersion").toString() + ")");
        allure.setProperty("Library for Automation", "Appium + TestNG (for Assertion)");
        allure.setProperty("Code by", "Ahmad Azeri Chandra Bhuana");
        try {
            File file = new File("./allure-results/environment.properties");
            FileOutputStream fileOut = new FileOutputStream(file);
            allure.store(fileOut, "Allure Report Environment");
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
