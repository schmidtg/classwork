/*************************************************************
* greedy.c
*
* Computer Science 50
* Pset1
* Graham Schmidt
*
* Accept a dollar amount and output the minimum number
* of coins that add up to that amount. 
*
**************************************************************/

#include <cs50.h>
#include <stdio.h>

int to_cents(float amount);
int num_coins(int type_coin, int amount);
int num_remain(int type_coin, int amount);

int main(void)
{
    float amount;
    do
    {
        printf("How much change is owed?\n");
        amount = GetFloat();
    }
    while (amount < 0.0);

    int amount_cents = to_cents(amount);

    // Quarters
    int type_coin = 25;
    int total = num_coins(type_coin, amount_cents);
    amount_cents = num_remain(type_coin, amount_cents);

    // Dimes
    type_coin = 10;
    total = total + num_coins(type_coin, amount_cents);
    amount_cents = num_remain(type_coin, amount_cents);

    // Nickels
    type_coin = 5;
    total = total + num_coins(type_coin, amount_cents);
    amount_cents = num_remain(type_coin, amount_cents);

    // Pennies
    type_coin = 1;
    total = total + num_coins(type_coin, amount_cents);
    amount_cents = num_remain(type_coin, amount_cents);

    printf("Total: %d\n\n", total);

    // Determine how many
    // quarters
    // dimes
    // nickels
    // pennies

    // TODO
    /*
    - test large numbers 1.00039393
    - test large ints
    - test negative floats, ints
    - test words
    - words and ints
    */
}

/*
 * Convert a dollar amount to cents
 * (float to int)
 */
int to_cents(float amount)
{
    float amount_in_cents = amount * 100.0;

    return (int) amount_in_cents;
}

/*
 * Determine how many coins to return
 */
int num_coins(int type_coin, int amount)
{
    int remain = num_remain(type_coin, amount);
    return ( amount - remain ) / type_coin;
}

/*
 * Determine number of coins after largest removed
 */
int num_remain(int type_coin, int amount)
{
    return amount % type_coin;
}
