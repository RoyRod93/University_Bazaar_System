package com.team8.universitybazaar.misc;

import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class Validations {

    /**
     * Verify Blank Fields
     */
    public boolean isBlank(String string) {
        return string.trim().equals("");
    }

    /**
     * @param string
     * @return return true if this is null otherwise false
     */
    public boolean isBlank(EditText string) {
        return string.getText().toString().trim().equals("");
    }

    /**
     * @param string
     * @return return true if this is null otherwise false
     * For checking if the TextInputEditText is empty or not
     */
    public boolean isBlankTIET(TextInputEditText string) {
        return string.getText().toString().trim().equals("");
    }

    public boolean isBlankText(TextView string) {
        return string.getText().toString().trim().equals("");
    }

    /**
     * Verify Full Name
     */
    public boolean isValidFullName(EditText fullName) {
//        return Pattern.compile("^[\\p{L} .'-]+$", Pattern.CASE_INSENSITIVE).matcher(fullName.getText().toString().trim()).matches();
        return Pattern.compile("^[A-Z][a-zA-Z]{1,}(?: [A-Z][a-zA-Z]*){0,1}$", Pattern.CASE_INSENSITIVE).matcher(fullName.getText().toString().trim()).matches();
    }

    public boolean isValidAlphabets(EditText fullName) {
//        return Pattern.compile("^[\\p{L} .'-]+$", Pattern.CASE_INSENSITIVE).matcher(fullName.getText().toString().trim()).matches();
        return Pattern.compile("^[a-zA-Z ]+$", Pattern.CASE_INSENSITIVE).matcher(fullName.getText().toString().trim()).matches();
    }



    /*public boolean isValidWebsite(EditText website)
    {
        return Pattern.compile("^[\\p{L} .'-]+$", Pattern.CASE_INSENSITIVE).matcher(fullName.getText().toString().trim()).matches();
        return Pattern.compile("@\"^http(s)?://([\\w-]+.)+[\\w-]+(/[\\w- ./?%&=])?$", Pattern.CASE_INSENSITIVE).matcher(website.getText().toString().trim()).matches();
    }*/

    /**
     * Match Passwords
     */
    public boolean isMatching(EditText stringFirst, EditText stringSecond) {
        return stringFirst.getText().toString().trim().equals(stringSecond.getText().toString().trim());
    }

    /**
     * Email Validations
     */
    public boolean isValidEmail(EditText email) {
        String PATTERN_EMAIL = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        return Pattern.compile(PATTERN_EMAIL, Pattern.CASE_INSENSITIVE).matcher(email.getText().toString().trim()).matches();
    }

    /**
     * Verify City, State, Country
     */
    public boolean isValidLocation(EditText location) {
        return Pattern.compile("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)", Pattern.CASE_INSENSITIVE).matcher(location.getText().toString().trim()).matches();
    }

    /**
     * Check Postal Location
     */
    public boolean isValidPostalAddress(EditText postalAddress) {
        return Pattern.compile("^[a-zA-Z0-9_\\s\\.,]{1,}$", Pattern.CASE_INSENSITIVE).matcher(postalAddress.getText().toString().trim()).matches();
    }

    /**
     * Check Valid Date
     */
    public boolean isValidDate(EditText date) {
        return Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)", Pattern.CASE_INSENSITIVE).matcher(date.getText().toString().trim()).matches();
    }
//(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)

    /**
     * Check Valid NumberHelper
     */
    public boolean isValidNumber(EditText editText) {
        return Pattern.compile("((\\d{1,9})(((\\.)(\\d{0,2})){0,1}))", Pattern.CASE_INSENSITIVE).matcher(editText.getText().toString().trim()).matches();
    }

    /**
     * Check Valid Phone NumberHelper
     */
    /*public boolean isValidPhoneNumber(EditText editText)
    {
        return Pattern.compile("\"^[7-9][0-9]{9}$\"", Pattern.CASE_INSENSITIVE).matcher(editText.getText().toString().trim()).matches();
    }*/

    /**
     * checck valid mobile no
     *
     * @param editText
     * @return
     */
    public boolean isValidPhone(EditText editText) {
        return Pattern.compile("\\d{10}").matcher(editText.getText().toString().trim()).matches();
    }

    public boolean isValidPassword(EditText editText) {
        return Pattern.compile("^.{6,}$").matcher(editText.getText().toString().trim()).matches();
    }


    public boolean isValidPincode(EditText editText) {
        return Pattern.compile("\\d{5}").matcher(editText.getText().toString().trim()).matches();
    }
}
