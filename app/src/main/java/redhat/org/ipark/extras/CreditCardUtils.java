package redhat.org.ipark.extras;

import android.text.TextUtils;
import android.util.Log;
import java.util.Calendar;

import io.card.payment.CardType;

public class CreditCardUtils {
    public static String EXPDATE_PATTERN = "##/##";

    public static String validEditableNumber(String cardNumber) {
        String cleanCardNumber = cardNumber.replaceAll("\\D+", "");
        int length = cleanCardNumber.length();

        CardType cardType = CardType.fromCardNumber(cleanCardNumber);
        if (cardType == CardType.UNKNOWN || cardType == CardType.INSUFFICIENT_DIGITS) {
            if (length <= 16) {
                return cleanCardNumber;
            } else {
                return cleanCardNumber.substring(0, 16);
            }
        } else {
            if (length <= cardType.numberLength()) {
                return cleanCardNumber;
            } else {
                return cleanCardNumber.substring(0, cardType.numberLength());
            }
        }
    }

    public static String formattedCardNumber(String cardNumber) {
        String cleanCardNumber = validEditableNumber(cardNumber);
        StringBuilder stringBuilder = new StringBuilder(cleanCardNumber);

        // Check card type
        String pattern = "";
        CardType cardType = CardType.fromCardNumber(cleanCardNumber);
        switch (cardType) {
            case AMEX:
                pattern = "#### ###### #####";
                break;
            case VISA:
            case MASTERCARD:
            case DISCOVER:
            case JCB:
            case MAESTRO:
                pattern = "#### #### #### ####";
                break;
            case DINERSCLUB:
                pattern = "#### ###### ####";
                break;
            default:
                break;
        }

        if (pattern.isEmpty()) {
            return cleanCardNumber;
        } else {
            for (int i = 0; i < stringBuilder.length(); i ++) {
                char c = pattern.charAt(i);

                if ((c != '#') && (c != stringBuilder.charAt(i))) {
                    stringBuilder.insert(i, c);
                }
            }

            return stringBuilder.toString();
        }
    }

    public static boolean isValidCard(String cardNumber) {
        try {
            String cleanCardNumber = cardNumber.replaceAll("\\D+","");
            if (!TextUtils.isEmpty(cardNumber) && cleanCardNumber.length() >= 4) {
                CardType cardType = CardType.fromCardNumber(cleanCardNumber);
                if (cardType == CardType.UNKNOWN || cardType == CardType.INSUFFICIENT_DIGITS) {
                    return false;
                } else {
                    if (cleanCardNumber.length() == cardType.numberLength()) {
                        int originalCheckDigit = Integer.parseInt(cleanCardNumber.substring(cleanCardNumber.length() - 1));
                        StringBuffer buffer = new StringBuffer(cleanCardNumber.substring(0, cleanCardNumber.length() - 1));
                        String characters = buffer.reverse().toString();

                        int digitSum = 0;
                        for (int idx = 0; idx < characters.length(); idx++) {
                            int value = Integer.parseInt(characters.substring(idx, idx + 1));
                            if (idx % 2 == 0) {
                                int product = value * 2;
                                if (product > 9) {
                                    product = product - 9;
                                }

                                digitSum += product;
                            } else {
                                digitSum += value;
                            }
                        }

                        digitSum = digitSum * 9;
                        int computedCheckDigit = digitSum % 10;

                        boolean valid = originalCheckDigit == computedCheckDigit;
                        return valid;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            Log.d("isValidCard exception: ", e.getLocalizedMessage());
            return false;
        }
    }

    public static String formattedExpDate(String expDate) {
        expDate = expDate.replaceAll("\\D+", "");
        int length = expDate.length();
        String str = expDate;
        if (length > 0 && length < 3) {
            int month = Integer.parseInt(expDate);
            if (length == 1 && month >= 2) {
                str = "0" + month + "/";
            } else if (length == 2 && month <= 12) {
                str = month + "/";
            } else if (length == 2 && month > 12) {
                str = "01/" + expDate.substring(1) ;
            }
        }

        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = 0; i < stringBuilder.length(); i ++) {
            char c = EXPDATE_PATTERN.charAt(i);

            if ((c != '#') && (c != stringBuilder.charAt(i))) {
                stringBuilder.insert(i, c);
            }
        }

        return stringBuilder.toString();
    }

    public static boolean isValidExpDate(String cardValidity) {
        if (!TextUtils.isEmpty(cardValidity) && cardValidity.length() == 5) {
            String month = cardValidity.substring(0, 2);
            String year = cardValidity.substring(3, 5);

            int monthpart = -1, yearpart = -1;

            try {
                monthpart = Integer.parseInt(month) - 1;
                yearpart = Integer.parseInt(year);

                Calendar current = Calendar.getInstance();
                current.set(Calendar.DATE, 1);
                current.set(Calendar.HOUR, 12);
                current.set(Calendar.MINUTE, 0);
                current.set(Calendar.SECOND, 0);
                current.set(Calendar.MILLISECOND, 0);

                Calendar validity = Calendar.getInstance();
                validity.set(Calendar.DATE, 1);
                validity.set(Calendar.HOUR, 12);
                validity.set(Calendar.MINUTE, 0);
                validity.set(Calendar.SECOND, 0);
                validity.set(Calendar.MILLISECOND, 0);

                if (monthpart > -1 && monthpart < 12 && yearpart > -1) {
                    validity.set(Calendar.MONTH, monthpart);
                    validity.set(Calendar.YEAR, yearpart + 2000);
                } else
                    return false;

                Log.d("Util", "isValidDate: " + current.compareTo(validity));

                if (current.compareTo(validity) <= 0)
                    return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean isValidCVV(String cvv, String cardNumber) {
        cvv = cvv.replaceAll("\\D+", "");
        String cleanCardNumber = cardNumber.replaceAll("\\D+","");
        CardType cardType = CardType.fromCardNumber(cleanCardNumber);
        return cvv.length() == cardType.cvvLength();
    }

}
