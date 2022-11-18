package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;
import static java.time.Duration.ofSeconds;

public class RequestsCardDeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(3);
    SelenideElement notification = $x("//div[@data-test-id='notification']");


    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    public void ShouldCardDelivery() {
        $x(".//span[@data-test-id='city']//input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x(".//span[@data-test-id='name']//input").setValue("Миронов Артур");
        $x(".//span[@data-test-id='phone']//input").setValue("+79137891509");
        $x(".//label[@data-test-id='agreement']").click();
        $x(".//span[contains(text(),'Забронировать')]").click();
        notification.should(visible, ofSeconds(15));
        notification.$x(".//div[@class='notification__title']").should(text("Успешно!"));
        notification.$x(".//div[@class='notification__content']").should(text("Встреча успешно забронирована на " + planningDate));
        notification.$x(".//button").click();
        notification.should(hidden);

    }
}

