package demoqa.pages;

import demoqa.core.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import static java.util.Objects.isNull;

public class PracticeFormPage extends BasePage {
    public PracticeFormPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }


    @FindBy(id = "firstName")
    WebElement firstName;
    @FindBy(id = "lastName")
    WebElement lastName;
    @FindBy(id = "userEmail")
    WebElement userEmail;
    @FindBy(id = "userNumber")
    WebElement userNumber;

    public PracticeFormPage enterPersonalData(String name, String surName, String email, String number) {
        type(firstName, name);
        type(lastName, surName);
        type(userEmail, email);
        type(userNumber, number);
        System.out.printf("✅ Personal data: [%s], [%s], [%s], [%s]%n", name, surName, email, number);
        return this;
    }

    public PracticeFormPage selectGender(String gender) {
        try {
            String xpathGender = "//label[.='" + gender + "']";
            WebElement genderLocator = driver.findElement(By.xpath(xpathGender));
            click(genderLocator);
            System.out.printf("✅ Gender selected: [%s]%n", gender);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("⛔ Gender element not found: [" + gender + "]. " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("⛔ Error selecting gender: [" + gender + "]. " + e);
        }
        return this;
    }

    @FindBy(id = "dateOfBirthInput")
    WebElement dateOfBirthInput;

    public PracticeFormPage chooseDateAsString(String date) {
        //type(dateOfBirthInput, date);
        click(dateOfBirthInput);

        if (System.getProperty("os.name").contains("Mac")) {
            dateOfBirthInput.sendKeys(Keys.COMMAND, "a");
        } else {
            dateOfBirthInput.sendKeys(Keys.CONTROL, "a");
        }
        dateOfBirthInput.sendKeys(date);
        dateOfBirthInput.sendKeys(Keys.ENTER);
        System.out.printf("✅ Date: [%s]%n", date);
        return this;
    }

    @FindBy(id = "subjectsInput")
    WebElement subjectsInput;

    public PracticeFormPage enterSubjects(String[] subjects) {

        for (String subject : subjects) {
            type(subjectsInput, subject);
            dateOfBirthInput.sendKeys(Keys.ENTER);
            System.out.printf("✅ Subject: [%s]%n", subject);

        }
        return this;
    }

    public PracticeFormPage chooseHobbies(String[] hobbies) {
        for (String hobby : hobbies) {

            By hobbyLocator = By.xpath("//label[.='" + hobby + "']");
            WebElement element = driver.findElement(hobbyLocator);
            click(element);
            System.out.printf("✅ Hobby: [%s]%n", hobby);
        }

        return this;
    }

    @FindBy(id = "uploadPicture")
    WebElement uploadPicture;


    public PracticeFormPage uploadPicture(String imgPath, String imgName) {
        uploadPicture.sendKeys(imgPath);
        shouldHaveText2(uploadPicture, imgName, 1000);
        System.out.printf("✅ Image path: [%s]%n", imgPath);
        return this;
    }

    @FindBy(id = "currentAddress")
    WebElement currentAddress;

    public PracticeFormPage enterCurrentAddress(String address) {
        type(currentAddress, address);
        System.out.printf("✅ Address: [%s]%n", address);
        return this;
    }

    @FindBy(id = "state")
    WebElement stateContainer;

    @FindBy(id = "react-select-3-input")
    WebElement stateInput;

    public PracticeFormPage enterState(String state) {
        // type(stateContainer, state);
        click(stateContainer);
        stateInput.sendKeys(state);
        stateInput.sendKeys(Keys.ENTER);
        System.out.printf("✅ Address state: [%s]%n", state);
        return this;
    }

    @FindBy(id = "city")
    WebElement cityContainer;

    @FindBy(id = "react-select-4-input")
    WebElement cityInput;

    public PracticeFormPage enterCity(String city) {
        click(cityContainer);
        cityInput.sendKeys(city);
        cityInput.sendKeys(Keys.ENTER);
        System.out.printf("✅ Address city: [%s]%n", city);
        return this;
    }

    @FindBy(id = "submit")
    WebElement submitButton;

    public PracticeFormPage submitForm() {
        click(submitButton);
        return this;
    }

    @FindBy(id = "example-modal-sizes-title-lg")
    WebElement registrationModal;

    public PracticeFormPage verifySuccessRegistration(String textToCheck) {
        shouldHaveText(registrationModal, textToCheck, 5000);
        System.out.printf("✅ Registration success: [%s]%n", textToCheck);
        return this;
    }

    @FindBy(xpath = "//select[@class='react-datepicker__month-select']")
    WebElement monthDropdown;
    @FindBy(xpath = "//select[@class='react-datepicker__year-select']")
    WebElement yearDropdown;

    public PracticeFormPage chooseDate(String day, String month, String year) {
        //if(day == null || day.isBlank() || month == null || month.isBlank() || year == null || year.isBlank()){
        if (isNull(day) || isNull(month) || isNull(year) || month.isBlank() || year.isBlank() || day.isBlank()) {
            throw new IllegalArgumentException("❌ Date parameter is null or empty");
        }

        if (!day.matches("^(0?[1-9]|[12][0-9]|3[01])$")) {
            throw new IllegalArgumentException("❌ Day must be a valid number between 1 and 31");
        }

        if (!month.matches("^(January|February|March|April|May|June|July|August|September|October|November|December)$")) {
            throw new IllegalArgumentException("❌ Month must be a valid month name");
        }

        day = day.replaceFirst("^0", ""); //! убираем в числе "04" перый "0" чтоб селениум распознал число 4 и подставил его в локатор

        click(dateOfBirthInput);

        Select selectMonth = new Select(monthDropdown);
        selectMonth.selectByVisibleText(month);
        Select selectYear = new Select(yearDropdown);
        selectYear.selectByVisibleText(year);

        //By dateLocator = By.xpath("//div[contains(@class, 'react-datepicker__day') and text()='"+ day +"' and contains(@aria-label, '"+month+" "+day+"th, "+year+"')]");
        By dateLocator = By.xpath("//div[contains(@aria-label, '" + month + "') and contains(@aria-label, '" + year + "') and text()='" + day + "']");
        WebElement element = driver.findElement(dateLocator);
        click(element);

        day = "0" + day;
        System.out.printf("✅ Date: [%s], [%s], [%s]%n", day, month, year);
        return this;
    }
}
