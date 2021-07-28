# Shopping Basket
Scala program that can price a basket of goods taking into account some special offers.

### Storage
Mock storage is located in object `com.athena.sbasket.storage.Dao` 

### Run
Run via class `com.athena.sbasket.Main` and write goods in string that follows pattern:

*PriceBasket item1 item2 item3*

### Output
Result output works via scala-logging loggers. Can be easy replaced by implementing trait `com.athena.sbasket.Output` and applied it to constructor of class `com.athena.sbasket.ShoppingBasketFacade`

### Exit
To exit write word `exit`