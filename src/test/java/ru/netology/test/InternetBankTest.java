package ru.netology.test;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class InternetBankTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void replenishmentOfTheFirstCard() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

    }
}
