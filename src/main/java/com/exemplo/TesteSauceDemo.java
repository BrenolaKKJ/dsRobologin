package com.exemplo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class TesteSauceDemo {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.saucedemo.com/");

            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            List<WebElement> produtos = driver.findElements(By.className("inventory_item"));

            System.out.println("=== PRODUTOS COM PREÇO MENOR QUE $20.00 ===");

            for (WebElement produto : produtos) {
                String nome = produto.findElement(By.className("inventory_item_name")).getText();
                String precoTexto = produto.findElement(By.className("inventory_item_price")).getText();
                
                double preco = Double.parseDouble(precoTexto.replace("$", ""));

                if (preco < 20.00) {
                    System.out.println("Produto: " + nome + " | Preço: " + precoTexto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}