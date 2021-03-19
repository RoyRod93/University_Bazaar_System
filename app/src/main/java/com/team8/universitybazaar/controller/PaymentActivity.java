package com.team8.universitybazaar.controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityPaymentBinding;
import com.team8.universitybazaar.misc.Validations;
import com.team8.universitybazaar.model.SaleItem;
import com.team8.universitybazaar.model.Transaction;
import com.team8.universitybazaar.model.User;

import java.util.Calendar;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = PaymentActivity.class.getSimpleName();

    ActivityPaymentBinding activityPaymentBinding;
    private User loggedInUser;
    private SaleItem saleItem;
    private Validations validations;
    private DatabaseHelper databaseHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityPaymentBinding = ActivityPaymentBinding.inflate(getLayoutInflater());
        View view = activityPaymentBinding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Make Payment");

        loggedInUser = (User) getIntent().getSerializableExtra("loggedUser");
        saleItem = (SaleItem) getIntent().getSerializableExtra("bazaar_sale_item");

        validations = new Validations();
        databaseHelper = new DatabaseHelper(this);

        int costWithTaxes = (int) (saleItem.getPrice() + (saleItem.getPrice() * 0.08));

        activityPaymentBinding.tvItemName.setText(saleItem.getItemName());
        activityPaymentBinding.tvSeller.setText(saleItem.getUserName());
        activityPaymentBinding.tvItemCategory.setText(saleItem.getItemCategory());
        activityPaymentBinding.tvItemPrice.setText("$" + String.valueOf(saleItem.getPrice()));
        activityPaymentBinding.tvTotalCost.setText("$" + String.valueOf(costWithTaxes));


        /*https://stackoverflow.com/a/34219645/5990108*/
        activityPaymentBinding.etCardNo.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });

        /*https://stackoverflow.com/a/34219645/5990108*/
        activityPaymentBinding.etExpiryDate.addTextChangedListener(new TextWatcher() {

            private static final int CARD_DATE_TOTAL_SYMBOLS = 5; // size of pattern MM/YY
            private static final int CARD_DATE_TOTAL_DIGITS = 4; // max numbers of digits in pattern: MM + YY
            private static final int CARD_DATE_DIVIDER_MODULO = 3; // means divider position is every 3rd symbol beginning with 1
            private static final int CARD_DATE_DIVIDER_POSITION = CARD_DATE_DIVIDER_MODULO - 1; // means divider position is every 2nd symbol beginning with 0
            private static final char CARD_DATE_DIVIDER = '/';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!isInputCorrect(s, CARD_DATE_TOTAL_SYMBOLS, CARD_DATE_DIVIDER_MODULO, CARD_DATE_DIVIDER)) {
                    s.replace(0, s.length(), concatString(getDigitArray(s, CARD_DATE_TOTAL_DIGITS), CARD_DATE_DIVIDER_POSITION, CARD_DATE_DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String concatString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });

        activityPaymentBinding.btnMakePayment.setOnClickListener(v -> {

            if (isValid()) {

                Transaction transaction = new Transaction();

                transaction.setSaleId(saleItem.getSaleId());
                transaction.setUserName(loggedInUser.getUserName());
                transaction.setCardNo(activityPaymentBinding.etCardNo.getText().toString());
                transaction.setSecurityCode(activityPaymentBinding.etSecurityCode.getText().toString());
                transaction.setNameOnCard(activityPaymentBinding.etNameOnCard.getText().toString());
                transaction.setExpiryDate(activityPaymentBinding.etExpiryDate.getText().toString());
                transaction.setZipCode(activityPaymentBinding.etZipCode.getText().toString());
                transaction.setTransactionDate(Calendar.getInstance().getTime().toString());
                transaction.setTransactionAmount(activityPaymentBinding.tvTotalCost.getText().toString());

                databaseHelper.hideSoldItem(saleItem.getSaleId());
                databaseHelper.addTransaction(transaction);

                Toast.makeText(this, "Successful payment...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PaymentActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("logged-user", loggedInUser);
                startActivity(i);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isValid() {

        String[] splitDate = new String[2];
        String currentDate = java.time.LocalDate.now().toString();
        String[] splitCurrentDate = currentDate.split("-");
        int currentYear = Integer.parseInt(splitCurrentDate[0]) % 100;
        int currentMonth = Integer.parseInt(splitCurrentDate[1]);


        if (!activityPaymentBinding.etExpiryDate.getText().toString().isEmpty() || activityPaymentBinding.etExpiryDate.getText().toString() != null) {

             splitDate = activityPaymentBinding.etExpiryDate.getText().toString().trim().split("/");
        }

        if (validations.isBlank(activityPaymentBinding.etCardNo) || activityPaymentBinding.etCardNo.getText().toString().trim().length() != 19) {
            activityPaymentBinding.etCardNo.setError("Please check the card number");
            return false;
        } else if (validations.isBlank(activityPaymentBinding.etExpiryDate) || (Integer.parseInt(splitDate[0]) < 0 || Integer.parseInt(splitDate[0]) > 12) || (Integer.parseInt(splitDate[1]) < currentYear)) {
            activityPaymentBinding.etExpiryDate.setError("Please check the expiration date");
            return false;
        } else if (validations.isBlank(activityPaymentBinding.etSecurityCode) || activityPaymentBinding.etSecurityCode.getText().toString().length() < 3) {
            activityPaymentBinding.etSecurityCode.setError("Please check the security code");
            return false;
        } else if (validations.isBlank(activityPaymentBinding.etNameOnCard)) {
            activityPaymentBinding.etNameOnCard.setError("Please enter cardholder's name");
            return false;
        } else if (validations.isBlank(activityPaymentBinding.etZipCode)) {
            activityPaymentBinding.etZipCode.setError("Please enter your zipcode");
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("isUserLogin");
            editor.remove("userName");
            editor.apply();

            Intent i = new Intent(PaymentActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(PaymentActivity.this, ViewUserProfileActivity.class);
            i.putExtra("loggedUsernameKey", loggedInUser.getUserName());
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}