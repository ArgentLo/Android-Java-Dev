/**
 * Our Package Name
 */
package com.example.my_justjava;

/**
 * import
 */

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.net.Uri;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    /**
     * Initialize Variables
     */
    private int quantity = 0;
    private int cost = 5;
    //Objects are in CamelCase
    private TextView orderSummaryTextView;
    private TextView quantityTextView;
    private EditText userName;
    private CheckBox whippedCreamCheckBox;
    private CheckBox chocolateCheckBox;
    private boolean hasWhippedCream = false;
    private boolean hasChocolate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        userName = findViewById(R.id.user_name);
    }


    private void displayQuantity(int num) {
        quantityTextView.setText(String.valueOf(num));
    }

    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(this,
                    "Sorry, Quantity greater than 99 is not supported currently.",
                    Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity++;
            displayQuantity(quantity);
        }
    }

    public void decrement(View view) {
        if (quantity <= 0) {
            Toast.makeText(this,
                    "Sorry, quantity should be greater than 1.",
                    Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity--;
            displayQuantity(quantity);
        }
    }

    public void submitOrder(View view) {
        int price = calculatePrice();
        String message = createOrderSummary(price);
        sendMessage(message);
    }

    private int calculatePrice() {
        int price = cost;
        if (hasWhippedCream) {
            price += 1;
        }
        if (hasWhippedCream) {
            price += 2;
        }
        return quantity * price;
    }

    private String createOrderSummary(int price) {
        String message = "Name : " + userName.getText().toString();
        message += getString(R.string.order_whipped_cream) + hasWhippedCream;
        message += getString(R.string.order_chocolate) + hasChocolate;
        message += getString(R.string.order_quantity) + quantity;
        message += getString(R.string.order_total) + price;
        message += getString(R.string.order_thank);
        return message;
    }

    /**
     * Implicit Intent --> Send info to any Email apps
     */
    private void sendMessage(String message) {
        // create an Array of type String
        String[] address = new String[]{"argent@gmail.com"};
        Intent email_intent = new Intent(Intent.ACTION_SENDTO);
        email_intent.setData(Uri.parse("mailto:"));
        email_intent.putExtra(Intent.EXTRA_EMAIL, address);
        email_intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Ordering Info");
        email_intent.putExtra(Intent.EXTRA_TEXT, message);
        // if there is No Email app for this action
        if (email_intent.resolveActivity(getPackageManager()) != null) {
            startActivity(email_intent);
        } else {
            Toast.makeText(this,
                    "No Email app installed!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * one CheckBox method for 2 cases:
     * case1: whipped_Cream; case2: chocolate
     *
     * @param view
     */
    public void onCheckBoxClicked(View view) {
        int checkBoxId = view.getId();
        switch (checkBoxId) {
            case R.id.whipped_cream_checkbox:
                hasWhippedCream = whippedCreamCheckBox.isChecked();
                break;
            case R.id.chocolate_check_box:
                hasChocolate = chocolateCheckBox.isChecked();
                break;
            default:
                break;
        }
    }
}
