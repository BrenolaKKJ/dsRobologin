package com.exemplo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://the-internet.herokuapp.com/tables");

            WebElement tabela1 = driver.findElement(By.id("table1"));
            List<WebElement> linhas = tabela1.findElements(By.cssSelector("tbody tr"));

            System.out.println("=== CLIENTES COM DÍVIDA SUPERIOR A $50.00 ===");

            for (WebElement linha : linhas) {
                List<WebElement> colunas = linha.findElements(By.tagName("td"));

                String sobrenome = colunas.get(0).getText();
                String primeiroNome = colunas.get(1).getText();
                
                String valorDueStr = colunas.get(3).getText().replace("$", "");
                double due = Double.parseDouble(valorDueStr);

                if (due > 50.00) {
                    String webSite = colunas.get(4).getText();
                    System.out.println("Primeiro Nome: " + primeiroNome + 
                                       " | Último Nome: " + sobrenome + 
                                       " | Site: " + webSite + 
                                       " | Due: $" + due);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}