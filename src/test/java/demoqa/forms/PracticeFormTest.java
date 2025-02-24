package demoqa.forms;

import demoqa.core.TestBase;
import demoqa.pages.HomePage;
import demoqa.pages.PracticeFormPage;
import demoqa.pages.SidePanel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PracticeFormTest extends TestBase {
    @BeforeMethod
    public void precondition() {
        new HomePage(app.driver, app.wait).getForms().hideAds();
        new SidePanel(app.driver, app.wait).selectPracticeFormMenu().hideAds();
    }

    @Test
    public void practiceFormPositiveTest(){
        new PracticeFormPage(app.driver,app.wait)
                .enterPersonalData("Marklin","Müller","derk.i26252@gmail.com","1234567890")
                .selectGender("Female")
                .chooseDateAsString("04 May 1965")
                .enterSubjects(new String[]{"Maths","English"})
                .chooseHobbies(new String[]{"Sports","Music"})
                .uploadPicture("C:\\Users\\derki\\OneDrive\\Pictures\\Image20250221114742.png")
//                .enterCurrentAddress("Rostock Deutschland")
//                .enterState("GR")
//                .enterCity("Rostock")
//                .submitForm()
//                .verifySuccessRegistration("Thanks for submitting the form")
        ;
    }
}
