/*************************************************************************
 * sll.c
 *
 * Implements a simple singly-linked list structure for ints.
 ************************************************************************/

#include <assert.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

// the size of our test list: feel free to adjust as you wish!
#define TEST_SIZE 10

typedef struct node
{
    // the value to store in this node
    int i;

    // the link to the next node in the list
    struct node* next;
}
node;

// declare the first node of our list (as a global variable)
node* first = NULL;

/**
 * Returns the length of the list.
 */
int length(void)
{
    int count = 0;
    node* nodeptr = first;

    // count all elements until end of list
    // advance to tail
    /* while (nodenext->next != NULL) { */
    /*     count++; */
    /*     nodenext = nodenext->next; */
    /* } */

    while (nodeptr != NULL) {
        count++;
        nodeptr = nodeptr->next;
    }

    return count;
}

/**
 * Returns true if a node in the list contains the value i and false
 * otherwise.
 */
bool contains(int i)
{
    node* nodeptr = first;
    while (nodeptr != NULL) {

        // check if i exists in list
        if (nodeptr->i == i)
            return true;

        // check next node
        nodeptr = nodeptr->next;
    }

    // not found
    return false;
}

/**
 * Puts a new node containing i at the front (head) of the list.
 */
void prepend(int i)
{
    // allocate memory for new node
    node* newnode = (node *) malloc(sizeof(node));
    newnode->i = i;

    // insertion at front of list
    newnode->next = first;

    // point first pointer to new node
    first = newnode;
}

/**
 * Puts a new node containing i at the end (tail) of the list.
 */
void append(int i)
{
    // allocate memory for new node
    node *newnode = (node *) malloc(sizeof(node));
    if (newnode == NULL) {
        fprintf(stderr,  "Unable to allocate for new node.\n");
        exit(-1);
    }

    newnode->i = i;
    newnode->next = NULL;

    // check for insertion at front
    if (first == NULL) {
        first = newnode;

    // walk to end
    } else {
        // start at beginning, advance to end
        node *nodenext = first;

        // advance to tail
        while (nodenext->next != NULL) {
            nodenext = nodenext->next;
        }

        // set tail to point to new node
        nodenext->next = newnode;
    }
}

/**
 * Puts a new node containing i at the appropriate position in a list
 * sorted in ascending order.
 */
void insert_sorted(int i)
{
    // allocate memory for new node
    node* newnode = malloc(sizeof(node));
    newnode->i = i;
    newnode->next = NULL;

    // start at beginning (need two pointers)
    node* nodenext = first;
    node* nodeprev = first;

    // count all elements until end of list
    while (nodenext != NULL) {

        // insert
        if (nodenext->i >= i) {
            newnode->next = nodeprev;
            nodeprev->next = newnode;
        }

        // advance
        nodeprev = nodenext;
        nodenext = nodenext->next;
    }
}

/**
 * Implements some simple test code for our singly-linked list.
 */
int main(void)
{
    printf("Prepending ints 0-%d onto the list...", TEST_SIZE);
    for (int i = 0; i < TEST_SIZE; i++)
    {
        prepend(i);
    }
    printf("done!\n");

    printf("Making sure that the list length is indeed %d...", TEST_SIZE);
    assert(length() == TEST_SIZE);
    printf("good!\n");

    printf("Making sure that values are arranged in descending order...");
    node* n = first;
    for (int i = 0; i < TEST_SIZE; i++)
    {
        assert(n != NULL);
        assert(n->i == TEST_SIZE - i - 1);
        n = n->next;
    }
    printf("good!\n");

    printf("Freeing the list...");
    while (first != NULL)
    {
        node* next = first->next;
        free(first);
        first = next;
    }
    printf("done!\n");

    printf("Appending ints 0-%d to the list...", TEST_SIZE);
    for (int i = 0; i < TEST_SIZE; i++)
    {
        append(i);
    }
    printf("done!\n");

    printf("Making sure that the list length is indeed %d...", TEST_SIZE);
    assert(length() == TEST_SIZE);
    printf("good!\n");

    printf("Making sure that values are arranged in ascending order...");
    n = first;
    for (int i = 0; i < TEST_SIZE; i++)
    {
        assert(n != NULL);
        assert(n->i == i);
        n = n->next;
    }
    printf("good!\n");

    printf("Freeing the list...");
    while (first != NULL)
    {
        node* next = first->next;
        free(first);
        first = next;
    }
    printf("done!\n");

    printf("Inserting %d random ints to the list...", TEST_SIZE);
    for (int i = 0; i < TEST_SIZE; i++)
    {
        insert_sorted(rand() % TEST_SIZE);
    }
    printf("done!\n");

    printf("Making sure that the list length is indeed %d...", TEST_SIZE);
    assert(length() == TEST_SIZE);
    printf("good!\n");

    printf("Making sure that values are arranged in sorted order...");
    n = first;
    int prev = 0;
    for (int i = 0; i < TEST_SIZE; i++)
    {
        assert(n != NULL);
        assert(n->i >= prev);
        prev = n->i;
        n = n->next;
    }
    printf("good!\n");

    printf("Freeing the list...");
    while (first != NULL)
    {
        node* next = first->next;
        free(first);
        first = next;
    }
    printf("done!\n");

    printf("\n********\nSuccess!\n********\n");

    return 0;
}
