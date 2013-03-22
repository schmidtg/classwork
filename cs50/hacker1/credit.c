/*************************************************************
*
* credit.c
*
* Computer Science 50
* Pset1 - Hacker
* Graham Schmidt
*
* Checks whether a credit card is valid and reports it as
* AMEX, Visa, or Mastercard
*
**************************************************************/

#include <cs50.h>
#include <stdio.h>
#include <math.h>

int num_digits(long long num);
int sum_digits(int num);

int main()
{
    // get credit number
    long long num;
    do
    {
        printf("Number: ");
        num = GetLongLong();
    }
    while (num < 0);

    // get number of digits in card provided
    int num_d = num_digits(num);

    // ensure CC is correct length
    if (   num_d == 13 
        || num_d == 15
        || num_d == 16
    ) {
        // convert long long to string
        char str[num_d];
        sprintf(str, "%llu", num);

        // store first and second numbers
        char first_num, second_num;
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                first_num = str[i];
            }
            if (i == 1) {
                second_num = str[i];
            }
        }

        // determine the start digits based on length
        int start_digit, second_digit;
        if (num_d % 2 == 0) {
            start_digit = 0;
            second_digit = 1;
        } else {
            start_digit = 1;
            second_digit = 0;
        }

        // add sum of digits products
        int total_sum = 0;
        for (int j = start_digit; j < num_d; j += 2) {
            // subtract ASCII '0' (48) to get 
            // int value and left-shift by 1
            total_sum += sum_digits((str[j] - '0') << 1);
        }

        // add sum of those digits not multiplied
        int sum_not_multiplied = 0;
        for (int j = second_digit; j < num_d; j += 2) {
            sum_not_multiplied += (str[j] - '0');
        }

        // add sums together
        int sum_total = total_sum + sum_not_multiplied;

        // check last digit is '0'
        if (sum_total % 10 == 0) {

            switch (first_num) {
                // AMEX
                case '3':
                        if (second_num == '4'
                            || second_num == '7' 
                        ) {
                            printf("AMEX\n");
                        }
                        break;

                // VISA
                case '4':
                        printf("VISA\n");
                        break;

                // MASTERCARD
                case '5':
                        if ((int) second_num > (int) '0'
                            && (int) second_num < (int) '6' 
                        ) {
                            printf("MASTERCARD\n");
                        }
                        break;

                // OTHER CARD
                default:
                        printf("OTHER\n");
                        break;
                    
            }

        // not a valid card
        } else {
            printf("INVALID\n");
        }

    // not a valid card (greater than 16 digits)
    } else {
        printf("INVALID\n");
    }
    
    return 0;
}

/*
 * Determine number of digits in long num
 */

int num_digits(long long num)
{
    int length = 1;

    // reduce by 10 until less than 9
    while (num > 9) {
        length++;
        num = num / 10;
    }

    return length;
}

/*
 * Calculate the sum of the products' digits
 */
int sum_digits(int num)
{
    if (num > 9) {
        // cast to floats
        float num_f = (float) num / 10.0;

        // get the second digit
        num_f = num_f - 1.0;
        num_f = num_f * 10.0;

        return 1 + roundf(num_f);

    // numbers 1 - 9
    } else {
        return num;
    }
}
