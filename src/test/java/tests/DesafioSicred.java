package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.Keys;

public class DesafioSicred implements Variables {

	private WebDriver driver;
	private WebDriverWait wait;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "navegador\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, 30);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
	
	@Test
	public void cadastroDesafio1() throws InterruptedException {
		driver.manage().deleteAllCookies();
		// Acesso a url solicitada
		driver.get("https://www.grocerycrud.com/demo/bootstrap_theme");

        //Seleção Versão Bootstrap
		Select bootStrapVersion = new Select(driver.findElement(By.id("switch-version-select")));
		bootStrapVersion.selectByVisibleText("Bootstrap V4 Theme");
		Thread.sleep(3000);

		// Clica no botão para Cadastro
		driver.findElement(By.xpath("//a[@href='/demo/bootstrap_theme_v4/add']")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='floatL l5' and contains(text(),'Add Customer')]")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("field-customerName")));
		//Preenchimento Formulário
		driver.findElement(By.id("field-customerName")).sendKeys(customerName);
		driver.findElement(By.id("field-contactLastName")).sendKeys(lastName);
		driver.findElement(By.id("field-contactFirstName")).sendKeys(contactFirstName);
		driver.findElement(By.id("field-phone")).sendKeys(phoneNumber);
		driver.findElement(By.id("field-addressLine1")).sendKeys(addressLine1);
		driver.findElement(By.id("field-addressLine2")).sendKeys(adressLine2);
		driver.findElement(By.id("field-city")).sendKeys(city);
		driver.findElement(By.id("field-state")).sendKeys(state);
		driver.findElement(By.id("field-postalCode")).sendKeys(postalCode);
		driver.findElement(By.id("field-country")).sendKeys(country);
		driver.findElement(By.xpath("//div[@id='field_salesRepEmployeeNumber_chosen']//b")).click();
		driver.findElement(By.xpath("//div[@class='chosen-search']//input[@type='text']")).sendKeys(employeeNumber,
				Keys.ENTER);
		driver.findElement(By.id("field-creditLimit")).sendKeys(creditLimit);

		// Clica no botão Salvar
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='report-success']//p")));
		// Verifica Mensagem de Sucesso
		String mensagemSucesso = driver.findElement(By.xpath("//div[@id='report-success']//p")).getText();
		assertTrue(mensagemSucesso.contains(assertMessageSucess));
	}

	@Test
	public void cadastroDesafio2() throws InterruptedException {

		//Executa o cadastro do desafio 1
		cadastroDesafio1();
		// Clica no botão Save and Go Back
		driver.findElement(By.xpath("//button[@id='save-and-go-back-button']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='alert alert-success growl-animated animated bounceInDown']")));
		driver.findElement(By.xpath("//span[@aria-hidden='true']")).click();

		// Pesquisa o nome solicitado
		driver.findElement(By.name("customerName")).sendKeys("Teste Sicredi");
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='container-fluid gc-container loading-opacity']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='container-fluid gc-container loading-opacity']")));
		Thread.sleep(1000);

		// Clica no botão delete e verifica se a mensagem está correta
		driver.findElement(By.xpath("(//tbody/tr/td//input)[1]")).click();
		driver.findElement(By.xpath("  (//div/a[@title='Delete']/span)[1]")).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='modal-body']//p[@class='alert-delete-multiple-one']")));

		String mensagemExclusao = driver
				.findElement(By.xpath("//div[@class='modal-body']//p[@class='alert-delete-multiple-one']")).getText();
		assertTrue(mensagemExclusao.contains(deleteAlert));

		driver.findElement(By.xpath("//button[@class='btn btn-danger delete-multiple-confirmation-button']")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-growl='message']")));
		String mensagemRetornoExclusao = driver.findElement(By.xpath("//span[@data-growl='message']")).getText();
		assertTrue(mensagemRetornoExclusao.contains(deleteSucess));

	}

}
