
Feature: Amazon UI Testing
   
  @Exercise1  
  Scenario Outline: Search Product in Amazon by Suggestion
  	Given The name of the test "Cucumber Test 1: Search Product by Suggestion"
    And User is on Amazon Landing Page
    When User type "<search>" in search bar and click in suggestion <position>
    Then Verify Page Title
    And Verify that price is correct of product <productNumber> in Search Product Page

    Examples: 
      | search  					| position | productNumber |
      | Macbook Air 			| 1	    	 | 1						 |
      | Iphone 15 Pro Max |	3				 | 4             |
      | NVIDIA RTX 4060   | 5  			 | 2             |

 @Exercise2
	Scenario Outline: Check Amazon product price in Product Page
		Given The name of the test "Cucumber Test 2: Search Product by Suggestion"
    And User is on Amazon Landing Page
    When User type "<search>" in search bar and click in suggestion <position>
    Then Verify Page Title
    And Click the price of product <productNumber>
    And Verify that price is correct in Product Page
  	
  	Examples: 
      | search  					| position | productNumber |
      | Macbook Air 			| 1	    	 | 1						 |
      | Iphone 15 Pro Max |	3				 | 4             |
      | NVIDIA RTX 4060   | 5  			 | 2             |
      
 @Exercise3
	Scenario Outline: Check Amazon Hambuger Menu Categories
		Given The name of the test "Cucumber Test 3: Check Amazon Hamburger Menu Categories"
    And User is on Amazon Landing Page
    When Click the hamburger menu and Get menu categories and subcategories
    Then Compare the Category using "BurcarPorCategoriaJSONPath"
    And Compare the Category using "ProgramasYCaracteristicasJSONPath"
    
    
    
    
    