package Ejercicios;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class EjercicioFinal {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Maximizar la ventana del navegador
        WebDriver driver = new ChromeDriver(options);

        try {
            // Paso 1: Acceder a Google con el navegador Chrome
            driver.get("https://www.google.com");
            
            
            // Esperar un momento para que cargue la página
            Thread.sleep(2000);

            // Buscar el botón de aceptar cookies y hacer clic en él
            WebElement cookiesButton = driver.findElement(By.id("L2AGLb"));
            cookiesButton.click();

            // Esperar un momento para que se cierre el aviso de cookies
            Thread.sleep(2000);

            // Paso 2: Busca un producto (por ejemplo, compresor eléctrico)
            String product = "compresor eléctrico";
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys(product);
            searchBox.submit();

            // Paso 3: En los resultados accede al enlace de Amazon
            WebElement amazonLink = driver.findElement(By.partialLinkText("Amazon"));
            amazonLink.click();

            // Esperar un momento para que cargue la página de Amazon
            Thread.sleep(3000);
            
            // Buscar el botón de aceptar cookies y hacer clic en él
            cookiesButton = driver.findElement(By.id("sp-cc-accept"));
            cookiesButton.click();
            
            // Esperar un momento para que se cierre el aviso de cookies
            Thread.sleep(2000);
            
            WebElement searchBoxAmazon = driver.findElement(By.id("twotabsearchtextbox"));
            searchBoxAmazon.sendKeys(product);
            searchBoxAmazon.submit();
            
            // Esperar un momento para que cargue la página
            Thread.sleep(3000);
            
            // Hacer click en el primer resultado
            WebElement firstProduct = driver.findElement(By.xpath("//div[@data-component-type='s-search-result']"));
            firstProduct.click();

            // Esperar un momento para que cargue la página
            Thread.sleep(3000);

            // Paso 4: Imprime por pantalla el precio y la fecha de entrega
            WebElement price = driver.findElement(By.xpath("//span[@class='a-price-whole']")); 
            WebElement priceDecimal = driver.findElement(By.xpath("//span[@class='a-price-fraction']")); 
            WebElement deliveryDate = driver.findElement(By.xpath("//*[@id=\"mir-layout-DELIVERY_BLOCK-slot-PRIMARY_DELIVERY_MESSAGE_LARGE\"]/span/span")); 
            
            String priceString = price.getText();
            String priceDecimalString = priceDecimal.getText();
            String deliveryDateString = deliveryDate.getText();
            
            System.out.println("Precio: " + priceString + "," + priceDecimalString + "€");
            System.out.println("Fecha de entrega: " + deliveryDateString);

            // Paso 5: En el buscador general vuelve a buscar el producto
            searchBoxAmazon = driver.findElement(By.id("twotabsearchtextbox"));
            searchBoxAmazon.submit();
            
            // Esperar un momento para que cargue la página
            Thread.sleep(3000);

            // Paso 6: Filtrar por entregas Prime
            WebElement entregasPrimeFilter = driver.findElement(By.id("p_n_free_shipping_eligible/20930980031"));
            entregasPrimeFilter.click();

            // Esperar un momento para que se aplique el filtro
            Thread.sleep(3000);

            // Paso 7: Ordena por precio de mas bajo a más alto
            Actions action = new Actions(driver);
            WebElement sortButton = driver.findElement(By.id("s-result-sort-select"));
            action.moveToElement(sortButton).click().perform();
            WebElement newSort = driver.findElement(By.linkText("Precio: De menor a mayor"));
            newSort.click();

            // Esperar un momento para que se aplique la ordenación
            Thread.sleep(3000);
           
            // Paso 8: Imprime por pantalla el nombre de los productos de la primera pantalla y el precio, solo imprime los productos que se han encontrado no los sugeridos, ni las búsquedas antiguas.
            List<WebElement> productList = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
            int i = 1;
            System.out.println("Lista de productos:");
            for (WebElement productUnit : productList) {         
            	
            	WebElement productUnitName = productUnit.findElement(By.xpath(".//span[@class='a-size-base-plus a-color-base a-text-normal']"));
                String productUnitNameString = productUnitName.getText();
                Boolean havePrice=false;
                String priceUnitString = null;
                String priceDecimalUnitString = null;
            	try {
                	WebElement priceUnit = productUnit.findElement(By.xpath(".//span[@class='a-price-whole']")); 
                    WebElement priceDecimalUnit = productUnit.findElement(By.xpath(".//span[@class='a-price-fraction']"));                  
				
                    priceUnitString = priceUnit.getText();
                    priceDecimalUnitString = priceDecimalUnit.getText();
                    havePrice=true;
                    
                } catch (Exception e) {
					// TODO: handle exception
				} finally {
		            if (havePrice) {
		                System.out.println(i + ". Nombre del producto: " + productUnitNameString + ", Precio del producto: " + priceUnitString + "," + priceDecimalUnitString + "€");
		            } else {
		                System.out.println(i + ". Nombre del producto: " + productUnitNameString + ", Precio del producto: Sin precio");
		            }
		            i++;
				}
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Cerrar el navegador
            driver.quit();
        }
    }
}