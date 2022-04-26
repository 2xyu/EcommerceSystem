Everything in my code works

FOR THE BONUS PART

For the bonus part, I added 3 methods and one private method. This being the rateProduct(String productId, String rating) for "RateProd", the ratingsOfOneProduct(String productId) for "AvgRatingOfOneProd", and the printCategoryBaseOnMinimumRating(String categoryInput, String minimumRating) for "PrintCategoryWithMinimumRating". They all use the private averRatingFinder(String productId) method.

The rateProduct method just adds a rating to a product and as well as displays the current average rate of the product

The ratingsOfOneProduct method just displays the average rating of a product

The printCategoryBaseOnMinimumRating prints all the products of a selected category (unless "7" is select to which it will print all the products) with an average of atleast the minimum given rating.

The private averRatingFinder method is used by the three methods listed above as a quick way to calculate the average of a given product Id.

For a more detailed description, Javadoc comments have been provided for each of the methods.

some things to note

-I included a productsForTesting.txt used for testing out the shoes and the books with no year as a comment in the code (you can uncomment out the code to test it out)

-I decided to make another Category for shoe, this being SHOES to make it easier for me to keep track of my shoes

-I decided to make it so that the addToCart method would take in an extra parameter. This being TypeBookOrTypeShoe to keep track of whether Type 2 (book) or Type 3 (shoe) was used. (I did this to help with dealing with errors for the addToCart method 
ie when you use Type 2 (book) to order a non book and "" was typed in for the options
when you use Type 2 (book) to order a shoe and type in "Size 6 in Black" for the options
when you use Type 3 (shoe) to order a non-shoe/non-book and type in "" for all the options
when you use Type 3 (shoe) to order a book and either "Ebook" was typed in for only one of the option).

-ADDTOCART Type 1 (Non-Shoe and Non-Book) will only work on non-books and non-shoes products (it will account for errors ie for when a book or a shoe is selected, it will print the proper error msg)

-ADDTOCART Type 3 (shoe) will only work on shoe products (it will account for errors ie when a non-shoe is selected, it will print the proper error msg)

-ADDTOCART Type 2 (book) will only work on book products (it will account for errors ie when a non-book is selected, it will print the proper error msg)

-I also decided to make CartItem take in another instance varible (this being TypeBookOrTypeShoe to keep track of which Product Type the product is) since my orderProduct uses a forth variable that is identical to the TypeBookOrTypeShoe, this would make things alot easier for me.

-For the reading of products.txt (the readProducts method), I decided to make it so that it would account for shoes as well
I formmatted the 4th line of shoes as the shoe sizes for black
and the 5th line of the shoes as the shoe sizes for brown 
ie 
4th line)  1 54 23 61 23 <- black shoes
5th line) 12 7 10 5 1 <- brown shoes
