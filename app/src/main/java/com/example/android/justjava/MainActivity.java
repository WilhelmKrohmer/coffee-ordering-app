package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 *
 * The app derives from the android basics course at udacity:
 * https://eu.udacity.com/course/android-basics-nanodegree-by-google--nd803
 *
 * See the original here: https://github.com/udacity/Just-Java/tree/master
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    int price = 5;
    int whippedCreamPrice = 1;
    int chocolatePrice = 2;
    int toppingPrice = 0;
    String chosenTopping = "Coffee without topping";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayPrice(price);
    }

    /**
     * This method is called when the checkboxes are checked
     */
    public void onCheckBoxClicked(View view) {
        // First find all the checkboxes and assign them to a variable
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox ChocolateCheckBox = findViewById(R.id.chocolate_cream_checkbox);
        boolean hasChocolate = ChocolateCheckBox.isChecked();

        //Next change the value of chosenTopping
        if (hasWhippedCream && hasChocolate) {
            chosenTopping = "Topping: Whipped Cream and Chocolate";
            toppingPrice = whippedCreamPrice + chocolatePrice;
            displayPrice(price);
        } else if (hasWhippedCream && !hasChocolate) {
            chosenTopping = "Topping: Whipped Cream";
            toppingPrice = whippedCreamPrice;
            displayPrice(price);
        } else if (hasChocolate && !hasWhippedCream) {
            chosenTopping = "Topping: Chocolate";
            toppingPrice = chocolatePrice;
            displayPrice(price);
        } else {
            chosenTopping = "Coffee without topping";
            toppingPrice = 0;
            displayPrice(price);
        }
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity < 5) {
            quantity += 1;
        }
        displayQuantity(quantity);
        displayPrice(price);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity -= 1;
        }
        displayQuantity(quantity);
        displayPrice(price);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        createOrderSummary();
    }

    /**
     * This method calculates the total price of ordered coffees
     */
    private int calculatePrice() {
        return quantity * (price + toppingPrice);
    }

    /**
    * This method creates a Order Summary with the final coffee price and all the chosen options.
    * Next it sends all the information to a mail app.
    * */
    private void createOrderSummary() {
        // Find the Text Field and get the value
        EditText editText = findViewById(R.id.buyer_name);
        String buyerName = editText.getText().toString();

        // Handle Edit Text Errors
        if (editText.getText().toString().trim().equalsIgnoreCase("")) {
            editText.setError("This field can not be blank");
        }

        // Calculate the final price and put all the values inside the summary
        int price = calculatePrice();
        String name = "Name: " + buyerName;
        String topping = "\n" + chosenTopping;
        String quantitiy = "\nQuantity: " + quantity;
        String total = "\nTotal: $" + price;
        String subline = "\nThank you!";
        String summary = name + topping + quantitiy + total + subline;

        // Send the Summary String to a mail app
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:mail@your_favorite_coffe.de"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Ein Kaffee für " + buyerName);
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int coffeeQuantity) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + coffeeQuantity);
    }

    private void displayPrice(int price) {
        int finalPrice = calculatePrice();
        TextView priceTextView = findViewById(R.id.price_text_view);
        priceTextView.setText("$" + finalPrice);
    }
}